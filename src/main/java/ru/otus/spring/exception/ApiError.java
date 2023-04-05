package ru.otus.spring.exception;

import lombok.Data;

@Data
public class ApiError {
    private String message;
    private String debugMessage;
    private String path;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
//    private LocalDateTime timestamp;

    public ApiError(String message, String debugMessage, String path) {
        this.message = message;
        this.debugMessage = debugMessage;
        this.path = path;
    }
}