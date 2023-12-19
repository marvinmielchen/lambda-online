package com.marvinmielchen.lambo.lexicalanalysis;

import com.marvinmielchen.lambo.Lambo;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class Lexer {

    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    public List<Token> scanTokens() throws LexingError{
        while(!isAtEnd()){
            start = current;
            scanToken();
        }
        tokens.add(new Token(TokenType.EOF, "", null, line));
        return tokens;
    }

    private void scanToken() throws LexingError{
        char c = advance();
        switch (c) {
            case '(': addToken(TokenType.LEFT_PAREN); break;
            case ')': addToken(TokenType.RIGHT_PAREN); break;
            case '{': addToken(TokenType.LEFT_BRACE); break;
            case '}': addToken(TokenType.RIGHT_BRACE); break;
            case '=': addToken(TokenType.EQUAL); break;
            case ';': addToken(TokenType.SEMICOLON); break;
            case '/':
                if (match('/')) {
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else {
                    throw new LexingError(line, "Unexpected character.");
                }
                break;
            case ' ': case '\r': case '\t': break;
            case '\n': line++; break;
            default:
                if (isAlpha(c)) {
                    identifierOrKeyword();
                } else {
                    throw new LexingError(line, "Unexpected character.");
                }
                break;
        }

    }

    private void identifierOrKeyword() {
        while (isAlphaNumeric(peek())) advance();
        String text = source.substring(start, current);
        TokenType type = keywords.get(text);
        if (type == null) type = TokenType.IDENTIFIER;
        addToken(type);
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
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

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("def", TokenType.DEF);
    }

}
