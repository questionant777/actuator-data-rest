package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.dto.AuthorDto;
import ru.otus.spring.domain.dto.BookDto;
import ru.otus.spring.domain.dto.GenreDto;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exception.BookNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final MappingService mappingService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, GenreService genreService, MappingService mappingService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
        this.mappingService = mappingService;
    }

    @Transactional
    @Override
    public BookDto insert(BookDto bookDto) {
        if (bookDto.getId() != null && bookDto.getId() != 0)
            throw new RuntimeException("При добавлении книги идентификатор должен быть пустым");
        return save(bookDto);
    }

    @Transactional
    @Override
    public BookDto update(BookDto bookDto) {
        Long bookId = bookDto.getId();

        if (bookRepository.countById(bookId) == 0)
            throw new BookNotFoundException(bookId);

        return save(bookDto);
    }

    private BookDto save(BookDto bookDto) {
        AuthorDto authorDto = Optional.ofNullable(bookDto.getAuthor()).orElse(new AuthorDto());
        GenreDto genreDto = Optional.ofNullable(bookDto.getGenre()).orElse(new GenreDto());

        if (authorDto.getId() == null || authorDto.getId() == 0)
            authorDto = mappingService.toDto(authorService.getOrSaveByName(authorDto.getName()));

        if (genreDto.getId() == null || genreDto.getId() == 0)
            genreDto = mappingService.toDto(genreService.getOrSaveByName(genreDto.getName()));

        Book saved = bookRepository.save(Book.builder()
                .id(bookDto.getId())
                .name(bookDto.getName())
                .author(mappingService.toDomainObject(authorDto))
                .genre(mappingService.toDomainObject(genreDto))
                .build());

        return mappingService.toDto(saved);
    }

    @Override
    public void deleteById(Long bookId) {
        if (bookRepository.countById(bookId) == 0)
            throw new BookNotFoundException(bookId);
        bookRepository.deleteById(bookId);
    }

    @Override
    public Book findById(Long bookId) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isPresent())
            return bookOpt.get();
        else
            throw new BookNotFoundException(bookId);
    }

    @Transactional(readOnly = true)
    @Override
    public BookDto findByIdInDto(Long bookId) {
        return mappingService.toDto(findById(bookId));
    }

    @Override
    public Book findByName(String bookName) {
        Optional<Book> bookOpt = bookRepository.findByName(bookName);
        if (bookOpt.isPresent())
            return bookOpt.get();
        else
            throw new BookNotFoundException(bookName);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAllInDto() {
        return findAll()
                .stream()
                .map(mappingService::toDto)
                .collect(Collectors.toList());
    }
}
