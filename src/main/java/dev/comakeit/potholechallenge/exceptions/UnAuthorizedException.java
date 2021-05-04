package dev.comakeit.potholechallenge.exceptions;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException() {
        super("UnAuthorized Request");
    }
}
