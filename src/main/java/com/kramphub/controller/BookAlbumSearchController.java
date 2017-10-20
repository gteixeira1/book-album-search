package com.kramphub.controller;

import com.kramphub.model.AlbumModel;
import com.kramphub.model.BookModel;
import com.kramphub.model.ItemModel;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/book-album-search/api/v1", produces = "application/json")
public class BookAlbumSearchController {

    @Autowired
    private GoogleBooksService googleBooksService;

    @Autowired
    private ITunesAlbumService iTunesAlbumService;

    @RequestMapping(path = "/book-album", method = RequestMethod.GET, params = {"searchKey!="})
    public ResponseEntity<List<ItemModel>> getBookAlbum(@RequestParam("searchKey") String searchKey){
        List<ItemModel> itemModelList = new ArrayList<>();

        googleBooksService.searchBook(searchKey).stream().forEach(book -> itemModelList.add(book));
        iTunesAlbumService.searchAlbums(searchKey).stream().forEach(album -> itemModelList.add(album));
        itemModelList.sort(Comparator.comparing(ItemModel::getTitle));

        return new ResponseEntity<List<ItemModel>>(itemModelList, HttpStatus.OK);
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
