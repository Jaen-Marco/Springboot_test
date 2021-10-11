package org.jaen.springtest.springboot_test.exceptions;

public class DineroInsuficiente extends RuntimeException {
    public DineroInsuficiente(String message) {
        super(message);
    }
}
