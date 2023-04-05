package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.exception.BookCommentNotFoundException;
import ru.otus.spring.repository.BookCommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookCommentServiceImplTest {

    @Mock
    BookCommentRepository bookCommentJpa;
    @Mock
    BookService bookService;
    @InjectMocks
    BookCommentServiceImpl service;

    public static final long NEW_BOOK_COMMENT_ID = 1L;
    public static final String NEW_BOOK_COMMENT = "any book comment";
    public static final Long EXISTING_BOOK_COMMENT_ID = 2L;
    public static final String EXISTING_BOOK_COMMENT = "exists book comment";
    public static final String GENRE_NAME = "genre book name";
    public static final long AUTHOR_ID = 2L;
    public static final long GENRE_ID = 3L;
    public static final long DELETE_BOOK_ID = 4L;
    public static final long EXISTING_BOOK_ID = 5L;
    public static final String EXISTING_BOOK_NAME = "exists book name";
    public static final long NOT_EXISTING_BOOK_ID = 6L;

    @Test
    void updateBookCommentExistingBookTest() {
        Book existingBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, null, null, null);

        BookComment expectedBookCom = new BookComment(EXISTING_BOOK_COMMENT_ID, NEW_BOOK_COMMENT, existingBook);

        when(bookCommentJpa.save(any()))
                .thenReturn(expectedBookCom);
        when(bookCommentJpa.findById(EXISTING_BOOK_COMMENT_ID))
                .thenReturn(Optional.of(expectedBookCom));

        BookComment actualBookComment = service.update(expectedBookCom);

        assertThat(actualBookComment)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedBookCom);
    }

    @Test
    void findByIdAllCorrectiveTest() {
        Book existingBook = new Book(EXISTING_BOOK_ID, null, null, null, null);

        BookComment expectedBookCom = new BookComment(EXISTING_BOOK_COMMENT_ID, EXISTING_BOOK_COMMENT, existingBook);

        when(bookCommentJpa.findById(EXISTING_BOOK_COMMENT_ID))
                .thenReturn(Optional.of(expectedBookCom));

        BookComment foundBookCom = service.findById(EXISTING_BOOK_COMMENT_ID);

        assertThat(foundBookCom).usingRecursiveComparison().isEqualTo(expectedBookCom);
    }

    @Test
    void findByIdExceptionNotExistingBookTest() {
        assertThatCode(() -> service.findById(NOT_EXISTING_BOOK_ID))
                .isInstanceOf(BookCommentNotFoundException.class);
    }

    @Test
    void findByBookIdAllCorrectiveTest() {
        Book existingBook = new Book(EXISTING_BOOK_ID, null, null, null, null);

        BookComment bookComment = new BookComment(EXISTING_BOOK_COMMENT_ID, EXISTING_BOOK_COMMENT, existingBook);

        List<BookComment> expectedBookCommentList = new ArrayList<>();
        expectedBookCommentList.add(bookComment);

        when(bookService.findById(EXISTING_BOOK_ID))
                .thenReturn(existingBook);
        when(bookCommentJpa.findByBookId(EXISTING_BOOK_ID))
                .thenReturn(expectedBookCommentList);

        List<BookComment> actualBookComList = service.findByBookId(EXISTING_BOOK_ID);

        assertThat(actualBookComList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(bookComment);
    }
}
