package com.booktool.backend.controller;


import com.booktool.backend.api.dto.EnumDTO;
import com.booktool.backend.domain.Category;
import com.booktool.backend.domain.Language;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class EnumController {

    @GetMapping("/api/categories")
    public List<EnumDTO> getCategories() {
        return Arrays.stream(Category.values())
                .map(c -> new EnumDTO(c.name(), c.getLabel()))
                .toList();
    }

    @GetMapping("/api/languages")
    public List<EnumDTO> getLanguages() {
        return Arrays.stream(Language.values())
                .map(l -> new EnumDTO(l.name(), l.getLabel()))
                .toList();
    }
}
