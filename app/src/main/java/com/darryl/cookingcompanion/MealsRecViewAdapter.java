package com.darryl.cookingcompanion;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MealsRecViewAdapter extends RecyclerView.Adapter<MealsRecViewAdapter.ViewHolder>
{
    private ArrayList<Meal> meals = new ArrayList<>();
    private Context context;

    public MealsRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MealsRecViewAdapter.ViewHolder holder, int position)
    {
        holder.txtMealTitle.setText(meals.get(position).getTitle());
        holder.txtMealDate.setText(meals.get(position).getDate());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context, MealDetailActivity.class);
                intent.putExtra("meal", meals.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void setMeals(ArrayList<Meal> meals)
    {
        this.meals = meals;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private FrameLayout parent;
        private TextView txtMealTitle, txtMealDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            txtMealTitle = itemView.findViewById(R.id.txtMealTitle);
            txtMealDate = itemView.findViewById(R.id.txtMealDate);
        }
    }
}
