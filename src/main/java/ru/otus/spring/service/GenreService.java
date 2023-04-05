package ru.otus.spring.service;

import ru.otus.spring.domain.Genre;

public interface GenreService {
    Genre getOrSaveByName(String name);
}
