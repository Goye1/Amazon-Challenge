package com.Sirius.AmazonChallenge.controller;

import com.Sirius.AmazonChallenge.crawler.AmazonCrawler;
import com.Sirius.AmazonChallenge.generator.CloudGenerator;
import com.Sirius.AmazonChallenge.service.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/wordcloud")
public class CloudController {
    @Autowired
    private CloudService cloudService;
    @Autowired
    private AmazonCrawler crawler;

    @CrossOrigin(origins = "*")
    @PostMapping("/generateWordCloud")
    public CompletableFuture<ResponseEntity<Map<String, Integer>>> generateWordCloud(@RequestParam String productUrl) {
        return cloudService.generateWordCloudAsync(productUrl)
                .thenApply(wordFrequencies -> new ResponseEntity<>(wordFrequencies, HttpStatus.OK));
    }


}