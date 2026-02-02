package com.booktool.backend.domain;

public enum Category {

    FANTASY("Fantasy"),
    SCIENCE_FICTION("Science Fiction"),
    MYSTERY("Mystery"),
    THRILLER("Thriller"),
    ROMANCE("Romance"),
    DRAMA("Drama"),
    HORROR("Horror"),
    ADVENTURE("Adventure"),
    HISTORICAL("Historical"),
    BIOGRAPHY("Biography"),
    POETRY("Poetry"),
    CHILDREN("Children"),
    YOUNG_ADULT("Young Adult"),
    SELF_HELP("Self Help"),
    PHILOSOPHY("Philosophy"),
    TECHNOLOGY("Technology"),
    SCIENCE("Science"),
    ART("Art"),
    COMICS("Comics");

    private final String label;

    Category(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}