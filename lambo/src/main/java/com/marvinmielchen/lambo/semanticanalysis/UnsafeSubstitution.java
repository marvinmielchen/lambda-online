package com.marvinmielchen.lambo.semanticanalysis;

import com.marvinmielchen.lambo.syntacticanalysis.LamboExpression;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class UnsafeSubstitution implements LamboExpression.Visitor<LamboExpression> {


    private final Map<String, LamboExpression> substitutions;

    public LamboExpression evaluate(LamboExpression expression){
        return expression.accept(this);
    }
    @Override
    public LamboExpression visit(LamboExpression.Abstraction abstraction) {
        LamboExpression body = abstraction.getBody().accept(this);
        return new LamboExpression.Abstraction(abstraction.getBoundVariable(), body);
    }

    @Override
    public LamboExpression visit(LamboExpression.Application application) {
        LamboExpression left = application.getLeft().accept(this);
        LamboExpression right = application.getRight().accept(this);
        return new LamboExpression.Application(left, right);
    }

    @Override
    public LamboExpression visit(LamboExpression.Variable variable) {
        if (substitutions.containsKey(variable.getName())){
            return new LamboExpressionClone().evaluate(substitutions.get(variable.getName()));
        } else {
            return variable;
        }
    }
}
