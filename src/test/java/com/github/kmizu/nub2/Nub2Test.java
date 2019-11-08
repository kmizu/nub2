package com.github.kmizu.nub2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

import java.util.Arrays;

import static com.github.kmizu.nub2.Ast.*;

@RunWith(JUnit4.class)
public class Nub2Test {
    private static Object eval(Expression input) {
        return new Evaluator().evaluate(
                new Block(
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
                eval(new BinaryExpression("+", new IntLiteral(1), new IntLiteral(1)))
        );
    }

    @Test
    public void test1Minus1() {
        assertEquals(
                0,
                eval(new BinaryExpression("-", new IntLiteral(1), new IntLiteral(1)))
        );
    }

    @Test
    public void test2Mul2() {
        assertEquals(
                4,
                eval(new BinaryExpression("*", new IntLiteral(2), new IntLiteral(2)))
        );
    }

    @Test
    public void test6Div2() {
        assertEquals(
                3,
                eval(new BinaryExpression("/", new IntLiteral(6), new IntLiteral(2)))
        );
    }

    @Test
    public void testLet() {
        assertEquals(
                10,
                eval(new Block(
                        new LetExpression("x", new IntLiteral(10)),
                        new Identifier("x")
                ))
        );
    }

    @Test
    public void testAssignment() {
        assertEquals(
                20,
                eval(new Block(
                        new LetExpression("x", new IntLiteral(10)),
                        new AssignmentExpression("x", new IntLiteral(20)),
                        new Identifier("x")
                ))
        );
    }

    @Test
    public void testWhile() {
        assertEquals(
                10,
                eval(
                        new Block(
                                new LetExpression("x", new IntLiteral(0)),
                                new WhileExpression(
                                        new BinaryExpression("<", new Identifier("x"), new IntLiteral(10)),
                                        new AssignmentExpression(
                                                "x",
                                                new BinaryExpression("+", new Identifier("x"), new IntLiteral(1))
                                        )
                                ),
                                new Identifier("x")
                        )
                )
        );
    }

    @Test
    public void testIf() {
        assertEquals(
                "1 < 2",
                eval(
                        new IfExpression(
                                new BinaryExpression("<", new IntLiteral(1), new IntLiteral(2)),
                                Arrays.asList(new StringLiteral("1 < 2")),
                                Arrays.asList(new StringLiteral("1 >= 2"))
                        )
                )
        );
    }
}

