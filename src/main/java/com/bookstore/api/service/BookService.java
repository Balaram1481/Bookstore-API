package com.bookstore.api.service;

import java.util.List;
import com.bookstore.api.dto.BookDto;
import com.bookstore.api.dto.BookResponse;
import java.util.List;
public interface BookService {

    BookDto createBook(BookDto bookDto);

    BookDto getBookById(Long id);

    BookResponse getAllBooks(int pageNo,int pageSize,String sortBy,String sortDir);

    BookDto updateBook(Long id, BookDto bookDto);

    void deleteBook(Long id);
    
}