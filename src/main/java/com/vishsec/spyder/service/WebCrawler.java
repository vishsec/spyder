package com.vishsec.spyder.service;

import com.vishsec.spyder.model.PageData;
import com.vishsec.spyder.repository.PageDataRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class WebCrawler {


    private final PageDataRepository repository;

    private final List<InetSocketAddress> proxyList = Arrays.asList(
            new InetSocketAddress("123.45.67.89", 8080),
            new InetSocketAddress("111.222.333.444", 3128)
            // Add more...
    );

    private final List<String> userAgents = Arrays.asList(
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/114.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 Safari/605.1.15",
            "Mozilla/5.0 (X11; Linux x86_64) Firefox/89.0"
            // Add more...
    );

    private final Random random = new Random();

    public WebCrawler(PageDataRepository repository){
        this.repository = repository;
    }

    public PageData crawl(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        String title = doc.title();
        String description = Optional.ofNullable(doc.select("meta[name=description]").attr("content")).orElse("");
        List<String> internalLinks = doc.select("a[href]")
                .stream()
                .map(link -> link.absUrl("href"))
                .filter(link -> link.startsWith(url)) // basic filter for internal
                .distinct()
                .collect(Collectors.toList());

        PageData data = PageData.builder()
                .url(url)
                .title(title)
                .description(description)
                .internalLinks(internalLinks)
                .build();

        return repository.save(data);
    }
}

