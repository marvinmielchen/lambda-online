package com.marvinmielchen.lambo.intermediaterep;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeBruijnClone implements DeBruijnExpression.Visitor<DeBruijnExpression> {

    private final DeBruijnExpression input;

    public DeBruijnExpression evaluate(){
        return input.accept(this);
    }

    @Override
    public DeBruijnExpression visit(DeBruijnExpression.Abstraction abstraction) {
        return new DeBruijnExpression.Abstraction(abstraction.getBody().accept(this), abstraction.getOldTokenLexeme());
    }

    @Override
    public DeBruijnExpression visit(DeBruijnExpression.Application application) {
        DeBruijnExpression left = application.getLeft().accept(this);
        DeBruijnExpression right = application.getRight().accept(this);
        return new DeBruijnExpression.Application(left, right);
    }

    @Override
    public DeBruijnExpression visit(DeBruijnExpression.Variable variable) {
        return new DeBruijnExpression.Variable(variable.getName());
    }

    @Override
    public DeBruijnExpression visit(DeBruijnExpression.DeBruijnIndex deBruijnIndex) {
        return new DeBruijnExpression.DeBruijnIndex(deBruijnIndex.getIndex());
    }
}
