package com.marvinmielchen.lambo.semanticanalysis;

import com.marvinmielchen.lambo.lexicalanalysis.Token;
import lombok.Getter;

@Getter
public class RuntimeError extends RuntimeException{
    private final Token token;

    public RuntimeError(Token token, String message){
        super(message);
        this.token = token;
    }
}
