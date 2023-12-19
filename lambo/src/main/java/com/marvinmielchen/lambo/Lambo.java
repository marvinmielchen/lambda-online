package com.marvinmielchen.lambo;

import com.marvinmielchen.lambo.intermediaterep.DeBruijnExpression;
import com.marvinmielchen.lambo.intermediaterep.DeBruijnPrinter;
import com.marvinmielchen.lambo.lexicalanalysis.Lexer;
import com.marvinmielchen.lambo.lexicalanalysis.LexingError;
import com.marvinmielchen.lambo.lexicalanalysis.Token;
import com.marvinmielchen.lambo.semanticanalysis.Interpreter;
import com.marvinmielchen.lambo.semanticanalysis.RuntimeError;
import com.marvinmielchen.lambo.syntacticanalysis.LamboStatement;
import com.marvinmielchen.lambo.syntacticanalysis.ParseError;
import com.marvinmielchen.lambo.syntacticanalysis.Parser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class Lambo {

    static Interpreter interpreter = new Interpreter();

    public static synchronized List<LamboStatement> syntaxCheck(String source) throws LexingError, ParseError, RuntimeError {
        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.scanTokens();

        Parser parser = new Parser(tokens);
        List<LamboStatement> statements = parser.parse();

        interpreter.calculateBindingEnvironment(statements);

        return statements;
    }

    public static synchronized String betaReduction(String source) throws LexingError, ParseError, RuntimeError {
        List<LamboStatement> statements = syntaxCheck(source);
        HashMap<String, DeBruijnExpression> env = interpreter.calculateBindingEnvironment(statements);
        env = interpreter.performSomeBetaReductions(env);
        return stringifyStatements(statements, env);
    }

    public static synchronized String statementSubstitution(String source) throws LexingError, ParseError, RuntimeError {
        List<LamboStatement> statements = syntaxCheck(source);
        HashMap<String, DeBruijnExpression> env = interpreter.calculateBindingEnvironment(statements);
        env = interpreter.substituteDefinitionsOnce(env);
        return stringifyStatements(statements, env);
    }

    private static String stringifyStatements(List<LamboStatement> statements, HashMap<String, DeBruijnExpression> env) {
        String result = "";
        for (LamboStatement statement : statements) {
            if (statement instanceof LamboStatement.Definition definition){
                String key = definition.getIdentifier().getLexeme();
                result = result.concat(new DeBruijnPrinter(env.get(key), key).evaluate());
            }
        }
        return result;
    }
}
