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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Books {

    @JsonProperty("items")
    private List<Book> books = null;

    @JsonProperty("items")
    public List<Book> getBooks() {
        return books;
    }

    @JsonProperty("items")
    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
