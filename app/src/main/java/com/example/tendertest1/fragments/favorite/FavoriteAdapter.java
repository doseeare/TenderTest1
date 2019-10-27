package com.example.tendertest1.fragments.favorite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tendertest1.R;
import com.example.tendertest1.base.ClickListener;
import com.example.tendertest1.model.Models;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter <FavoriteViewHolder> {
    private List<Models> list;

    private ClickListener listener;
    @NonNull  @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.home_item_layout, parent, false);
        return new FavoriteViewHolder(view, listener);
    }

    public FavoriteAdapter(ClickListener listener) {
        this.list = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int i) {
        holder.bind(getItem(i));
    }


    public void add(Models value) {
        list.add(value);
            notifyDataSetChanged();
    }

    public void set(int index, Models models) {
        list.set(index, models);
            notifyItemChanged(index);

    }

    public void remove(int index) {
        list.remove(index);
            notifyItemRemoved(index);
    }

    public int getItemIndex(Models models) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == models.getId()) {
                return i;
            }
        }
        return -1;
    }


    private Models getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
