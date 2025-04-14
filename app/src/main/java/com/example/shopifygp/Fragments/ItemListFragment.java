package com.example.shopifygp.Fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.shopifygp.Adapter.ItemAdapter;
import com.example.shopifygp.Database.OrderItemDatabase;
import com.example.shopifygp.Model.ItemModel;
import com.example.shopifygp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ItemListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference foodRef;
    ItemAdapter itemMAdapter;
    Button viewCartButton;

    public ItemListFragment() {
    }

    public static ItemListFragment newInstance(String param1, String param2) {
        ItemListFragment fragment = new ItemListFragment();
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
        itemMAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        itemMAdapter.stopListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        ItemListFragmentArgs args = ItemListFragmentArgs.fromBundle(getArguments());
        String CategoryID = args.getRestaurantID();
        String CategoryName = args.getRestaurantName();

        viewCartButton = view.findViewById(R.id.goToCart);
        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = ItemListFragmentDirections.actionItemListFragmentToMycart();
                Navigation.findNavController(view).navigate(action);
            }
        });


        OrderItemDatabase cartDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), OrderItemDatabase.class, "order_items_database").allowMainThreadQueries().build();

        database = FirebaseDatabase.getInstance("https://shopifygp-70897-default-rtdb.firebaseio.com/");
        foodRef = database.getReference("ItemModel");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if(!(CategoryID.isEmpty()))
        {
            // Load corresponding Menu
            FirebaseRecyclerOptions<ItemModel> options =
                    new FirebaseRecyclerOptions.Builder<ItemModel>()
                            .setQuery(foodRef.orderByChild("categoryID").equalTo(CategoryID), ItemModel.class)
                            .build();
            itemMAdapter = new ItemAdapter(options, cartDatabase);
            recyclerView.setAdapter(itemMAdapter);
        }

        return view;
    }
}
