package com.Sirius.AmazonChallenge.service;

import com.Sirius.AmazonChallenge.crawler.AmazonCrawler;
import com.Sirius.AmazonChallenge.generator.CloudGenerator;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CloudService {

    private final ExecutorService executorService = Executors.newFixedThreadPool(10); // Ejemplo: 10 hilos en el pool

    @Autowired
    private AmazonCrawler crawler;
    @Autowired
    private CloudGenerator generator;

    public CompletableFuture<Map<String, Integer>> generateWordCloudAsync(String productUrl) {
        return crawler.crawlProductAsync(productUrl)
                .thenCompose(description -> CompletableFuture.supplyAsync(() -> generator.generateWordCloud(description), executorService));
    }
    @PreDestroy
    public void preDestroy() {
        executorService.shutdown();
    }

}





