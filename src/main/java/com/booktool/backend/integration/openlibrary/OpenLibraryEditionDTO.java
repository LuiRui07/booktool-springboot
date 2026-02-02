package com.booktool.backend.integration.openlibrary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenLibraryEditionDTO {

    private String title;

    @JsonProperty("publish_date")
    private String publishDate;

    private List<String> publishers;

    @JsonProperty("isbn_10")
    private List<String> isbn10;

    @JsonProperty("key")
    private String openLibraryKey;

    private List<LanguageDTO> languages;

    private String pagination;

    public String getTitle() {
        return title;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public List<String> getPublishers() {
        return publishers;
    }

    public List<String> getIsbn10() {
        return isbn10;
    }

    public List<LanguageDTO> getLanguages() {
        return languages;
    }

    public String getPagination() {
        return pagination;
    }

    public Integer getNumberOfPages() {
        if (pagination == null) return null;
        return Integer.parseInt(pagination.replaceAll("\\D", ""));
    }

    public String getOpenLibraryLink() {
        if (openLibraryKey == null) return null;
        return "https://openlibrary.org" + openLibraryKey;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LanguageDTO {
        private String key;

        public String getKey() {
            return key;
        }

        public String getCode() {
            return key != null ? key.replace("/languages/", "") : null;
        }
    }
}