package com.marvinmielchen.lambo.semanticanalysis;

import com.marvinmielchen.lambo.lexicalanalysis.Token;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final Map<String, Object> values = new HashMap<>();

    void define(Token identifier, Object value) {
        if (values.containsKey(identifier.getLexeme())) {
            throw new RuntimeError(identifier.getLine(),
                    "Variable '" + identifier.getLexeme() + "' was already defined. Redefinition is not allowed in Lambo.");
        }
        values.put(identifier.getLexeme(), value);
    }

    Object get(Token identifier) {
        if (values.containsKey(identifier.getLexeme())) {
            return values.get(identifier.getLexeme());
        }
        throw new RuntimeError(identifier.getLine(),
                "Undefined variable '" + identifier.getLexeme() + "'.");
    }
}
