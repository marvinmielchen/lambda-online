package com.marvinmielchen.lambo.intermediaterep;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindAndPerformOneBetaReduction implements DeBruijnExpression.Visitor<DeBruijnExpression> {

    private final DeBruijnExpression input;
    private boolean betaReductionPerformed = false;

    public DeBruijnExpression evaluate(){
        return input.accept(this);
    }

    @Override
    public DeBruijnExpression visit(DeBruijnExpression.Abstraction abstraction) {
        return new DeBruijnExpression.Abstraction(abstraction.getBody().accept(this), abstraction.getOldTokenLexeme());
    }

    @Override
    public DeBruijnExpression visit(DeBruijnExpression.Application application) {
        if (!betaReductionPerformed && application.getLeft() instanceof DeBruijnExpression.Abstraction abstraction) {
            betaReductionPerformed = true;
            BetaReduction betaReduction = new BetaReduction(abstraction, application.getRight());
            return betaReduction.evaluate();
        } else {
            return new DeBruijnExpression.Application(application.getLeft().accept(this), application.getRight().accept(this));
        }
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
