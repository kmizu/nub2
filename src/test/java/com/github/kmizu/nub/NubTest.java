package com.github.kmizu.nub;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

import java.util.Arrays;

import static com.github.kmizu.nub.Ast.*;

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
        assertEquals("Hello, World!", eval(new StringLiteral("Hello, World!")));
    }

    @Test
    public void testPrintln() {
        assertEquals(
                "Hello, World!",
                eval(new PrintlnExpression(new StringLiteral("Hello, World!")))
        );
    }

    @Test
    public void test1Plus1() {
        assertEquals(
                2,
                eval(new BinaryOperation("+", new IntLiteral(1), new IntLiteral(1)))
        );
    }

    @Test
    public void test1Minus1() {
        assertEquals(
                0,
                eval(new BinaryOperation("-", new IntLiteral(1), new IntLiteral(1)))
        );
    }

    @Test
    public void test2Mul2() {
        assertEquals(
                4,
                eval(new BinaryOperation("*", new IntLiteral(2), new IntLiteral(2)))
        );
    }

    @Test
    public void test6Div2() {
        assertEquals(
                3,
                eval(new BinaryOperation("/", new IntLiteral(6), new IntLiteral(2)))
        );
    }
}
