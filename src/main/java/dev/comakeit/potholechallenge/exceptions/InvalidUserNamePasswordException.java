package dev.comakeit.potholechallenge.exceptions;

public class InvalidUserNamePasswordException extends RuntimeException {
    public InvalidUserNamePasswordException() {
        super("Username/Password is Invalid");
    }
}
