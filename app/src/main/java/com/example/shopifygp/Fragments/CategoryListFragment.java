package com.example.shopifygp.Fragments;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopifygp.Adapter.CategoryAdapter;
import com.example.shopifygp.Model.CategoryModel;
import com.example.shopifygp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class CategoryListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference categoryRef;
    CategoryAdapter categoryYAdapter;
    EditText searchBar;
    FirebaseRecyclerOptions<CategoryModel> options;

    public CategoryListFragment() {
    }

    public static CategoryListFragment newInstance(String param1, String param2) {
        CategoryListFragment fragment = new CategoryListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        categoryYAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        categoryYAdapter.stopListening();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_category);
        searchBar = (EditText) view.findViewById(R.id.searchBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        database = FirebaseDatabase.getInstance("https://shopifygp-70897-default-rtdb.firebaseio.com/");
        categoryRef = database.getReference("CategoryModel");



        loadDataInRecyclerView("");

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString() != null)
                {
                    loadDataInRecyclerView(s.toString());
                }
                else
                {
                    loadDataInRecyclerView("");
                }
            }
        });

        return view;
    }

    public void loadDataInRecyclerView(String data)
    {
        Query query = categoryRef.orderByChild("categoryName").startAt(data).endAt(data + "\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<CategoryModel>().setQuery(query, CategoryModel.class).build();
        categoryYAdapter = new CategoryAdapter(options);
        recyclerView.setAdapter(categoryYAdapter);
        categoryYAdapter.startListening();
    }


}