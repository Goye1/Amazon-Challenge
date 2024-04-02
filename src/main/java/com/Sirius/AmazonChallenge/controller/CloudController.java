package com.Sirius.AmazonChallenge.controller;
import com.Sirius.AmazonChallenge.service.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/wordcloud")
public class CloudController {
    @Autowired
    private CloudService cloudService;

    @CrossOrigin(origins = "*")
    @GetMapping("/generateWordCloud")
    public CompletableFuture<ResponseEntity<Map<String, Integer>>> generateWordCloud(@RequestParam String productUrl) {
        return cloudService.generateWordCloudAsync(productUrl)
                .thenApply(wordFrequencies -> new ResponseEntity<>(wordFrequencies, HttpStatus.OK));
    }


}