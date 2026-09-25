package com.bookstore.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bookstore.api.dto.BookResponse;

import com.bookstore.api.dto.BookDto;
import com.bookstore.api.service.BookService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Validated
public class BookController {

    @Autowired
    private BookService bookService;

    // Accessible by all authenticated users
    @GetMapping
        public ResponseEntity<BookResponse> getAllBooks(

                @RequestParam(
                        value = "pageNo",
                        defaultValue = "0",
                        required = false)
                int pageNo,

                @RequestParam(
                        value = "pageSize",
                        defaultValue = "10",
                        required = false)
                int pageSize,

                @RequestParam(
                        value = "sortBy",
                        defaultValue = "id",
                        required = false)
                String sortBy,

                @RequestParam(
                        value = "sortDir",
                        defaultValue = "asc",
                        required = false)
                String sortDir) {

        return ResponseEntity.ok(
                bookService.getAllBooks(
                        pageNo,
                        pageSize,
                        sortBy,
                        sortDir));
        }

    // Accessible by all authenticated users
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookService.getBookById(id));
    }

    // Admin only
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BookDto> createBook(
            @Valid @RequestBody BookDto bookDto) {
                System.out.println("CREATE BOOK CONTROLLER ENTERED");

        BookDto savedBook = bookService.createBook(bookDto);

        return new ResponseEntity<>(
                savedBook,
                HttpStatus.CREATED);
    }

    // Admin only
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookDto bookDto) {

        BookDto updatedBook =
                bookService.updateBook(id, bookDto);

        return ResponseEntity.ok(updatedBook);
    }

    // Admin only
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(
            @PathVariable Long id) {

        bookService.deleteBook(id);

        return ResponseEntity.ok(
                "Book deleted successfully");
    }
}