package com.kramphub.controller;

import com.kramphub.executor.ServiceExecutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(BookAlbumSearchController.class)
public class BookAlbumSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceExecutor executor;

    private final String basePath = "/book-album-search/api/v1";

    @Test
    public void shouldGetBookAlbum() throws Exception {
        mockMvc.perform(get(basePath + "/book-album?searchKey=test")).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnBadRequestOnGetBookAlbum() throws Exception {
        mockMvc.perform(get(basePath + "/book-album")).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGetBooks() throws Exception {
        mockMvc.perform(get(basePath + "/books?searchKey=test")).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnBadRequestOnGetBooks() throws Exception {
        mockMvc.perform(get(basePath + "/books")).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGetAlbums() throws Exception {
        mockMvc.perform(get(basePath + "/albums?searchKey=test")).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnBadRequestOnGetAlbums() throws Exception {
        mockMvc.perform(get(basePath + "/albums")).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnNotFoundOnNotMappingEndpoint() throws Exception {
        mockMvc.perform(get(basePath + "/notMapped")).andExpect(status().isNotFound());
    }
}
