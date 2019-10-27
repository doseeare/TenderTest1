package com.example.tendertest1.fragments.favorite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tendertest1.R;
import com.example.tendertest1.base.ClickListener;
import com.example.tendertest1.model.Models;

public class FavoriteViewHolder extends RecyclerView.ViewHolder {
    private ClickListener listener;

    private TextView tittle, content, deadLine, cost;
    private ImageView companyLogo;

    public FavoriteViewHolder(@NonNull View itemView,ClickListener listener) {
        super(itemView);
        tittle = itemView.findViewById(R.id.titleTextView);
        content = itemView.findViewById(R.id.descriptionTextView);
        deadLine = itemView.findViewById(R.id.deadlineTextView);
        cost = itemView.findViewById(R.id.costTextView);
        companyLogo = itemView.findViewById(R.id.logotypeImageView);
        this.listener = listener;
    }

    public void bind(final Models myModels) {
        Glide
                .with(itemView)
                .load(myModels.getLogoLink())
                .error(R.drawable.image_not_found)
                .into(companyLogo);

        tittle.setText(myModels.getTittle());
        content.setText(myModels.getContent());
        deadLine.setText(myModels.getDeadLine());
        cost.setText(myModels.getCost());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnClickItemListener(myModels);
            }
        });

    }
}
