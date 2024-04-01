### AmazonChallenge

## Description
***AmazonChallenge*** is a project that implements a service for generating word clouds based on product descriptions from Amazon. This service leverages *CompletableFuture* to perform asynchronous operations, improving the application's efficiency and scalability. Through a crawler, the description of an Amazon product is obtained, and a word cloud generator processes this description to calculate word frequencies and sends it to the Front-End.

**Amazon Crawler:** A component that fetches the description of an Amazon product based on a product URL.\

**Word Cloud Generator:** A component that processes a product description to calculate word frequencies.\

**Word Cloud Service:** A service that uses the crawler and generator to fetch a product description and generate a word cloud asynchronously, taking advantage of CompletableFuture.
