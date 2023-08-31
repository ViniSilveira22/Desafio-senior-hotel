package com.checkin.checkin.exceptions;

public class GuestNotFoundException extends RuntimeException {
    public GuestNotFoundException(Long id) {
        super("Guest " + id + " not found");
    }

}
