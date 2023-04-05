package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.domain.dto.BookCommentDto;
import ru.otus.spring.exception.BookCommentNotFoundException;
import ru.otus.spring.repository.BookCommentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookCommentServiceImpl implements BookCommentService {

    private final BookCommentRepository bookCommentRepository;
    private final BookService bookService;
    private final MappingService mappingService;

    public BookCommentServiceImpl(BookCommentRepository bookCommentRepository, BookService bookService, MappingService mappingService) {
        this.bookCommentRepository = bookCommentRepository;
        this.bookService = bookService;
        this.mappingService = mappingService;
    }

    @Transactional
    @Override
    public BookComment update(BookComment bookComment) {
        Long bookCommentId = bookComment.getId();

        Optional<BookComment> foundBookCommentOpt = bookCommentRepository.findById(bookCommentId);

        if (foundBookCommentOpt.isPresent()) {
            bookComment.setBook(foundBookCommentOpt.get().getBook());
        } else {
            throw new BookCommentNotFoundException(bookCommentId);
        }

        return bookCommentRepository.save(bookComment);
    }

    @Transactional
    @Override
    public BookComment insert(BookComment bookComment) {
        if (bookComment.getId() != null && bookComment.getId() != 0)
            throw new RuntimeException("При добавлении комментария идентификатор должен быть пустым");

        Book book = Optional.ofNullable(bookComment.getBook()).orElse(new Book());

        Book foundBook;

        if (book.getId() == null) {
            foundBook = bookService.findByName(book.getName());
        } else {
            foundBook = bookService.findById(book.getId());
        }

        bookComment.setBook(foundBook);

        return bookCommentRepository.save(bookComment);
    }

    @Override
    public void deleteById(Long bookCommentId) {
        bookCommentRepository.deleteById(bookCommentId);
    }

    @Override
    public BookComment findById(Long bookCommentId) {
        Optional<BookComment> bookCommentOpt = bookCommentRepository.findById(bookCommentId);
        if (bookCommentOpt.isPresent())
            return bookCommentOpt.get();
        else
            throw new BookCommentNotFoundException(bookCommentId);
    }

    @Override
    public List<BookComment> findByBookId(Long bookId) {
        bookService.findById(bookId);
        return bookCommentRepository.findByBookId(bookId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookCommentDto> findByBookIdInDto(Long bookId) {
        return findAll()
                .stream()
                .map(mappingService::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookComment> findAll() {
        return bookCommentRepository.findAll();
    }
}
