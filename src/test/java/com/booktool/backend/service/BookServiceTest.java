package com.booktool.backend.service;

import com.booktool.backend.domain.Book;
import com.booktool.backend.domain.compensation.CompensationCalculator;
import com.booktool.backend.domain.isbn.IsbnService;
import com.booktool.backend.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private IsbnService isbnService;

    @Mock
    private CompensationCalculator calculator;

    @InjectMocks
    private BookService service;

    @Test
    void create_success_setsIsbnAndCompensationAndSaves() {
        Book book = Book.builder()
                .title("My Book")
                .isbn("raw-isbn")
                .compensation(0.0)
                .build();

        when(isbnService.isValid("raw-isbn")).thenReturn(true);
        when(isbnService.normalize("raw-isbn")).thenReturn("NORMALIZED");
        when(calculator.calculate(any(Book.class))).thenReturn(123.45);
        when(bookRepository.existsByIsbn("NORMALIZED")).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        Book saved = service.create(book);

        assertEquals("NORMALIZED", saved.getIsbn());
        assertEquals(123.45, saved.getCompensation());
        verify(bookRepository).save(saved);
    }

    @Test
    void create_missingTitle_throws() {
        Book book = Book.builder()
                .title(null)
                .isbn("any")
                .build();

        Exception ex = assertThrows(IllegalStateException.class, () -> service.create(book));
        assertEquals("Title is required", ex.getMessage());
        verifyNoInteractions(isbnService, calculator, bookRepository);
    }

    @Test
    void create_invalidIsbn_throws() {
        Book book = Book.builder()
                .title("T")
                .isbn("bad")
                .build();

        when(isbnService.isValid("bad")).thenReturn(false);

        Exception ex = assertThrows(IllegalStateException.class, () -> service.create(book));
        assertEquals("ISBN is invalid", ex.getMessage());
        verify(isbnService).isValid("bad");
        verifyNoMoreInteractions(isbnService);
        verifyNoInteractions(calculator, bookRepository);
    }

    @Test
    void create_alreadyExists_throws() {
        Book book = Book.builder()
                .title("T")
                .isbn("raw")
                .build();

        when(isbnService.isValid("raw")).thenReturn(true);
        when(isbnService.normalize("raw")).thenReturn("NORM");
        when(bookRepository.existsByIsbn("NORM")).thenReturn(true);

        Exception ex = assertThrows(IllegalStateException.class, () -> service.create(book));
        assertEquals("ISBN is already registered", ex.getMessage());
        verify(bookRepository).existsByIsbn("NORM");
        verifyNoInteractions(calculator);
    }
}
