package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto insert(BookDto bookDto);
    BookDto update(BookDto bookDto);
    void deleteById(Long bookId);
    Book findById(Long bookId);
    BookDto findByIdInDto(Long bookId);
    Book findByName(String bookName);
    List<Book> findAll();
    List<BookDto> findAllInDto();
}
