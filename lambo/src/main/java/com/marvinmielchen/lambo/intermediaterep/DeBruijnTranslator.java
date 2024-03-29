package com.marvinmielchen.lambo.intermediaterep;

import com.marvinmielchen.lambo.lexicalanalysis.Token;
import com.marvinmielchen.lambo.semanticanalysis.RuntimeError;
import com.marvinmielchen.lambo.syntacticanalysis.LamboExpression;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DeBruijnTranslator implements LamboExpression.Visitor<DeBruijnExpression>{

    private DeBruijnEnvironment environment = new DeBruijnEnvironment(null);
    private final List<String> reservedVariables;
    private final LamboExpression input;

    public DeBruijnExpression translate() throws RuntimeError {
        return input.accept(this);
    }

    @Override
    public DeBruijnExpression visit(LamboExpression.Abstraction abstraction) throws RuntimeError{
        Token lambdaVar = abstraction.getBoundVariable().getToken();
        if(environment.contains(lambdaVar.getLexeme()) || reservedVariables.contains(lambdaVar.getLexeme())){
            throw new RuntimeError(lambdaVar.getLine(), String.format("Variable %s already defined in this scope", lambdaVar.getLexeme()));
        }
        environment = new DeBruijnEnvironment(environment);
        environment.define(lambdaVar.getLexeme());
        DeBruijnExpression body = abstraction.getBody().accept(this);
        environment = environment.getEnclosing();
        return new DeBruijnExpression.Abstraction(body, lambdaVar.getLexeme());
    }

    @Override
    public DeBruijnExpression visit(LamboExpression.Application application) throws RuntimeError {
        DeBruijnExpression left = application.getLeft().accept(this);
        DeBruijnExpression right = application.getRight().accept(this);
        return new DeBruijnExpression.Application(left, right);
    }

    @Override
    public DeBruijnExpression visit(LamboExpression.Variable variable) {
        if (environment.contains(variable.getToken().getLexeme())) {
            return new DeBruijnExpression.DeBruijnIndex(environment.getVariableIndex(variable.getToken().getLexeme()));
        }else {
            return new DeBruijnExpression.Variable(variable.getToken().getLexeme());
        }
    }
}
