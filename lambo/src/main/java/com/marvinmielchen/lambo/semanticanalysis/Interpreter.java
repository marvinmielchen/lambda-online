package com.marvinmielchen.lambo.semanticanalysis;

import com.marvinmielchen.lambo.Lambo;
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



    private Set<String> findBoundVariableLexemes(LamboExpression expression){
        return null;
    }

    private Set<String> findFreeVariableLexemes(LamboExpression expression){
        return null;
    }

    private LamboExpression betaReduction(LamboExpression.Application beta_redex){
        //TODO: Implement beta reduction
        return null;
    }

    private LamboExpression alphaConversion(LamboExpression expression ){
        //TODO: Implement alpha conversion
        return null;
    }

}
