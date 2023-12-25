package com.marvinmielchen.lambo.intermediaterep;


import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public class DeBruijnAlphaEquivalence {

    private final DeBruijnExpression expression1;
    private final DeBruijnExpression expression2;

    public boolean evaluate(){
        return recursiveEvaluate(expression1, expression2);
    }

    private boolean recursiveEvaluate(DeBruijnExpression input1, DeBruijnExpression input2){
        if(input1 instanceof DeBruijnExpression.Abstraction abstraction1
                && input2 instanceof DeBruijnExpression.Abstraction abstraction2){
            return recursiveEvaluate(abstraction1.getBody(), abstraction2.getBody());
        }
        if(input1 instanceof DeBruijnExpression.Application application1
                && input2 instanceof DeBruijnExpression.Application application2){
            return recursiveEvaluate(application1.getLeft(), application2.getLeft()) &&
                     recursiveEvaluate(application1.getRight(), application2.getRight());
        }
        if(input1 instanceof DeBruijnExpression.Variable variable1
                && input2 instanceof DeBruijnExpression.Variable variable2){
            return Objects.equals(variable1.getName(), variable2.getName());
        }
        if (input1 instanceof DeBruijnExpression.DeBruijnIndex index1
                && input2 instanceof DeBruijnExpression.DeBruijnIndex index2){
            return index1.getIndex() == index2.getIndex();
        }
        return false;
    }
}
