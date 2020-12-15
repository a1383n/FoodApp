package ir.amirsobhan.foodordering.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart {
    int id,priceInt,deliveryChargeInt;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("deliveryTime")
    @Expose
    private String deliveryTime;
    @SerializedName("deliveryCharges")
    @Expose
    private String deliveryCharges;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("note")
    @Expose
    private String note;

    private int count;
    private boolean favorite;

    public Cart(String name, String imageUrl, String rating, String deliveryTime, String deliveryCharges, String price, String note, int count, int priceInt, int deliveryChargeInt, boolean favorite) {
        this.priceInt = priceInt;
        this.deliveryChargeInt = deliveryChargeInt;
        this.name = name;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.deliveryCharges = deliveryCharges;
        this.price = price;
        this.note = note;
        this.count = count;
        this.favorite = favorite;
    }

    public Cart(int id, String name, String imageUrl, String rating, String deliveryTime, String deliveryCharges, String price, String note, int count,int priceInt, int deliveryChargeInt, boolean favorite) {
        this.id = id;
        this.priceInt = priceInt;
        this.deliveryChargeInt = deliveryChargeInt;
        this.name = name;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.deliveryCharges = deliveryCharges;
        this.price = price;
        this.note = note;
        this.count = count;
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPriceInt() {
        return priceInt;
    }

    public void setPriceInt(int priceInt) {
        this.priceInt = priceInt;
    }

    public int getDeliveryChargeInt() {
        return deliveryChargeInt;
    }

    public void setDeliveryChargeInt(int deliveryChargeInt) {
        this.deliveryChargeInt = deliveryChargeInt;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
