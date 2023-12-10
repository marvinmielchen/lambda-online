package com.marvinmielchen.lambo.semanticanalysis;

import com.marvinmielchen.lambo.syntacticanalysis.LamboExpression;

import java.util.Map;

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
            Map<String, LamboExpression> substitutions = Map.of(safeAbstraction.getBoundVariable().getName(), safeSubstitute);
            UnsafeSubstitution unsafeSubstitution =
                    new UnsafeSubstitution(substitutions);
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
