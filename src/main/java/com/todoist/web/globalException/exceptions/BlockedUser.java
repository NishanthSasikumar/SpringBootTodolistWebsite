package com.todoist.web.globalException.exceptions;

public class BlockedUser extends RuntimeException {
    public BlockedUser(String message) {
        super(message);
    }
}
