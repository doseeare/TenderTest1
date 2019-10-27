package com.example.tendertest1.base;


import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tendertest1.model.Models;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<V extends BaseRecyclerViewHolder, T> extends RecyclerView.Adapter<V> {

    protected List<T> list = new ArrayList<>();

    @NonNull
    @Override
    public V onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(getLayout(), parent, false);
        return OnCreateViewHolder(view);
    }

    @LayoutRes
    protected abstract int getLayout();

    protected abstract V OnCreateViewHolder(View view);


    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {
        holder.bind(getListPosition(position));
    }

    private T getListPosition(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void update(List<Models> rcs) {
        this.list = (List<T>) rcs;
        notifyDataSetChanged();
    }
}

