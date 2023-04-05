package ru.otus.spring.repository;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.*;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class GenreRepositoryTest {

    private static final Long EXISTING_GENRE_ID = 2L;
    private static final String EXISTING_GENRE_NAME = "detective";

    @Autowired
    private GenreRepository repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @Test
    void saveTest() {
        Genre newGenre = new Genre(null, "poema");
        Genre savedGenre = repositoryJpa.save(newGenre);
        Genre actualGenre = repositoryJpa.findById(savedGenre.getId()).orElse(new Genre());
        assertThat(actualGenre).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(newGenre);
    }

    @Test
    void findByIdTest() {
        val actualGenreOpt = repositoryJpa.findById(EXISTING_GENRE_ID);
        val expectedGenre = em.find(Genre.class, EXISTING_GENRE_ID);
        assertThat(actualGenreOpt).isPresent().get()
                .usingRecursiveComparison()
                .isEqualTo(expectedGenre);
    }

    @Test
    void findByIdExistingGenreTest() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Genre actualGenre = repositoryJpa.findById(EXISTING_GENRE_ID).orElse(new Genre());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void deleteByIdOkTest() {
        assertThatCode(() -> repositoryJpa.findById(EXISTING_GENRE_ID))
                .doesNotThrowAnyException();

        repositoryJpa.deleteById(EXISTING_GENRE_ID);

        Optional<Genre> genreOpt = repositoryJpa.findById(EXISTING_GENRE_ID);

        assertThat(genreOpt.isPresent()).isFalse();
    }

    @Test
    void getAllTest() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        List<Genre> actualGenreList = repositoryJpa.findAll();
        assertThat(actualGenreList.stream().anyMatch(item -> EXISTING_GENRE_NAME.equals(item.getName())));
    }

}
