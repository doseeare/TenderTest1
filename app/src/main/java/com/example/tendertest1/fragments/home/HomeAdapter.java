package com.example.tendertest1.fragments.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.tendertest1.R;
import com.example.tendertest1.base.ClickListener;
import com.example.tendertest1.model.Models;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder> implements Filterable {
    private ClickListener listener;
    private ArrayList<Models> list;
    private List<Models> searchResult;
    private boolean isSearching = false;

    @Override
    public int getItemCount() {
        return searchResult.size();
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.home_item_layout, parent, false);
        return new HomeViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.bind(searchResult.get(position));
    }

    //привязка
    public HomeAdapter(ClickListener listener) {
        this.listener = listener;
        list = new ArrayList<>();
        searchResult = new ArrayList<>();
    }

    public void add(Models value) {
        list.add(value);
        if (!isSearching) {
            searchResult.add(value);
            notifyDataSetChanged();
        }
    }

    public void set(int index, Models models) {
        list.set(index, models);
        if (!isSearching) {
            searchResult.set(index, models);
            notifyItemChanged(index);
        }
    }


    public void remove(int index) {
        if(index < 0) {
            return;
        }
        list.remove(index);
        if (!isSearching) {
            searchResult.remove(index);
            notifyItemRemoved(index);
        }
    }

    public int getItemIndex(Models models) {
        for (int position = 0; position < list.size(); position++) {
            if (list.get(position).getId() == models.getId()) {
                return position;
            }
        }
        return -1;
    }

    @Override
    public Filter getFilter() {
        return fFilter;
    }

    //Добавление поисковика
    private Filter fFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence text) {
            isSearching = true;
            List<Models> filteredList = new ArrayList<>();
            if (!TextUtils.isEmpty(text)) {
                String filterPattern = text.toString().toLowerCase().trim();
                for (Models item : list) {
                    if (item.getTittle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
                searchResult.clear();
                searchResult.addAll(filteredList);
            } else {
                filteredList = list;
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (filterResults.values == null) {
                return;
            }
            searchResult.clear();
            searchResult.addAll((ArrayList<Models>) filterResults.values);
            isSearching = false;
            notifyDataSetChanged();
        }
    };
}
