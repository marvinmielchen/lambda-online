package com.marvinmielchen.lambo.semanticanalysis;

import lombok.AllArgsConstructor;
import lombok.Getter;

public abstract class DeBruijnExpression {

    public abstract <R> R accept(DeBruijnVisitor<R> visitor);

    public interface DeBruijnVisitor<R>{
        R visit(DeBruijnAbstraction abstraction);
        R visit(DeBruijnApplication application);
        R visit(DeBruijnVariable variable);
        R visit(DeBruijnIndex index);

    }

    @AllArgsConstructor
    @Getter
    public static class DeBruijnAbstraction extends DeBruijnExpression{
        final DeBruijnExpression body;


        @Override
        public <R> R accept(DeBruijnVisitor<R> visitor) {
            return visitor.visit(this);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class DeBruijnApplication extends DeBruijnExpression{
        final DeBruijnExpression left;
        final DeBruijnExpression right;

        @Override
        public <R> R accept(DeBruijnVisitor<R> visitor) {
            return visitor.visit(this);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class DeBruijnVariable extends DeBruijnExpression {
        final String name;

        @Override
        public <R> R accept(DeBruijnVisitor<R> visitor) {
            return visitor.visit(this);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class DeBruijnIndex extends DeBruijnExpression {
        final int index;

        @Override
        public <R> R accept(DeBruijnVisitor<R> visitor) {
            return visitor.visit(this);
        }
    }
}
