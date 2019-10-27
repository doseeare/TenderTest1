package com.example.tendertest1.addActivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tendertest1.R;

import java.util.ArrayList;
import java.util.List;

public class AddAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> list = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        if (viewType == 1) {
            View headerView = layoutInflater.inflate(R.layout.header_layout, viewGroup, false);
            return new HeaderViewHolder(headerView);
        }
        View view = layoutInflater.inflate(R.layout.add_photo_item, viewGroup, false);
        return new AddViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
        } else if (holder instanceof AddViewHolder) {
            AddViewHolder viewHolder = (AddViewHolder) holder;
            viewHolder.bind(list.get(position - 1));
        }
    }

    @Override
    public int getItemCount() {
        return 1 + list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        }
        return 0;
    }

    public void setNewList(ArrayList<String> allShownImagesPath) {
        this.list = allShownImagesPath;
        notifyDataSetChanged();
    }

}
