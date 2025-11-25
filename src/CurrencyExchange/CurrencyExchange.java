import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import java.util.HashMap;

public class CurrencyExchange {

    private static HttpResponse<String> executeRequestWithRetry (HttpClient client, HttpRequest request)
            throws IOException, InterruptedException {

        final int MAX_RETRIES = 3;
        long delay = 1;

        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            try {
                return client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                if (attempt < MAX_RETRIES - 1) {
                    TimeUnit.SECONDS.sleep(delay);
                    delay *= 2;
                } else {
                    throw e;
                }
            }
        }
        throw new IllegalStateException("Request execution failed after multiple attempts.");
    }

    private static double extractRate (String jsonResponse, String currencyKey, String baseCurrencyCode){
        if (currencyKey.equalsIgnoreCase(baseCurrencyCode)) {
            return 1.0;
        }

        String rateSearchPrefix = "\"" + currencyKey.toLowerCase(Locale.ROOT) + "\":";
        int currencyStart = jsonResponse.indexOf(rateSearchPrefix);

        if (currencyStart == -1) {
            return -1.0;
        }

        int ratePrefixIndex = jsonResponse.indexOf("\"rate\":", currencyStart);

        if (ratePrefixIndex == -1) {
            return -1.0;
        }

        int valueStart = ratePrefixIndex + "\"rate\":".length();

        int valueEnd = valueStart;
        while (valueEnd < jsonResponse.length()) {
            char c = jsonResponse.charAt(valueEnd);
            if (Character.isDigit(c) || c == '.' || c == 'E' || c == 'e' || c == '-' || c == '+') {
                valueEnd++;
            } else {
                break;
            }
        }

        if (valueEnd > valueStart) {
            String rateString = jsonResponse.substring(valueStart, valueEnd).trim();
            try {
                return Double.parseDouble(rateString);
            } catch (NumberFormatException e) {
                return -1.0;
            }
        }

        return -1.0;
    }

    public static void main (String[]args){
        Scanner scanner = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();

        System.out.print("Enter Base Currency Code (e.g., ILS, USD): ");
        String baseCurrencyCode = scanner.nextLine().trim().toUpperCase(Locale.ROOT);

        Map<String, Double> ratesCache = new HashMap<>();
        ratesCache.put(baseCurrencyCode, 1.0);

        System.out.println("\nStarting interactive conversion (enter empty line to exit).");

        while (true) {
            double amount;

            System.out.print("> ");
            String targetCurrencyCode = scanner.nextLine().trim().toUpperCase(Locale.ROOT);

            if (targetCurrencyCode.isEmpty()) {
                break;
            }

            System.out.print("> ");
            String amountInput = scanner.nextLine().trim();

            try {
                amount = Double.parseDouble(amountInput);
            } catch (NumberFormatException e) {
                System.err.println("Invalid amount entered. Please enter a number.");
                continue;
            }

            double rateToUse = -1.0;

            System.out.println("Checking the cache...");

            if (ratesCache.containsKey(targetCurrencyCode)) {
                rateToUse = ratesCache.get(targetCurrencyCode);
                System.out.println("It is in the cache!");

            } else {
                System.out.println("Sorry, but it is not in the cache!");

                String url = "http://www.floatrates.com/daily/" + baseCurrencyCode.toLowerCase(Locale.ROOT) + ".json";
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Accept", "application/json")
                        .timeout(java.time.Duration.ofSeconds(10))
                        .build();

                try {
                    HttpResponse<String> response = executeRequestWithRetry(client, request);

                    if (response.statusCode() != 200) {
                        System.err.printf("HTTP Error: Failed to retrieve data for %s. Status code: %d\n", baseCurrencyCode, response.statusCode());
                        continue;
                    }

                    String jsonResponse = response.body();

                    String[] mandatoryCurrencies = {"USD", "EUR"};
                    for (String mandatory : mandatoryCurrencies) {
                        if (!ratesCache.containsKey(mandatory)) {
                            double mandatoryRate = extractRate(jsonResponse, mandatory, baseCurrencyCode);
                            if (mandatoryRate > 0) {
                                ratesCache.put(mandatory, mandatoryRate);
                            }
                        }
                    }

                    double fetchedTargetRate = extractRate(jsonResponse, targetCurrencyCode, baseCurrencyCode);

                    if (fetchedTargetRate > 0) {
                        ratesCache.put(targetCurrencyCode, fetchedTargetRate);
                        rateToUse = fetchedTargetRate;
                    } else {
                        System.err.printf("Error: Rate for %s not found in the response for %s.\n", targetCurrencyCode, baseCurrencyCode);
                        continue;
                    }

                } catch (IOException e) {
                    System.err.println("I/O Error or network problem. Skipping conversion.");
                    continue;
                } catch (InterruptedException e) {
                    System.err.println("Request was interrupted. Skipping conversion.");
                    Thread.currentThread().interrupt();
                    continue;
                }
            }

            if (rateToUse > 0) {
                double convertedAmount = amount * rateToUse;
                System.out.printf("You received %.2f %s.\n", convertedAmount, targetCurrencyCode);
            }
        }

        scanner.close();
    }
}