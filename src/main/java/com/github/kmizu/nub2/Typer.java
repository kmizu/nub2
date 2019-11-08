package com.github.kmizu.nub2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Typer implements Ast.ExpressionVisitor<Object> {
    @Override
    public Object visitBinaryExpression(Ast.BinaryExpression node) {
        return null;
    }

    @Override
    public Object visitIntLiteral(Ast.IntLiteral node) {
        return null;
    }

    @Override
    public Object visitBooleanLiteral(Ast.BooleanLiteral node) {
        return null;
    }

    @Override
    public Object visitStringLiteral(Ast.StringLiteral node) {
        return null;
    }

    @Override
    public Object visitLetExpression(Ast.LetExpression node) {
        return null;
    }

    @Override
    public Object visitId(Ast.Id node) {
        return null;
    }

    @Override
    public Object visitBlock(Ast.Block node) {
        return null;
    }

    @Override
    public Object visitIfExpression(Ast.IfExpression node) {
        return null;
    }

    @Override
    public Object visitWhileExpression(Ast.WhileExpression node) {
        return null;
    }

    @Override
    public Object visitAssignmentExpression(Ast.AssignmentExpression node) {
        return null;
    }

    @Override
    public Object visitPrintlnExpression(Ast.PrintlnExpression node) {
        return null;
    }

    @Override
    public Object visitDefFunction(Ast.DefFunction node) {
        return null;
    }

    @Override
    public Object visitFunctionCall(Ast.FunctionCall node) {
        return null;
    }

    public Ast.Block typeCheck(Ast.Block program) {
        return program;
    }
}
