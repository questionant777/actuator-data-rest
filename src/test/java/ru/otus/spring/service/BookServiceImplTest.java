package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.domain.dto.AuthorDto;
import ru.otus.spring.domain.dto.BookDto;
import ru.otus.spring.domain.dto.GenreDto;
import ru.otus.spring.exception.BookNotFoundException;
import ru.otus.spring.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MappingServiceImpl.class})
class BookServiceImplTest {

    public static final long NEW_BOOK_ID = 1L;
    public static final String BOOK_NAME = "any book name";
    public static final String NEW_BOOK_NAME = "new other book name";
    public static final String AUTHOR_NAME = "author book name";
    public static final String GENRE_NAME = "genre book name";
    public static final long AUTHOR_ID = 2L;
    public static final long GENRE_ID = 3L;
    public static final long DELETE_BOOK_ID = 4L;
    public static final long EXISTING_BOOK_ID = 5L;
    public static final long NOT_EXISTING_BOOK_ID = 6L;

    @Mock
    BookRepository bookRepository;
    @Mock
    AuthorService authorService;
    @Mock
    GenreService genreService;
    @Autowired
    MappingService mappingService;

    BookServiceImpl service;

    @BeforeEach
    public void setup() {
        service = new BookServiceImpl(
                bookRepository, authorService, genreService, mappingService
        );
    }

    private Book getAnyBook(Long bookId) {
        return new Book(
                bookId,
                BOOK_NAME,
                new Author(AUTHOR_ID, AUTHOR_NAME),
                new Genre(GENRE_ID, GENRE_NAME),
                new ArrayList<>()
        );
    }

    private BookDto getAnyBookDto(Long bookId) {
        return new BookDto(
                bookId,
                BOOK_NAME,
                new AuthorDto(AUTHOR_ID, AUTHOR_NAME),
                new GenreDto(GENRE_ID, GENRE_NAME),
                new ArrayList<>()
        );
    }

    @Test
    void insertBookTest() {
        when(bookRepository.save(any()))
                .thenReturn(getAnyBook(NEW_BOOK_ID));
        when(authorService.getOrSaveByName(AUTHOR_NAME))
                .thenReturn(new Author(AUTHOR_ID, AUTHOR_NAME));

        BookDto newBookDto = getAnyBookDto(null);

        BookDto afterInsBookDto = service.insert(newBookDto);

        assertThat(afterInsBookDto)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(newBookDto);
    }

    @Test
    void updateBookExistingTest() {
        BookDto befUpdBookDto = getAnyBookDto(EXISTING_BOOK_ID);
        befUpdBookDto.setName(NEW_BOOK_NAME);

        Book expectedBook = getAnyBook(EXISTING_BOOK_ID);
        expectedBook.setName(NEW_BOOK_NAME);

        when(bookRepository.save(any()))
                .thenReturn(expectedBook);
        when(authorService.getOrSaveByName(AUTHOR_NAME))
                .thenReturn(new Author(AUTHOR_ID, AUTHOR_NAME));
        when(bookRepository.countById(EXISTING_BOOK_ID))
                .thenReturn(1);

        BookDto actualBookDto = service.update(befUpdBookDto);

        assertThat(actualBookDto)
                .usingRecursiveComparison()
                .isEqualTo(mappingService.toDto(expectedBook));
    }

    @Test
    void updateBookNotFoundExceptionTest() {
        BookDto befUpdBookDto = getAnyBookDto(NOT_EXISTING_BOOK_ID);
        befUpdBookDto.setName(NEW_BOOK_NAME);

        assertThatCode(() -> service.update(befUpdBookDto))
                .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void deleteByIdTest() {
        when(bookRepository.countById(DELETE_BOOK_ID))
                .thenReturn(1);

        service.deleteById(DELETE_BOOK_ID);

        verify(bookRepository, times(1)).deleteById(DELETE_BOOK_ID);
    }

    @Test
    void getByIdExistingBookIdTest() {
        Book expectedBook = getAnyBook(EXISTING_BOOK_ID);

        when(bookRepository.findById(EXISTING_BOOK_ID))
                .thenReturn(Optional.of(expectedBook));

        Book actualBook = service.findById(EXISTING_BOOK_ID);

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void getByIdNotExistingBookIdExceptionTest() {
        assertThatCode(() -> service.findById(NOT_EXISTING_BOOK_ID))
                .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void getAllTest() {
        List<Book> bookList = new ArrayList<>();

        bookList.add(getAnyBook(EXISTING_BOOK_ID));

        when(bookRepository.findAll())
                .thenReturn(bookList);

        Book expectedBook = getAnyBook(EXISTING_BOOK_ID);

        List<Book> actualBookList = service.findAll();

        assertThat(actualBookList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedBook);
    }
}