package com.booktool.backend.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LanguageTest {

    @Test
    void mapLanguage_null_returnsUnknown() {
        assertEquals(Language.UNKNOWN, Language.mapLanguage(null));
    }

    @Test
    void mapLanguage_codes_mapCorrectly() {
        assertEquals(Language.DE, Language.mapLanguage("ger"));
        assertEquals(Language.DE, Language.mapLanguage("deu"));
        assertEquals(Language.EN, Language.mapLanguage("eng"));
        assertEquals(Language.ES, Language.mapLanguage("spa"));
        assertEquals(Language.ES, Language.mapLanguage("es"));
        assertEquals(Language.UNKNOWN, Language.mapLanguage("zzz"));
    }

    @Test
    void getLabel_returnsNonEmpty() {
        assertNotNull(Language.EN.getLabel());
        assertFalse(Language.EN.getLabel().isEmpty());
    }
}
