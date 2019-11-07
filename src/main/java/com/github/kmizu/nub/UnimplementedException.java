package com.github.kmizu.nub;

public class UnimplementedException extends RuntimeException {
    public UnimplementedException(String feature) {
        super("Feature `" + feature + "` is not implemented yet");
    }
}
