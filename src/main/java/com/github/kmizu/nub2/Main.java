package com.github.kmizu.nub2;

import static com.github.kmizu.nub2.Ast.*;
import static com.github.kmizu.nub2.Ast.Factory.*;

public class Main {
    public static void main(String[] args) {
        Evaluator evaluator = new Evaluator();
        evaluator.eval(
                tBlock(
                        tPrintln(tString("Hello, World"))
                )
        );
    }
}
