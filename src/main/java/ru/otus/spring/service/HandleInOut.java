package ru.otus.spring.service;

public interface HandleInOut {
    void resetSystemIn();

    String in();

    void out(String s);

    void outAndVk(String s);
}
