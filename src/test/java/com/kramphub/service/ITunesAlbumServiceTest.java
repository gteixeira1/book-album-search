package com.kramphub.service;

import com.kramphub.model.AlbumModel;
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

public class ITunesAlbumServiceTest {

    @InjectMocks
    private ITunesAlbumService iTunesAlbumService;

    @Mock
    private RestTemplate restTemplate;

    private List<AlbumModel> albumModelList;

    private String albums;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(iTunesAlbumService, "iTunesApiURL", "URL");

        albumModelList = BookAlbumUtilTest.buildAlbumModelList();
        albums = BookAlbumUtilTest.buildAlbumsString();
    }

    @Test
    public void shouldGetAlbumList(){
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(new ResponseEntity<String>(albums, HttpStatus.OK));

        List<AlbumModel> response = iTunesAlbumService.searchAlbums("test", new CountDownLatch(1));
        assertEquals(albumModelList.get(0).getArtist(),response.get(0).getArtist());
        assertEquals(albumModelList.get(0).getKind(),response.get(0).getKind());
        assertEquals(albumModelList.get(0).getTitle(),response.get(0).getTitle());
    }

    @Test
    public void shouldGetEmptyAlbumList(){
        String emptyAlbums = "";
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(new ResponseEntity<String>(emptyAlbums, HttpStatus.OK));
        List<AlbumModel> response = iTunesAlbumService.searchAlbums("test", new CountDownLatch(1));
        assertEquals(response.size(),0);
    }

    @Test(expected = HttpClientErrorException.class)
    public void shouldReturnHttpResponseException(){
        doThrow(HttpClientErrorException.class).when(restTemplate).getForEntity(anyString(), eq(String.class));
        iTunesAlbumService.searchAlbums("test", new CountDownLatch(1));
    }
}
