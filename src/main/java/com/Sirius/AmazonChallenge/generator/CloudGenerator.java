package com.Sirius.AmazonChallenge.generator;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CloudGenerator {

    private static final Set<String> IRRELEVANT_WORDS = new HashSet<>(Arrays.asList("a", "the", "is", "and", "to", "this", "for", "in", "it", "of", "with", "it","an","at", "can"));
    public Map<String, Integer> generateWordCloud(String productDescription) {
        String filteredDescription = filterWords(productDescription);
        HashMap<String, Integer> wordFrequencies = new HashMap<>();
        String[] words = filteredDescription.toLowerCase().split("\\W+");
        //For each word it gets its frequency(value) through the key(word) and adds 1 to it, if word not found, just 0.
        for (String word : words) {
            if (!word.isEmpty()) { // \\W will prob. leave empty spaces.
                wordFrequencies.put(word, wordFrequencies.getOrDefault(word, 0) + 1);
            }
        }
        return wordFrequencies;
    }

    // Divide, replace and filter punctuation and irrelevant words.
    private String filterWords(String description) {
        String noPunctuationDescription = description.replaceAll("[\\p{Punct}\\s]+", " ");
        return Arrays.stream(noPunctuationDescription.split("\\s+"))
                .filter(word -> word.matches("[a-zA-Z]{2,}") && !IRRELEVANT_WORDS.contains(word.toLowerCase()))
                .collect(Collectors.joining(" "));
    }



}