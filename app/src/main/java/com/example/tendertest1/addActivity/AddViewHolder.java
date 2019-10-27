package com.example.tendertest1.addActivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.tendertest1.R;
import com.example.tendertest1.custom.SquareImageView;

class AddViewHolder extends ViewHolder {
    private SquareImageView imageView;

    public AddViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
    }

    public void bind(String path) {
        Glide
                .with(itemView)
                .load(path)
                .into(imageView);
    }
}
