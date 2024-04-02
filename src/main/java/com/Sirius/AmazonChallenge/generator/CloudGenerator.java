package com.Sirius.AmazonChallenge.generator;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CloudGenerator {

    private static final Set<String> IRRELEVANT_WORDS = new HashSet<>(Arrays.asList("a", "the", "is", "and", "to", "this", "for", "in", "it", "of", "with", "it"));
    public Map<String, Integer> generateWordCloud(String productDescription) {
        String filteredDescription = filterWords(productDescription);
        Map<String, Integer> wordFrequencies = new HashMap<>();
        String[] words = filteredDescription.toLowerCase().split("\\W+");
        //Add 1 for each time a word appears.
        for (String word : words) {
            if (!word.isEmpty()) {
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