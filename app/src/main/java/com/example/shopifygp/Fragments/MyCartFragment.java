package com.example.shopifygp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.shopifygp.Adapter.OrderItemAdapter;
import com.example.shopifygp.Database.OrderItemDao;
import com.example.shopifygp.Database.OrderItemDatabase;
import com.example.shopifygp.Database.OrderItemModel;
import com.example.shopifygp.R;

import java.util.List;

public class MyCartFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    Button procceedToPaymentBtn;
    Button clearCartBtn;
    OrderItemAdapter adapter;
    TextView orderTotalPrice;
    TextView deliveryFees;
    TextView orderTotalPricePlusDeliveryFees;

    public MyCartFragment() {

    }

    public static MyCartFragment newInstance(String param1, String param2) {
        MyCartFragment fragment = new MyCartFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OrderItemDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(), OrderItemDatabase.class, "order_items_database").allowMainThreadQueries().build();
        OrderItemDao orderItemDao = db.orderItemDao();
        procceedToPaymentBtn = (Button)getView().findViewById(R.id.proceedtopayment);
        procceedToPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(orderItemDao.getAll().size() != 0) {
                    NavDirections action = MyCartFragmentDirections.actionMycartToPaymentFragment();
                    Navigation.findNavController(procceedToPaymentBtn).navigate(action);
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Cart has no items !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        clearCartBtn = (Button)getView().findViewById(R.id.clearCart);
        clearCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(orderItemDao.getAll().size() != 0) {
                    orderItemDao.clearCart();
                    final List<OrderItemModel> orderItems = orderItemDao.getAll();
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
                    adapter = new OrderItemAdapter(orderItems);
                    recyclerView.setAdapter(adapter);

                    Toast.makeText(getActivity().getApplicationContext(), "Cart is Cleared !", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Cart is Empty !", Toast.LENGTH_SHORT).show();
                }
            }
        });


        recyclerView = getView().findViewById(R.id.recycler_mycart);


        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<OrderItemModel> orderItems = orderItemDao.getAll();
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
                        adapter = new OrderItemAdapter(orderItems);
                        recyclerView.setAdapter(adapter);

                    }
                });
            }
        }).start();


        orderTotalPrice = view.findViewById(R.id.order_total_price);
        deliveryFees = view.findViewById(R.id.delivery_fees);
        orderTotalPricePlusDeliveryFees = view.findViewById(R.id.order_total_plus_deliveryfees);
        if(orderItemDao.getTotalPriceSum() == null)
        {
            orderTotalPrice.setText("0");
            deliveryFees.setText("0");
            orderTotalPricePlusDeliveryFees.setText("0");
        }else {
            orderTotalPrice.setText(orderItemDao.getTotalPriceSum().toString());
            deliveryFees.setText("20.0");
            orderTotalPricePlusDeliveryFees.setText(String.valueOf(Float.parseFloat(orderTotalPrice.getText().toString()) + 20.0F));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_cart, container, false);

    }
}