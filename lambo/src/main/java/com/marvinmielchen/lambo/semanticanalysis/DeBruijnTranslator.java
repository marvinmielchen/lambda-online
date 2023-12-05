package com.marvinmielchen.lambo.semanticanalysis;

import com.marvinmielchen.lambo.syntacticanalysis.Expr;

public class DeBruijnTranslator implements DeBruijnExpression.DeBruijnVisitor<DeBruijnExpression> {

    @Override
    public DeBruijnExpression.DeBruijnAbstraction visit(DeBruijnExpression.DeBruijnAbstraction abstraction) {
        return null;
    }

    @Override
    public DeBruijnExpression.DeBruijnApplication visit(DeBruijnExpression.DeBruijnApplication application) {
        return null;
    }

    @Override
    public DeBruijnExpression.DeBruijnVariable visit(DeBruijnExpression.DeBruijnVariable variable) {
        return null;
    }

    @Override
    public DeBruijnExpression.DeBruijnIndex visit(DeBruijnExpression.DeBruijnIndex index) {
        return null;
    }

    public DeBruijnExpression translate(Expr expression){
        return null;
    }

    private DeBruijnExpression translate(Expr.Variable var, Expr expression, int depth){
        if( expression instanceof Expr.Abstraction abstraction){
            return new DeBruijnExpression.DeBruijnAbstraction(translate(abstraction.getBoundVariable(), abstraction.getBody(), depth + 1));
        }
        if( expression instanceof Expr.Application application){
            return new DeBruijnExpression.DeBruijnApplication(translate(var, application.getLeft(), depth), translate(var, application.getRight(), depth));
        }
        if( expression instanceof Expr.Variable variable){
            if(variable.getToken().getLexeme().equals(var.getToken().getLexeme())){
                return new DeBruijnExpression.DeBruijnIndex(depth);
            }
            return new DeBruijnExpression.DeBruijnVariable(variable.getToken().getLexeme());
        }
        return null;
    }
}
