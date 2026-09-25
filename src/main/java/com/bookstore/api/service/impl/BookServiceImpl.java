package com.bookstore.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bookstore.api.dto.BookDto;
import com.bookstore.api.entity.Book;
import com.bookstore.api.exception.BookAPIException;
import com.bookstore.api.exception.ResourceNotFound;
import com.bookstore.api.mapper.BookMapper;
import com.bookstore.api.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.bookstore.api.dto.BookResponse;
import com.bookstore.api.service.BookService;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {

        if (bookRepository.existsByIsbn(bookDto.getIsbn())) {

            throw new BookAPIException(
                    HttpStatus.BAD_REQUEST,
                    "Book with ISBN "
                            + bookDto.getIsbn()
                            + " already exists");
        }

        Book book = BookMapper.mapToBook(bookDto);

        Book savedBook = bookRepository.save(book);

        return BookMapper.mapToBookDto(savedBook);
    }

    @Override
    public BookDto getBookById(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFound(
                                "Book",
                                "id",
                                id));

        return BookMapper.mapToBookDto(book);
    }

    @Override
    public BookResponse getAllBooks(
            int pageNo,
            int pageSize,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable =
                PageRequest.of(pageNo, pageSize, sort);

        Page<Book> books = bookRepository.findAll(pageable);

        List<BookDto> content = books.getContent()
                .stream()
                .map(BookMapper::mapToBookDto)
                .toList();

        BookResponse bookResponse = new BookResponse();

        bookResponse.setContent(content);
        bookResponse.setPageNo(books.getNumber());
        bookResponse.setPageSize(books.getSize());
        bookResponse.setTotalElements(books.getTotalElements());
        bookResponse.setTotalPages(books.getTotalPages());
        bookResponse.setLast(books.isLast());

        return bookResponse;
    }

    @Override
    public BookDto updateBook(Long id, BookDto bookDto) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFound(
                                "Book",
                                "id",
                                id));

        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setPrice(bookDto.getPrice());
        book.setStockQuantity(bookDto.getStockQuantity());

        Book updatedBook = bookRepository.save(book);

        return BookMapper.mapToBookDto(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFound(
                                "Book",
                                "id",
                                id));

        bookRepository.delete(book);
    }
}