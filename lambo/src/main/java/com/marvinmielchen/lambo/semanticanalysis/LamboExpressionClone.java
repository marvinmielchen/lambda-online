package com.marvinmielchen.lambo.semanticanalysis;

import com.marvinmielchen.lambo.syntacticanalysis.LamboExpression;

public class LamboExpressionClone implements LamboExpression.Visitor<LamboExpression> {
    @Override
    public LamboExpression visit(LamboExpression.Abstraction abstraction) {
        return new LamboExpression.Abstraction(new LamboExpression.Variable(abstraction.getBoundVariable().getName(), null), abstraction.getBody().accept(this));
    }

    @Override
    public LamboExpression visit(LamboExpression.Application application) {
        return new LamboExpression.Application(application.getLeft().accept(this), application.getRight().accept(this));
    }

    @Override
    public LamboExpression visit(LamboExpression.Variable variable) {
        return new LamboExpression.Variable(variable.getName(), null);
    }

    public LamboExpression evaluate(LamboExpression expression){
        return expression.accept(this);
    }
}
