package com.kramphub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/book-album-search", produces = "application/json")
public class BookAlbumSearchController {

    @RequestMapping(path = "/book-album", method = RequestMethod.GET, params = {"searchKey!="})
    public ResponseEntity<Object> getBookAlbum(@RequestParam("searchKey") String searchKey){

        return new ResponseEntity<>("Response OK", HttpStatus.OK);
    }

    @RequestMapping(path = "/books", method = RequestMethod.GET, params = {"searchKey!="})
    public ResponseEntity<Object> getBooks(@RequestParam("searchKey") String searchKey){

        return new ResponseEntity<>("Response OK", HttpStatus.OK);
    }

    @RequestMapping(path = "/albums", method = RequestMethod.GET, params = {"searchKey!="})
    public ResponseEntity<Object> getAlbums(@RequestParam("searchKey") String searchKey){

        return new ResponseEntity<>("Response OK", HttpStatus.OK);
    }
}