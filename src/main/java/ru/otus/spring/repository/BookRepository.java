package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByName(String name);

    int countById(Long bookId);

    @EntityGraph(attributePaths = {"author", "genre"})
    //@Query("select b from Book b join fetch b.author join fetch b.genre")  //for inner join
    @Override
    List<Book> findAll();

}
