package me.sinoroo.pracjwt.advice.exception;

public class CUserNotFoundException extends RuntimeException{

    public CUserNotFoundException(){
        super();
    }

    public CUserNotFoundException(String message){
        super(message);
    }
}
