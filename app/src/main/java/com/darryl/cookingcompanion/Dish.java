package com.darryl.cookingcompanion;

import java.io.Serializable;
import java.util.ArrayList;

public class Dish implements Serializable
{
    private String name;
    
    public Dish(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
