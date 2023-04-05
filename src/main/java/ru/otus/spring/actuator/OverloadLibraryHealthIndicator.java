package ru.otus.spring.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.spring.repository.BookRepository;

@Component
public class OverloadLibraryHealthIndicator implements HealthIndicator {

    private final BookRepository bookRepository;

    public OverloadLibraryHealthIndicator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Health health() {
        boolean isOverloadLibrary = bookRepository.count() > 5;
        if (!isOverloadLibrary) {
            return Health.up().withDetail("message", "Место в библиотеке еще есть!").build();
        } else {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Место в библиотеке закончилось!")
                    .build();
        }
    }
}
