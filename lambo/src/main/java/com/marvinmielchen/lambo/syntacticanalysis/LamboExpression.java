package com.marvinmielchen.lambo.syntacticanalysis;

import com.marvinmielchen.lambo.lexicalanalysis.Token;
import com.marvinmielchen.lambo.semanticanalysis.RuntimeError;
import lombok.AllArgsConstructor;
import lombok.Getter;

public abstract class LamboExpression {
    @AllArgsConstructor
    @Getter
    public static class Abstraction extends LamboExpression {
        private final Variable boundVariable;
        private final LamboExpression body;

        @Override
        public <R> R accept(Visitor<R> visitor) throws RuntimeError {
            return visitor.visit(this);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Application extends LamboExpression {
        private final LamboExpression left;
        private final LamboExpression right;

        @Override
        public <R> R accept(Visitor<R> visitor) throws RuntimeError {
            return visitor.visit(this);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Variable extends LamboExpression {
        private final Token token;
        @Override
        public <R> R accept(Visitor<R> visitor) throws RuntimeError {
            return visitor.visit(this);
        }
    }

    public abstract <R> R accept(Visitor<R> visitor) throws RuntimeError;

    public interface Visitor<R>{
        R visit(Abstraction abstraction) throws RuntimeError;
        R visit(Application application) throws RuntimeError;
        R visit(Variable variable) throws RuntimeError;
    }
}
