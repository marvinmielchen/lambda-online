package com.marvinmielchen.lambo.semanticanalysis;

import com.marvinmielchen.lambo.Lambo;
import com.marvinmielchen.lambo.lexicalanalysis.Token;
import com.marvinmielchen.lambo.syntacticanalysis.LamboExpression;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;


@Slf4j
public class Interpreter implements LamboExpression.Visitor<String>{

    private int depth = 0;

    public void interpret(LamboExpression expression){
        try {
            depth = 0;
            String value = "\n" + evaluate(expression);
            log.info(value);
        } catch (RuntimeError e){
            Lambo.runtimeError(e);
        }
    }

    @Override
    public String visit(LamboExpression.Abstraction abstraction) {
        LamboExpression body = abstraction.getBody();
        return String.format("(%s)%s", abstraction.getBoundVariable().getToken().getLexeme(), evaluate(body));
    }

    @Override
    public String visit(LamboExpression.Application application) {
        LamboExpression left = application.getLeft();
        LamboExpression right = application.getRight();
        StringBuilder result = new StringBuilder();
        result.append("{\n");
        depth++;
        result.append(indentation());
        result.append(evaluate(left));
        result.append("\n");
        result.append(indentation());
        result.append(evaluate(right));
        result.append("\n");
        depth--;
        result.append(indentation());
        result.append("}");
        return result.toString();
    }

    @Override
    public String visit(LamboExpression.Variable variable) {
        return variable.getToken().getLexeme();
    }

    private String evaluate(LamboExpression expression){
        return expression.accept(this);
    }

    private String indentation(){
        return "    ".repeat(Math.max(0, depth));
    }

    private LamboExpression betaReduction(LamboExpression.Application beta_redex){
        LamboExpression left = stupidAlphaConversion(beta_redex.getLeft(), "a");
        LamboExpression right = stupidAlphaConversion(beta_redex.getRight(), "b");

        return null;
    }

    //applies a given prefix to all variables in the expression
    private LamboExpression stupidAlphaConversion(LamboExpression expression, String prefix){
        if (expression instanceof LamboExpression.Abstraction abstraction){
            return new LamboExpression.Abstraction(
                    new LamboExpression.Variable(prefix + abstraction.getBoundVariable().getName()),
                    stupidAlphaConversion(abstraction.getBody(), prefix)
            );
        } else if (expression instanceof LamboExpression.Application application){
            return new LamboExpression.Application(
                    stupidAlphaConversion(application.getLeft(), prefix),
                    stupidAlphaConversion(application.getRight(), prefix)
            );
        } else if (expression instanceof LamboExpression.Variable variable){
            return new LamboExpression.Variable(prefix + variable.getName());
        }
        return null;
    }

}
