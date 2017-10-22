package com.kramphub.service;

import com.kramphub.domain.books.Books;
import com.kramphub.model.BookModel;
import com.kramphub.util.BookAlbumUtilTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class GoogleBookServiceTest {

    @InjectMocks
    private GoogleBookService googleBookService;

    @Mock
    private RestTemplate restTemplate;

    private List<BookModel> bookModelList;

    private Books books;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(googleBookService, "googleBookApiURL", "URL");

        bookModelList = BookAlbumUtilTest.buildBookModelList();
        books = BookAlbumUtilTest.buildBooks();
    }

    @Test
    public void shouldGetBookList(){
        when(restTemplate.getForEntity(anyString(), eq(Books.class)))
                .thenReturn(new ResponseEntity<Books>(books, HttpStatus.OK));

        List<BookModel> response = googleBookService.searchBooks("test", new CountDownLatch(1));
        assertEquals(bookModelList.get(0).getAuthor(),response.get(0).getAuthor());
        assertEquals(bookModelList.get(0).getKind(),response.get(0).getKind());
        assertEquals(bookModelList.get(0).getTitle(),response.get(0).getTitle());
    }

    @Test
    public void shouldGetEmptyBookList(){
        Books emptyBooks = new Books();
        when(restTemplate.getForEntity(anyString(), eq(Books.class)))
                .thenReturn(new ResponseEntity<Books>(emptyBooks, HttpStatus.OK));
        List<BookModel> response = googleBookService.searchBooks("test", new CountDownLatch(1));
        assertEquals(response.size(),0);
    }

    @Test(expected = HttpClientErrorException.class)
    public void shouldReturnHttpResponseException(){
        doThrow(HttpClientErrorException.class).when(restTemplate).getForEntity(anyString(), eq(Books.class));
        googleBookService.searchBooks("test", new CountDownLatch(1));
    }
}
