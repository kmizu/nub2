package com.github.kmizu.nub2;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import static com.github.kmizu.nub2.Ast.BinaryOperator.*;

public class Ast {
    public interface ExpressionVisitor<E> {
        E visitBinaryExpression(BinaryExpression node);
        E visitIntLiteral(IntLiteral node);
        E visitBooleanLiteral(BooleanLiteral node);
        E visitStringLiteral(StringLiteral node);
        E visitLetExpression(LetExpression node);
        E visitId(Id node);
        E visitBlock(Block node);
        E visitIfExpression(IfExpression node);
        E visitWhileExpression(WhileExpression node);
        E visitAssignmentExpression(AssignmentExpression node);
        E visitPrintlnExpression(PrintlnExpression node);
        E visitDefFunction(DefFunction node);
        E visitFunctionCall(FunctionCall node);
    }

    public static abstract class Node {}

    public static abstract class Expression extends Node {
        public abstract <E> E accept(ExpressionVisitor<E> visitor);
    }

    public static class FunctionCall extends Expression {
        public final Id name;
        public final List<Ast.Expression> params;
        public FunctionCall(Id name, List<Ast.Expression> params) {
            this.name = name;
            this.params = params;
        }
        public Id name() {
            return name;
        }
        public List<Ast.Expression> params() {
            return params;
        }

        public <E> E accept(ExpressionVisitor<E> visitor) { return visitor.visitFunctionCall(this); }
    }

    public static class DefFunction extends Expression {
        public final String                   name;
        public final List<String>             args;
        public List<Ast.Expression> body;
        public DefFunction(String name, List<String> args, List<Ast.Expression> body) {
            this.name = name;
            this.args = args;
            this.body = body;
        }
        public String name() {
            return name;
        }
        public List<String> args() {
            return args;
        }
        public List<Ast.Expression> body() {
            return body;
        }

        public <E> E accept(ExpressionVisitor<E> visitor) { return visitor.visitDefFunction(this); }
    }

    public static class LetExpression extends Expression {
        public final String variableName;
        public final Ast.Expression init;
        public final Ast.Block body;

        public LetExpression(String variableName, Ast.Expression init, Ast.Block body) {
            this.variableName = variableName;
            this.init = init;
            this.body = new Ast.Block(Arrays.asList(body));
        }

        public <E> E accept(ExpressionVisitor<E> visitor) { return visitor.visitLetExpression(this); }
    }

    public static class AssignmentExpression extends Expression {
        public final String variableName;
        public final Ast.Expression expression;
        public AssignmentExpression(String variableName, Ast.Expression expression) {
            this.variableName = variableName;
            this.expression = expression;
        }

        public <E> E accept(ExpressionVisitor<E> visitor) { return visitor.visitAssignmentExpression(this); }
    }

    public static class IfExpression extends Expression {
        public final Ast.Expression condition;
        public final Ast.Block thenClause, elseClause;
        public IfExpression(
            Ast.Expression condition,
            Ast.Block thenClause,
            Ast.Block elseClause) {
            this.condition = condition;
            this.thenClause = thenClause;
            this.elseClause = elseClause;
        }

        @Override
        public <E> E accept(ExpressionVisitor<E> visitor) {
            return visitor.visitIfExpression(this);
        }
    }

    public static class WhileExpression extends Expression {
        public final Ast.Expression condition;
        public final List<Ast.Expression> body;
        public WhileExpression(Ast.Expression condition, List<Ast.Expression> body) {
            this.condition = condition;
            this.body = body;
        }
        public WhileExpression(Ast.Expression condition, Ast.Expression... body) {
            this.condition = condition;
            this.body = Arrays.asList(body);
        }

        @Override
        public <E> E accept(ExpressionVisitor<E> visitor) {
            return visitor.visitWhileExpression(this);
        }
    }

    public static class PrintlnExpression extends Expression {
        public final Ast.Expression target;
        public PrintlnExpression(Ast.Expression target) {
            this.target = target;
        }

        @Override
        public <E> E accept(ExpressionVisitor<E> visitor) { return visitor.visitPrintlnExpression(this); }
    }

    public static class Block extends Expression {
        public final List<Expression> expressions;
        public Block(List<Expression> expressions) {
            this.expressions = expressions;
        }
        public Block(Expression... expressions) {
            this.expressions = Arrays.asList(expressions);
        }

        @Override
        public <E> E accept(ExpressionVisitor<E> visitor) {
            return visitor.visitBlock(this);
        }
    }

    public enum BinaryOperator {
        ADD("+"), SUBTRACT("+"), MULTIPLY("*"), DIVIDE("/"),
        LESS_THAN("<"), LESS_THAN_OR_EQUAL("<="), GREATER_THAN(">"), GREATER_THAN_OR_EQUAL(">="),
        EQUAL("=="), NOT_EQUAL("!="), LOGICAL_AND("&&"), LOGCIAL_OR("||");
        private String op;
        public String getOp() {
            return op;
        }
        private BinaryOperator(String name) {
            this.op = op;
        }
    }

    public static class BinaryExpression extends Expression {
        public final BinaryOperator operator;
        public final Expression lhs, rhs;
        public BinaryExpression(BinaryOperator operator, Expression lhs, Expression rhs) {
            this.operator = operator;
            this.lhs = lhs;
            this.rhs = rhs;
        }

        public <E> E accept(ExpressionVisitor<E> visitor) {
            return visitor.visitBinaryExpression(this);
        }
    }

    public static class IntLiteral extends Expression {
        public final int value;
        public IntLiteral(int value) {
            this.value = value;
        }

        public <E> E accept(ExpressionVisitor<E> visitor) {
            return visitor.visitIntLiteral(this);
        }
    }

    public static class StringLiteral extends Expression {
        public final String value;
        public StringLiteral(String value) { this.value = value; }

        @Override
        public <E> E accept(ExpressionVisitor<E> visitor) {
            return visitor.visitStringLiteral(this);
        }
    }

    public static class BooleanLiteral extends Expression {
        public final boolean value;
        public BooleanLiteral(boolean value) { this.value = value; }

        @Override
        public <E> E accept(ExpressionVisitor<E> visitor) {
            return visitor.visitBooleanLiteral(this);
        }
    }

    public static class Id extends Expression {
        public final String name;
        public Id(String name) {
            this.name = name;
        }

        @Override
        public <E> E accept(ExpressionVisitor<E> visitor) {
            return visitor.visitId(this);
        }
    }

    public static class Factory {
        /*
         * Literals
         */
        public static StringLiteral tString(String value) {
            return new StringLiteral(value);
        }
        public static IntLiteral tInt(int value) {
            return new IntLiteral(value);
        }
        public static BooleanLiteral tBoolean(boolean value) {
            return new BooleanLiteral(value);
        }

        public static Id tId(String name) {
            return new Id(name);
        }

        /*
         * Println expression
         */
        public static PrintlnExpression tPrintln(Expression target) {
            return new PrintlnExpression(target);
        }

        /*
         * Binary expressions
         */
        public static BinaryExpression tAdd(Expression lhs, Expression rhs) {
            return new BinaryExpression(ADD ,lhs, rhs);
        }
        public static BinaryExpression tSubtract(Expression lhs, Expression rhs) {
            return new BinaryExpression(SUBTRACT ,lhs, rhs);
        }
        public static BinaryExpression tMultiply(Expression lhs, Expression rhs) {
            return new BinaryExpression(MULTIPLY ,lhs, rhs);
        }
        public static BinaryExpression tDivide(Expression lhs, Expression rhs) {
            return new BinaryExpression(DIVIDE ,lhs, rhs);
        }
        public static BinaryExpression tAnd(Expression lhs, Expression rhs) {
            return new BinaryExpression(LOGICAL_AND, lhs, rhs);
        }
        public static BinaryExpression tOr(Expression lhs, Expression rhs) {
            return new BinaryExpression(LOGCIAL_OR, lhs, rhs);
        }
        public static BinaryExpression tLt(Expression lhs, Expression rhs) {
            return new BinaryExpression(LESS_THAN, lhs, rhs);
        }
        public static BinaryExpression tLte(Expression lhs, Expression rhs) {
            return new BinaryExpression(LESS_THAN_OR_EQUAL, lhs, rhs);
        }
        public static BinaryExpression tGt(Expression lhs, Expression rhs) {
            return new BinaryExpression(GREATER_THAN, lhs, rhs);
        }
        public static BinaryExpression tGte(Expression lhs, Expression rhs) {
            return new BinaryExpression(GREATER_THAN_OR_EQUAL, lhs, rhs);
        }
        public static BinaryExpression tEqual(Expression lhs, Expression rhs) {
            return new BinaryExpression(EQUAL, lhs, rhs);
        }
        public static BinaryExpression tNotEqual(Expression lhs, Expression rhs) {
            return new BinaryExpression(NOT_EQUAL, lhs, rhs);
        }

        /*
         * Assignment
         */
        public static AssignmentExpression tAssign(String variableName, Expression newValue) {
            return new AssignmentExpression(variableName, newValue);
        }

        /*
         * Control structures
         */
        public static Block tBlock(Expression... elements) {
            return new Block(elements);
        }
        public static LetExpression tLet(String variableName, Expression init, Block body) {
            return new LetExpression(variableName, init, body);
        }
        public static LetExpression tLet(String variableName, Expression init, Function<String, Block> bodyFactory) {
            Block body = bodyFactory.apply(variableName);
            return tLet(variableName, init, body);
        }
        public static IfExpression tIf(Expression tCondition, Expression tThen, Expression tElse) {
            return new IfExpression(tCondition, new Block(tThen), new Block(tElse));
        }
        public static WhileExpression tWhile(Expression tCondition, Expression... tBody) {
            return new WhileExpression(tCondition, tBody);
        }
    }
}
