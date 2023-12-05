package com.marvinmielchen.lambo;

import com.marvinmielchen.lambo.lexicalanalysis.Lexer;
import com.marvinmielchen.lambo.lexicalanalysis.Token;
import com.marvinmielchen.lambo.lexicalanalysis.TokenType;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Lambo {

    public static class ParseError extends RuntimeException {}
    static boolean hadError = false;

    public static void run(String source){
        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.scanTokens();

        for (Token token : tokens){
                System.out.println(token);
        }

        if (hadError) System.exit(65);
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
