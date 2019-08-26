package com.github.kmizu.nub;

import java.util.Arrays;
import java.util.List;

import static com.github.kmizu.nub.AstNode.*;

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
