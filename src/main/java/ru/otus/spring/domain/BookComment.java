package ru.otus.spring.domain;

import lombok.*;
import ru.otus.spring.domain.dto.BookCommentDto;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookcomment")
public class BookComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name="bookid")
    @ToString.Exclude
    private Book book;

}
