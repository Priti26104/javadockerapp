package com.example.app;

import java.time.LocalDateTime;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello from Java Docker App! Time: " + LocalDateTime.now());
        // keep the app running for demo (optional) - comment out if you want a quick exit
        try {
            Thread.sleep(1000); // sleeps 1 second then exits
        } catch (InterruptedException e) {
            // ignore
        }
    }
}
