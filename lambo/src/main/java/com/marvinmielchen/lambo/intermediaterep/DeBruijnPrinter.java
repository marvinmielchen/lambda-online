package com.marvinmielchen.lambo.intermediaterep;

import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.Stack;

@RequiredArgsConstructor
public class DeBruijnPrinter implements DeBruijnExpression.Visitor<String> {

    private final DeBruijnExpression input;
    private final String defName;
    private final LinkedList<String> nameSpace = new LinkedList<>();

    private int indentAmount = 0;

    public String evaluate(){
        return "\ndef " + this.defName + " " + this.input.accept(this) + "\n";
    }

    @Override
    public String visit(DeBruijnExpression.Abstraction abstraction) {
        String name = getAbstractionName(abstraction);
        StringBuilder builder = new StringBuilder();
        DeBruijnExpression.Abstraction localAbstraction = abstraction;
        builder.append("(");
        builder.append(name);
        /*
        while (localAbstraction.getBody() instanceof DeBruijnExpression.Abstraction innerAbstraction){
            builder.append(" ");
            builder.append(getAbstractionName(innerAbstraction));
            localAbstraction = innerAbstraction;
        }
         */
        builder.append(")");
        builder.append("{\n");
        indentAmount++;
        builder.append(indent());
        builder.append(localAbstraction.getBody().accept(this));
        builder.append("\n");
        indentAmount--;
        builder.append(indent());
        builder.append("}");
        nameSpace.pop();
        return builder.toString();
    }

    @Override
    public String visit(DeBruijnExpression.Application application) {
        StringBuilder builder = new StringBuilder();
        if(application.getLeft() instanceof DeBruijnExpression.Abstraction ||
                application.getRight() instanceof DeBruijnExpression.Abstraction ||
                application.getRight() instanceof DeBruijnExpression.Application ||
                application.getLeft() instanceof DeBruijnExpression.Application
        ) {
            builder.append("{\n");
            indentAmount++;
            builder.append(indent()).append(application.getLeft().accept(this));
            builder.append("\n");
            builder.append(indent()).append(application.getRight().accept(this));
            builder.append("\n");
            indentAmount--;
            builder.append(indent());
            builder.append("}");
        } else {
            builder.append("{");
            builder.append(application.getLeft().accept(this));
            builder.append(" ");
            builder.append(application.getRight().accept(this));
            builder.append("}");
        }
        return builder.toString();
    }

    @Override
    public String visit(DeBruijnExpression.Variable variable) {
        return variable.getName();
    }

    @Override
    public String visit(DeBruijnExpression.DeBruijnIndex deBruijnIndex) {
        int index = deBruijnIndex.getIndex();
        return nameSpace.get(index);
    }

    private String nextName(String name){
        if (name == null) return "a";
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) < 'z'){
                char c = name.charAt(i);
                c++;
                return name.substring(0, i) + c + name.substring(i+1);
            }
        }
        return name + "a";
    }

    private String indent(){
        return "    ".repeat(indentAmount);
    }

    private String getAbstractionName(DeBruijnExpression.Abstraction abstraction){
        String name = abstraction.getOldTokenLexeme();
        name = nameSpace.contains(name) ? name + "'" : name;
        if(nameSpace.contains(name)) {
            name = nextName(abstraction.getOldTokenLexeme());
        }
        while(nameSpace.contains(name)){
            name = nextName(name);
        }
        nameSpace.push(name);
        return name;
    }
}
