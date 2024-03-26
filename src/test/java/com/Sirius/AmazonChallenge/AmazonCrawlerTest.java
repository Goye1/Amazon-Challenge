package com.Sirius.AmazonChallenge;

import com.Sirius.AmazonChallenge.crawler.AmazonCrawler;
import com.Sirius.AmazonChallenge.generator.CloudGenerator;
import com.Sirius.AmazonChallenge.service.CloudService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AmazonCrawlerTest {

    @Autowired
    private CloudService cloudService;



    @Test
    public void testGenerator() throws Exception {
        cloudService.generateWordCloudAsync("https://www.amazon.com/gp/product/B0CL5KNB9M");

    }
}