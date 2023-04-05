package ru.otus.spring.repository;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DataJpaTest
public class BookCommentRepositoryTests {
    @Autowired
    private BookCommentRepository repositoryJpa;

    @Autowired
    private TestEntityManager em;

    private static final Long EXISTING_BOOK_COMMENT_ID = 1L;
    private static final String EXISTING_BOOK_COMMENT_NAME = "comment11";

    @Test
    void findByIdTest() {
        val actualBookComOpt = repositoryJpa.findById(EXISTING_BOOK_COMMENT_ID);
        val expectedAuthor = em.find(BookComment.class, EXISTING_BOOK_COMMENT_ID);

        assertThat(actualBookComOpt).isPresent().get()
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);
    }

    @Test
    void saveTest() {
        BookComment bookComment = new BookComment(null, "comment", Book.builder().id(1L).build());
        BookComment savedBookComment = repositoryJpa.save(bookComment);

        BookComment actualBookComment = repositoryJpa.findById(savedBookComment.getId()).orElse(new BookComment());
        assertThat(actualBookComment).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(bookComment);
    }

    @Test
    void getByIdExistingCommentTest() {
        BookComment actualBookComment = repositoryJpa.findById(EXISTING_BOOK_COMMENT_ID).orElse(new BookComment());
        assertThat(actualBookComment.getComment()).isEqualTo(EXISTING_BOOK_COMMENT_NAME);
    }

    @Test
    void deleteByIdOkTest() {
        assertThatCode(() -> repositoryJpa.findById(EXISTING_BOOK_COMMENT_ID))
                .doesNotThrowAnyException();

        repositoryJpa.deleteById(EXISTING_BOOK_COMMENT_ID);

        Optional<BookComment> bookCommentOpt = repositoryJpa.findById(EXISTING_BOOK_COMMENT_ID);

        assertThat(bookCommentOpt.isPresent()).isFalse();
    }

    @Test
    void getAllTest() {
        List<BookComment> actualBookCommentList = repositoryJpa.findAll();

        assertThat(actualBookCommentList.stream().anyMatch(item -> EXISTING_BOOK_COMMENT_NAME.equals(item.getComment())));
    }

}
