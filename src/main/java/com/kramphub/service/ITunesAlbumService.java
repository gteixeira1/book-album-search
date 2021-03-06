package com.kramphub.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kramphub.domain.albums.Albums;
import com.kramphub.model.AlbumModel;
import com.kramphub.util.BookAlbumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Service
public class ITunesAlbumService {

    @Value("${itunes.api.url}")
    private String iTunesApiURL;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(ITunesAlbumService.class);

    public List<AlbumModel> searchAlbums(String searchKey, CountDownLatch latch){
        long startTime = System.nanoTime();
        log.info(String.format("Started searchAlbums at %s.", LocalDateTime.now()));

        StringBuilder searchUrl = new StringBuilder(iTunesApiURL);
        searchUrl.append(searchKey);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(searchUrl.toString(), String.class);
        List<AlbumModel> response = new LinkedList<AlbumModel>();

        response = processResponse(responseEntity);

        log.info(String.format("Finished searchAlbums at %s. Executed in %s millis.",
                LocalDateTime.now(), (System.nanoTime() - startTime)/ 1_000_000));
        latch.countDown();

        return response;
    }


    private List<AlbumModel> processResponse(ResponseEntity<String> response){
        List<AlbumModel> albumModelList = new LinkedList<AlbumModel>();
        if (response.getBody() != null){
            Albums albums = null;
            try {
                albums = new ObjectMapper().readValue(response.getBody(),Albums.class);
                albumModelList = albums.getAlbums().stream()
                        .map(album -> BookAlbumUtil.convertAlbumToAlbumModel(album))
                        .collect(Collectors.toList());
            } catch (IOException e) {
                log.error("Error parsing Album response. {}", response.toString());
            }
        }
        return albumModelList;
    }
}
