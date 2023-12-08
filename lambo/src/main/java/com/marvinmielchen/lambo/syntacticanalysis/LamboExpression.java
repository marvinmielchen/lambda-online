package com.marvinmielchen.lambo.syntacticanalysis;

import com.marvinmielchen.lambo.lexicalanalysis.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;

public abstract class LamboExpression {
    @AllArgsConstructor
    @Getter
    public static class Abstraction extends LamboExpression {
        final Variable boundVariable;
        final LamboExpression body;

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Application extends LamboExpression {
        final LamboExpression left;
        final LamboExpression right;

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Variable extends LamboExpression {
        final Token token;
        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }
    }

    public abstract <R> R accept(Visitor<R> visitor);

    public interface Visitor<R>{
        R visit(Abstraction abstraction);
        R visit(Application application);
        R visit(Variable variable);
    }
}
