package com.example.shopifygp.Fragments;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopifygp.Adapter.OrderHistoryAdapter;
import com.example.shopifygp.Database.User;
import com.example.shopifygp.Database.UserDao;
import com.example.shopifygp.Model.OrderModel;
import com.example.shopifygp.MyApplication;
import com.example.shopifygp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class OrderHistoryFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference ordersRef;
    OrderHistoryAdapter orderHistoryAdapter;
    FirebaseRecyclerOptions<OrderModel> options;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    private UserDao userDao = MyApplication.getUserDao();

    public OrderHistoryFragment() {

    }

    public static OrderHistoryFragment newInstance(String param1, String param2) {
        OrderHistoryFragment fragment = new OrderHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        orderHistoryAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        orderHistoryAdapter.stopListening();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);

        database = FirebaseDatabase.getInstance("https://shopifygp-70897-default-rtdb.firebaseio.com/");
        ordersRef = database.getReference("Orders");

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        User user = userDao.getUser(firebaseUser.getEmail());

        Query query = ordersRef.orderByChild("userPhoneNumber").equalTo(user.getPhoneNumber());

        options = new FirebaseRecyclerOptions.Builder<OrderModel>().setQuery(query, OrderModel.class).build();
        orderHistoryAdapter = new OrderHistoryAdapter(options);


        recyclerView = view.findViewById(R.id.recycler_orderhistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(orderHistoryAdapter);


        return view;
    }
}