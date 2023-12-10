package com.marvinmielchen.lambo.syntacticanalysis;

import com.marvinmielchen.lambo.lexicalanalysis.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;

public abstract class LamboStatement {

    @AllArgsConstructor
    @Getter
    public static class Definition extends LamboStatement {
        private final Token identifier;
        private final LamboExpression expression;

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }
    }

    public abstract <R> R accept(LamboStatement.Visitor<R> visitor);
    public interface Visitor<R>{
        R visit(Definition definition);
    }
}
