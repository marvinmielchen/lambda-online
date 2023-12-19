package com.marvinmielchen.lambo.semanticanalysis;

import lombok.Getter;

@Getter
public class RuntimeError extends Exception{
    private final int line;

    public RuntimeError(int line, String message){
        super(message);
        this.line = line;
    }
}
