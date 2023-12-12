package com.marvinmielchen.lambo.intermediaterep;

import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.Stack;

@RequiredArgsConstructor
public class DeBruijnPrinter implements DeBruijnExpression.Visitor<String> {

    private final DeBruijnExpression input;
    private final String defName;

    private final LinkedList<String> nameSpace = new LinkedList<>();

    public String evaluate(){
        return "def " + this.defName + " " + this.input.accept(this) + ";";
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
        String result = String.format("("  + name + ") {%s}", abstraction.getBody().accept(this));
        nameSpace.pop();
        return result;
    }

    @Override
    public String visit(DeBruijnExpression.Application application) {
        return String.format("{%s %s}", application.getLeft().accept(this), application.getRight().accept(this));
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
}
