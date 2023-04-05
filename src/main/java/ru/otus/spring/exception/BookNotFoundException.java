package ru.otus.spring.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long bookId){
        super(String.format("Книга с идентификатором (%d) не найдена", bookId));
    }
    public BookNotFoundException(String bookName){
        super(String.format("Книга с наименованием (%s) не найдена", bookName));
    }
}
