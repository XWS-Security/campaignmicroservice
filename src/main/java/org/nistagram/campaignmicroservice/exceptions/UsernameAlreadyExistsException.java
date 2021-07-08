package org.nistagram.campaignmicroservice.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException() {
        super("Username is taken, please choose another one.");
    }
}
