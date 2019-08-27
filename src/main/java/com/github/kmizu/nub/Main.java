package com.github.kmizu.nub;

import java.util.Arrays;

import static com.github.kmizu.nub.Ast.*;

public class Main {
    public static void main(String[] args) {
        Evaluator evaluator = new Evaluator();
        evaluator.evaluate(
                new ExpressionList(
                        Arrays.asList(new Expression[]{
                                new PrintlnExpression(
                                        new StringLiteral("Hello, World")
                                )
                        })
                )
        );
    }
}
