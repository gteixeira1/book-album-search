package com.kramphub.util;

import com.kramphub.domain.albums.Album;
import com.kramphub.domain.albums.Albums;
import com.kramphub.domain.books.Book;
import com.kramphub.domain.books.Books;
import com.kramphub.domain.books.VolumeInfo;
import com.kramphub.model.AlbumModel;
import com.kramphub.model.BookModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookAlbumUtilTest {

    public static List<BookModel> buildBookModelList(){
        List<BookModel> bookModelList = new ArrayList<>();
        bookModelList.add(buildBookModel());
        return bookModelList;
    }

    public static List<AlbumModel> buildAlbumModelList(){
        List<AlbumModel> albumModelList = new ArrayList<>();
        albumModelList.add(buildAlbumModel());
        return albumModelList;
    }

    public static BookModel buildBookModel(){
        BookModel book = new BookModel();
        book.setTitle("BookTitle");
        book.setKind("Book");
        book.setAuthor("BookAuthor");
        return book;
    }

    public static Books buildBooks(){
        Books books = new Books();
        books.setBooks(buildBookList());
        return books;
    }

    public static List<Book> buildBookList(){
        List<Book> bookList = new ArrayList<>();
        Book single = new Book();
        VolumeInfo volumeInfo = new VolumeInfo();
        volumeInfo.setTitle("BookTitle");
        volumeInfo.setAuthors(Arrays.asList("BookAuthor"));
        single.setKind("Book");
        single.setVolumeInfo(volumeInfo);
        bookList.add(single);
        return bookList;
    }

    public static AlbumModel buildAlbumModel(){
        AlbumModel album = new AlbumModel();
        album.setTitle("AlbumTitle");
        album.setKind("Album");
        album.setArtist("AlbumArtist");
        return album;
    }


    public static Albums buildAlbums(){
        Albums albums = new Albums();
        Album single = new Album();
        single.setArtistName("AlbumArtist");
        single.setCollectionName("AlbumTitle");
        single.setCollectionType("Album");
        albums.setAlbums(Arrays.asList(single));
        return albums;
    }

    public static String buildAlbumsString(){
        StringBuilder response = new StringBuilder();
        response.append("{\n");
        response.append(" \"resultCount\":1,\n");
        response.append(" \"results\": [\n");
        response.append("{\"collectionType\":\"Album\", \"artistName\":\"AlbumArtist\", \"collectionName\":\"AlbumTitle\"}\n");
        response.append("]\n");
        response.append("}");
        return response.toString();
    }

}
