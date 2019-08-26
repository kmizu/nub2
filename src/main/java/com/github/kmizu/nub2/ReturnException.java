package com.github.kmizu.nub2;

public class ReturnException extends RuntimeException {
    private final Object value;
    public ReturnException(Object value) {
        super("return");
        this.value = value;
    }
    public Object value() {
        return value;
    }
}
