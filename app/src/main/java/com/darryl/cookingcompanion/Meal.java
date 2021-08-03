package com.darryl.cookingcompanion;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class Meal implements Serializable
{
    private String title;
    private String date;
    private ArrayList<Dish> dishes;
    private String imagePath;

    public Meal(String title, String date) {
        this.title = title;
        this.date = date;
    }
    
    public Meal(String title, String date, ArrayList<Dish> dishes) {
        this.title = title;
        this.date = date;
        this.dishes = dishes;
    }

    
    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
    
    public LocalDate getFormattedDate()
    {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("d/M/yy"));
    }
    
    public int[] getDateAsArray()
    {
        LocalDate newDate = getFormattedDate();
        return new int[] {newDate.getYear(), newDate.getMonthValue(), newDate.getDayOfMonth()};
    }
    
    public ArrayList<Dish> getDishes() {
        return dishes;
    }
    
    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }
    
    public String getImagePath() {
        return imagePath;
    }
    
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Meal meal = (Meal) obj;
        return title.equals(meal.title) && date.equals(meal.date);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(title, date);
    }
}
