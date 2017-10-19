package com.kramphub.domain.books;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({
        "subtitle",
        "publishedDate",
        "industryIdentifiers",
        "readingModes",
        "pageCount",
        "printType",
        "categories",
        "averageRating",
        "ratingsCount",
        "maturityRating",
        "allowAnonLogging",
        "contentVersion",
        "imageLinks",
        "language",
        "previewLink",
        "infoLink",
        "canonicalVolumeLink",
        "publisher",
        "panelizationSummary",
        "description"
})
public class VolumeInfo {

    @JsonProperty("title")
    private String title;

    @JsonProperty("authors")
    private List<String> authors = null;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("authors")
    public List<String> getAuthors() {
        return authors;
    }

    @JsonProperty("authors")
    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
