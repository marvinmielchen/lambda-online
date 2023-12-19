package com.marvinmielchen.lambo.lexicalanalysis;

import lombok.Getter;

@Getter
public class LexingError extends Exception {
    private final int line;

    public LexingError(int line, String message){
        super(message);
        this.line = line;
    }
}
