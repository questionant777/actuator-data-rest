package ru.otus.spring.actuator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import ru.otus.spring.repository.BookRepository;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OverloadLibraryHealthIndicatorTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    OverloadLibraryHealthIndicator service;

    @Test
    void healthUpTest() {
        Health expected = Health.up().withDetail("message", "Место в библиотеке еще есть!").build();

        when(bookRepository.count())
                .thenReturn(3L);

        Health result = service.health();

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void healthDownTest() {
        Health expected = Health.down()
                .status(Status.DOWN)
                .withDetail("message", "Место в библиотеке закончилось!")
                .build();;

        when(bookRepository.count())
                .thenReturn(31L);

        Health result = service.health();

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }
}