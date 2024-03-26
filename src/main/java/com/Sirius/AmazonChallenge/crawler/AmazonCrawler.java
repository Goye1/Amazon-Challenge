package com.Sirius.AmazonChallenge.crawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class AmazonCrawler {

    private static final Set<String> IRRELEVANT_WORDS = new HashSet<>(Arrays.asList("a", "the", "is", "and", "to", "this", "for", "in", "it"));

    public CompletableFuture<String> crawlProductAsync(String productUrl) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Creando nuevo hilo: " + Thread.currentThread().getName());
           String productDescription = crawlProduct(productUrl);
            //String productDescription = "Enjoy The Creative Life with the TCL 40\" 1080p direct LED HDTV. It delivers premium picture quality and tremendous value in a sophisticated slim frame design perfect for bringing entertainment to any space. This flat screen LED HDTV features High Definition 1080p resolution for a sharper image and TCL True Color Technology for brilliant color and contrast. With direct LED backlighting, view darker blacks and luminous brightness while maintaining the best standards in energy efficiency.";
            return filterWords(productDescription);
        });
    }

    public String crawlProduct(String productUrl) {
        try {
            Document document = Jsoup.connect(productUrl).get();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String filterWords(String description) {
        String[] words = description.split("\\s+");
        List<String> filteredWords = new ArrayList<>();
        for (String word : words) {
            // Filtrar palabras que contienen solo letras y no son palabras irrelevantes
            if (word.matches("[a-zA-Z]+") && !IRRELEVANT_WORDS.contains(word.toLowerCase())) {
                filteredWords.add(word);
            }
        }
        return String.join(" ", filteredWords);
    }
}
