package com.marvinmielchen.lambo.intermediaterep;

import java.util.Map;

public class DeBruijnSubstitution implements DeBruijnExpression.Visitor<DeBruijnExpression> {

    private Map<String, DeBruijnExpression> substitutionMap;

    public void evaluateSubstitution(DeBruijnExpression expression, Map<String, DeBruijnExpression> substitutionMap){
        this.substitutionMap = substitutionMap;
        expression.accept(this);
    }

    @Override
    public DeBruijnExpression visit(DeBruijnExpression.Abstraction abstraction) {
        return new DeBruijnExpression.Abstraction(abstraction.getBody().accept(this));
    }

    @Override
    public DeBruijnExpression visit(DeBruijnExpression.Application application) {
        DeBruijnExpression left = application.getLeft().accept(this);
        DeBruijnExpression right = application.getRight().accept(this);
        return new DeBruijnExpression.Application(left, right);
    }

    @Override
    public DeBruijnExpression visit(DeBruijnExpression.Variable variable) {
        if (substitutionMap.containsKey(variable.getName())){
            DeBruijnClone deBruijnClone = new DeBruijnClone();
            return deBruijnClone.clone(substitutionMap.get(variable.getName()));
        }else{
            return new DeBruijnExpression.Variable(variable.getName()) ;
        }
    }

    @Override
    public DeBruijnExpression visit(DeBruijnExpression.DeBruijnIndex deBruijnIndex) {
        return new DeBruijnExpression.DeBruijnIndex(deBruijnIndex.getIndex());
    }
}
