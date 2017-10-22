package com.kramphub.controller;

import com.kramphub.executor.BookAlbumServiceExecutor;
import com.kramphub.model.AlbumModel;
import com.kramphub.model.BookModel;
import com.kramphub.model.ItemModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(path = "/book-album-search/api/v1", produces = "application/json")
public class BookAlbumSearchController {

    @Autowired
    private BookAlbumServiceExecutor bookAlbumServiceExecutor;

    private static final Logger log = LoggerFactory.getLogger(BookAlbumSearchController.class);

    @RequestMapping(path = "/book-album", method = RequestMethod.GET, params = {"searchKey!="})
    public ResponseEntity<List<ItemModel>> getBookAlbum(@RequestParam("searchKey") String searchKey){
        return new ResponseEntity<List<ItemModel>>(bookAlbumServiceExecutor.searchItems(searchKey), HttpStatus.OK);
    }

    @RequestMapping(path = "/books", method = RequestMethod.GET, params = {"searchKey!="})
    @Profile("dev")
    public ResponseEntity<List<BookModel>> getBooks(@RequestParam("searchKey") String searchKey){
        return new ResponseEntity<List<BookModel>>(bookAlbumServiceExecutor.searchBookThread(searchKey), HttpStatus.OK);
    }

    @RequestMapping(path = "/albums", method = RequestMethod.GET, params = {"searchKey!="})
    @Profile("dev")
    public ResponseEntity<List<AlbumModel>> getAlbums(@RequestParam("searchKey") String searchKey){
        return new ResponseEntity<List<AlbumModel>>(bookAlbumServiceExecutor.searchAlbumThread(searchKey), HttpStatus.OK);
    }
}
