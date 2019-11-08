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
    private Map<String, Ast.DefFunction> functions = new HashMap<>();

    private boolean asBoolean(Object value) {
        return ((Boolean)value).booleanValue();
    }

    private int asInt(Object value) {
        return ((Integer)value).intValue();
    }

    public Object visitBinaryExpression(Ast.BinaryExpression node) {
        switch (node.operator) {
            case ADD:
                Object lhs = node.lhs.accept(this);
                Object rhs = node.rhs.accept(this);
                if(lhs instanceof String || rhs instanceof String) {
                    return lhs.toString() + rhs.toString();
                } else {
                    return asInt(lhs) + asInt(rhs);
                }
            case SUBTRACT:
                return asInt(node.lhs.accept(this)) - asInt(node.rhs.accept(this));
            case MULTIPLY:
                return asInt(node.lhs.accept(this)) * asInt(node.rhs.accept(this));
            case DIVIDE:
                return asInt(node.lhs.accept(this)) / asInt(node.rhs.accept(this));
            case LESS_THAN_OR_EQUAL:
                return asInt((node.lhs.accept(this))) <= asInt(node.rhs.accept(this));
            case GREATER_THAN_OR_EQUAL:
                throw new NotImplementedException("comparing operator " + node.operator.getOp());
            case LESS_THAN:
                return asInt((node.lhs.accept(this))) < asInt(node.rhs.accept(this));
            case GREATER_THAN:
                throw new NotImplementedException("comparing operator " + node.operator.getOp());
            case EQUAL:
                return node.lhs.accept(this).equals(node.rhs.accept(this));
            case NOT_EQUAL:
                throw new NotImplementedException("equality operator " + node.operator.getOp());
            case LOGICAL_AND:
                throw new NotImplementedException("logical operator " + node.operator.getOp());
            case LOGCIAL_OR:
                throw new NotImplementedException("logical operator " + node.operator.getOp());
            default:
                throw new RuntimeException("cannot reach here");
        }
    }

    @Override
    public Integer visitIntLiteral(Ast.IntLiteral node) {
        return node.value;
    }

    @Override
    public String visitStringLiteral(Ast.StringLiteral node) {
        return node.value;
    }

    @Override
    public Object visitBooleanLiteral(Ast.BooleanLiteral node) {
        return node.value;
    }

    @Override
    public Object visitLetExpression(Ast.LetExpression node) {
        Object value = node.init.accept(this);
        if(environment.contains(node.variableName)) {
            throw new NubRuntimeException("variable " + node.variableName + " is already defined");
        }
        Environment backup = environment;
        this.environment = new Environment(environment);
        environment.register(node.variableName, value);
        node.body.accept(this);
        this.environment = backup;
        return value;
    }

    @Override
    public Object visitPrintlnExpression(Ast.PrintlnExpression node) {
        Object value = node.target.accept(this);
        System.out.println(value);
        return value;
    }

    @Override
    public Object visitBlock(Ast.Block node) {
        Object last = 0;
        for (Ast.Expression e : node.expressions) {
            last = e.accept(this);
        }
        return last;
    }

    @Override
    public Object visitId(Ast.Id node) {
        Object ret = environment.find(node.name);
        if (ret == null)
            throw new NubRuntimeException(node.name + " is not defined");
        else
            return ret;
    }

    @Override
    public Object visitDefFunction(Ast.DefFunction node) {
        // Nothing to be done
        return null;
    }

    @Override
    public Object visitIfExpression(Ast.IfExpression node) {
        throw new NotImplementedException("if expression");
    }

    @Override
    public Object visitWhileExpression(Ast.WhileExpression node) {
        throw new NotImplementedException("while expression");
    }

    @Override
    public Object visitAssignmentExpression(Ast.AssignmentExpression node) {
        throw new NotImplementedException("assignment");
    }

    @Override
    public Object visitFunctionCall(Ast.FunctionCall node) {
        throw new NotImplementedException("function call");
    }

    public Object eval(Ast.Block program) {
        Ast.Block target = program;
        VariableChecker checker = new VariableChecker();
        target = checker.checkVariable(program);
        Typer typer = new Typer();
        target = typer.typeCheck(target);
        for(Ast.Expression top:target.expressions) {
            if(top instanceof Ast.DefFunction) {
                Ast.DefFunction f = (Ast.DefFunction)top;
                functions.put(f.name, f);
            }
        }
        return program.accept(this);
    }
}
