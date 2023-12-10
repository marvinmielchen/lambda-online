package com.marvinmielchen.lambo.syntacticanalysis;

import lombok.AllArgsConstructor;
import lombok.Getter;

public abstract class LamboExpression {
    @AllArgsConstructor
    @Getter
    public static class Abstraction extends LamboExpression {
        private final Variable boundVariable;
        private final LamboExpression body;

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Application extends LamboExpression {
        private final LamboExpression left;
        private final LamboExpression right;

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Variable extends LamboExpression {
        private final String name;
        private final Integer definitionLine;
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
