package com.github.kmizu.nub2;

import static com.github.kmizu.nub2.Ast.*;

public class AbstractNub2Test {
    protected Object eval(Expression input) {
        return new Evaluator().eval(new Block(input));
    }
    protected Object eval(Block input) {
        return new Evaluator().eval(input);
    }
}
