package dev.comakeit.potholechallenge.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String username) {
        super("Username: " + username + " already exists try another one");
    }
}
