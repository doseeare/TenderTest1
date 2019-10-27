package com.example.tendertest1.fragments.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tendertest1.R;
import com.example.tendertest1.base.ClickListener;
import com.example.tendertest1.fragments.home.openedHomeActivity.OpenedHomeActivity;
import com.example.tendertest1.model.Models;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FavoriteFragment extends Fragment implements ClickListener {
    private FavoriteAdapter favouriteAdapter;
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ShimmerFrameLayout shimmerFrameLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorite_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("favourite");
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container_favourite);
        updateList();
        setRecyclerView(view);
    }

    private void setRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.favoriteRecyclerView);
        recyclerView.setHasFixedSize(true);
        favouriteAdapter = new FavoriteAdapter(this);
        recyclerView.setAdapter(favouriteAdapter);

    }

    private void updateList() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                favouriteAdapter.add(dataSnapshot.getValue(Models.class));
                stopUpdate();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Models models = dataSnapshot.getValue(Models.class);
                int index = favouriteAdapter.getItemIndex(models);
                favouriteAdapter.set(index, models);
                stopUpdate();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Models models = dataSnapshot.getValue(Models.class);
                int index = favouriteAdapter.getItemIndex(models);
                favouriteAdapter.remove(index);
                stopUpdate();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                stopUpdate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                stopUpdate();
            }
        });
    }

    private void stopUpdate() {
        shimmerFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        }, 250);
    }

    @Override
    public void OnClickItemListener(Models myModels) {
        Intent intent = new Intent(getActivity(), OpenedHomeActivity.class);
        intent.putExtra("Start", myModels);
        startActivity(intent);
    }
}
