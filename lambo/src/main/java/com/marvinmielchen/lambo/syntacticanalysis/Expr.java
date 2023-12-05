package com.marvinmielchen.lambo.syntacticanalysis;

import com.marvinmielchen.lambo.lexicalanalysis.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;

public abstract class Expr {
    @AllArgsConstructor
    @Getter
    public static class Abstraction extends Expr{
        final Variable boundVariable;
        final Expr body;

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Application extends Expr{
        final Expr left;
        final Expr right;

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Variable extends Expr{
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
