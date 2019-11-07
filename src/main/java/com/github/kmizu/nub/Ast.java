package com.github.kmizu.nub;

import java.util.Arrays;
import java.util.List;

public class Ast {
    public interface ExpressionVisitor<E> {
        E visitBinaryOperation(BinaryOperation node);
        E visitNumber(IntLiteral node);
        E visitStringLiteral(StringLiteral node);
        E visitLetExpression(LetExpression node);
        E visitIdentifier(Identifier node);
        E visitPrintExpression(PrintExpression node);
        E visitExpressionList(ExpressionList node);
        E visitIfExpression(IfExpression node);
        E visitWhileExpression(WhileExpression node);
        E visitAssignmentOperation(AssignmentOperation node);
        E visitPrintlnExpression(PrintlnExpression node);
        E visitDefFunction(DefFunction node);
        E visitFunctionCall(FunctionCall node);
    }

    public static abstract class Node {}

    public static abstract class Expression extends Node {
        public abstract <E> E accept(ExpressionVisitor<E> visitor);
    }

    public static class FunctionCall extends Expression {
        private final Ast.Identifier       name;
        private final List<Ast.Expression> params;
        public FunctionCall(Ast.Identifier name, List<Ast.Expression> params) {
            this.name = name;
            this.params = params;
        }
        public Ast.Identifier name() {
            return name;
        }
        public List<Ast.Expression> params() {
            return params;
        }

        public <E> E accept(ExpressionVisitor<E> visitor) { return visitor.visitFunctionCall(this); }
    }

    public static class DefFunction extends Expression {
        private final String                   name;
        private final List<String>             args;
        private final List<Ast.Expression> body;
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
        private final String variableName;
        private final Ast.Expression expression;
        public LetExpression(String variableName, Ast.Expression expression) {
            this.variableName = variableName;
            this.expression = expression;
        }
        public String variableName() {
            return variableName;
        }
        public Ast.Expression expression() {
            return expression;
        }

        public <E> E accept(ExpressionVisitor<E> visitor) { return visitor.visitLetExpression(this); }
    }

    public static class AssignmentOperation extends Expression {
        private final String variableName;
        private final Ast.Expression expression;
        public AssignmentOperation(String variableName, Ast.Expression expression) {
            this.variableName = variableName;
            this.expression = expression;
        }
        public String variableName() {
            return variableName;
        }
        public Ast.Expression expression() {
            return expression;
        }

        public <E> E accept(ExpressionVisitor<E> visitor) { return visitor.visitAssignmentOperation(this); }
    }

    public static class IfExpression extends Expression {
        private final Ast.Expression condition;
        private final List<Ast.Expression> thenClause, elseClause;
        public IfExpression(
            Ast.Expression condition,
            List<Ast.Expression> thenClause,
            List<Ast.Expression> elseClause) {
            this.condition = condition;
            this.thenClause = thenClause;
            this.elseClause = elseClause;
        }

        public Ast.Expression condition() {
            return condition;
        }

        public List<Ast.Expression> thenClause() {
            return thenClause;
        }

        public List<Ast.Expression> elseClause() {
            return elseClause;
        }

        @Override
        public <E> E accept(ExpressionVisitor<E> visitor) {
            return visitor.visitIfExpression(this);
        }
    }

    public static class WhileExpression extends Expression {
        private final Ast.Expression condition;
        private final List<Ast.Expression> body;
        public WhileExpression(Ast.Expression condition, List<Ast.Expression> body) {
            this.condition = condition;
            this.body = body;
        }
        public WhileExpression(Ast.Expression condition, Ast.Expression... body) {
            this.condition = condition;
            this.body = Arrays.asList(body);
        }

        public Ast.Expression condition() {
            return condition;
        }

        public List<Ast.Expression> body() {
            return body;
        }

        @Override
        public <E> E accept(ExpressionVisitor<E> visitor) {
            return visitor.visitWhileExpression(this);
        }
    }

    public static class PrintExpression extends Expression {
        private final Ast.Expression target;
        public PrintExpression(Ast.Expression target) {
            this.target = target;
        }
        public Ast.Expression target() {
            return target;
        }

        @Override
        public <E> E accept(ExpressionVisitor<E> visitor) {
            return visitor.visitPrintExpression(this);
        }
    }

    public static class PrintlnExpression extends Expression {
        private final Ast.Expression target;
        public PrintlnExpression(Ast.Expression target) {
            this.target = target;
        }
        public Ast.Expression target() {
            return target;
        }

        @Override
        public <E> E accept(ExpressionVisitor<E> visitor) { return visitor.visitPrintlnExpression(this); }
    }

    public static class ExpressionList extends Expression {
        private final List<Expression> expressions;
        public ExpressionList(List<Expression> expressions) {
            this.expressions = expressions;
        }
        public ExpressionList(Expression... expressions) {
            this.expressions = Arrays.asList(expressions);
        }
        public List<Expression> expressions() {
            return expressions;
        }

        @Override
        public <E> E accept(ExpressionVisitor<E> visitor) {
            return visitor.visitExpressionList(this);
        }
    }

    public static class BinaryOperation extends Expression {
        private final String operator;
        private final Expression lhs, rhs;
        public BinaryOperation(String operator, Expression lhs, Expression rhs) {
            this.operator = operator;
            this.lhs = lhs;
            this.rhs = rhs;
        }
        public String operator() { return operator; }
        public Expression lhs() { return lhs; }
        public Expression rhs() { return rhs; }

        public <E> E accept(ExpressionVisitor<E> visitor) {
            return visitor.visitBinaryOperation(this);
        }
    }

    public static class IntLiteral extends Expression {
        private final int value;
        public IntLiteral(int value) {
            this.value = value;
        }
        public int value() { return value; }

        public <E> E accept(ExpressionVisitor<E> visitor) {
            return visitor.visitNumber(this);
        }
    }

    public static class StringLiteral extends Expression {
        private final String value;
        public StringLiteral(String value) { this.value = value; }
        public String value() { return value; }

        @Override
        public <E> E accept(ExpressionVisitor<E> visitor) {
            return visitor.visitStringLiteral(this);
        }
    }

    public static class Identifier extends Expression {
        private final String name;
        public Identifier(String name) {
            this.name = name;
        }
        public String name() { return name; }

        @Override
        public <E> E accept(ExpressionVisitor<E> visitor) {
            return visitor.visitIdentifier(this);
        }
    }

    public static class T {
        public static DefFunction makeFunctionDefinition(String functionName, List<String> args, Expression... body) {
            return new DefFunction(functionName, args, Arrays.asList(body));
        }

        public static FunctionCall makeCall(Identifier functionName, Expression... parameters) {
            return new FunctionCall(functionName, Arrays.asList(parameters));
        }

        public static AssignmentOperation makeAssignment(String varialeName, Expression expression) {
            return new AssignmentOperation(varialeName, expression);
        }

        public static LetExpression makeLet(String variableName, Expression expression) {
            return new LetExpression(variableName, expression);
        }

        public static IfExpression makeIf(Expression condition, Expression thenClause, Expression elseClause) {
            return new IfExpression(condition, Arrays.asList(thenClause), Arrays.asList(elseClause));
        }
        public static BinaryOperation makeBinaryOperation(
                String op, Expression lhs, Expression rhs
        ) {
            return new BinaryOperation(op, lhs, rhs);
        }

        public static WhileExpression makeWhile(
                Expression condition, Expression... body
        ) {
            return new WhileExpression(condition, Arrays.asList(body));
        }

        public static ExpressionList makeBlock(Expression... elements) {
            return new ExpressionList(Arrays.asList(elements));
        }
        public static Identifier makeId(String name) {
            return new Identifier(name);
        }
        public static StringLiteral makeString(String value) {
            return new StringLiteral(value);
        }
        public static IntLiteral makeNumber(int value) {
            return new IntLiteral(value);
        }
        public static PrintlnExpression makePrintln(Expression parameter) {
            return new PrintlnExpression(parameter);
        }
        public static PrintExpression makePrint(Expression parameter) {
            return new PrintExpression(parameter);
        }
    }
}
