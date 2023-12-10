package com.marvinmielchen.lambo.semanticanalysis;

import com.marvinmielchen.lambo.syntacticanalysis.LamboExpression;

public class RecursiveBetaReduction implements LamboExpression.Visitor<LamboExpression>{

    @Override
    public LamboExpression visit(LamboExpression.Abstraction abstraction) {
        return new LamboExpression.Abstraction(abstraction.getBoundVariable(), evaluate(abstraction.getBody()));
    }

    @Override
    public LamboExpression visit(LamboExpression.Application application) {
        LamboExpression left = application.getLeft();
        LamboExpression right = application.getRight();
        if (left instanceof LamboExpression.Abstraction){
            LamboExpression.Abstraction safeAbstraction =
                    (LamboExpression.Abstraction) (new PrefixAlphaConverter("`", "")).evaluate(left);
            LamboExpression safeSubstitute =
                    (new PrefixAlphaConverter("˙", "")).evaluate(right);
            UnsafeSubstitution unsafeSubstitution =
                    new UnsafeSubstitution(safeSubstitute, safeAbstraction.getBoundVariable().getName());
            return unsafeSubstitution.evaluate(safeAbstraction.getBody());
        }
        return new LamboExpression.Application(evaluate(left), evaluate(right));
    }

    @Override
    public LamboExpression visit(LamboExpression.Variable variable) {
        return variable;
    }

    public LamboExpression evaluate(LamboExpression expression){
        return expression.accept(this);
    }
}
