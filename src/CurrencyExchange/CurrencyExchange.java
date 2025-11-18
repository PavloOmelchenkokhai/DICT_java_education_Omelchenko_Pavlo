import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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

            System.out.print("Enter Currency 1 code (Base, e.g., USD): ");
            String currency1Code = scanner.nextLine().trim().toUpperCase(Locale.ROOT);

            System.out.print("Enter Currency 2 code (Target, e.g., EUR): ");
            String currency2Code = scanner.nextLine().trim().toUpperCase(Locale.ROOT);

            System.out.print("Enter Amount to convert: ");
            double amount = 0.0;
            try {
                amount = Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.err.println("Invalid amount entered. Please enter a number.");
                return;
            } finally {
                scanner.close();
            }

            String url = "http://www.floatrates.com/daily/" + currency1Code.toLowerCase(Locale.ROOT) + ".json";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .timeout(java.time.Duration.ofSeconds(10))
                    .build();

            try {
                HttpResponse<String> response = executeRequestWithRetry(client, request);

                if (response.statusCode() != 200) {
                    System.err.printf("HTTP Error: Failed to retrieve data for %s. Status code: %d\n", currency1Code, response.statusCode());
                    return;
                }

                String jsonResponse = response.body();

                double rate = extractRate(jsonResponse, currency2Code, currency1Code);

                double convertedAmount = 0.0;

                if (rate > 0) {
                    convertedAmount = amount * rate;
                } else {
                    System.err.printf("Error: Rate for %s not found in relation to %s.\n", currency2Code, currency1Code);
                    return;
                }

                System.out.println("\n> " + currency1Code);
                System.out.println("> " + currency2Code);
                System.out.println("> " + String.format(Locale.ROOT, "%.0f", amount));
                System.out.printf(Locale.ROOT, "%.12f\n", convertedAmount);

            } catch (IOException e) {
                System.err.println("I/O Error or network problem. Check connection.");
            } catch (InterruptedException e) {
                System.err.println("Request was interrupted.");
                Thread.currentThread().interrupt();
            } catch (IllegalArgumentException e) {
                System.err.println("Error: Invalid URL format.");
            }
        }

    }