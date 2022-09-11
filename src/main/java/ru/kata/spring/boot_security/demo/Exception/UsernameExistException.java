package ru.kata.spring.boot_security.demo.Exception;

import org.springframework.dao.DataIntegrityViolationException;

public class UsernameExistException extends DataIntegrityViolationException {
    public UsernameExistException(String msg) {
        super(msg);
    }
}
