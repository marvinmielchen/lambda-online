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

    public List<LamboStatement> parse() throws ParseError {
        List<LamboStatement> statements = new ArrayList<>();
        while (!isAtEnd()) {
            try {
                LamboStatement statement = statement();
                statements.add(statement);
            } catch (ParseError error) {
                //synchronize();
                throw error;
            }
        }
        return statements;
    }

    private LamboStatement statement() throws ParseError {
        consume(TokenType.DEF, "Expected 'def'.", peek());
        Token identifier = consume(TokenType.IDENTIFIER, "Expected identifier.", previous());
        LamboExpression expression = expression();
        return new LamboStatement.Definition(identifier, expression);
    }

    private LamboExpression expression() throws ParseError {
        if(peek().getTokenType() == TokenType.IDENTIFIER){
            return variable();
        }
        if(peek().getTokenType() == TokenType.LEFT_BRACE){
            return expressionBody();
        }
        if (peek().getTokenType() == TokenType.LEFT_PAREN){
            return expressionHead();
        }
        throw error(previous(), "Expected expression.");
    }

    private LamboExpression.Variable variable() throws ParseError {
        if(match(TokenType.IDENTIFIER)){
            return new LamboExpression.Variable(previous());
        }
        throw error(peek(), "Expected lambda expression.");
    }

    private LamboExpression expressionHead() throws ParseError {
        consume(TokenType.LEFT_PAREN, "Expected '('.", previous());
        List<LamboExpression.Variable> variables = new ArrayList<>();
        while(peek().getTokenType() == TokenType.IDENTIFIER){
            LamboExpression.Variable variable = variable();
            variables.add(variable);
        }
        consume(TokenType.RIGHT_PAREN, "Expected ')'.", previous());
        LamboExpression expression = expressionBody();
        if(variables.isEmpty()){
            return expression;
        }
        //iterate over variables from last to first
        int last = variables.size() - 1;
        LamboExpression.Abstraction abstraction = new LamboExpression.Abstraction(variables.get(last), expression);;
        for(int i = variables.size() - 2; i >= 0; i--){
            LamboExpression.Variable variable = variables.get(i);
            abstraction = new LamboExpression.Abstraction(variable, abstraction);
        }
        return abstraction;
    }

    private LamboExpression expressionBody() throws ParseError {
        consume(TokenType.LEFT_BRACE, "Expected '{'.", previous());
        LamboExpression expression = expression();
        while (List.of(TokenType.LEFT_BRACE, TokenType.LEFT_PAREN, TokenType.IDENTIFIER)
                .contains(peek().getTokenType())){
            LamboExpression newExpression = expression();
            expression = new LamboExpression.Application(expression, newExpression);
        }
        consume(TokenType.RIGHT_BRACE, "Expected '}'.", previous());
        return expression;
    }


    private Token consume(TokenType type, String message, Token errorToken) throws ParseError {
        if (check(type)) return advance();

        throw error(errorToken, message);
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
        return new ParseError(token, message);
    }

    private void synchronize() {
        advance();

        while (!isAtEnd()) {
            if (previous().getTokenType() == TokenType.SEMICOLON) return;
            advance();
        }
    }
}
