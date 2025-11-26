package WebPageScraper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Scanner;

public class WebPageScraper {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input the URL: > ");
        String url = scanner.nextLine().trim();

        // Перевірка, що сторінка містить "title"
        if (!url.contains("imdb.com/title/")) {
            System.out.println("Invalid movie page!");
            return;
        }

        try {
            Connection connection = Jsoup.connect(url);
            connection.header("Accept-Language", "en-US,en;q=0.5");

            Document doc = connection.get();

            String title = doc.title();
            if (title == null || title.isEmpty()) {
                System.out.println("Invalid movie page!");
                return;
            }

            Element metaDescription = doc.select("meta[name=description]").first();

            if (metaDescription == null) {
                System.out.println("Invalid movie page!");
                return;
            }

            String description = metaDescription.attr("content");

            if (description == null || description.isEmpty()) {
                System.out.println("Invalid movie page!");
                return;
            }

            System.out.println(title);
            System.out.println(description);

        } catch (IOException e) {
            System.out.println("Invalid movie page!");
        }
    }
}

