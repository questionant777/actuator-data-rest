package ru.otus.spring.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.spring.domain.Genre;

@RepositoryRestResource
public interface GenreDataRestRepository extends PagingAndSortingRepository<Genre, Long> {
}
