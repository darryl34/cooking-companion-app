package com.darryl.cookingcompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateMealActivity extends AppCompatActivity {
    
    private Button nextButton, selectButton;
    private TextInputLayout inputMealTitle, inputDishCount;
    private AutoCompleteTextView dishCountList;
    private DatePickerDialog dialog;
    private String inputDate;
    private static final List<String> COUNT = Arrays.asList("1", "2", "3", "4");
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meal);
        
        inputMealTitle = (TextInputLayout) findViewById(R.id.inputMealTitle);
        EditText editText = inputMealTitle.getEditText();
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (view.getId() == editText.getId() && !hasFocus)
                {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
        
        inputDishCount = (TextInputLayout) findViewById(R.id.inputDishCount);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, COUNT);
        dishCountList = (AutoCompleteTextView) findViewById(R.id.dishCountList);
        dishCountList.setAdapter(adapter);
        
        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkRequiredFields())
                {
                    Intent intent = new Intent(CreateMealActivity.this, AddDishesActivity.class);
                    addData(intent);
                    startActivity(intent);
                }
            }
        });
    }
    
    private Intent addData(Intent intent)
    {
        String title = inputMealTitle.getEditText().getText().toString().trim();
        int dishCount = Integer.parseInt(inputDishCount.getEditText().getText().toString());
        intent.putExtra("meal", new Meal(title, inputDate));
        intent.putExtra("dishCount", dishCount);
        return intent;
    }
    
    private boolean checkRequiredFields()
    {
        String title = inputMealTitle.getEditText().getText().toString().trim();
        String date = inputDate;
        String num = inputDishCount.getEditText().getText().toString();
        if (!title.isEmpty() && date != null && !date.isEmpty() && !num.isEmpty()) return true;
        else
        {
            Toast.makeText(this, "Please fill out the required fields!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    
    public void onSelectButtonClicked(View view)
    {
        dialog = new DatePickerDialog(this);
        dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                try {
                    inputDate = Integer.toString(day) + "/" + Integer.toString(month+1) +
                            "/" + Integer.toString(year).substring(2);
                    selectButton = (Button) findViewById(R.id.selectDateButton);
                    selectButton.setText(inputDate);
                    selectButton.setBackgroundColor(getResources().getColor(R.color.light_green));
                }
                catch (Exception e) {
                    inputDate = null;
                }
            }
        });
        dialog.show();
    }
}