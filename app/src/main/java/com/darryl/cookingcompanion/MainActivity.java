package com.darryl.cookingcompanion;

import static com.prolificinteractive.materialcalendarview.CalendarDay.from;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    
    private MaterialCalendarView calendarView;
    private RecyclerView calendarRecView;
    
    private FloatingActionButton createMealButton;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Model model = ModelFactory.getModel(MainActivity.this);
        if (model.getItemCount() == 0) model.setDefaultMeals();
    
        MealsRecViewAdapter adapter = new MealsRecViewAdapter(this);
        calendarView = findViewById(R.id.calendarView);
        calendarRecView = findViewById(R.id.calendarRecView);
    
        calendarView.clearSelection();
        calendarView.setDateSelected(CalendarDay.today(), true);
        calendarView.addDecorator(new EventDecorator(Color.RED, getEventDates(model.getMeals())));
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                String formatted = formatDate(date.getYear(), date.getMonth(), date.getDay());
                adapter.setMeals(model.getMeals(formatted));
                calendarRecView.setAdapter(adapter);
                calendarRecView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        });
        
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                calendarView.clearSelection();
                calendarView.setDateSelected(date, true);
            }
        });
        
        createMealButton = findViewById(R.id.createMealButton);
        createMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateMealActivity.class);
                startActivity(intent);
            }
        });
        
        toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CalendarViewActivity.class);
                startActivity(intent);
            }
        });
    }
    
    private String formatDate(int year, int month, int dayOfMonth)
    {
        String dayStr = Integer.toString(dayOfMonth);
        String monthStr = Integer.toString(month);
        String yearStr = Integer.toString(year).substring(2);
        String rawDate = dayStr + "/" + monthStr + "/" + yearStr;
        return rawDate;
    }
    
    private ArrayList<CalendarDay> getEventDates(ArrayList<Meal> meals)
    {
        ArrayList<CalendarDay> result = new ArrayList<>();
        for (Meal meal : meals)
        {
            int[] data = meal.getDateAsArray();
            result.add(from(data[0], data[1], data[2]));
        }
        return result;
    }
}