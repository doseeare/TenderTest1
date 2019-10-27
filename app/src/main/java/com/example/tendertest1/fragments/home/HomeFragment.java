package com.example.tendertest1.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.tendertest1.base.ClickListener;
import com.example.tendertest1.R;
import com.example.tendertest1.fragments.home.openedHomeActivity.OpenedHomeActivity;
import com.example.tendertest1.model.Models;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment implements ClickListener {

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    public ShimmerFrameLayout shimmerFrameLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("results");
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        updateList();
        setupRecyclerView(view);
        startUpdate();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                homeAdapter.getFilter().filter(newText);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                return false;
            }
        });
    }

    private void setupRecyclerView(final View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        homeAdapter = new HomeAdapter(this);
        recyclerView.setAdapter(homeAdapter);
    }

    private void updateList() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                homeAdapter.add(dataSnapshot.getValue(Models.class));
                stopUpdate();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Models models = dataSnapshot.getValue(Models.class);
                int index = homeAdapter.getItemIndex(models);
                homeAdapter.set(index, models);
                stopUpdate();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Models models = dataSnapshot.getValue(Models.class);
                int index = homeAdapter.getItemIndex(models);
                homeAdapter.remove(index);
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

    private void startUpdate() {
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void OnClickItemListener(Models results) {
        Intent intent = new Intent(getActivity(), OpenedHomeActivity.class);
        intent.putExtra("Start", results);
        startActivity(intent);
    }
}