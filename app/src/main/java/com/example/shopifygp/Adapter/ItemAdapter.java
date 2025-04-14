package com.example.shopifygp.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.shopifygp.Database.OrderItemDao;
import com.example.shopifygp.Database.OrderItemDatabase;
import com.example.shopifygp.Database.OrderItemModel;
import com.example.shopifygp.Model.ItemModel;
import com.example.shopifygp.R;
import com.example.shopifygp.ViewHolder.ViewHolderItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class ItemAdapter extends FirebaseRecyclerAdapter<ItemModel, ViewHolderItem> {
    OrderItemDao orderItemDao;
    public ItemAdapter(@NonNull FirebaseRecyclerOptions<ItemModel> options, OrderItemDatabase cartDatabase) {
        super(options);
        orderItemDao = cartDatabase.orderItemDao();

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolderItem holder, int position, @NonNull ItemModel model) {
        int pos = position;
        TextView itemCountTextView = holder.itemView.findViewById(R.id.itemcount);
        itemCountTextView.setText(getItemCountForFood(getRef(pos).getKey()).toString());
        holder.itemName.setText(model.getItemName());
        holder.itemDescription.setText(model.getItemDescription());
        holder.itemPrice.setText(model.getItemPrice());
        Picasso.get().load(model.getItemImage()).into(holder.itemImage);

        if(model.getItemAvailability().equals("Available")) {
            holder.itemView.findViewById(R.id.addicon).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView itemCountTextView = holder.itemView.findViewById(R.id.itemcount);
                    Integer itemCount = Integer.parseInt(itemCountTextView.getText().toString());
                    itemCount++;
                    Float totalPrice = Float.parseFloat(model.getItemPrice()) * itemCount;
                    itemCountTextView.setText(itemCount.toString());
                    OrderItemModel orderItem = new OrderItemModel(getRef(pos).getKey(), model.getItemName(), itemCount.toString(), totalPrice.toString());
                    orderItemDao.insert(orderItem);
                }
            });

            holder.itemView.findViewById(R.id.removeicon).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView itemCountTextView = holder.itemView.findViewById(R.id.itemcount);
                    Integer itemCount = Integer.parseInt(itemCountTextView.getText().toString());
                    if (itemCount == 0) {
                        return;
                    }
                    itemCount--;
                    Float totalPrice = Float.parseFloat(model.getItemPrice()) * itemCount;
                    itemCountTextView.setText(itemCount.toString());
                    OrderItemModel orderItem = new OrderItemModel(getRef(pos).getKey(), model.getItemName(), itemCount.toString(), totalPrice.toString());
                    orderItemDao.update(orderItem);

                    if (itemCount == 0) {
                        orderItemDao.deleteOrderItemById(getRef(pos).getKey());
                    }

                }
            });
        }else{
            holder.itemView.findViewById(R.id.removeicon).setVisibility(View.GONE);
            holder.itemView.findViewById(R.id.addicon).setVisibility(View.GONE);
            itemCountTextView = holder.itemView.findViewById(R.id.itemcount);
            itemCountTextView.setText("N/A");

        }
    }

    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolderItem(view);
    }

    private Integer getItemCountForFood(String foodId) {
        OrderItemModel orderItem = orderItemDao.getOrderItemById(foodId);
        if (orderItem == null) {
            return 0;
        }
        return Integer.parseInt(orderItem.getOrderItemCount());
    }
}
