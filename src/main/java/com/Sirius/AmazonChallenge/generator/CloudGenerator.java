package com.Sirius.AmazonChallenge.generator;
import com.Sirius.AmazonChallenge.crawler.AmazonCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CloudGenerator{

    @Async
    public Map<String, Integer> generateWordCloud(String productDescription) {
        Map<String, Integer> wordFrequencies = new HashMap<>();
        String[] words = productDescription.split("\\s+");
        for (String word : words) {
            wordFrequencies.put(word, wordFrequencies.getOrDefault(word, 0) + 1);
        }
        return wordFrequencies;
    }
}
