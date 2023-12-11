package com.marvinmielchen.lambo.semanticanalysis;


import com.marvinmielchen.lambo.Lambo;
import com.marvinmielchen.lambo.intermediaterep.DeBruijnClone;
import com.marvinmielchen.lambo.intermediaterep.DeBruijnExpression;
import com.marvinmielchen.lambo.intermediaterep.DeBruijnSubstitution;
import com.marvinmielchen.lambo.intermediaterep.DeBruijnTranslator;
import com.marvinmielchen.lambo.lexicalanalysis.Token;
import com.marvinmielchen.lambo.syntacticanalysis.LamboStatement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
public class Interpreter {

    public HashMap<String, DeBruijnExpression> calculateBindingEnvironment(List<LamboStatement> statements){
        HashMap<String, DeBruijnExpression> environment = new HashMap<>();
        try {
            for (LamboStatement statement : statements) {
                if(statement instanceof LamboStatement.Definition definition){
                    Token lambdaVar = definition.getIdentifier();
                    if(environment.containsKey(lambdaVar.getLexeme())){
                        throw new RuntimeError(lambdaVar.getLine(), String.format("Variable %s already defined in this scope", lambdaVar.getLexeme()));
                    }else {
                        DeBruijnTranslator translator = new DeBruijnTranslator(definition.getExpression());
                        environment.put(lambdaVar.getLexeme(), translator.translate());
                    }
                }
            }
        } catch (RuntimeError error){
            Lambo.runtimeError(error);
        }
        return environment;
    }

    public HashMap<String, DeBruijnExpression> substituteDefinitionsOnce(HashMap<String, DeBruijnExpression> environment){
        HashMap<String, DeBruijnExpression> newEnvironment = new HashMap<>();
        for (Map.Entry<String, DeBruijnExpression> entry : environment.entrySet()) {
            DeBruijnExpression expression = new DeBruijnClone(entry.getValue()).evaluate();
            newEnvironment.put(entry.getKey(), new DeBruijnSubstitution(expression, environment).evaluate());
        }
        return newEnvironment;
    }




}
