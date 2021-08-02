package com.darryl.cookingcompanion;

import java.io.Serializable;
import java.util.ArrayList;

public class Dish implements Serializable
{
    private String name;
    private String description;
    
    public Dish(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public ArrayList<String> toArrayList()
    {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(name);
        arrayList.add(description);
        
        return arrayList;
    }
}
