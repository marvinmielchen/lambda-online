package com.marvinmielchen.lambo.syntacticanalysis;

import com.marvinmielchen.lambo.lexicalanalysis.Token;
import lombok.Getter;


@Getter
public class ParseError extends Exception {
    Token token;

    public ParseError(Token token, String message){
        super(message);
        this.token = token;
    }
}
