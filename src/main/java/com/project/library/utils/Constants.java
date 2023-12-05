package com.project.library.utils;

public class Constants {
    public static final String JWT_SECRET = "a5fd3e3e5d1b75e5d7b7f1e3ccdbcaaa0df8e9ac1a4e5df66c603f54d464743d";

    public static final Integer BOOK_UNAVAILABLE = 0;
    public static final Integer BOOK_AVAILABLE = 1;

    public enum Role {
        USER,
        ADMIN
    }
}
