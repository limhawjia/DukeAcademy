package com.dukeacademy.model.question;

import static com.dukeacademy.commons.util.AppUtil.checkArgument;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Question's difficulty in the question bank.
 * Guarantees: immutable; is valid as declared in {@link #isValidDifficulty(String)}
 */
public class Difficulty {

    public static final String MESSAGE_CONSTRAINTS = "Addresses can take any values, and it should not be blank";

    /*
     * The first character of the difficulty must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Difficulty}.
     *
     * @param difficulty A valid difficulty.
     */
    public Difficulty(String difficulty) {
        requireNonNull(difficulty);
        checkArgument(isValidDifficulty(difficulty), MESSAGE_CONSTRAINTS);
        value = difficulty;
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidDifficulty(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Difficulty // instanceof handles nulls
                && value.equals(((Difficulty) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}