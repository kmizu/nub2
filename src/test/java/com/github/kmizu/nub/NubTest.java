package com.github.kmizu.nub;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

import java.util.Arrays;

import static com.github.kmizu.nub.AstNode.*;

@RunWith(JUnit4.class)
public class NubTest {
    private static Object eval(Expression input) {
        return new Evaluator().evaluate(
                new ExpressionList(
                        Arrays.asList(input)
                )
        );
    }

    @Test
    public void testStringLiteral() {
        assertEquals("Hello", eval(new StringLiteral("Hello")));
    }

}
