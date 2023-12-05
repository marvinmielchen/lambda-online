package com.marvinmielchen.lambo;

import com.marvinmielchen.lambo.lexicalanalysis.Lexer;
import com.marvinmielchen.lambo.lexicalanalysis.Token;
import com.marvinmielchen.lambo.lexicalanalysis.TokenType;
import com.marvinmielchen.lambo.syntacticanalysis.AstPrinter;
import com.marvinmielchen.lambo.syntacticanalysis.Expr;
import com.marvinmielchen.lambo.syntacticanalysis.Parser;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Lambo {

    public static class ParseError extends RuntimeException {}
    static boolean hadError = false;

    public static void run(String source){
        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.scanTokens();

        for (Token token : tokens) {
            if (token.getTokenType() == TokenType.EOF) break;
            log.info(token.toString());
        }

        Parser parser = new Parser(tokens);
        Expr expression = parser.parse();

        if (hadError) return;
        log.info(new AstPrinter().print(expression));
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

    private static void report(int line, String where, String message) {
        log.error("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }
}
