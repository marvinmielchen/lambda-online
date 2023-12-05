package com.marvinmielchen.lambo.syntacticanalysis;

public class AstPrinter implements Expr.Visitor<String> {

    public String print(Expr expr){
        return expr.accept(this);
    }

    @Override
    public String visit(Expr.Abstraction abstraction) {
        return parenthesize("λ", abstraction.boundVariable, abstraction.body);
    }

    @Override
    public String visit(Expr.Application application) {
        return parenthesize("apply", application.left, application.right);
    }

    @Override
    public String visit(Expr.Variable variable) {
        return variable.token.getLexeme();
    }

    private String parenthesize(String name, Expr... expressions) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (Expr expr : expressions) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");

        return builder.toString();
    }
}
