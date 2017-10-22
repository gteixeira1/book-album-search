package com.kramphub.executor;

import com.kramphub.model.AlbumModel;
import com.kramphub.model.BookModel;
import com.kramphub.model.ItemModel;
import com.kramphub.service.GoogleBookService;
import com.kramphub.service.ITunesAlbumService;
import com.kramphub.util.BookAlbumUtilTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class BookAlbumServiceExecutorTest {

    @InjectMocks
    private BookAlbumServiceExecutor executor;

    @Mock
    private GoogleBookService googleBookService;

    @Mock
    private ITunesAlbumService iTunesAlbumService;

    @Mock
    private CountDownLatch latch;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSuccessfullyProcessSearchItems(){
        ReflectionTestUtils.setField(executor, "appMaxResponseTime", 1000);
        when(googleBookService.searchBooks(any(), any())).thenReturn(BookAlbumUtilTest.buildBookModelList());
        when(iTunesAlbumService.searchAlbums(any(), any())).thenReturn(BookAlbumUtilTest.buildAlbumModelList());
        List<ItemModel> itemModels = executor.searchItems("test");
        itemModels.stream().forEach(itemModel -> {
            if (itemModel != null && itemModel.getClass().equals("AlbumModel")){
                assertEquals(itemModel.getTitle(),"AlbumTitle");
                assertEquals(itemModel.getKind(),"Album");
            } else if (itemModel != null && itemModel.getClass().equals("BookModel")){
                assertEquals(itemModel.getTitle(),"BookTitle");
                assertEquals(itemModel.getKind(),"Book");
            }
        });
    }

    @Test
    public void shouldSuccessfullyProcessSearchBookThread(){
        ReflectionTestUtils.setField(executor, "appMaxResponseTime", 1000);
        when(googleBookService.searchBooks(any(), any())).thenReturn(BookAlbumUtilTest.buildBookModelList());
        List<BookModel> bookModels = executor.searchBookThread("test");
        bookModels.stream().forEach(bookModel -> {
            if (bookModel != null){
                assertEquals(bookModel.getTitle(),"BookTitle");
                assertEquals(bookModel.getKind(),"Book");
                assertEquals(bookModel.getAuthor(),"BookAuthor");
            }
        });
    }

    @Test
    public void shouldSuccessfullyProcessSearchAlbumThread(){
        ReflectionTestUtils.setField(executor, "appMaxResponseTime", 1000);
        when(iTunesAlbumService.searchAlbums(any(), any())).thenReturn(BookAlbumUtilTest.buildAlbumModelList());
        List<AlbumModel> albumModels = executor.searchAlbumThread("test");
        albumModels.stream().forEach(albumModel -> {
            if (albumModel != null){
                assertEquals(albumModel.getTitle(),"AlbumTitle");
                assertEquals(albumModel.getKind(),"Album");
                assertEquals(albumModel.getArtist(),"AlbumArtist");
            }
        });
    }
}
