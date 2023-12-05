package com.marvinmielchen.lambo.syntacticanalysis;

import com.marvinmielchen.lambo.Lambo;
import com.marvinmielchen.lambo.lexicalanalysis.Token;
import com.marvinmielchen.lambo.lexicalanalysis.TokenType;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Parser {
    private final List<Token> tokens;
    private int current = 0;

    private Expr lambdaExpression(){
        if(match(TokenType.LEFT_BRACE)){
            Expr.Variable boundVariable = variable();
            consume(TokenType.COLON, "Expected ':' after bound variable");
            Expr body = lambdaExpression();
            consume(TokenType.RIGHT_BRACE, "Expected '}' after abstraction expression");
            return new Expr.Abstraction(boundVariable, body);
        }

        if(match(TokenType.LEFT_PAREN)){
            Expr left = lambdaExpression();
            Expr right = lambdaExpression();
            consume(TokenType.RIGHT_PAREN, "Expected ')' after application expression");
            return new Expr.Application(left, right);
        }

        return variable();
    }

    private Expr.Variable variable(){
        Token token = consume(TokenType.IDENTIFIER, "Expected variable name");
        return new Expr.Variable(token);
    }


    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();

        throw error(peek(), message);
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().getTokenType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().getTokenType() == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private Lambo.ParseError error(Token token, String message) {
        Lambo.error(token, message);
        return new Lambo.ParseError();
    }
}
