package com.kramphub.controller;

import com.kramphub.model.AlbumModel;
import com.kramphub.model.BookModel;
import com.kramphub.model.ItemModel;
import com.kramphub.service.GoogleBooksService;
import com.kramphub.service.ITunesAlbumService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(path = "/book-album-search/api/v1", produces = "application/json")
public class BookAlbumSearchController {

    @Autowired
    private GoogleBooksService googleBooksService;

    @Autowired
    private ITunesAlbumService iTunesAlbumService;

    private static final Logger log = LoggerFactory.getLogger(BookAlbumSearchController.class);

    @RequestMapping(path = "/book-album", method = RequestMethod.GET, params = {"searchKey!="})
    public ResponseEntity<List<ItemModel>> getBookAlbum(@RequestParam("searchKey") String searchKey){
        long startTime = System.nanoTime();
        log.info(String.format("Started request on GET:/book-album at %s.", LocalDateTime.now()));

        List<ItemModel> itemModelList = new ArrayList<>();

        googleBooksService.searchBooks(searchKey).stream().forEach(book -> itemModelList.add(book));
        iTunesAlbumService.searchAlbums(searchKey).stream().forEach(album -> itemModelList.add(album));
        itemModelList.sort(Comparator.comparing(ItemModel::getTitle,Comparator.nullsFirst(Comparator.naturalOrder())));

        log.info(String.format("Finished request on GET:/book-album at %s. Executed in %s millis.",
                LocalDateTime.now(), (System.nanoTime() - startTime)/ 1_000_000));

        return new ResponseEntity<List<ItemModel>>(itemModelList, HttpStatus.OK);
    }

    @RequestMapping(path = "/books", method = RequestMethod.GET, params = {"searchKey!="})
    @Profile("dev")
    public ResponseEntity<List<BookModel>> getBooks(@RequestParam("searchKey") String searchKey){
        long startTime = System.nanoTime();
        log.info(String.format("Started request on GET:/books at %s.", LocalDateTime.now()));

        List<BookModel> response = googleBooksService.searchBooks(searchKey);

        log.info(String.format("Finished request on GET:/books at %s. Executed in %s millis.",
                LocalDateTime.now(), (System.nanoTime() - startTime)/ 1_000_000));

        return new ResponseEntity<List<BookModel>>(response, HttpStatus.OK);
    }

    @RequestMapping(path = "/albums", method = RequestMethod.GET, params = {"searchKey!="})
    @Profile("dev")
    public ResponseEntity<List<AlbumModel>> getAlbums(@RequestParam("searchKey") String searchKey){
        long startTime = System.nanoTime();
        log.info(String.format("Started request on GET:/albums at %s.", LocalDateTime.now()));

        List<AlbumModel> response = iTunesAlbumService.searchAlbums(searchKey);

        log.info(String.format("Finished request on GET:/albums at %s. Executed in %s millis.",
                LocalDateTime.now(), (System.nanoTime() - startTime)/ 1_000_000));

        return new ResponseEntity<List<AlbumModel>>(response, HttpStatus.OK);
    }
}
