package com.booktool.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "books",
        uniqueConstraints = @UniqueConstraint(columnNames = "isbn")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 20)
    private String isbn;

    private Integer year;

    private Integer pages;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Enumerated(EnumType.STRING)
    private Category subjectCategory;

    private String publisher;

    private String openLibraryLink;

    @Column(nullable = false)
    private Double compensation;
}