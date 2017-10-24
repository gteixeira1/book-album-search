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
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

@Service
public class BookAlbumServiceExecutor {

    @Autowired
    private GoogleBookService googleBookService;

    @Autowired
    private ITunesAlbumService iTunesAlbumService;

    @Value("${global.app.max.responseTime}")
    public int appMaxResponseTime;

    private static final Logger log = LoggerFactory.getLogger(BookAlbumServiceExecutor.class);

    private CountDownLatch latch;

    private List<ItemModel> itemModelList;

    public BookAlbumServiceExecutor(){}

    public List<ItemModel> searchItems(String searchKey){
        long startTime = System.nanoTime();
        log.info(String.format("Started searchItems at %s.", LocalDateTime.now()));

        latch = new CountDownLatch(2);
        itemModelList = new ArrayList<>();

        new Thread(
                () -> googleBookService.searchBooks(searchKey, latch).stream().forEach(itemModelList::add)
        ).start();
        new Thread(
                () -> iTunesAlbumService.searchAlbums(searchKey, latch).stream().forEach(itemModelList::add)
        ).start();

        startTimerScheduler(2);
        countDownLatchAwait();

        processItemModelListResponse();

        log.info(String.format("Finished searchItems at %s. Executed in %s millis.",
                LocalDateTime.now(), (System.nanoTime() - startTime)/ 1_000_000));

        return itemModelList;
    }

    public List<BookModel> searchBookThread(String searchKey){
        long startTime = System.nanoTime();
        log.info(String.format("Started searchBookThread at %s.", LocalDateTime.now()));

        List<BookModel> bookModelList = new ArrayList<>();
        latch = new CountDownLatch(1);
        new Thread(
                () -> googleBookService.searchBooks(searchKey, latch).stream().forEach(bookModelList::add)
        ).start();

        startTimerScheduler(1);
        countDownLatchAwait();

        log.info(String.format("Finished searchBookThread at %s. Executed in %s millis.",
                LocalDateTime.now(), (System.nanoTime() - startTime)/ 1_000_000));

        return bookModelList;
    }

    public List<AlbumModel> searchAlbumThread(String searchKey){
        long startTime = System.nanoTime();
        log.info(String.format("Started searchAlbumThread at %s.", LocalDateTime.now()));

        List<AlbumModel> albumModelList = new ArrayList<>();
        latch = new CountDownLatch(1);
        new Thread(
                () -> iTunesAlbumService.searchAlbums(searchKey, latch).stream().forEach(albumModelList::add)
        ).start();

        startTimerScheduler(1);
        countDownLatchAwait();

        log.info(String.format("Finished searchAlbumThread at %s. Executed in %s millis.",
                LocalDateTime.now(), (System.nanoTime() - startTime)/ 1_000_000));

        return albumModelList;
    }

    private void startTimerScheduler(int threadCount){
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        IntStream.range(0,threadCount).forEach(i -> latch.countDown());
                    }
                }, appMaxResponseTime
        );
    }

    private void countDownLatchAwait(){
        log.info("Waiting thread finish. {}", latch.getCount());
        try {
            latch.await();
        } catch (InterruptedException e) {
            log.error("Error on CountDownLatch.await. {}", e.getCause());
        }
        log.info("Thread finished. {}",latch.getCount());
    }

    private List<ItemModel> processItemModelListResponse(){
        List<ItemModel> response = itemModelList;
        try{
            itemModelList.sort(Comparator.comparing(ItemModel::getTitle,Comparator.nullsLast(Comparator.naturalOrder())));
            response = itemModelList;
        } catch (NullPointerException e){
            log.error("Error while sorting ItemModelList. Returning unsorted result.");
        }
        return response;
    }

}
