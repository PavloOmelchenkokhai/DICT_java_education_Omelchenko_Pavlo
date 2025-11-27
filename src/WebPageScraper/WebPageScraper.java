package WebPageScraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class WebPageScraper {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int pages = Integer.parseInt(scanner.nextLine().trim());

        String articleType = scanner.nextLine().trim();

        String baseUrl = "https://www.nature.com/nature/articles?sort=PubDate&year=2023&page=";

        try {
            for (int page = 1; page <= pages; page++) {

                String url = baseUrl + page;

                String dirName = "Page_" + page;
                File dir = new File(dirName);
                if (!dir.exists()) dir.mkdir();

                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0")
                        .get();

                Elements articles = doc.select("article");

                for (Element article : articles) {

                    Element typeTag = article.selectFirst("span[data-test=article.type]");
                    if (typeTag == null) continue;

                    String type = typeTag.text().trim();

                    if (!type.equalsIgnoreCase(articleType)) continue;

                    Element linkTag = article.selectFirst("a[data-track-action='view article']");
                    if (linkTag == null) continue;

                    String articleUrl = "https://www.nature.com" + linkTag.attr("href");

                    Document articleDoc = Jsoup.connect(articleUrl)
                            .userAgent("Mozilla/5.0")
                            .get();

                    Element body = articleDoc.selectFirst("div[class*=body]");

                    if (body == null)
                        body = articleDoc.selectFirst("div[itemprop=articleBody]");

                    if (body == null) continue;

                    String articleText = body.text().trim();
                    if (articleText.isEmpty()) continue;

                    String title = linkTag.text();

                    String fileName = title
                            .replaceAll("[^a-zA-Z0-9 ]", "") // видаляємо розділові знаки
                            .replaceAll(" ", "_")
                            .replaceAll("_+", "_")
                            .trim() + ".txt";

                    File outFile = new File(dir, fileName);

                    try (FileOutputStream fos = new FileOutputStream(outFile)) {
                        fos.write(articleText.getBytes(StandardCharsets.UTF_8));
                    }
                }
            }

            System.out.println("Saved all articles");

        } catch (IOException e) {
            System.out.println("Error while loading pages.");
        }
    }
}
