package com.kramphub.controller;

import com.kramphub.domain.books.Book;
import com.kramphub.service.GoogleBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/book-album-search", produces = "application/json")
public class BookAlbumSearchController {

    @Autowired
    private GoogleBooksService googleBooksService;

    @RequestMapping(path = "/book-album", method = RequestMethod.GET, params = {"searchKey!="})
    public ResponseEntity<Object> getBookAlbum(@RequestParam("searchKey") String searchKey){

        return new ResponseEntity<>("Response OK", HttpStatus.OK);
    }

    @RequestMapping(path = "/books", method = RequestMethod.GET, params = {"searchKey!="})
    @Profile("dev")
    public ResponseEntity<List<Book>> getBooks(@RequestParam("searchKey") String searchKey){

        return new ResponseEntity<List<Book>>(googleBooksService.searchBook(searchKey), HttpStatus.OK);
    }

    @RequestMapping(path = "/albums", method = RequestMethod.GET, params = {"searchKey!="})
    @Profile("dev")
    public ResponseEntity<Object> getAlbums(@RequestParam("searchKey") String searchKey){

        return new ResponseEntity<>("Response OK", HttpStatus.OK);
    }
}
