package com.github.kmizu.nub2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Typer implements Ast.ExpressionVisitor<Type> {
    public static class TypeEnvironment {
        public final Map<String, Type> mapping = new HashMap<>();
        public final TypeEnvironment parent;
        public TypeEnvironment(TypeEnvironment parent) {
            this.parent = parent;
        }

        public Type find(String name) {
            Type type = mapping.get(name);
            if(type == null && parent != null) {
                type = parent.find(name);
            }
            return type;
        }

        public Optional<TypeEnvironment> findEnvironment(String name) {
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

        public Type register(String name, Type type) {
            return mapping.put(name, type);
        }
    }

    private TypeEnvironment environment = new TypeEnvironment(null);
    private Map<String, Ast.DefFunction> functions = new HashMap<>();

    @Override
    public Type visitBinaryExpression(Ast.BinaryExpression node) {
        Type lhsType = node.lhs.accept(this);
        Type rhsType = node.rhs.accept(this);
        switch(node.operator) {
            case ADD:
                if(lhsType != Type.INT || rhsType != Type.INT) {
                    throw new TypeError("incompatible type: (lhs, rhs) should be (Int, Int), but (" + lhsType + ", " + rhsType + ")");
                }
                return Type.INT;
            case SUBTRACT:
                if(lhsType != Type.INT || rhsType != Type.INT) {
                    throw new TypeError("incompatible type: (lhs, rhs) should be (Int, Int), but (" + lhsType + ", " + rhsType + ")");
                }
                return Type.INT;
            case MULTIPLY:
                if(lhsType != Type.INT || rhsType != Type.INT) {
                    throw new TypeError("incompatible type: (lhs, rhs) should be (Int, Int), but (" + lhsType + ", " + rhsType + ")");
                }
                return Type.INT;
            case DIVIDE:
                if(lhsType != Type.INT || rhsType != Type.INT) {
                    throw new TypeError("incompatible type: (lhs, rhs) should be (Int, Int), but (" + lhsType + ", " + rhsType + ")");
                }
                return Type.INT;
            case LESS_THAN:
                if(lhsType != Type.INT || rhsType != Type.INT) {
                    throw new TypeError("incompatible type: (lhs, rhs) should be (Int, Int), but (" + lhsType + ", " + rhsType + ")");
                }
                return Type.BOOLEAN;
            case LESS_THAN_OR_EQUAL:
                if(lhsType != Type.INT || rhsType != Type.INT) {
                    throw new TypeError("incompatible type: (lhs, rhs) should be (Int, Int), but (" + lhsType + ", " + rhsType + ")");
                }
                return Type.BOOLEAN;
            case GREATER_THAN:
                if(lhsType != Type.INT || rhsType != Type.INT) {
                    throw new TypeError("incompatible type: (lhs, rhs) should be (Int, Int), but (" + lhsType + ", " + rhsType + ")");
                }
                return Type.BOOLEAN;
            case GREATER_THAN_OR_EQUAL:
                if(lhsType != Type.INT || rhsType != Type.INT) {
                    throw new TypeError("incompatible type: (lhs, rhs) should be (Int, Int), but (" + lhsType + ", " + rhsType + ")");
                }
                return Type.BOOLEAN;
            case EQUAL:
                if(lhsType != rhsType) {
                    throw new TypeError("incompatible type: " + lhsType + " should be " + rhsType);
                }
                return Type.BOOLEAN;
            case NOT_EQUAL:
                if(lhsType != rhsType) {
                    throw new TypeError("incompatible type: " + lhsType + " should be " + rhsType);
                }
                return Type.BOOLEAN;
            case LOGICAL_AND:
                if(lhsType != Type.BOOLEAN || rhsType != Type.BOOLEAN) {
                    throw new TypeError("incompatible type: (lhs, rhs) should be (Boolean, Boolean), but (" + lhsType + ", " + lhsType + ")");
                }
                return Type.BOOLEAN;
            case LOGCIAL_OR:
                if(lhsType != Type.BOOLEAN || rhsType != Type.BOOLEAN) {
                    throw new TypeError("incompatible type: (lhs, rhs) should be (Boolean, Boolean), but (" + lhsType + ", " + lhsType + ")");
                }
                return Type.BOOLEAN;
            default:
                return null;
        }
    }

    @Override
    public Type visitIntLiteral(Ast.IntLiteral node) {
        return Type.INT;
    }

    @Override
    public Type visitBooleanLiteral(Ast.BooleanLiteral node) {
        return Type.BOOLEAN;
    }

    @Override
    public Type visitStringLiteral(Ast.StringLiteral node) {
        return Type.STRING;
    }

    @Override
    public Type visitLetExpression(Ast.LetExpression node) {
        return null;
    }

    @Override
    public Type visitId(Ast.Id node) {
        return environment.find(node.name);
    }

    @Override
    public Type visitBlock(Ast.Block node) {
        Type last = null;
        for(Ast.Expression expression:node.expressions) {
            last = expression.accept(this);
        }
        return last;
    }

    @Override
    public Type visitIfExpression(Ast.IfExpression node) {
        Type conditionType = node.condition.accept(this);
        if(conditionType != Type.BOOLEAN) {
            throw new TypeError("expected: Boolean, actual: " + conditionType.name);
        }
        Type thenType = new Ast.Block(node.thenClause.expressions).accept(this);
        Type elseType = new Ast.Block(node.elseClause.expressions).accept(this);
        if(thenType != elseType) {
            throw new TypeError("then: " + thenType + ", else: " + elseType);
        }
        return thenType;
    }

    @Override
    public Type visitWhileExpression(Ast.WhileExpression node) {
        Type conditionType = node.condition.accept(this);
        if(conditionType != Type.BOOLEAN) {
            throw new TypeError("expected: Boolean, actual: " + conditionType.name);
        }
        Type bodyType = new Ast.Block(node.body).accept(this);
        return Type.INT;
    }

    @Override
    public Type visitAssignmentExpression(Ast.AssignmentExpression node) {
        Type valueType = node.expression.accept(this);
        Type variableType = environment.find(node.variableName);
        if(variableType != variableType) {
            throw new TypeError("expected: " + variableType + ", actual: " + valueType);
        }
        return valueType;
    }

    @Override
    public Type visitPrintlnExpression(Ast.PrintlnExpression node) {
        return node.target.accept(this);
    }

    @Override
    public Type visitDefFunction(Ast.DefFunction node) {
        TypeEnvironment backup = environment;
        environment = new TypeEnvironment(environment);
        for(Ast.Argument argument:node.arguments) {
            environment.register(argument.name, argument.type);
        }
        Type actualReturnType = node.body.accept(this);
        if(actualReturnType != node.returnType) {
            throw new TypeError("expected: " + node.returnType + ", actual: " + actualReturnType);
        }
        environment = backup;
        return node.returnType;
    }

    @Override
    public Type visitFunctionCall(Ast.FunctionCall node) {
        List<Type> actualTypes = node.params.stream().map(p -> p.accept(this)).collect(Collectors.toList());
        Ast.DefFunction function = functions.get(node.name);
        if(function == null) {
            throw new TypeError(
                    "function " + function.name + " doesn't exist"
            );
        }
        List<Type> formalTypes = function.arguments.stream().map(a -> a.type).collect(Collectors.toList());
        if(!actualTypes.equals(formalTypes)) {
            throw new TypeError(
                    "in function invocation " + node.name + "(), expected: " + formalTypes + ", actual: " + actualTypes
            );
        }
        return function.returnType;
    }

    public Ast.Block typeCheck(Ast.Block program) {
        for(Ast.Expression top:program.expressions) {
            if(top instanceof Ast.DefFunction) {
                Ast.DefFunction function = (Ast.DefFunction)top;
                functions.put(function.name, function);
            }
        }
        program.accept(this);
        return program;
    }
}
