package com.marvinmielchen.lambo.semanticanalysis;

import com.marvinmielchen.lambo.Lambo;
import com.marvinmielchen.lambo.syntacticanalysis.LamboExpression;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Interpreter implements LamboExpression.Visitor<String>{

    public void interpret(LamboExpression expression){
        try {
            String value = evaluate(expression);
            log.info(value);
        } catch (RuntimeError e){
            Lambo.error(e.getToken(), e.getMessage());
        }
    }

    @Override
    public String visit(LamboExpression.Abstraction abstraction) {
        LamboExpression body = abstraction.getBody();
        return String.format("(λ%s.%s)", abstraction.getBoundVariable().getToken().getLexeme(), evaluate(body));
    }

    @Override
    public String visit(LamboExpression.Application application) {
        LamboExpression left = application.getLeft();
        LamboExpression right = application.getRight();
        return String.format("(%s %s)", evaluate(left), evaluate(right));
    }

    @Override
    public String visit(LamboExpression.Variable variable) {
        return variable.getToken().getLexeme();
    }

    private String evaluate(LamboExpression expression){
        return expression.accept(this);
    }
}
