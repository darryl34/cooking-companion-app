package com.darryl.cookingcompanion;

import static com.prolificinteractive.materialcalendarview.CalendarDay.from;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.CalendarView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarViewActivity extends AppCompatActivity {
    
    private MaterialCalendarView calendarView;
    private RecyclerView calendarRecView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);
        
        Model model = ModelFactory.getModel();
        
        calendarView = findViewById(R.id.calendarView);
        calendarRecView = findViewById(R.id.calendarRecView);
        MealsRecViewAdapter adapter = new MealsRecViewAdapter(this);
        
        calendarView.setCurrentDate(CalendarDay.today());
        calendarView.addDecorator(new EventDecorator(Color.RED, getEventDates(model.getMeals())));
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                String formatted = formatDate(date.getYear(), date.getMonth(), date.getDay());
                adapter.setMeals(model.getMeals(formatted));
                calendarRecView.setAdapter(adapter);
                calendarRecView.setLayoutManager(new LinearLayoutManager(CalendarViewActivity.this));
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