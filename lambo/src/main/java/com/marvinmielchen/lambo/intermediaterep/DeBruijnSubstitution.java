package com.marvinmielchen.lambo.intermediaterep;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class DeBruijnSubstitution implements DeBruijnExpression.Visitor<DeBruijnExpression>{

    private final DeBruijnExpression input;
    private final Map<String, DeBruijnExpression> substitutes;
    private int depth = 0;

    public DeBruijnExpression evaluate(){
        return input.accept(this);
    }

    @Override
    public DeBruijnExpression visit(DeBruijnExpression.Abstraction abstraction) {
        depth++;
        DeBruijnExpression.Abstraction result = new DeBruijnExpression.Abstraction(abstraction.getBody().accept(this), abstraction.getOldTokenLexeme());
        depth--;
        return result;
    }

    @Override
    public DeBruijnExpression visit(DeBruijnExpression.Application application) {
        DeBruijnExpression left = application.getLeft().accept(this);
        DeBruijnExpression right = application.getRight().accept(this);
        return new DeBruijnExpression.Application(left, right);
    }

    @Override
    public DeBruijnExpression visit(DeBruijnExpression.Variable variable) {
        if(substitutes.containsKey(variable.getName())){
            IncrementFreeDeBruijnIndices incrementor = new IncrementFreeDeBruijnIndices(depth, substitutes.get(variable.getName()));
            return incrementor.evaluate();
        }
        return new DeBruijnExpression.Variable(variable.getName());
    }

    @Override
    public DeBruijnExpression visit(DeBruijnExpression.DeBruijnIndex deBruijnIndex) {
        return new DeBruijnExpression.DeBruijnIndex(deBruijnIndex.getIndex());
    }
}
