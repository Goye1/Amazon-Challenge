package com.Sirius.AmazonChallenge.crawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
@Component
public class AmazonCrawler {
    private static final String SCRAPER_API_KEY = "81f389089361e3b543d9fa512a16a04e"; // Specific ScraperAPI Key.

    public CompletableFuture<String> crawlProductAsync(String productUrl) {
        return CompletableFuture.supplyAsync(() -> {
            return crawlProduct(productUrl);
        });
    }

    public String crawlProduct(String productUrl) {
        if(!productUrl.contains("amazon")){
            return null;
        }
        try {
            Document document = Jsoup.connect(productUrl).get();
            if (isCaptchaPresent(document)) {      // If captcha appears, scrapes prod. description with an API.
                return scrapeWithScraperAPI(productUrl);
            }
            return extractProductDescription(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private String scrapeWithScraperAPI(String productUrl) throws Exception {
        String scraperAPIUrl = "http://api.scraperapi.com?api_key=" + SCRAPER_API_KEY + "&url=" + productUrl;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(scraperAPIUrl))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        Document document = Jsoup.parse(response.body());
        System.out.println("Scraped with ScraperAPI");
        return extractProductDescription(document);
    }

    private String extractProductDescription(Document document) {
        Element productDescriptionDiv = document.getElementById("productDescription");
        if (productDescriptionDiv != null) {
            Elements paragraphs = productDescriptionDiv.select("p");
            StringBuilder description = new StringBuilder();
            for (Element paragraph : paragraphs) {  //Product description might have two <p>, two descriptions.
                Element span = paragraph.selectFirst("span");
                if (span != null) {
                    description.append(span.text()).append(" ");
                }
            }
            return description.toString();
        }
        return null;
    }

    // Looks for specific <div> that only appears when captcha is present.
    public Boolean isCaptchaPresent(Document document){
        Elements captchaDiv = document.select("div.a-box.a-alert.a-alert-info.a-spacing-base");
        return !captchaDiv.isEmpty();
    }


}
