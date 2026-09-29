package com.todoist.web.globalException.exceptions;

public class UserAlreadyExists extends RuntimeException{
    public UserAlreadyExists(String msg){
        super(msg);
    }
}
