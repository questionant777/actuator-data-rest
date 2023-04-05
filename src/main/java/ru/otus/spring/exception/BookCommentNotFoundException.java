package ru.otus.spring.exception;

public class BookCommentNotFoundException extends RuntimeException {
    public BookCommentNotFoundException(Long bookCommentId){
        super(String.format("Комментарий с идентификатором (%d) не найден", bookCommentId));
    }
}
