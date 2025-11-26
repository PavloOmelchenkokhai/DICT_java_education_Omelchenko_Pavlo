package WebPageScraper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class WebPageScraper {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input the URL: > ");
        String url = scanner.nextLine().trim();

        try {
            Connection.Response response = Jsoup.connect(url)
                    .ignoreHttpErrors(true)
                    .execute();

            int statusCode = response.statusCode();

            if (statusCode != 200) {
                System.out.println("The URL returned " + statusCode + "!");
                return;
            }

            byte[] contentBytes = response.bodyAsBytes();

            try (FileOutputStream fos = new FileOutputStream("source.html")) {
                fos.write(contentBytes);
            }

            System.out.println("Content saved.");

        } catch (IOException e) {
            System.out.println("The URL returned 404!"); // або інша помилка доступу
        }
    }
}
