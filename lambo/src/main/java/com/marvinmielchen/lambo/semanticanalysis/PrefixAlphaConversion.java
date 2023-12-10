package com.marvinmielchen.lambo.semanticanalysis;

import com.marvinmielchen.lambo.syntacticanalysis.LamboExpression;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//This Visitor Performs an Alpha Conversion by appending prefix to all bound variables and a different prefix to all free variables
@RequiredArgsConstructor
public class PrefixAlphaConversion implements LamboExpression.Visitor<LamboExpression> {

    private final String boundVariablePrefix;
    private final String freeVariablePrefix;
    private BindingSpace bindingSpace = new BindingSpace(null);

    @Override
    public LamboExpression visit(LamboExpression.Variable variable) {
        if(bindingSpace.contains(variable.getName())){
            return new LamboExpression.Variable(boundVariablePrefix + variable.getName(), variable.getDefinitionLine());
        } else {
            return new LamboExpression.Variable(freeVariablePrefix + variable.getName(), variable.getDefinitionLine());
        }
    }

    @Override
    public LamboExpression visit(LamboExpression.Abstraction abstraction) {
        int criticalLine = abstraction.getBoundVariable().getDefinitionLine();
        bindingSpace = new BindingSpace(bindingSpace);
        bindingSpace.add(abstraction.getBoundVariable().getName(), criticalLine);
        LamboExpression convertedBody = evaluate(abstraction.getBody());
        bindingSpace = bindingSpace.enclosing;
        return new LamboExpression.Abstraction(
                new LamboExpression.Variable(
                        boundVariablePrefix + abstraction.getBoundVariable().getName(),
                        criticalLine),
                convertedBody
        );
    }

    @Override
    public LamboExpression visit(LamboExpression.Application application) {
        LamboExpression left = evaluate(application.getLeft());
        LamboExpression right = evaluate(application.getRight());
        return new LamboExpression.Application(left, right);
    }

    private LamboExpression evaluate(LamboExpression expression){
        return expression.accept(this);
    }

    @RequiredArgsConstructor
    private static class BindingSpace {
        private final BindingSpace enclosing;
        private final List<String> boundVariables = new ArrayList<>();

        private void add(String variable, int criticalLine){
            if ((enclosing != null && enclosing.contains(variable))
                    || boundVariables.contains(variable)){
                throw new RuntimeError(criticalLine, "Variable " + variable + " is already bound in the vicinity of this expression.");
            }
            boundVariables.add(variable);
        }

        private boolean contains(String variable){
            if(boundVariables.contains(variable)){
                return true;
            } else if(enclosing != null){
                return enclosing.contains(variable);
            } else {
                return false;
            }
        }
    }
}
