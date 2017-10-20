package com.kramphub.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kramphub.domain.albums.Albums;
import com.kramphub.model.AlbumModel;
import com.kramphub.utils.BookAlbumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ITunesAlbumService {

    @Value("${itunes.api.url}")
    private String iTunesApiURL;

    @Autowired
    private RestTemplate restTemplate;

    public List<AlbumModel> searchAlbums(String searchKey){
        StringBuilder searchUrl = new StringBuilder(iTunesApiURL);
        searchUrl.append(searchKey);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(searchUrl.toString(), String.class);
        List<AlbumModel> response = new LinkedList<AlbumModel>();
        try {
            Albums albums = new ObjectMapper().readValue(responseEntity.getBody(),Albums.class);
            response = albums.getAlbums().stream()
                    .map(album -> BookAlbumUtils.convertAlbumToAlbumModel(album))
                    .collect(Collectors.toList());
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
