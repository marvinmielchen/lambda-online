package com.marvinmielchen.lambo.syntacticanalysis;

public class AstPrinter implements LamboExpression.Visitor<String>, LamboStatement.Visitor<String>{


    public String print(LamboStatement statement){
        return statement.accept(this);
    }
    @Override
    public String visit(LamboExpression.Abstraction abstraction) {
        return String.format("(%s) %s", abstraction.getBoundVariable().getToken().getLexeme(), abstraction.getBody().accept(this));
    }

    @Override
    public String visit(LamboExpression.Application application) {
        return String.format("{%s %s}", application.getLeft().accept(this), application.getRight().accept(this));
    }

    @Override
    public String visit(LamboExpression.Variable variable) {
        return variable.getToken().getLexeme();
    }

    @Override
    public String visit(LamboStatement.Definition definition) {
        return String.format("def %s %s;", definition.getIdentifier().getLexeme(), definition.getExpression().accept(this));
    }
}
