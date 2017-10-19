package com.kramphub.service;

import com.kramphub.domain.books.Book;
import com.kramphub.domain.books.Books;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GoogleBooksService {

    @Value("${google.book.api.url}")
    private String googleBookApiURL;

    @Autowired
    private RestTemplate restTemplate;

    public List<Book> searchBook(String searchKey){
        StringBuilder searchUrl = new StringBuilder(googleBookApiURL);
        searchUrl.append(searchKey);
        ResponseEntity<Books> responseEntity = restTemplate.getForEntity(searchUrl.toString(), Books.class);
        List<Book> response = responseEntity.getBody().getBooks();
        return response;
    }

}
