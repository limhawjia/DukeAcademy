package com.dukeacademy.solution.models;

/**
 * Represents a compile error when compiling a user's program into a Java class file.
 */
public class CompileError {
    private final String errorMessage;

    /**
     * Instantiates a new Compile error.
     *
     * @param errorMessage the error message
     */
    public CompileError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Gets error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}