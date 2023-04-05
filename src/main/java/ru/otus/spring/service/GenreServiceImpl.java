package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.domain.Genre;

import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre getOrSaveByName(String name) {
        Optional<Genre> genreOpt = genreRepository.findByName(name);
        return genreOpt.orElseGet(() -> genreRepository.save(Genre.builder().name(name).build()));
    }
}
