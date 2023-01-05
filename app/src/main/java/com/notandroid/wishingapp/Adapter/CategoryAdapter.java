package com.notandroid.wishingapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.notandroid.wishingapp.Model.CategoryModel;
import com.notandroid.wishingapp.R;
import com.notandroid.wishingapp.WishActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
    ArrayList<CategoryModel> allCategory;

    public  CategoryAdapter(Context context,ArrayList<CategoryModel> allCategory){
        this.context = context;
        this.allCategory = allCategory;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_row,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textCat.setText(allCategory.get(position).getCategoryName());
        holder.cardCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WishActivity.class);
                intent.putExtra("id",allCategory.get(position).getId());
                intent.putExtra("categoryName",allCategory.get(position).getCategoryName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return allCategory.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        CardView cardCategory;
        TextView textCat ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textCat = itemView.findViewById(R.id.textCat);
            cardCategory = itemView.findViewById(R.id.cardCategory);
        }
    }
}
