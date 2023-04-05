package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.repository.AuthorRepository;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getOrSaveByName(String name) {
        Optional<Author> authorOpt = authorRepository.findByName(name);
        return authorOpt.orElseGet(() -> authorRepository.save(Author.builder().name(name).build()));
    }
}
