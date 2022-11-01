package com.ds.twophasecommit.exceptions;

public class TimedOutException extends Exception {
    public TimedOutException(String message) {
        super(message);
    }
}
