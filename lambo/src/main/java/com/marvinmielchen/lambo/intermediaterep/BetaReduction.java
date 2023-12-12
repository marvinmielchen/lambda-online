package com.marvinmielchen.lambo.intermediaterep;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BetaReduction implements DeBruijnExpression.Visitor<DeBruijnExpression> {

    private final DeBruijnExpression.Abstraction abstraction;
    private final DeBruijnExpression substitute;
    private int numOfBinders = 0;

    public DeBruijnExpression evaluate(){
        return abstraction.getBody().accept(this);
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
        int index = deBruijnIndex.getIndex();
        if(index == numOfBinders){
            return new IncrementFreeDeBruijnIndices(numOfBinders, substitute).evaluate();
        }else if(index > numOfBinders){
            return new DeBruijnExpression.DeBruijnIndex(index - 1);
        }else{
            return new DeBruijnExpression.DeBruijnIndex(index);
        }
    }
}
