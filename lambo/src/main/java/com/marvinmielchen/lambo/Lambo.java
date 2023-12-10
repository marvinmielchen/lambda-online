package com.marvinmielchen.lambo;

import com.marvinmielchen.lambo.lexicalanalysis.Lexer;
import com.marvinmielchen.lambo.lexicalanalysis.Token;
import com.marvinmielchen.lambo.lexicalanalysis.TokenType;
import com.marvinmielchen.lambo.semanticanalysis.Interpreter;
import com.marvinmielchen.lambo.semanticanalysis.RuntimeError;
import com.marvinmielchen.lambo.syntacticanalysis.AstPrinter;
import com.marvinmielchen.lambo.syntacticanalysis.LamboExpression;
import com.marvinmielchen.lambo.syntacticanalysis.LamboStatement;
import com.marvinmielchen.lambo.syntacticanalysis.Parser;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Lambo {

    private static final Interpreter interpreter = new Interpreter();
    static boolean hadError = false;
    static boolean hadRuntimeError = false;

    public static void run(String source){
        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.scanTokens();

        Parser parser = new Parser(tokens);
        List<LamboStatement> statements = parser.parse();

        AstPrinter astPrinter = new AstPrinter();
        for (LamboStatement statement : statements) {
            log.info(astPrinter.print(statement));
        }

        if (hadError| hadRuntimeError) return;

        //interpreter.simplifyOneStep(statements);
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
        log.error(error.getMessage() + "\n[line " + error.getLine() + "]");
        hadRuntimeError = true;
    }

    private static void report(int line, String where, String message) {
        log.error("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }
}
