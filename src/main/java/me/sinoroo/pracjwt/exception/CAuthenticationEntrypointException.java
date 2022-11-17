package me.sinoroo.pracjwt.exception;

public class CAuthenticationEntrypointException extends RuntimeException{
    
    public CAuthenticationEntrypointException(){
        super();
    }

    public CAuthenticationEntrypointException(String message, Throwable cause) {
        super(message, cause);
    }
    public CAuthenticationEntrypointException(String message) {
        super(message);
    }
    public CAuthenticationEntrypointException(Throwable cause) {
        super(cause);
    }
}
