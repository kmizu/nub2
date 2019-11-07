package com.github.kmizu.nub2;

public class UnimplementedException extends RuntimeException {
    public UnimplementedException(String feature) {
        super("Feature `" + feature + "` is not implemented yet");
    }
}
