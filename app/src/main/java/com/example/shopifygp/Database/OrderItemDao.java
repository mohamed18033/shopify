package com.example.shopifygp.Database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface OrderItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OrderItemModel orderItem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addOrderItems(List<OrderItemModel> orderItems);

    @Update
    void update(OrderItemModel orderItem);

    @Delete
    void delete(OrderItemModel orderItem);

    @Query("DELETE FROM order_items WHERE orderItemID = :orderItemID")
    void deleteOrderItemById(String orderItemID);

    @Query("SELECT * FROM order_items")
    List<OrderItemModel> getAll();

    @Query("DELETE FROM order_items")
    void clearCart();

    @Query("SELECT * FROM order_items WHERE orderItemID = :orderItemID")
    OrderItemModel getOrderItemById(String orderItemID);

    @Query("SELECT SUM(orderItemTotalPrice) FROM order_items")
    Float getTotalPriceSum();

}
