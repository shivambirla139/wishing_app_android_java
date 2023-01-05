package com.notandroid.wishingapp.Adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.notandroid.wishingapp.Model.WishModel;
import com.notandroid.wishingapp.R;
import com.notandroid.wishingapp.WishActivity;

import java.util.ArrayList;

import static android.content.Context.CLIPBOARD_SERVICE;

public class WishAdapter extends RecyclerView.Adapter<WishAdapter.ViewHolder> {
    Context context;
    ArrayList<WishModel> allWish;

    public WishAdapter(Context context, ArrayList<WishModel> allWish) {
        this.context = context;
        this.allWish = allWish;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wish_row,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textWish.setText(allWish.get(position).getWishData());
        holder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(android.content.Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(android.content.Intent.EXTRA_TEXT, allWish.get(position).getWishData());
                context.startActivity(i);

            }
        });

        holder.copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", allWish.get(position).getWishData());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context,"Copied",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return allWish.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textWish;
        ImageView shareBtn,copyBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textWish = itemView.findViewById(R.id.textWish);
            shareBtn = itemView.findViewById(R.id.shareBtn);
            copyBtn = itemView.findViewById(R.id.copyBtn);
        }
    }
}
