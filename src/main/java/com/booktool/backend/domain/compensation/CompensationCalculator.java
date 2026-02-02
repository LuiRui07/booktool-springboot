package com.booktool.backend.domain.compensation;

import com.booktool.backend.domain.Book;
import com.booktool.backend.domain.Language;
import org.springframework.stereotype.Component;

@Component
public class CompensationCalculator {

    private static final double BASE_PRICE = 100;

    public double calculate(Book book) {
        double factor = getPageFactor(book.getPages());
        double amount = BASE_PRICE * factor;

        double surcharges = 0;

        if (book.getYear() != null && book.getYear() < 1990) {
            surcharges += 15;
        }

        if (book.getLanguage() == Language.DE) {
            surcharges += amount * 0.1;
        }

        return amount + surcharges;
    }

    private double getPageFactor(Integer pages) {
        if (pages == null || pages < 50) return 0.7;
        if (pages <= 99) return 1.0;
        if (pages <= 199) return 1.1;
        if (pages <= 299) return 1.2;
        if (pages <= 499) return 1.3;
        return 1.5;
    }
}