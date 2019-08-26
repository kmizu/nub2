package com.github.kmizu.nub2;

public final class Type {
    public final String name;
    private Type(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static final Type INT = new Type("Int");
    public static final Type STRING = new Type("String");
    public static final Type BOOLEAN = new Type("Boolean");
}
