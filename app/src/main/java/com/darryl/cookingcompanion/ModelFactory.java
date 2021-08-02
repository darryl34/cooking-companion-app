package com.darryl.cookingcompanion;


// This class gives access to the model to any other class that needs it.
// Calling the static method getModel (i.e., ModelFactory.getModel()) returns
// an initialised Model object. This version limits the program to one model object,
// which is returned whenever getModel is called.

import android.content.Context;

import java.io.IOException;

public class ModelFactory {
    private static Model model;
    
    public static Model getModel(Context context)
    {
        try {
            if (model == null)
            {
                model = new Model(context);
                model.readFromFile();
            }
            return model;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Model getModel()
    {
        return model;
    }
}
