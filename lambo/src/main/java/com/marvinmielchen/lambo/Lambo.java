package com.marvinmielchen.lambo;

import com.marvinmielchen.lambo.lexicalanalysis.Lexer;
import com.marvinmielchen.lambo.lexicalanalysis.Token;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Lambo {

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

    private static void report(int line, String where, String message) {
        log.error("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }
}
