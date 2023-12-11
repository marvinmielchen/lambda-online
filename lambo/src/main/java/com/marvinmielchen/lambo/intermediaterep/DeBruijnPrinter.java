package com.marvinmielchen.lambo.intermediaterep;

public class DeBruijnPrinter implements DeBruijnExpression.Visitor<String> {

    public String print(DeBruijnExpression expression){
        return expression.accept(this);
    }

    @Override
    public String visit(DeBruijnExpression.Abstraction abstraction) {
        return String.format("(λ %s)", abstraction.getBody().accept(this));
    }

    @Override
    public String visit(DeBruijnExpression.Application application) {
        return String.format("{%s %s}", application.getLeft().accept(this), application.getRight().accept(this));
    }

    @Override
    public String visit(DeBruijnExpression.Variable variable) {
        return variable.getName();
    }

    @Override
    public String visit(DeBruijnExpression.DeBruijnIndex deBruijnIndex) {
        return String.valueOf(deBruijnIndex.getIndex());
    }
}
