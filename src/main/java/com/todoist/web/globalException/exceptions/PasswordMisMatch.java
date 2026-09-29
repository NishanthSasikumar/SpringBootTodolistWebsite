package com.todoist.web.globalException.exceptions;

public class PasswordMisMatch extends RuntimeException{
    public PasswordMisMatch(String msg) {
        super(msg);
    }
}
