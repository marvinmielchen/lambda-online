package com.marvinmielchen.lambo.intermediaterep;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IncrementFreeDeBruijnIndices implements DeBruijnExpression.Visitor<DeBruijnExpression> {

    private final int increment;
    private final DeBruijnExpression input;
    private int numOfBinders = 0;

    public DeBruijnExpression evaluate(){
        return input.accept(this);
    }

    @Override
    public DeBruijnExpression visit(DeBruijnExpression.Abstraction abstraction) {
        numOfBinders++;
        DeBruijnExpression.Abstraction result = new DeBruijnExpression.Abstraction(abstraction.getBody().accept(this), abstraction.getOldTokenLexeme());
        numOfBinders--;
        return result;
    }

    @Override
    public DeBruijnExpression visit(DeBruijnExpression.Application application) {
        return new DeBruijnExpression.Application(
                application.getLeft().accept(this),
                application.getRight().accept(this)
        );
    }

    @Override
    public DeBruijnExpression visit(DeBruijnExpression.Variable variable) {
        return new DeBruijnExpression.Variable(variable.getName());
    }

    @Override
    public DeBruijnExpression visit(DeBruijnExpression.DeBruijnIndex deBruijnIndex) {
        return deBruijnIndex.getIndex() >= numOfBinders
                ? new DeBruijnExpression.DeBruijnIndex(deBruijnIndex.getIndex() + increment)
                : new DeBruijnExpression.DeBruijnIndex(deBruijnIndex.getIndex());
    }
}
