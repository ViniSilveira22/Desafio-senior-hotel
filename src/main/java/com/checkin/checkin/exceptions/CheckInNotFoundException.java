package com.checkin.checkin.exceptions;

public class CheckInNotFoundException extends RuntimeException {
    public CheckInNotFoundException(Long id) {
        super("Check-in " + id + " not found");
    }
}
