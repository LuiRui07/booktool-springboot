package com.booktool.backend.domain.compensation;

import com.booktool.backend.domain.Book;
import com.booktool.backend.domain.Language;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompensationCalculatorTest {

    private final CompensationCalculator calc = new CompensationCalculator();

    @Test
    void calculate_pagesNull_returnsBaseFactorAmount() {
        Book b = Book.builder().pages(null).year(null).language(null).build();
        double result = calc.calculate(b);
        assertEquals(100 * 0.7, result, 0.0001);
    }

    @Test
    void calculate_oldYear_addsSurcharge() {
        Book b = Book.builder().pages(60).year(1980).language(null).build();
        double result = calc.calculate(b);
        assertEquals(115.0, result, 0.0001);
    }

    @Test
    void calculate_germanLanguage_addsPercentageSurcharge() {
        Book b = Book.builder().pages(150).year(null).language(Language.DE).build();
        double result = calc.calculate(b);
        // pages 150 -> factor 1.1 -> amount 110, DE -> +10% = 11 => 121
        assertEquals(121.0, result, 0.0001);
    }

    @Test
    void calculate_largePages_highFactor() {
        Book b = Book.builder().pages(600).year(null).language(null).build();
        double result = calc.calculate(b);
        assertEquals(100 * 1.5, result, 0.0001);
    }
}
