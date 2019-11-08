package com.github.kmizu.nub2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import static com.github.kmizu.nub2.Ast.Factory.*;
import static com.github.kmizu.nub2.Ast.*;


@RunWith(JUnit4.class)
public class Nub2Test {
    private static Object eval(Expression input) {
        return new Evaluator().eval(new Block(input));
    }

    @Test
    public void testStringLiteral() {
        assertEquals("Hello, World!", eval(tString("Hello, World!")));
    }

    @Test
    public void testPrintln() {
        assertEquals(
                "Hello, World!",
                eval(tPrintln(tString("Hello, World!")))
        );
    }

    @Test
    public void test1Plus1Is2() {
        assertEquals(
                2,
                eval(tAdd(tInt(1), tInt(1)))
        );
    }

    @Test
    public void test1Minus1Is0() {
        assertEquals(
                0,
                eval(tSubtract(tInt(1), tInt(1)))
        );
    }

    @Test
    public void test2Mul2Is4() {
        assertEquals(
                4,
                eval(tMultiply(tInt(2), tInt(2)))
        );
    }

    @Test
    public void test6Div2Is3() {
        assertEquals(
                3,
                eval(tDivide(tInt(6), tInt(2)))
        );
    }

    @Test
    public void test1And1Is1() {
        assertEquals(
                1,
                eval(tAnd(tInt(1), tInt(1)))
        );
    }

    @Test
    public void testLetX10() {
        assertEquals(
                10,
                eval(tBlock(
                        tLet("x", tInt(10)),
                        tId("x")
                ))
        );
    }

    @Test
    public void testAssignmentX20() {
        assertEquals(
                20,
                eval(new Block(
                        new LetExpression("x", new IntLiteral(10)),
                        new AssignmentExpression("x", new IntLiteral(20)),
                        new Id("x")
                ))
        );
    }

    @Test
    public void testWhileLt10() {
        assertEquals(
                10,
                eval(
                        tBlock(
                                tLet("x", new IntLiteral(0)),
                                tWhile(
                                        tLt( new Id("x"), tInt(10)),
                                        tAssign(
                                                "x",
                                                tAdd(tId("x"), tInt(1))
                                        )
                                ),
                                tId("x")
                        )
                )
        );
    }

    @Test
    public void testIf1Lt2() {
        assertEquals(
                "1 < 2",
                eval(
                        tIf(
                                tLt(tInt(1), tInt(2)),
                                tString("1  < 2"),
                                tString("1 >= 2")
                        )
                )
        );
    }
}

