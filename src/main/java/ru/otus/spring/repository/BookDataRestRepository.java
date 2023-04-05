package ru.otus.spring.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.spring.domain.Book;

import java.util.List;

@RepositoryRestResource
public interface BookDataRestRepository extends PagingAndSortingRepository<Book, Long> {
    @RestResource(path = "names", rel = "names")
    List<Book> findByName(String name);
}
