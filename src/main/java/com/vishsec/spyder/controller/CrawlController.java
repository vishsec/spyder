package com.vishsec.spyder.controller;
import com.vishsec.spyder.model.PageData;
import com.vishsec.spyder.service.WebCrawler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;

@RestController
@RequestMapping("/api/crawl")
public class CrawlController {

    private final WebCrawler crawlerService;

    public CrawlController(WebCrawler crawlerService) {
        this.crawlerService = crawlerService;
    }

    @PostMapping
    public ResponseEntity<PageData> crawlUrl(@RequestParam String url) {
        try {
            PageData data = crawlerService.crawl(url);
            return ResponseEntity.ok(data);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
