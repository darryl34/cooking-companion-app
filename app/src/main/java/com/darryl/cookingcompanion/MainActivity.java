package com.darryl.cookingcompanion;

import static com.prolificinteractive.materialcalendarview.CalendarDay.from;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

    private CalendarDay today = CalendarDay.today();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = sp.getBoolean("FIRSTRUN", true);
        if (isFirstRun)
        {
            // Operations to be performed only on app install
            Model model = ModelFactory.getModel(MainActivity.this);
            model.setDefaultMeals();
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.apply();
        }
        
        Model model = ModelFactory.getModel(MainActivity.this);
        MealsRecViewAdapter adapter = new MealsRecViewAdapter(this);
        calendarView = findViewById(R.id.calendarView);
        calendarRecView = findViewById(R.id.calendarRecView);
        calendarRecView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    
        calendarView.clearSelection();
        calendarView.setDateSelected(today, true);
        adapter.setMeals(model.getMeals(formatDate(today)));
        calendarRecView.setAdapter(adapter);
        calendarView.addDecorator(new EventDecorator(Color.RED, getEventDates(model.getMeals())));
        
        
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                String formatted = formatDate(date);
                adapter.setMeals(model.getMeals(formatted));
                calendarRecView.setAdapter(adapter);
            }
        });
        
        // TODO: set date to today if month selected is current month
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
//                Intent intent = new Intent(MainActivity.this, CalendarViewActivity.class);
//                startActivity(intent);
            }
        });
    }
    
    private String formatDate(CalendarDay date)
    {
        String dayStr = Integer.toString(date.getDay());
        String monthStr = Integer.toString(date.getMonth());
        String yearStr = Integer.toString(date.getYear()).substring(2);
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