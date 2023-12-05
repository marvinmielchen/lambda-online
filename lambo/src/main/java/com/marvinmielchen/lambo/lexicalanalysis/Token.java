package com.marvinmielchen.lambo.lexicalanalysis;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Token {
    private final TokenType tokenType;
    private final String lexeme;
    private final Object literal;
    private final int line;
}
