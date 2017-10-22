package com.kramphub.util;

import com.kramphub.domain.albums.Album;
import com.kramphub.domain.books.Book;
import com.kramphub.model.AlbumModel;
import com.kramphub.model.BookModel;

import java.util.List;
import java.util.stream.Collectors;

public class BookAlbumUtil {

    public static BookModel convertBookToBookModel(Book book){
        BookModel bookModel = new BookModel();
        List<String> authors = book.getVolumeInfo().getAuthors();
        bookModel.setAuthor(authors.stream().collect(Collectors.joining(", ")));
        bookModel.setTitle(book.getVolumeInfo().getTitle());
        bookModel.setKind(book.getKind());
        return bookModel;
    }

    public static AlbumModel convertAlbumToAlbumModel(Album album){
        AlbumModel albumModel = new AlbumModel();
        albumModel.setArtist(album.getArtistName());
        albumModel.setTitle(album.getCollectionName());
        albumModel.setKind(album.getCollectionType());
        return albumModel;
    }
}
