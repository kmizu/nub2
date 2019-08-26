package com.github.kmizu.nub2;

import java.util.Arrays;
import java.util.List;

public class Collections {
    public static <T> List<T> listOf(T... elements) {
        return Arrays.asList(elements);
    }
}
