package com.marvinmielchen.lambo.syntacticanalysis;

public class AstPrinter implements LamboExpression.Visitor<String> {

    public String print(LamboExpression lamboExpression){
        return lamboExpression.accept(this);
    }

    @Override
    public String visit(LamboExpression.Abstraction abstraction) {
        return parenthesize("λ", abstraction.boundVariable, abstraction.body);
    }

    @Override
    public String visit(LamboExpression.Application application) {
        return parenthesize("apply", application.left, application.right);
    }

    @Override
    public String visit(LamboExpression.Variable variable) {
        return variable.token.getLexeme();
    }

    private String parenthesize(String name, LamboExpression... lamboExpressions) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (LamboExpression lamboExpression : lamboExpressions) {
            builder.append(" ");
            builder.append(lamboExpression.accept(this));
        }
        builder.append(")");

        return builder.toString();
    }
}
