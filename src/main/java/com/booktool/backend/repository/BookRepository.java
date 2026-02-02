package com.booktool.backend.repository;

import com.booktool.backend.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book,Long> {

    boolean existsByIsbn(String isbn);
}
