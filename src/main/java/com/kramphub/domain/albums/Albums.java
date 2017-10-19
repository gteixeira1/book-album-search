package com.kramphub.domain.albums;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Albums {

    @JsonProperty("results")
    private List<Album> albums = null;

    @JsonProperty("results")
    public List<Album> getAlbums() {
        return albums;
    }

    @JsonProperty("results")
    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

}
