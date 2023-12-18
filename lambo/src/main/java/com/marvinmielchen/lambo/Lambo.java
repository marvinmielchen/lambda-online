package com.marvinmielchen.lambo;

import com.marvinmielchen.lambo.intermediaterep.DeBruijnExpression;
import com.marvinmielchen.lambo.intermediaterep.DeBruijnPrinter;
import com.marvinmielchen.lambo.lexicalanalysis.Lexer;
import com.marvinmielchen.lambo.lexicalanalysis.Token;
import com.marvinmielchen.lambo.lexicalanalysis.TokenType;
import com.marvinmielchen.lambo.semanticanalysis.Interpreter;
import com.marvinmielchen.lambo.semanticanalysis.RuntimeError;
import com.marvinmielchen.lambo.syntacticanalysis.AstPrinter;
import com.marvinmielchen.lambo.syntacticanalysis.LamboStatement;
import com.marvinmielchen.lambo.syntacticanalysis.Parser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class Lambo {

    static Interpreter interpreter = new Interpreter();
    static boolean hadError = false;
    static boolean hadRuntimeError = false;
    static final List<String> lastErrors = new ArrayList<>();

    public static synchronized List<String> syntaxCheck(String source) {
        lastErrors.clear();

        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.scanTokens();

        if (hadError| hadRuntimeError) return lastErrors;

        Parser parser = new Parser(tokens);
        List<LamboStatement> statements = parser.parse();

        if (hadError | hadRuntimeError) return lastErrors;

        interpreter.calculateBindingEnvironment(statements);

        return lastErrors;
    }

    public static synchronized List<String> betaReduction(String source) {
        lastErrors.clear();

        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.scanTokens();

        if (hadError | hadRuntimeError) return lastErrors;

        Parser parser = new Parser(tokens);
        List<LamboStatement> statements = parser.parse();

        if (hadError | hadRuntimeError) return lastErrors;

        HashMap<String, DeBruijnExpression> env = interpreter.calculateBindingEnvironment(statements);

        if (hadError | hadRuntimeError) return lastErrors;

        env = interpreter.performSomeBetaReductions(env);

        if (hadError | hadRuntimeError) return lastErrors;

        List<String> result = new ArrayList<>();
        for (LamboStatement statement : statements) {
            if (statement instanceof LamboStatement.Definition definition){
                String key = definition.getIdentifier().getLexeme();
                result.add(new DeBruijnPrinter(env.get(key), key).evaluate());
            }
        }
        return result;
    }


    public static void error(int line, String message){
        report(line, "", message);
    }

    public static void error(Token token, String message) {
        if (token.getTokenType() == TokenType.EOF) {
            report(token.getLine(), " at end", message);
        } else {
            report(token.getLine(), " at '" + token.getLexeme() + "'", message);
        }
    }

    public static void runtimeError(RuntimeError error){
        lastErrors.add("Runtime Error: " + error.getMessage() + " [line " + error.getLine() + "]");
        hadRuntimeError = true;
    }

    private static void report(int line, String where, String message) {
        lastErrors.add("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }
}
