package com.marvinmielchen.lambo.semanticanalysis;

import com.marvinmielchen.lambo.Lambo;
import com.marvinmielchen.lambo.syntacticanalysis.LamboExpression;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


@Slf4j
public class Interpreter implements LamboExpression.Visitor<String>{

    public void interpret(LamboExpression expression){
        try {
            String value = evaluate(expression);
            log.info(value);
        } catch (RuntimeError e){
            Lambo.runtimeError(e);
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
        log.info("Evaluating expression: " + expression.toString());
        log.info("Bound variables: " + findBoundVariables(expression));
        log.info("Free variables: " + findFreeVariables(expression));
        return expression.accept(this);
    }



    private Set<String> findBoundVariableLexemes(LamboExpression expression){

    }

    private Set<String> findFreeVariableLexemes(LamboExpression expression){

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
