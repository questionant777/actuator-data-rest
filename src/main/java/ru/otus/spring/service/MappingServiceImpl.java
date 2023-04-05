package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.domain.dto.AuthorDto;
import ru.otus.spring.domain.dto.BookCommentDto;
import ru.otus.spring.domain.dto.BookDto;
import ru.otus.spring.domain.dto.GenreDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MappingServiceImpl implements MappingService{

    public BookDto toDto(Book book) {
        List<BookComment> bookCommentList = Optional.ofNullable(book.getBookCommentList()).orElse(new ArrayList<>());
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .author(toDto(book.getAuthor()))
                .genre(toDto(book.getGenre()))
                .bookCommentList(
                        bookCommentList.stream()
                                .map(this::toDto)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public BookCommentDto toDto(BookComment comment) {
        return BookCommentDto.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .build();
    }

    public Book toDomainObject(BookDto bookDto) {
        Book result = Book.builder()
                .id(bookDto.getId())
                .name(bookDto.getName())
                .author(toDomainObject(bookDto.getAuthor()))
                .genre(toDomainObject(bookDto.getGenre()))
                .build();

        List<BookCommentDto> bookCommentDtoList = Optional.ofNullable(bookDto.getBookCommentList()).orElse(new ArrayList<>());

        List<BookComment> bookCommentList = bookCommentDtoList
                .stream()
                .map(com -> new BookComment(com.getId(), com.getComment(), result))
                .collect(Collectors.toList())
        ;

        result.setBookCommentList(bookCommentList);

        return result;
    }

    public AuthorDto toDto(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .build();
    }

    public Author toDomainObject(AuthorDto authorDto) {
        return Author.builder()
                .id(authorDto.getId())
                .name(authorDto.getName())
                .build();
    }

    public GenreDto toDto(Genre genre) {
        return GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }

    public Genre toDomainObject(GenreDto genreDto) {
        return Genre.builder()
                .id(genreDto.getId())
                .name(genreDto.getName())
                .build();
    }
}
