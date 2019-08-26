package com.github.kmizu.nub;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class NubTest {
    static Object eval(AstNode.ExpressionList input) throws Exception {
        return new Evaluator().evaluate(input);
    }
}
