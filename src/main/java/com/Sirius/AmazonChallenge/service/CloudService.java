package com.Sirius.AmazonChallenge.service;

import com.Sirius.AmazonChallenge.crawler.AmazonCrawler;
import com.Sirius.AmazonChallenge.generator.CloudGenerator;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CloudService {

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Autowired
    private final AmazonCrawler crawler;
    @Autowired
    private final CloudGenerator generator;

    public CloudService(AmazonCrawler crawler, CloudGenerator generator) {
        this.crawler = crawler;
        this.generator = generator;
    }

    @Cacheable(value = "wordCloudCache", key = "#productUrl")
    public CompletableFuture<Map<String, Integer>> generateWordCloudAsync(String productUrl) {
        return crawler.crawlProductAsync(productUrl)
                .thenCompose(description -> CompletableFuture.supplyAsync(() -> generator.generateWordCloud(description), executorService));
    }
    @PreDestroy
    public void preDestroy() {
        executorService.shutdown();
    }

}





