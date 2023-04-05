package ru.otus.spring.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class HandleInOutImpl implements HandleInOut {
    private Scanner scanner;

    public HandleInOutImpl() {
        resetSystemIn();
    }

    public void resetSystemIn() {
        this.scanner = new Scanner(System.in);
    }

    public String in() {
        return this.scanner.nextLine().trim();
    }

    public void out(String s){
        System.out.print(s);
    }

    public void outAndVk(String s) {
        System.out.println(s);
    }
}
