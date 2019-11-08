package com.github.kmizu.nub2;

public class NotImplementedException extends RuntimeException {
    public NotImplementedException(String feature) {
        super("Feature `" + feature + "` is not implemented yet");
    }
}
