package com.example.shopifygp.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopifygp.Database.OrderItemDao;
import com.example.shopifygp.Database.OrderItemDatabase;
import com.example.shopifygp.Database.OrderItemModel;
import com.example.shopifygp.Database.User;
import com.example.shopifygp.Database.UserDao;
import com.example.shopifygp.Model.OrderModel;
import com.example.shopifygp.MyApplication;
import com.example.shopifygp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PaymentFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private UserDao userDao = MyApplication.getUserDao();

    public PaymentFragment() {

    }

    public static PaymentFragment newInstance(String param1, String param2) {
        PaymentFragment fragment = new PaymentFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        OrderItemDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(), OrderItemDatabase.class, "order_items_database").allowMainThreadQueries().build();
        OrderItemDao orderItemDao = db.orderItemDao();
        List<OrderItemModel> cartItems = orderItemDao.getAll();
        List<String> orderSummary = new ArrayList<>();
        TextView orderSummaryPrice = view.findViewById(R.id.order_summary_price);
        if(cartItems.size() != 0) {
            for (OrderItemModel orderItem : cartItems) {
                orderSummary.add("x" + orderItem.getOrderItemCount() + " " + orderItem.getOrderItemName() + "\n" + orderItem.getOrderItemTotalPrice() + "$");
            }
            ListView orderSummaryListView = view.findViewById(R.id.listView);
            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, orderSummary);
            orderSummaryListView.setAdapter(adapter);
            orderSummaryPrice.setText(String.valueOf(Float.parseFloat(orderItemDao.getTotalPriceSum().toString()) + 20.0F));
        }

        // Get a reference to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://shopifygp-70897-default-rtdb.firebaseio.com/");
        DatabaseReference ordersRef = database.getReference("Orders");

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        User user = userDao.getUser(firebaseUser.getEmail());

        Spinner deliveryGateSpinner = view.findViewById(R.id.delivery_gate_spinner);
        Spinner deliveryTimeSpinner = view.findViewById(R.id.delivery_time_spinner);
        Spinner paymentMethodSpinner = view.findViewById(R.id.payment_method_spinner);

        Button confirmOrderButton = view.findViewById(R.id.confirm_order);
        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Place the order if the difference is greater than or equal to 2 hours
                OrderModel order = new OrderModel();
                order.setOrderID(generateUniqueId()); // generate a unique ID for the order
                order.setOrderItems(orderItemDao.getAll()); // get the order items from the Room database
                order.setUserFullName(user.getFullName()); // get the user's full name
                order.setUserPhoneNumber(user.getPhoneNumber()); // get the user's phone number
                order.setDeliveryTime(deliveryTimeSpinner.getSelectedItem().toString()); // get the selected delivery time
                order.setDeliveryGate(deliveryGateSpinner.getSelectedItem().toString()); // get the selected delivery gate
                order.setPaymentMethod(paymentMethodSpinner.getSelectedItem().toString()); // get the selected payment method
                order.setOrderStatus("Order Requested");
                order.setPrice(String.valueOf(Float.parseFloat(orderItemDao.getTotalPriceSum().toString()) + 20.0F));

                ordersRef.child(order.getOrderID()).setValue(order);
                orderItemDao.clearCart();
                // Display an alert dialog with a message
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Order Placed Successfully");
                builder.setMessage("Thank you for placing your order!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog

                        NavDirections action = PaymentFragmentDirections.actionPaymentFragmentToOrderhistory();
                        Navigation.findNavController(view).navigate(action);

                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });





        return view;
    }

    public String generateUniqueId() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 6).toUpperCase(Locale.ROOT);
    }
}