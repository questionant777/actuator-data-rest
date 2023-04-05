package ru.otus.spring.service;

import ru.otus.spring.domain.BookComment;
import ru.otus.spring.domain.dto.BookCommentDto;

import java.util.List;

public interface BookCommentService {
    BookComment update(BookComment bookComment);
    BookComment insert(BookComment bookComment);
    void deleteById(Long bookCommentId);
    BookComment findById(Long bookCommentId);
    List<BookComment> findByBookId(Long bookId);
    List<BookCommentDto> findByBookIdInDto(Long bookId);
    List<BookComment> findAll();
}
