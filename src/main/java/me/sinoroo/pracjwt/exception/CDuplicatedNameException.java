package me.sinoroo.pracjwt.exception;

public class CDuplicatedNameException extends RuntimeException{
    
    public CDuplicatedNameException(){
        super();
    }

    public CDuplicatedNameException(String message, Throwable cause) {
        super(message, cause);
    }
    public CDuplicatedNameException(String message) {
        super(message);
    }
    public CDuplicatedNameException(Throwable cause) {
        super(cause);
    }
}
