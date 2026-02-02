package com.booktool.backend.domain.isbn;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IsbnServiceTest {

    private final IsbnService service = new IsbnService();

    @Test
    void isValid_nullIsbn_false() {
        assertFalse(service.isValid(null));
    }

    @Test
    void clean_removesHyphensAndSpaces() {
        String raw = "978-0-306-40615-7";
        assertEquals("9780306406157", service.clean(raw));
    }

    @Test
    void isValid_isbn13_true() {
        assertTrue(service.isValid("9780306406157"));
    }

    @Test
    void isValid_isbn10_true() {
        assertTrue(service.isValid("0306406152"));
    }

    @Test
    void normalize_formatsIsbn10And13() {
        String n10 = service.normalize("0306406152");
        assertEquals("0-306-40615-2", n10);

        String n13 = service.normalize("9780306406157");
        assertEquals("978-0-30-640615-7", n13);
    }
}
