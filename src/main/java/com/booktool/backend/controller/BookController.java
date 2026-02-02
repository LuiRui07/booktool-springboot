package com.booktool.backend.controller;


import com.booktool.backend.domain.Book;
import com.booktool.backend.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

    private final BookService bookService;

    public BookController (BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> findAll(){
        return bookService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Book book){
        bookService.create(book);
        return ResponseEntity.status(201).build();
    }
}
