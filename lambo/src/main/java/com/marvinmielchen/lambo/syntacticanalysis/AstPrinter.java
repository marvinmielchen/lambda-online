package com.marvinmielchen.lambo.syntacticanalysis;

import com.marvinmielchen.lambo.semanticanalysis.RuntimeError;

public class AstPrinter implements LamboExpression.Visitor<String>, LamboStatement.Visitor<String>{


    public String print(LamboStatement statement) throws RuntimeError {
        return statement.accept(this);
    }
    @Override
    public String visit(LamboExpression.Abstraction abstraction) throws RuntimeError {
        return String.format("(λ.%s %s)", abstraction.getBoundVariable().getToken().getLexeme(), abstraction.getBody().accept(this));
    }

    @Override
    public String visit(LamboExpression.Application application) throws RuntimeError {
        return String.format("(apply %s %s)", application.getLeft().accept(this), application.getRight().accept(this));
    }

    @Override
    public String visit(LamboExpression.Variable variable) {
        return variable.getToken().getLexeme();
    }

    @Override
    public String visit(LamboStatement.Definition definition) throws RuntimeError {
        return String.format("%s = %s;", definition.getIdentifier().getLexeme(), definition.getExpression().accept(this));
    }
}
