package com.kramphub.domain.albums;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Album {

    @JsonProperty("collectionType")
    private String collectionType;

    @JsonProperty("artistName")
    private String artistName;

    @JsonProperty("collectionName")
    private String collectionName;

    @JsonProperty("collectionType")
    public String getCollectionType() {
        return collectionType;
    }

    @JsonProperty("collectionType")
    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    @JsonProperty("artistName")
    public String getArtistName() {
        return artistName;
    }

    @JsonProperty("artistName")
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @JsonProperty("collectionName")
    public String getCollectionName() {
        return collectionName;
    }

    @JsonProperty("collectionName")
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

}
