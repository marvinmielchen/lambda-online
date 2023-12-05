package com.marvinmielchen.lambo.lexicalanalysis;

import com.marvinmielchen.lambo.Lambo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Lexer {

    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    public List<Token> scanTokens() {
        while(!isAtEnd()){
            start = current;
            scanToken();
        }
        tokens.add(new Token(TokenType.EOF, "", null, line));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(TokenType.LEFT_PAREN); break;
            case ')': addToken(TokenType.RIGHT_PAREN); break;
            case '{': addToken(TokenType.LEFT_BRACE); break;
            case '}': addToken(TokenType.RIGHT_BRACE); break;
            case ':': addToken(TokenType.COLON); break;
            case '=': addToken(TokenType.EQUAL); break;
            case ';': addToken(TokenType.SEMICOLON); break;
            default: Lambo.error(line, "Unexpected character."); break;
        }

    }

    private char advance() {
        char result = source.charAt(current);
        current++;
        return result;
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }
}
