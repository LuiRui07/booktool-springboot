package com.booktool.backend.service;


import com.booktool.backend.domain.Book;
import com.booktool.backend.domain.compensation.CompensationCalculator;
import com.booktool.backend.domain.isbn.IsbnService;
import com.booktool.backend.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;
    private final IsbnService isbnService;
    private final CompensationCalculator calculator;

    public BookService(BookRepository bookRepository, IsbnService isbnService, CompensationCalculator calculator){
        this.bookRepository = bookRepository;
        this.isbnService = isbnService;
        this.calculator = calculator;
    }


    public Book create(Book book) {

        if (book.getTitle() == null || book.getTitle().isBlank()) {
            throw new IllegalStateException("Title is required");
        }

        if (book.getIsbn() == null || book.getIsbn().isBlank()) {
            throw new IllegalStateException("ISBN is required");
        }

        if (!isbnService.isValid(book.getIsbn())){
            throw new IllegalStateException("ISBN is invalid");
        }

        String normalizedIsbn = isbnService.normalize(book.getIsbn());

        if (existsByIsbn(normalizedIsbn)){
            throw new IllegalStateException("ISBN is already registered");
        }

        book.setIsbn(normalizedIsbn);
        book.setCompensation(calculator.calculate(book));

        log.info(
                "Book created isbn={} title='{}'",
                book.getIsbn(),
                book.getTitle()
        );
        return bookRepository.save(book);
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    private boolean existsByIsbn(String isbn){
        return bookRepository.existsByIsbn(isbn);
    }
}
