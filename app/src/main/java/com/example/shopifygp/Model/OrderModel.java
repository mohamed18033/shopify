package com.example.shopifygp.Model;


import com.example.shopifygp.Database.OrderItemModel;

import java.util.List;

public class OrderModel {
    private String orderID;
    private String userFullName;
    private String userPhoneNumber;
    private String deliveryTime;
    private String deliveryGate;
    private String paymentMethod;
    private List<OrderItemModel> orderItems;
    private String orderStatus;
    private String price;

    public OrderModel() {}  // Required empty constructor for Firebase

    public OrderModel(String orderID, String userFullName, String userPhoneNumber, String deliveryTime, String deliveryGate, String paymentMethod, List<OrderItemModel> orderItems, String orderStatus, String price) {
        this.orderID = orderID;
        this.userFullName = userFullName;
        this.userPhoneNumber = userPhoneNumber;
        this.deliveryTime = deliveryTime;
        this.deliveryGate = deliveryGate;
        this.paymentMethod = paymentMethod;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
        this.price = price;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryGate() {
        return deliveryGate;
    }

    public void setDeliveryGate(String deliveryGate) {
        this.deliveryGate = deliveryGate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<OrderItemModel> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemModel> orderItems) {
        this.orderItems = orderItems;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

