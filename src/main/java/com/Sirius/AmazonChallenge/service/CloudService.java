package com.Sirius.AmazonChallenge.service;
import com.Sirius.AmazonChallenge.crawler.AmazonCrawler;
import com.Sirius.AmazonChallenge.generator.CloudGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CloudService {

    private final ExecutorService executorService = Executors.newFixedThreadPool(10); //Specifies and creates a max of 10 threads.

    @Autowired
    private AmazonCrawler crawler;
    @Autowired
    private CloudGenerator generator;

    @Cacheable(value = "wordCloudCache", key = "#productUrl") //Returns product description if url has already been processed.
    public CompletableFuture<Map<String, Integer>> generateWordCloudAsync(String productUrl) {
        //Waits for crawler to return description, then returns a word frequencies map.
        return crawler.crawlProductAsync(productUrl)
                .thenCompose(description -> CompletableFuture.supplyAsync(() -> generator.generateWordCloud(description), executorService));
    }
}





