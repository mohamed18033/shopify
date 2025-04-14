package com.example.shopifygp.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.shopifygp.ViewHolder.CategoryViewHolder;
import com.example.shopifygp.Fragments.CategoryListFragmentDirections;
import com.example.shopifygp.Model.CategoryModel;
import com.example.shopifygp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class CategoryAdapter extends FirebaseRecyclerAdapter<CategoryModel, CategoryViewHolder> {

    public CategoryAdapter(@NonNull FirebaseRecyclerOptions<CategoryModel> options) {
        super(options);
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card, parent, false);
        return new CategoryViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull CategoryModel model) {
        int pos = position;
        holder.categoryName.setText(model.getCategoryName());
        holder.categoryDescription.setText(model.getCategoryDescription());
        Picasso.get().load(model.getCategoryImage()).into(holder.categoryImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = CategoryListFragmentDirections.actionCategoriesToItemListFragment(getRef(pos).getKey(), model.getCategoryName());
                Navigation.findNavController(holder.itemView).navigate(action);
            }
        });
    }



}
