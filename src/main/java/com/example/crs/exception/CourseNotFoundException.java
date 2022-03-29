package com.example.crs.exception;

public class CourseNotFoundException extends IllegalStateException {
    private static final long serialVersionUID = 1L;

    public CourseNotFoundException(String message) {
        super(message);
    }

}
