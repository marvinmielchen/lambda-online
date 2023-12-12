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
        return "\ndef " + this.defName + " " + this.input.accept(this) + ";";
    }

    @Override
    public String visit(DeBruijnExpression.Abstraction abstraction) {
        String name = abstraction.getOldTokenLexeme();
        name = nameSpace.contains(name) ? name + "'" : name;
        if(nameSpace.contains(name)) {
            name = nextName(abstraction.getOldTokenLexeme());
        }
        while(nameSpace.contains(name)){
            name = nextName(name);
        }
        nameSpace.push(name);
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("(%s){\n", name));
        indentAmount++;
        builder.append(String.format("%s%s\n", indent(), abstraction.getBody().accept(this)));
        indentAmount--;
        builder.append(String.format("%s}", indent()));
        nameSpace.pop();
        return builder.toString();
    }

    @Override
    public String visit(DeBruijnExpression.Application application) {
        StringBuilder builder = new StringBuilder();
        if ((application.getLeft() instanceof DeBruijnExpression.Variable)
                && application.getRight() instanceof DeBruijnExpression.Variable){
            builder.append(String.format("%s %s", application.getLeft().accept(this), application.getRight().accept(this)));
            return builder.toString();
        } else {
            builder.append(String.format("%s\n", application.getLeft().accept(this)));
        }
        if (application.getRight() instanceof DeBruijnExpression.Application){
            builder.append(String.format("%s{\n", indent()));
            indentAmount++;
            builder.append(String.format("%s%s\n", indent(), application.getRight().accept(this)));
            indentAmount--;
            builder.append(String.format("%s}", indent()));
        } else {
            builder.append(String.format("%s%s", indent(), application.getRight().accept(this)));
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
}
