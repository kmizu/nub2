package com.github.kmizu.nub2;

import java.util.*;

public class Evaluator implements Ast.ExpressionVisitor<Object> {
    public static class Environment {
        public final Map<String, Object> mapping = new HashMap<>();
        public final Environment parent;
        public Environment(Environment parent) {
            this.parent = parent;
        }

        public Object find(String name) {
            Object value = mapping.get(name);
            if(value == null && parent != null) {
                value = parent.find(name);
            }
            return value;
        }

        public Optional<Environment> findEnvironment(String name) {
            if(mapping.containsKey(name))
                return Optional.of(this);
            else
                return parent != null ? parent.findEnvironment(name) : Optional.empty();
        }

        public boolean contains(String name) {
            boolean contains = mapping.containsKey(name);
            if(contains)
                return true;
            else
                return parent != null ? parent.contains(name) : false;
        }

        public Object register(String name, Object value) {
            return mapping.put(name, value);
        }
    }

    private Environment environment = new Environment(null);
    private Environment globalEnvironment = environment;
    private Map<String, Ast.DefFunction> functions = new HashMap<>();

    private boolean asBoolean(Object value) {
        return ((Boolean)value).booleanValue();
    }

    private int asInt(Object value) {
        return ((Integer)value).intValue();
    }

    public Object visitBinaryOperation(Ast.BinaryOperation node) {
        switch (node.operator()) {
            case "+":
                Object lhs = node.lhs().accept(this);
                Object rhs = node.rhs().accept(this);
                if(lhs instanceof String || rhs instanceof String) {
                    return lhs.toString() + rhs.toString();
                } else {
                    return asInt(lhs) + asInt(rhs);
                }
            case "-":
                return asInt(node.lhs().accept(this)) - asInt(node.rhs().accept(this));
            case "*":
                return asInt(node.lhs().accept(this)) * asInt(node.rhs().accept(this));
            case "/":
                return asInt(node.lhs().accept(this)) / asInt(node.rhs().accept(this));
            case "<=":
                return asInt((node.lhs().accept(this))) <= asInt(node.rhs().accept(this)) ? 1 : 0;
            case ">=":
                return (asInt(node.lhs().accept(this)) >= asInt(node.rhs().accept(this))) ? 1 : 0;
            case "<":
                return (asInt(node.lhs().accept(this)) < asInt(node.rhs().accept(this))) ? 1 : 0;
            case ">":
                return (asInt(node.lhs().accept(this)) > asInt(node.rhs().accept(this))) ? 1 : 0;
            case "==":
                return (node.lhs().accept(this).equals(node.rhs().accept(this))) ? 1 : 0;
            case "!=":
                return (!(node.lhs().accept(this).equals(node.rhs().accept(this)))) ? 1 : 0;
            case "&&":
                throw new UnimplementedException("logical and operator &&");
            case "||":
                throw new UnimplementedException("logical or operator ||");
            default:
                throw new RuntimeException("cannot reach here");
        }
    }

    @Override
    public Integer visitNumber(Ast.IntLiteral node) {
        return node.value();
    }

    @Override
    public String visitStringLiteral(Ast.StringLiteral node) {
        return node.value();
    }

    @Override
    public Object visitLetExpression(Ast.LetExpression node) {
        Object value = node.expression().accept(this);
        if(environment.contains(node.variableName())) {
            throw new NubRuntimeException("variable " + node.variableName() + " is already defined");
        }
        environment.register(node.variableName(), value);
        return value;
    }

    @Override
    public Object visitAssignmentOperation(Ast.AssignmentOperation node) {
        throw new UnimplementedException("assignment");
    }

    @Override
    public Object visitPrintlnExpression(Ast.PrintlnExpression node) {
        Object value = node.target().accept(this);
        System.out.println(value);
        return value;
    }

    @Override
    public Object visitExpressionList(Ast.Block node) {
        Object last = 0;
        for (Ast.Expression e : node.expressions()) {
            last = e.accept(this);
        }
        return last;
    }

    @Override
    public Object visitWhileExpression(Ast.WhileExpression node) {
        throw new UnimplementedException("while expression");
    }

    @Override
    public Object visitIfExpression(Ast.IfExpression node) {
        throw new UnimplementedException("if expression");
    }

    @Override
    public Object visitDefFunction(Ast.DefFunction node) {
        return null;
    }

    @Override
    public Object visitIdentifier(Ast.Identifier node) {
        Object ret = environment.find(node.name());
        if (ret == null)
            throw new NubRuntimeException(node.name() + " is not defined");
        else
            return ret;
    }

    public Object evaluate(Ast.Block program) {
        for(Ast.Expression top:program.expressions()) {
            if(top instanceof Ast.DefFunction) {
                Ast.DefFunction f = (Ast.DefFunction)top;
                functions.put(f.name(), f);
            }
        }
        return program.accept(this);
    }

    @Override
    public Object visitFunctionCall(Ast.FunctionCall node) {
        throw new UnimplementedException("function call");
    }
}
