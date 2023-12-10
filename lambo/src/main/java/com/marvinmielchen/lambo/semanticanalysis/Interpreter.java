package com.marvinmielchen.lambo.semanticanalysis;


import com.marvinmielchen.lambo.Lambo;
import com.marvinmielchen.lambo.lexicalanalysis.Token;
import com.marvinmielchen.lambo.syntacticanalysis.LamboExpression;
import com.marvinmielchen.lambo.syntacticanalysis.LamboStatement;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public class Interpreter implements LamboExpression.Visitor<Void>, LamboStatement.Visitor<Void>{

    private Environment environment = new Environment();

    public void simplifyOneStep(List<LamboStatement> statement){
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
        for (LamboStatement s : statement) {
            try {
                if (s instanceof LamboStatement.Definition definition){
                    definition.getExpression().accept(this);
                }
            } catch (RuntimeError error) {
                Lambo.runtimeError(error);
                break;
            }
        }
    }

    //Statement Visitors
    @Override
    public Void visit(LamboStatement.Definition definition) {
        Token identifier = definition.getIdentifier();
        LamboExpression expression = definition.getExpression();
        environment.define(identifier, expression);
        return null;
    }


    //Expression Visitors
    @Override
    public Void visit(LamboExpression.Abstraction abstraction) {
        return null;
    }

    @Override
    public Void visit(LamboExpression.Application application) {
        return null;
    }

    @Override
    public Void visit(LamboExpression.Variable variable) {
        return null;
    }


}
