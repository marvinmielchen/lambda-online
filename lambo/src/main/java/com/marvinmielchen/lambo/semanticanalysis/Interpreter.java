package com.marvinmielchen.lambo.semanticanalysis;


import com.marvinmielchen.lambo.Lambo;
import com.marvinmielchen.lambo.lexicalanalysis.Token;
import com.marvinmielchen.lambo.syntacticanalysis.LamboExpression;
import com.marvinmielchen.lambo.syntacticanalysis.LamboStatement;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class Interpreter{


    public List<LamboStatement> simplifyOneStep(List<LamboStatement> statement){
        RecursiveBetaReduction betaReductionConverter = new RecursiveBetaReduction();
        List<LamboStatement> simplifiedStatements = new ArrayList<>();
        for (LamboStatement s : statement) {
            try {
                if (s instanceof LamboStatement.Definition definition){
                    Token identifier = definition.getIdentifier();
                    LamboExpression expression = betaReductionConverter.evaluate(definition.getExpression());
                    simplifiedStatements.add(new LamboStatement.Definition(identifier, expression));
                }
            } catch (RuntimeError error) {
                Lambo.runtimeError(error);
                break;
            }
        }
        return simplifiedStatements;
    }

    public List<LamboStatement> substituteDefinitionsOnStep(List<LamboStatement> statement){
        return null;
    }



}
