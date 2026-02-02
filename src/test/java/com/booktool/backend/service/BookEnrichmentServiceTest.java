package com.booktool.backend.service;

import com.booktool.backend.domain.Book;
import com.booktool.backend.domain.Language;
import com.booktool.backend.integration.openlibrary.OpenLibraryEditionDTO;
import com.booktool.backend.repository.BookRepository;
import com.booktool.backend.domain.compensation.CompensationCalculator;
import com.booktool.backend.integration.openlibrary.OpenLibraryEditionDTO.LanguageDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookEnrichmentServiceTest {

	@Mock
	private BookRepository bookRepository;

	@Mock
	private com.booktool.backend.integration.openlibrary.OpenLibraryClient openLibraryClient;

	@Mock
	private CompensationCalculator calculator;

	@Mock
	private com.booktool.backend.domain.isbn.IsbnService isbnService;

	@InjectMocks
	private BookEnrichmentService service;

	@Test
	void enrichBooks_enrichesAndSaves() {
		Book book = Book.builder()
				.title("Title")
				.isbn("ISBN123")
				.compensation(0.0)
				.year(null)
				.pages(null)
				.language(null)
				.publisher(null)
				.openLibraryLink(null)
				.build();

		when(bookRepository.findAll()).thenReturn(List.of(book));
		when(isbnService.clean("ISBN123")).thenReturn("ISBN123");

		OpenLibraryEditionDTO dto = Mockito.mock(OpenLibraryEditionDTO.class);
		LanguageDTO langDto = Mockito.mock(LanguageDTO.class);

		when(dto.getPublishDate()).thenReturn("Published 1985");
		when(dto.getNumberOfPages()).thenReturn(150);
		when(langDto.getCode()).thenReturn("eng");
		when(dto.getLanguages()).thenReturn(List.of(langDto));
		when(dto.getPublishers()).thenReturn(List.of("Pub"));
		when(dto.getOpenLibraryLink()).thenReturn("https://openlibrary.org/books/OL1M");

		when(openLibraryClient.fetchByIsbn("ISBN123")).thenReturn(dto);
		when(calculator.calculate(any(Book.class))).thenReturn(200.0);

		service.enrichBooks();

		verify(calculator).calculate(book);
		verify(bookRepository).save(book);

		assertEquals(150, book.getPages());
		assertEquals(1985, book.getYear());
		assertEquals(Language.EN, book.getLanguage());
		assertEquals("Pub", book.getPublisher());
		assertEquals("https://openlibrary.org/books/OL1M", book.getOpenLibraryLink());
		assertEquals(200.0, book.getCompensation());
	}

	@Test
	void enrichBooks_noChanges_noSave() {
		Book book = Book.builder()
				.title("Title")
				.isbn("ISBN123")
				.compensation(100.0)
				.year(2000)
				.pages(100)
				.language(Language.EN)
				.publisher("Pub")
				.openLibraryLink("link")
				.build();

		when(bookRepository.findAll()).thenReturn(List.of(book));

		service.enrichBooks();

		verifyNoInteractions(openLibraryClient, calculator, isbnService);
		verify(bookRepository, never()).save(any());
	}
}
