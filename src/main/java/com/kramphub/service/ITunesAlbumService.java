package com.kramphub.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kramphub.domain.albums.Album;
import com.kramphub.domain.albums.Albums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class ITunesAlbumService {

    @Value("${itunes.api.url}")
    private String iTunesApiURL;

    @Autowired
    private RestTemplate restTemplate;

    public List<Album> searchAlbums(String searchKey){
        StringBuilder searchUrl = new StringBuilder(iTunesApiURL);
        searchUrl.append(searchKey);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(searchUrl.toString(), String.class);
        Albums albums = null;
        try {
            albums = new ObjectMapper().readValue(responseEntity.getBody(),Albums.class);
            return albums.getAlbums();
        } catch (IOException e) {
            e.printStackTrace();
            return new LinkedList<Album>();
        }
    }

}
