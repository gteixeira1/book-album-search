package com.kramphub.executor;

import com.kramphub.model.AlbumModel;
import com.kramphub.model.BookModel;
import com.kramphub.model.ItemModel;
import com.kramphub.service.GoogleBooksService;
import com.kramphub.service.ITunesAlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

@Service
public class ServiceExecutor {

    @Autowired
    private GoogleBooksService googleBooksService;

    @Autowired
    private ITunesAlbumService iTunesAlbumService;

    private static final Logger log = LoggerFactory.getLogger(ServiceExecutor.class);

    public ServiceExecutor(){}

    public List<ItemModel> searchItems(String searchKey){
        long startTime = System.nanoTime();
        log.info(String.format("Started searchItems at %s.", LocalDateTime.now()));

        CountDownLatch latch = new CountDownLatch(2);
        List<ItemModel> itemModelList = new ArrayList<>();

//        Thread bookThread = new Thread(
//                () -> googleBooksService.searchBooks(searchKey, latch).stream().forEach(book -> itemModelList.add(book))
//        );
//        Thread albumThread = new Thread(
//                () -> iTunesAlbumService.searchAlbums(searchKey, latch).stream().forEach(album -> itemModelList.add(album))
//        );

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<List<ItemModel>> googleTask = () -> {
            googleBooksService.searchBooks(searchKey, latch).stream().forEach(book -> itemModelList.add(book));
            return itemModelList;
        };

        Callable<List<ItemModel>> itunesTask = () -> {
            iTunesAlbumService.searchAlbums(searchKey, latch).stream().forEach(album -> itemModelList.add(album));
            return itemModelList;
        };

        List<Callable<List<ItemModel>>> callableTasks = new ArrayList<>();
        callableTasks.add(googleTask);
        callableTasks.add(itunesTask);

        try {
            executor.invokeAll(callableTasks, 10, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Shutdown executor. {}",executor.isTerminated());
        executor.shutdown();
        log.info("Finished executor. {}",executor.isTerminated());

        log.info("Waiting all threads finish. {}",latch.getCount());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
                () -> googleBooksService.searchBooks(searchKey, latch).stream().forEach(book -> bookModelList.add(book))
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
