package com.hellohasan.android_file_upload_tutorial.ModelClass;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ModelAddProduct implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("p_name")
    private String name;
    @SerializedName("p_price")
    private String price;

    @SerializedName("p_description")
    private String description;

    @SerializedName("shop_name")
    private String shopname;

    @SerializedName("category")
    private String categorys;

    @SerializedName("quantity")
    private String quantitys;

    @SerializedName("offers")
    private String offer;

    @SerializedName("image_url")
    private String url;





    @SerializedName("message")
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getQuantitys() {
        return quantitys;
    }

    public void setQuantitys(String quantitys) {
        this.quantitys = quantitys;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getCategorys() {
        return categorys;
    }

    public void setCategorys(String categorys) {
        this.categorys = categorys;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public ModelAddProduct() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ModelAddProduct(String name, String price,String description,String shopname,String categorys,String quantitys, String offer) {
        this.name = name;
        this.price = price;
        this.description=description;
        this.shopname = shopname;
        this.categorys = categorys;
        this.quantitys = quantitys;
        this.offer = offer;

    }

    public final static Parcelable.Creator<ModelAddProduct> CREATOR = new Parcelable.Creator<ModelAddProduct>() {

        @SuppressWarnings({
                "unchecked"
        })
        public ModelAddProduct createFromParcel(Parcel in) {
            ModelAddProduct instance = new ModelAddProduct();
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.price = ((String) in.readValue((int.class.getClassLoader())));
            instance.description = ((String) in.readValue((int.class.getClassLoader())));
            instance.shopname = ((String) in.readValue((int.class.getClassLoader())));
            instance.categorys = ((String) in.readValue((String.class.getClassLoader())));
            instance.quantitys = ((String) in.readValue((String.class.getClassLoader())));
            instance.offer = ((String) in.readValue((String.class.getClassLoader())));

            instance.message = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public ModelAddProduct[] newArray(int size) {
            return (new ModelAddProduct[size]);
        }

    };


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(price);
        dest.writeValue(description);
        dest.writeValue(shopname);
        dest.writeValue(categorys);
        dest.writeValue(quantitys);
        dest.writeValue(offer);

        dest.writeValue(message);
    }

    public int describeContents() {
        return  0;
    }
}
