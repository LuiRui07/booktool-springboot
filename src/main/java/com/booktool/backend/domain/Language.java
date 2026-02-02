package com.booktool.backend.domain;

public enum Language {

    ES("Spanish"),
    EN("English"),
    DE("Deutsch"),
    UNKNOWN("Unknown");

    private final String label;

    Language(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Language mapLanguage(String code) {

        if (code == null) return UNKNOWN;

        return switch (code) {
            case "ger", "deu" -> DE;
            case "eng" -> EN;
            case "spa", "es" -> ES;
            default -> UNKNOWN;
        };
    }


}