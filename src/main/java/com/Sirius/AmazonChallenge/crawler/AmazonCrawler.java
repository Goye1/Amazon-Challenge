package com.Sirius.AmazonChallenge.crawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
public class AmazonCrawler {


    private static final String SCRAPER_API_KEY = "81f389089361e3b543d9fa512a16a04e";

    public CompletableFuture<String> crawlProductAsync(String productUrl) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Creando nuevo hilo: " + Thread.currentThread().getName());
            return crawlProduct(productUrl);
        });
    }


    public String crawlProduct(String productUrl) {
        if(!productUrl.contains("amazon")){
            return null;
        }
        try {
            Document document = Jsoup.connect(productUrl).get();
            if (isCaptchaPresent(document)) {
                return scrapeWithScraperAPI(productUrl);
            }
            System.out.println("Informacion extraida con JSoup");
            return extractProductDescription(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String scrapeWithScraperAPI(String productUrl) throws Exception {
        String scraperAPIUrl = "http://api.scraperapi.com?api_key=" + SCRAPER_API_KEY + "&url=" + productUrl;
        URL url = new URL(scraperAPIUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        Document document = Jsoup.parse(response.toString());
        System.out.println("Informacion extraida por ScraperAPI");
        return extractProductDescription(document);
    }

    private String extractProductDescription(Document document) {
        Element productDescriptionDiv = document.getElementById("productDescription");
        if (productDescriptionDiv != null) {
            Elements paragraphs = productDescriptionDiv.select("p");
            StringBuilder description = new StringBuilder();
            for (Element paragraph : paragraphs) {
                Element span = paragraph.selectFirst("span");
                if (span != null) {
                    description.append(span.text()).append(" ");
                }
            }
            return description.toString();
        }
        return null;
    }

    public Boolean isCaptchaPresent(Document document){
        Elements captchaDiv = document.select("div.a-box.a-alert.a-alert-info.a-spacing-base");
        return !captchaDiv.isEmpty();
    }


}
