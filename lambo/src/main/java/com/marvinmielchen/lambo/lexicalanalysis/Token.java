package com.marvinmielchen.lambo.lexicalanalysis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class Token {
    private final TokenType tokenType;
    private final String lexeme;
    private final Object literal;
    private final int line;
}
