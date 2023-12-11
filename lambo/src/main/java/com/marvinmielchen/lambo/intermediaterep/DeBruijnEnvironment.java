package com.marvinmielchen.lambo.intermediaterep;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
public class DeBruijnEnvironment {


    @Getter
    private final DeBruijnEnvironment enclosing;
    private final List<String> variables = new ArrayList<>();

    public boolean contains(String name){
        return variables.contains(name) || (enclosing != null && enclosing.contains(name));
    }

    public void define(String name) throws AlreadyDefinedException{
        if(contains(name)){
            throw new AlreadyDefinedException();
        }
        variables.add(name);
    }

    public int getVariableIndex(String name) throws NotDefinedException{
        if(variables.contains(name)){
            return 0;
        }else if(enclosing != null){
            return enclosing.getVariableIndex(name) + 1;
        }
        throw new NotDefinedException();
    }

    public static class AlreadyDefinedException extends RuntimeException{}
    public static class NotDefinedException extends RuntimeException{}

}
