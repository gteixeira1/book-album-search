package com.kramphub.executor;

import com.kramphub.model.AlbumModel;
import com.kramphub.model.BookModel;
import com.kramphub.model.ItemModel;
import com.kramphub.service.GoogleBookService;
import com.kramphub.service.ITunesAlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

@Service
public class ServiceExecutor {

    @Autowired
    private GoogleBookService googleBookService;

    @Autowired
    private ITunesAlbumService iTunesAlbumService;

    @Value("${global.app.max.responseTime}")
    public int appMaxResponseTime;

    private static final Logger log = LoggerFactory.getLogger(ServiceExecutor.class);

    public ServiceExecutor(){}

    public List<ItemModel> searchItems(String searchKey){
        long startTime = System.nanoTime();
        log.info(String.format("Started searchItems at %s.", LocalDateTime.now()));

        CountDownLatch latch = new CountDownLatch(2);
        List<ItemModel> itemModelList = new ArrayList<>();

        new Thread(
                () -> googleBookService.searchBooks(searchKey, latch).stream().forEach(book -> itemModelList.add(book))
        ).start();
        new Thread(
                () -> iTunesAlbumService.searchAlbums(searchKey, latch).stream().forEach(album -> itemModelList.add(album))
        ).start();

        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        latch.countDown();
                        latch.countDown();
                    }
                }, appMaxResponseTime
        );

        log.info("Waiting all threads finish.");
        try {
            latch.await();
        } catch (InterruptedException e) {
            log.error("Error on CountDownLatch.await. {}", e.getCause());
        }
        log.info("All threads finished.");

        itemModelList.sort(Comparator.comparing(ItemModel::getTitle,Comparator.nullsFirst(Comparator.naturalOrder())));

        log.info(String.format("Finished searchItems at %s. Executed in %s millis.",
                LocalDateTime.now(), (System.nanoTime() - startTime)/ 1_000_000));

        return itemModelList;
    }

    public List<BookModel> searchBookThread(String searchKey, CountDownLatch latch){
        List<BookModel> bookModelList = new ArrayList<>();
        new Thread(
                () -> googleBookService.searchBooks(searchKey, latch).stream().forEach(book -> bookModelList.add(book))
        ).start();
        return bookModelList;
    }

    public List<AlbumModel> searchAlbumThread(String searchKey, CountDownLatch latch){
        long startTime = System.nanoTime();
        log.info(String.format("Started searchAlbumThread at %s.", LocalDateTime.now()));

        List<AlbumModel> albumModelList = new ArrayList<>();
        new Thread(
                () -> iTunesAlbumService.searchAlbums(searchKey, latch).stream().forEach(album -> albumModelList.add(album))
        ).start();

        log.info(String.format("Finished searchAlbumThread at %s. Executed in %s millis.",
                LocalDateTime.now(), (System.nanoTime() - startTime)/ 1_000_000));

        return albumModelList;
    }

}
