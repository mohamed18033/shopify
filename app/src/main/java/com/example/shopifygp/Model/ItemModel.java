package com.example.shopifygp.Model;


public class ItemModel {
    private String itemName;
    private String itemImage;
    private String itemDescription;
    private String itemPrice;
    private String categoryID;
    private String itemAvailability;

    public ItemModel() {
    }

    public ItemModel(String itemName, String itemImage, String itemDescription, String itemPrice, String categoryID, String itemAvailability) {
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.categoryID = categoryID;
        this.itemAvailability = itemAvailability;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getItemAvailability() {
        return itemAvailability;
    }

    public void setItemAvailability(String itemAvailability) {
        this.itemAvailability = itemAvailability;
    }
}
