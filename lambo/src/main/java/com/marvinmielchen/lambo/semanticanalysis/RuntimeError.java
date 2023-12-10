package com.marvinmielchen.lambo.semanticanalysis;

import lombok.Getter;

@Getter
public class RuntimeError extends RuntimeException{
    private final Integer line;

    public RuntimeError(Integer line, String message){
        super(message);
        this.line = line;
    }
}
