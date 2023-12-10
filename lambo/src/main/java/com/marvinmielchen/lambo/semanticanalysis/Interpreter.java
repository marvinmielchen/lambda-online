package com.marvinmielchen.lambo.semanticanalysis;


import com.marvinmielchen.lambo.Lambo;
import com.marvinmielchen.lambo.lexicalanalysis.Token;
import com.marvinmielchen.lambo.syntacticanalysis.LamboExpression;
import com.marvinmielchen.lambo.syntacticanalysis.LamboStatement;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Slf4j
public class Interpreter implements LamboStatement.Visitor<Void>{

    private Environment environment = new Environment();

    public List<LamboStatement> simplifyOneStep(List<LamboStatement> statement){
        //store definitions in environment
        for (LamboStatement s : statement) {
            try {
                s.accept(this);
            } catch (RuntimeError error) {
                Lambo.runtimeError(error);
                break;
            }
        }
        //evaluate expressions with one turn of beta reductions
        PrefixAlphaConverter prefixAlphaConverter = new PrefixAlphaConverter("b", "f");
        List<LamboStatement> simplifiedStatements = new ArrayList<>();
        for (LamboStatement s : statement) {
            try {
                if (s instanceof LamboStatement.Definition definition){
                    Token identifier = definition.getIdentifier();
                    LamboExpression expression = prefixAlphaConverter.evaluate(definition.getExpression());
                    simplifiedStatements.add(new LamboStatement.Definition(identifier, expression));
                }
            } catch (RuntimeError error) {
                Lambo.runtimeError(error);
                break;
            }
        }

        return simplifiedStatements;
    }

    //Statement Visitors
    @Override
    public Void visit(LamboStatement.Definition definition) {
        Token identifier = definition.getIdentifier();
        LamboExpression expression = definition.getExpression();
        environment.define(identifier, expression);
        return null;
    }
}
