package com.marvinmielchen.lambo.intermediaterep;


import lombok.AllArgsConstructor;
import lombok.Getter;

public abstract class DeBruijnExpression {

    @AllArgsConstructor
    @Getter
    public static class Abstraction extends DeBruijnExpression{
        private final DeBruijnExpression body;
        private final String oldTokenLexeme;

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Application extends DeBruijnExpression{

        private final DeBruijnExpression left;
        private final DeBruijnExpression right;

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Variable extends DeBruijnExpression{

        private final String name;

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class DeBruijnIndex extends DeBruijnExpression{

        private final int index;

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
        R visit(DeBruijnIndex deBruijnIndex);
    }


}
