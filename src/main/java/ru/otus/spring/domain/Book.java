package ru.otus.spring.domain;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name="authorid")
    private Author author;

    @ManyToOne
    @JoinColumn(name="genreid")
    private Genre genre;

    @BatchSize(size = 3)
    @OneToMany(mappedBy = "book", targetEntity = BookComment.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookComment> bookCommentList;

    @Override
    public String toString() {
        String s = "{id=" + id +
                ", name=" + name +
                ", (" + Optional.ofNullable(author).orElse(new Author())+
                "), (" + Optional.ofNullable(genre).orElse(new Genre())+
                "), bookCommentList(";

        if (bookCommentList == null || bookCommentList.isEmpty())
            s += "null";
        else
            s += bookCommentList.stream()
                        .map(c -> "id="+c.getId()+",comment="+c.getComment())
                        .collect(Collectors.joining(","));
        return s + ")}\n";
    }
}
