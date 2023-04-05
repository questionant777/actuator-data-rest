package ru.otus.spring.service;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.domain.dto.AuthorDto;
import ru.otus.spring.domain.dto.BookCommentDto;
import ru.otus.spring.domain.dto.BookDto;
import ru.otus.spring.domain.dto.GenreDto;

public interface MappingService {
    BookDto toDto(Book book);
    BookCommentDto toDto(BookComment comment);
    Book toDomainObject(BookDto bookDto);
    AuthorDto toDto(Author author);
    Author toDomainObject(AuthorDto authorDto);
    GenreDto toDto(Genre genre);
    Genre toDomainObject(GenreDto genreDto);
}
