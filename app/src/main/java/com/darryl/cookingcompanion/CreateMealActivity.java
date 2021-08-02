package com.darryl.cookingcompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateMealActivity extends AppCompatActivity {
    
    private Button nextButton;
    private EditText inputMealTitle, inputDate, inputDishCount;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meal);
        
        inputMealTitle = (EditText) findViewById(R.id.inputMealTitle);
        inputDate = (EditText) findViewById(R.id.inputDate);
        inputDishCount = (EditText) findViewById(R.id.inputDishCount);
        nextButton = findViewById(R.id.nextButton);
        
        inputMealTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        
            }
    
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkRequiredFields();
            }
    
            @Override
            public void afterTextChanged(Editable editable) {
        
            }
        });
        
        inputDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
    
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkRequiredFields();
            }
    
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        
        inputDishCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        
            }
    
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkRequiredFields();
            }
    
            @Override
            public void afterTextChanged(Editable editable) {
        
            }
        });
    
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateMealActivity.this, AddDishesActivity.class);
                startActivity(addData(intent));
            }
        });
    }
    
    private Intent addData(Intent intent)
    {
        intent.putExtra("meal", new Meal(inputMealTitle.getText().toString(), inputDate.getText().toString()));
        intent.putExtra("dishCount", Integer.parseInt(inputDishCount.getText().toString()));
        return intent;
    }
    
    private void checkRequiredFields()
    {
        String title = inputMealTitle.getText().toString();
        String date = inputDate.getText().toString();
        String num = inputDishCount.getText().toString();
        if (!title.isEmpty() && !date.isEmpty() && !num.isEmpty())
        {
            nextButton.setEnabled(true);
        }
        else nextButton.setEnabled(false);
    }
}