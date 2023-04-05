package ru.otus.spring.domain.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class AuthorDto {
    private Long id;
    private String name;
}
