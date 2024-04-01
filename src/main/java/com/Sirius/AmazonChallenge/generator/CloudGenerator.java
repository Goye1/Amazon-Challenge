package com.Sirius.AmazonChallenge.generator;
import com.Sirius.AmazonChallenge.crawler.AmazonCrawler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CloudGenerator {

    protected static final Logger logger = LogManager.getLogger();
    private static final Set<String> IRRELEVANT_WORDS = new HashSet<>(Arrays.asList("a", "the", "is", "and", "to", "this", "for", "in", "it", "of"));
    public Map<String, Integer> generateWordCloud(String productDescription) {
        long startTime = System.nanoTime();
        String filteredDescription = filterWords(productDescription);
        Map<String, Integer> wordFrequencies = new HashMap<>();
        // Convertir la descripción filtrada a minúsculas y dividirla en palabras, manejando la puntuación
        String[] words = filteredDescription.toLowerCase().split("\\W+");
        for (String word : words) {
            if (!word.isEmpty()) { // Asegurarse de que la palabra no esté vacía
                wordFrequencies.put(word, wordFrequencies.getOrDefault(word, 0) + 1);
            }
        }
        long endTime = System.nanoTime();
        long durationInNano = endTime - startTime;
        double durationInMilli = durationInNano / 1_000_000.0; // Convertir nanosegundos a milisegundos
        logger.info("WordCloud generada en {} ms.", durationInMilli);
        return wordFrequencies;
    }



    private String filterWords(String description) {
        // Eliminar la puntuación de la descripción
        String noPunctuationDescription = description.replaceAll("[\\p{Punct}\\s]+", " ");
        return Arrays.stream(noPunctuationDescription.split("\\s+"))
                .filter(word -> word.matches("[a-z]+") && !IRRELEVANT_WORDS.contains(word))
                .collect(Collectors.joining(" "));
    }



}