package com.booktool.backend.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void builder_and_getters_work() {
        Book b = Book.builder()
                .id(1L)
                .title("My Book")
                .isbn("1234567890")
                .year(2000)
                .pages(123)
                .language(Language.EN)
                .compensation(50.0)
                .build();

        assertEquals(1L, b.getId());
        assertEquals("My Book", b.getTitle());
        assertEquals("1234567890", b.getIsbn());
        assertEquals(2000, b.getYear());
        assertEquals(123, b.getPages());
        assertEquals(Language.EN, b.getLanguage());
        assertEquals(50.0, b.getCompensation());
    }
}
