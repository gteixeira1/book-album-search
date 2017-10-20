package com.kramphub.controller;

import com.kramphub.model.AlbumModel;
import com.kramphub.model.BookModel;
import com.kramphub.model.Item;
import com.kramphub.service.GoogleBooksService;
import com.kramphub.service.ITunesAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/book-album-search/api/v1", produces = "application/json")
public class BookAlbumSearchController {

    @Autowired
    private GoogleBooksService googleBooksService;

    @Autowired
    private ITunesAlbumService iTunesAlbumService;

    @RequestMapping(path = "/book-album", method = RequestMethod.GET, params = {"searchKey!="})
    public ResponseEntity<List<Item>> getBookAlbum(@RequestParam("searchKey") String searchKey){
        List<Item> itemList = new ArrayList<>();
        BookModel bookModel = new BookModel();
        AlbumModel albumModel = new AlbumModel();
        itemList.add(bookModel);
        itemList.add(albumModel);

        return new ResponseEntity<List<Item>>(HttpStatus.OK);
    }

    @RequestMapping(path = "/books", method = RequestMethod.GET, params = {"searchKey!="})
    @Profile("dev")
    public ResponseEntity<List<BookModel>> getBooks(@RequestParam("searchKey") String searchKey){

        return new ResponseEntity<List<BookModel>>(googleBooksService.searchBook(searchKey), HttpStatus.OK);
    }

    @RequestMapping(path = "/albums", method = RequestMethod.GET, params = {"searchKey!="})
    @Profile("dev")
    public ResponseEntity<List<AlbumModel>> getAlbums(@RequestParam("searchKey") String searchKey){

        return new ResponseEntity<List<AlbumModel>>(iTunesAlbumService.searchAlbums(searchKey), HttpStatus.OK);
    }
}
