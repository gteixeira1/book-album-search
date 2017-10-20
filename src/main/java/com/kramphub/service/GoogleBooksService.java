package com.kramphub.service;

import com.kramphub.domain.books.Books;
import com.kramphub.model.BookModel;
import com.kramphub.utils.BookAlbumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoogleBooksService {

    @Value("${google.book.api.url}")
    private String googleBookApiURL;

    @Autowired
    private RestTemplate restTemplate;

    public List<BookModel> searchBook(String searchKey){
        StringBuilder searchUrl = new StringBuilder(googleBookApiURL);
        searchUrl.append(searchKey);
        ResponseEntity<Books> responseEntity = restTemplate.getForEntity(searchUrl.toString(), Books.class);
        List<BookModel> response = responseEntity.getBody().getBooks().stream()
                .map(book -> BookAlbumUtils.convertBookToBookModel(book))
                .collect(Collectors.toList());
        return response;
    }

}
