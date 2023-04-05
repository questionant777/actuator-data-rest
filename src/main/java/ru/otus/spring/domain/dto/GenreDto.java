package ru.otus.spring.domain.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class GenreDto {
    private Long id;
    private String name;
}
