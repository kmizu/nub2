package com.github.kmizu.nub2;

import static com.github.kmizu.nub2.Ast.*;

public class Main {
    public static void main(String[] args) {
        Evaluator evaluator = new Evaluator();
        evaluator.eval(
                new Block(
                        new PrintlnExpression(
                                new StringLiteral("Hello, World")
                        )
                )
        );
    }
}
