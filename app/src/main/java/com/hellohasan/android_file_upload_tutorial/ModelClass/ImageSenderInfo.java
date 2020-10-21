
package com.hellohasan.android_file_upload_tutorial.ModelClass;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ImageSenderInfo implements Parcelable {

    @SerializedName("sender_name")
    private String sender;
    @SerializedName("sender_age")
    private int age;

    @SerializedName("sender_phone")
    private String phone;

    @SerializedName("image_url")
    private String url;

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ImageSenderInfo() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageSenderInfo(String sender, int age, String phone) {
        this.sender = sender;
        this.age = age;
        this.phone = phone;
    }

    public final static Parcelable.Creator<ImageSenderInfo> CREATOR = new Creator<ImageSenderInfo>() {

        @SuppressWarnings({
            "unchecked"
        })
        public ImageSenderInfo createFromParcel(Parcel in) {
            ImageSenderInfo instance = new ImageSenderInfo();
            instance.sender = ((String) in.readValue((String.class.getClassLoader())));
            instance.age = ((int) in.readValue((int.class.getClassLoader())));
            instance.phone = ((String) in.readValue((String.class.getClassLoader())));
            instance.message = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public ImageSenderInfo[] newArray(int size) {
            return (new ImageSenderInfo[size]);
        }

    };


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(sender);
        dest.writeValue(age);
        dest.writeValue(phone);
        dest.writeValue(message);
    }

    public int describeContents() {
        return  0;
    }

}
