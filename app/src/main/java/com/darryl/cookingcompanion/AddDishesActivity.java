package com.darryl.cookingcompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;

public class AddDishesActivity extends AppCompatActivity {
    
    private Meal meal;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dishes);
        
        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras != null)
            {
                meal = (Meal) extras.getSerializable("meal");
                int dishCount = extras.getInt("dishCount");
                
                if (dishCount > 0)
                {
                    LinearLayout layout = (LinearLayout) findViewById(R.id.addDishesPage_layout);
                    for (int i = 0; i < dishCount; i++)
                    {
                        LinearLayout ll = new LinearLayout(this);
                        ll.setOrientation(LinearLayout.VERTICAL);
                        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                                                                    LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0,10,0,30);
                        
                        TextView tv = new TextView(this);
                        tv.setLayoutParams(layoutParams);
                        int addOne = i + 1;
                        tv.setText("Dish " + addOne);
                        ll.addView(tv);
                        
                        EditText dishName = new EditText(this);
                        dishName.setLayoutParams(layoutParams);
                        dishName.setHint("Name");
                        dishName.setWidth(200);
                        dishName.setId(R.id.editText_dishName);
                        dishName.setInputType(InputType.TYPE_CLASS_TEXT);
                        ll.addView(dishName);
    
                        EditText dishDesc = new EditText(this);
                        dishDesc.setLayoutParams(layoutParams);
                        dishDesc.setHint("Description");
                        dishDesc.setWidth(200);
                        dishDesc.setInputType(InputType.TYPE_CLASS_TEXT);
                        dishDesc.setId(R.id.editText_dishDesc);
                        ll.addView(dishDesc);
                        layout.addView(ll);
                    }
                }
            }
        }
    }
    
    public void onSubmitButtonClicked(View view)
    {
        meal.setDishes(getDishesData());
        Model model = ModelFactory.getModel(this);
        model.addMeal(meal);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    
    private ArrayList<Dish> getDishesData()
    {
        ArrayList<Dish> dishes = new ArrayList<>();
        LinearLayout layout = (LinearLayout) findViewById(R.id.addDishesPage_layout);
        int dishCount = layout.getChildCount();
        for (int i = 0; i < dishCount; i++)
        {
            View dishView = layout.getChildAt(i);
            EditText editText_dishName = (EditText) dishView.findViewById(R.id.editText_dishName);
            EditText editText_dishDesc = (EditText) dishView.findViewById(R.id.editText_dishDesc);
            
            String dishName = editText_dishName.getText().toString();
            String dishDesc = editText_dishDesc.getText().toString();
            dishes.add(new Dish(dishName, dishDesc));
        }
        return dishes;
    }
}