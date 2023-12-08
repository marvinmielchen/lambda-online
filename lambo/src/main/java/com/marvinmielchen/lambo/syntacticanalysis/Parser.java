package com.marvinmielchen.lambo.syntacticanalysis;

import com.marvinmielchen.lambo.Lambo;
import com.marvinmielchen.lambo.lexicalanalysis.Token;
import com.marvinmielchen.lambo.lexicalanalysis.TokenType;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Parser {
    private final List<Token> tokens;
    private int current = 0;

    public LamboExpression parse() {
        try {
            return lambdaExpression();
        } catch (ParseError error) {
            return null;
        }
    }

    private LamboExpression lambdaExpression(){
        return switch (peek().getTokenType()) {
            case LEFT_PAREN -> application();
            case LEFT_BRACE -> abstraction();
            default -> variable();
        };
    }

    private LamboExpression application(){
        if(match(TokenType.LEFT_PAREN)){
            LamboExpression left = lambdaExpression();
            LamboExpression right = lambdaExpression();
            while (peek().getTokenType() != TokenType.RIGHT_PAREN && !isAtEnd()){
                LamboExpression next = lambdaExpression();
                left = new LamboExpression.Application(left, right);
                right = next;
            }
            consume(TokenType.RIGHT_PAREN, "Expected ')' after application expression");
            return new LamboExpression.Application(left, right);
        }
        throw error(peek(), "Expected application expression.");
    }

    private LamboExpression abstraction(){
        if(match(TokenType.LEFT_BRACE)){
            List<LamboExpression.Variable> boundVariables = new ArrayList<>();
            boundVariables.add(variable());
            while (peek().getTokenType() != TokenType.COLON && !isAtEnd()){
                boundVariables.add(variable());
            }
            consume(TokenType.COLON, "Expected ':' after bound variables");
            LamboExpression abstraction = lambdaExpression();
            consume(TokenType.RIGHT_BRACE, "Expected '}' after abstraction expression");
            for (int i = boundVariables.size() - 1; i >= 0; i--) {
                abstraction = new LamboExpression.Abstraction(boundVariables.get(i), abstraction);
            }
            return abstraction;
        }
        throw error(peek(), "Expected abstraction expression.");
    }

    private LamboExpression.Variable variable(){
        if(match(TokenType.IDENTIFIER)){
            return new LamboExpression.Variable(previous());
        }
        throw error(peek(), "Expected lambda expression.");
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

    private ParseError error(Token token, String message) {
        Lambo.error(token, message);
        return new ParseError();
    }

    private void synchronize() {
        advance();

        while (!isAtEnd()) {
            if (previous().getTokenType() == TokenType.SEMICOLON) return;
            advance();
        }
    }
}
