package com.Sirius.AmazonChallenge.service;

import com.Sirius.AmazonChallenge.crawler.AmazonCrawler;
import com.Sirius.AmazonChallenge.generator.CloudGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CloudService {

    @Autowired
    private AmazonCrawler crawler;
    @Autowired
    private CloudGenerator generator;



    public Map<String, Integer> generateWordCloud(String productUrl) throws Exception {
        if (productUrl == null || productUrl.isEmpty()) {
            throw new IllegalArgumentException("The product URL is mandatory");
        }
        String productDescription = crawler.crawlProductAsync(productUrl).get();
        Map<String,Integer> wordFrequencies = generator.generateWordCloud(productDescription);
        System.out.println(wordFrequencies);
        return wordFrequencies;
    }





}
