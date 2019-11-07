package com.github.kmizu.nub2;

import java.util.Arrays;

import static com.github.kmizu.nub2.Ast.*;

public class Main {
    public static void main(String[] args) {
        Evaluator evaluator = new Evaluator();
        evaluator.evaluate(
                new Block(
                        Arrays.asList(new Expression[]{
                                new PrintlnExpression(
                                        new StringLiteral("Hello, World")
                                )
                        })
                )
        );
    }
}
