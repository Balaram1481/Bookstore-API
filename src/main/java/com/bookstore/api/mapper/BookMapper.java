package com.bookstore.api.mapper;

import com.bookstore.api.dto.BookDto;
import com.bookstore.api.entity.Book;

public class BookMapper {

    public static BookDto mapToBookDto(Book book) {

        BookDto dto = new BookDto();

        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setPrice(book.getPrice());
        dto.setStockQuantity(book.getStockQuantity());

        return dto;
    }

    public static Book mapToBook(BookDto dto) {

        Book book = new Book();

        // DO NOT SET ID FOR CREATE OPERATIONS
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPrice(dto.getPrice());
        book.setStockQuantity(dto.getStockQuantity());

        return book;
    }
}
