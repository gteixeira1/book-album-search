package com.kramphub.service;

import com.kramphub.domain.books.Books;
import com.kramphub.model.BookModel;
import com.kramphub.utils.BookAlbumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoogleBooksService {

    @Value("${google.book.api.url}")
    private String googleBookApiURL;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(GoogleBooksService.class);

    public List<BookModel> searchBooks(String searchKey){
        long startTime = System.nanoTime();
        log.info(String.format("Started searchBooks at %s.", LocalDateTime.now()));

        StringBuilder searchUrl = new StringBuilder(googleBookApiURL);
        searchUrl.append(searchKey);

        ResponseEntity<Books> responseEntity = restTemplate.getForEntity(searchUrl.toString(), Books.class);
        List<BookModel> response = responseEntity.getBody().getBooks().stream()
                .map(book -> BookAlbumUtils.convertBookToBookModel(book))
                .collect(Collectors.toList());

        log.info(String.format("Finished searchBooks at %s. Executed in %s millis.",
                LocalDateTime.now(), (System.nanoTime() - startTime)/ 1_000_000));

        return response;
    }

}
