package org.main_java.backend_springboot_aspectos.util;


public class UserValidationException extends RuntimeException {
    public UserValidationException(String message) {
        super(message);
    }
}