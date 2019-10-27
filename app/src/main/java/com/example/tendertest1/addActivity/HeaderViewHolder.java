package com.example.tendertest1.addActivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class HeaderViewHolder extends RecyclerView.ViewHolder {
    public View headerItemView;
    public HeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        this.headerItemView = itemView;
    }

}
