package com.marvinmielchen.lambo.lexicalanalysis;

public enum TokenType {
    // Single-character tokens.
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, SEMICOLON, COLON, EQUAL,

    // Literals.
    IDENTIFIER,

    EOF
}
