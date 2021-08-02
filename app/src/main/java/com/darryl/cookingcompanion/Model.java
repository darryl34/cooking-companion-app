package com.darryl.cookingcompanion;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Model
{
    private ArrayList<Meal> meals = new ArrayList<>();
    private Context context;
    private String filename = "data.ser";
    
    public Model(Context context) {
        this.context = context;
        createFile();
    }
    
    public ArrayList<Meal> getMeals()
    {
        return meals;
    }
    
    public ArrayList<Meal> getMeals(String date)
    {
        LocalDate formattedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("d/M/yy"));
        ArrayList<Meal> result = new ArrayList<>();
        for (Meal meal : meals)
        {
            if (formattedDate.equals(meal.getFormattedDate())) result.add(meal);
        }
        return result;
    }
    
    public void addMeal(Meal meal)
    {
        meals.add(meal);
        writeToFile();
    }
    
    public void removeMeal(Meal inputMeal)
    {
        for (Meal meal : meals)
        {
            if (meal.equals(inputMeal))
            {
                meals.remove(meal);
                writeToFile();
            }
        }
    }
    
    public void addMeals(ArrayList<Meal> inputMeals)
    {
        meals.addAll(inputMeals);
        writeToFile();
    }
    
    public void setDefaultMeals()
    {
        ArrayList<Meal> defaultMeals = new ArrayList<>();
        ArrayList<Dish> dishes = new ArrayList<>();
    
        dishes.add(new Dish("chicken", "fried"));
        dishes.add(new Dish("vege", ""));
        defaultMeals.add(new Meal("Lunch", "25/7/21"));
        defaultMeals.add(new Meal("Dinner", "25/7/21", dishes));
        defaultMeals.add(new Meal("Lunch", "26/7/21"));
        defaultMeals.add(new Meal("Dinner", "26/7/21", dishes));
        defaultMeals.add(new Meal("Lunch", "27/7/21"));
        defaultMeals.add(new Meal("Dinner", "27/7/21"));
        meals = defaultMeals;
    }
    
    public int getItemCount()
    {
        return meals.size();
    }
    
    public void writeToFile()
    {
        try {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream objOut = new ObjectOutputStream(fos);
            objOut.writeObject(meals);
            objOut.close();
            fos.close();
            System.out.println("Successfully written to file!");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Object readFromFile()
    {
        try {
            FileInputStream fis = context.openFileInput(filename);
            ObjectInputStream objIn = new ObjectInputStream(fis);
            meals = (ArrayList<Meal>) objIn.readObject();
            objIn.close();
            fis.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return meals;
    }
    
    public void createFile()
    {
         try {
             File file = new File(context.getFilesDir(), filename);
             if (!file.exists()) {
                 file.createNewFile();
                 setDefaultMeals();
                 ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(file));
                 objOut.writeObject(meals);
                 objOut.close();
                 System.out.println("Successfully created file!");
             }
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }
    }
}
