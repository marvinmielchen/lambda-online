package com.marvinmielchen.lambo.semanticanalysis;

import com.marvinmielchen.lambo.syntacticanalysis.Expr;

public class Interpreter implements Expr.Visitor<Expr>{

    @Override
    public Expr visit(Expr.Abstraction abstraction) {
        return abstraction;
    }

    @Override
    public Expr visit(Expr.Application application) {
        return betaReduction(application);
    }

    @Override
    public Expr visit(Expr.Variable variable) {
        return variable;
    }


    private Expr betaReduction(Expr.Application application) {
        return application;
    }

}
