package com.darryl.cookingcompanion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

import pub.devrel.easypermissions.EasyPermissions;

public class AddDishesActivity extends AppCompatActivity {
    
    private Meal meal;
    private ImageView imageView;
    private Button selectImageButton;
    
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    
    private static int SELECT_PICTURE = 200;
    
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
    
                imageView = (ImageView) findViewById(R.id.dishesImageView);
                selectImageButton = (Button) findViewById(R.id.selectImageButton);
                selectImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (EasyPermissions.hasPermissions(AddDishesActivity.this, galleryPermissions))
                        {
                            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                            gallery.setType("image/*");
                            startActivityForResult(gallery, SELECT_PICTURE);
                        }
                        else
                        {
                            EasyPermissions.requestPermissions(AddDishesActivity.this,
                                    "Access for Storage", 101, galleryPermissions);
                        }
                    }
                });
                
                LinearLayout layout = (LinearLayout) findViewById(R.id.addDishesPage_layout);
                for (int i = 0; i < dishCount; i++)
                {
                    LinearLayout ll = new LinearLayout(this);
                    ll.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    linearLayoutParams.setMargins(0,10,0,30);
        
                    TextView tv = new TextView(this);
                    tv.setLayoutParams(linearLayoutParams);
                    int addOne = i + 1;
                    tv.setText("Dish " + addOne);
                    ll.addView(tv);
        
                    EditText dishName = new EditText(this);
                    dishName.setLayoutParams(linearLayoutParams);
                    dishName.setHint("Name");
                    dishName.setWidth(200);
                    dishName.setId(R.id.editText_dishName);
                    dishName.setInputType(InputType.TYPE_CLASS_TEXT);
                    ll.addView(dishName);
        
                    layout.addView(ll);
                }
            }
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && null != data)
        {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
    
            try {
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                meal.setImagePath(picturePath);
            }
            catch (Exception e)
            {
                e.printStackTrace();
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
            
            String dishName = editText_dishName.getText().toString();
            dishes.add(new Dish(dishName));
        }
        return dishes;
    }
}