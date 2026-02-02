package com.booktool.backend.domain.isbn;

import org.springframework.stereotype.Component;

@Component
public class IsbnService {

    public boolean isValid(String isbn) {
        if (isbn == null) return false;

        String clean = clean(isbn);

        if (clean.length() == 10) return isValidIsbn10(clean);
        if (clean.length() == 13) return isValidIsbn13(clean);

        return false;
    }

    public String normalize(String isbn) {
        if (isbn == null) return null;

        String clean = clean(isbn);

        if (clean.length() == 10) {
            return clean.substring(0, 1) + "-" +
                    clean.substring(1, 4) + "-" +
                    clean.substring(4, 9) + "-" +
                    clean.substring(9);
        }

        if (clean.length() == 13) {
            return clean.substring(0, 3) + "-" +
                    clean.substring(3, 4) + "-" +
                    clean.substring(4, 6) + "-" +
                    clean.substring(6, 12) + "-" +
                    clean.substring(12);
        }

        return isbn;
    }

    public String clean(String isbn) {
        return isbn.replace("-", "").replace(" ", "").trim();
    }

    private boolean isValidIsbn10(String isbn) {
        int sum = 0;

        for (int i = 0; i < 10; i++) {
            char c = isbn.charAt(i);
            int value;

            if (i == 9 && c == 'X') value = 10;
            else if (Character.isDigit(c)) value = c - '0';
            else return false;

            sum += value * (10 - i);
        }

        return sum % 11 == 0;
    }

    private boolean isValidIsbn13(String isbn) {
        int sum = 0;

        for (int i = 0; i < 13; i++) {
            int digit = isbn.charAt(i) - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }

        return sum % 10 == 0;
    }
}