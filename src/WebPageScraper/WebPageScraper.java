package WebPageScraper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WebPageScraper {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Input the URL: > ");
        String url = scanner.nextLine().trim();

        List<String> savedArticles = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .get();

            Elements articles = doc.select("article");

            for (Element article : articles) {

                Element typeTag = article.selectFirst("span[data-test='article.type']");
                if (typeTag == null) continue;

                String type = typeTag.text().trim();
                if (!type.equals("News")) continue;  // беремо лише News

                Element linkTag = article.selectFirst("a[data-track-action='view article']");
                if (linkTag == null) continue;

                String articleUrl = "https://www.nature.com" + linkTag.attr("href");

                Document articleDoc = Jsoup.connect(articleUrl)
                        .userAgent("Mozilla/5.0")
                        .get();

                Element body = articleDoc.selectFirst("div[class*=body]");
                if (body == null) continue;

                String articleText = body.text().trim();
                if (articleText.isEmpty()) continue;

                String title = article.selectFirst("a[data-track-action='view article']").text();

                String fileName = title.replaceAll("[\\p{Punct}]", "")
                        .replaceAll(" ", "_")
                        .replaceAll("_+", "_")
                        .trim() + ".txt";

                // Записуємо файл UTF-8 у бінарному режимі
                try (FileOutputStream fos = new FileOutputStream(fileName)) {
                    fos.write(articleText.getBytes(StandardCharsets.UTF_8));
                }

                savedArticles.add(fileName);
            }

            System.out.println("Saved articles: " + savedArticles);

        } catch (IOException e) {
            System.out.println("Error: cannot load page.");
        }
    }
}
