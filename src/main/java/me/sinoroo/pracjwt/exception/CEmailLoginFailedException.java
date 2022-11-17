package me.sinoroo.pracjwt.exception;

public class CEmailLoginFailedException extends RuntimeException{

    public CEmailLoginFailedException(){
        super();
    }

    public CEmailLoginFailedException(String message){
        super(message);
    }
    
    public CEmailLoginFailedException(String message, Throwable cause){
        super(message, cause);
    }

    public CEmailLoginFailedException(Throwable cause) {
        super(cause);
    }
}
