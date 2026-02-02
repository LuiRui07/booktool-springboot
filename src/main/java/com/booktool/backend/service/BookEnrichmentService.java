package com.booktool.backend.service;

import com.booktool.backend.domain.Book;
import com.booktool.backend.domain.Language;
import com.booktool.backend.domain.compensation.CompensationCalculator;
import com.booktool.backend.domain.isbn.IsbnService;
import com.booktool.backend.integration.openlibrary.OpenLibraryClient;
import com.booktool.backend.integration.openlibrary.OpenLibraryEditionDTO;
import com.booktool.backend.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BookEnrichmentService {

    private final BookRepository bookRepository;
    private final EnrichmentStatusService statusService;
    private final OpenLibraryClient openLibraryClient;
    private final CompensationCalculator calculator;
    private final IsbnService isbnService;

    private static final Pattern YEAR_PATTERN = Pattern.compile("(18|19|20)\\d{2}");

    public BookEnrichmentService(
            BookRepository bookRepository,
            OpenLibraryClient openLibraryClient,
            CompensationCalculator calculator,
            IsbnService isbnService,
            EnrichmentStatusService statusService
    ) {
        this.bookRepository = bookRepository;
        this.openLibraryClient = openLibraryClient;
        this.calculator = calculator;
        this.isbnService = isbnService;
        this.statusService = statusService;
    }

    public void enrichBooks() {
        boolean anyChange = false;
        List<Book> books = bookRepository.findAll();

        for (Book book : books) {
            if (!needsEnrichment(book)) continue;

            OpenLibraryEditionDTO dto = openLibraryClient.fetchByIsbn(isbnService.clean(book.getIsbn()));

            if (dto == null) continue;

            boolean recalculate = false;

            Integer year = extractYear(dto.getPublishDate());
            if (book.getYear() == null && year != null){
                    book.setYear(year);
                    recalculate = true;
            }

            if(book.getPages() == null && dto.getNumberOfPages() != null){
                book.setPages(dto.getNumberOfPages());
                recalculate = true;
            }

            if (book.getLanguage() == null && dto.getLanguages() != null && !dto.getLanguages().isEmpty()) {
                Language lang = Language.mapLanguage(dto.getLanguages().get(0).getCode());
                if (lang != null){
                    book.setLanguage(lang);
                    recalculate = true;
                }
            }

            if (book.getPublisher() == null && dto.getPublishers() != null && !dto.getPublishers().isEmpty()){
                String publisher = dto.getPublishers().get(0);
                book.setPublisher(publisher);
                recalculate = true;
            }

            if (book.getOpenLibraryLink() == null && dto.getOpenLibraryLink() != null){
                book.setOpenLibraryLink(dto.getOpenLibraryLink());
                recalculate = true;
            }

            if (recalculate){
                anyChange = true;
                book.setCompensation(calculator.calculate(book));
                bookRepository.save(book);
            }
        }

        if (anyChange) {
            statusService.markEnriched();
        }
    }



    private boolean needsEnrichment(Book book) {
        return book.getYear() == null
                || book.getPages() == null
                || book.getLanguage() == null
                || book.getPublisher() == null
                || book.getOpenLibraryLink() == null;
    }


    private Integer extractYear(String publishDate) {
        if (publishDate == null || publishDate.isBlank()) {
            return null;
        }

        Matcher matcher = YEAR_PATTERN.matcher(publishDate);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }

        return null;
    }
}