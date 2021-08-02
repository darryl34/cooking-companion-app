package com.darryl.cookingcompanion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MealDetailActivity extends AppCompatActivity {
    
    private Meal meal;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);
        
        if (savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if (extras != null)
            {
                meal = (Meal) extras.getSerializable("meal");
                
                ( (TextView) findViewById(R.id.detailPage_mealTitle)).setText(meal.getTitle());
                ( (TextView) findViewById(R.id.detailPage_mealDate)).setText(meal.getDate());
                
                if (meal.getDishes() != null)
                {
                    LinearLayout layout = (LinearLayout) findViewById(R.id.detailPage_layout);
                    for (Dish dish : meal.getDishes())
                    {
                        TextView textView = new TextView(this);
                        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                                                                    LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 30, 10, 10);
                        textView.setLayoutParams(layoutParams);
                        textView.setText("Dish Name: " + dish.getName() + "\nDish Description: " + dish.getDescription());
                        //textView.setPadding(20,20,20,20);
                        layout.addView(textView);
                    }
                }
            }
        }
    }
    
    public void onDeleteClicked(MenuItem item)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(MealDetailActivity.this).create();
        alertDialog.setTitle("Warning!");
        alertDialog.setMessage("Are you sure you want to delete this item?");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "CANCEL",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Model model = ModelFactory.getModel();
                        model.removeMeal(meal);
                        Intent intent = new Intent(MealDetailActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
        alertDialog.show();
    }
}