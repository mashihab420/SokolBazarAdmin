package com.hellohasan.android_file_upload_tutorial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hellohasan.android_file_upload_tutorial.ModelClass.CheckoutModel;
import com.hellohasan.android_file_upload_tutorial.ModelClass.ModelAllOrders;
import com.hellohasan.android_file_upload_tutorial.R;
import com.hellohasan.android_file_upload_tutorial.SendData;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.MyViewHolder> {
    List<ModelAllOrders> checkoutModelList;
    Context context;

    private SendData sendData;

    public CheckoutAdapter(List<ModelAllOrders> checkoutModelList,SendData sendData, Context context) {
        this.checkoutModelList = checkoutModelList;
        this.sendData = sendData;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(checkoutModelList.get(position).getProduct_Name());
        holder.price.setText(checkoutModelList.get(position).getPrice());
        holder.quantity.setText(checkoutModelList.get(position).getQuantity());
        String subtotal = checkoutModelList.get(position).getSubtotal();
        String total = checkoutModelList.get(position).getTotal();
        String discount = checkoutModelList.get(position).getDiscount();

        Glide
                .with(context)
                .load(checkoutModelList.get(position).getImage_Url())
                .into(holder.imageView);

        sendData.totalPrice(subtotal,total,discount);
    }

    @Override
    public int getItemCount() {
        return checkoutModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,price,quantity;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.title_name_id);
            price = itemView.findViewById(R.id.price_product_id);
            quantity = itemView.findViewById(R.id.cart_quantity_id);
            imageView = itemView.findViewById(R.id.product_image_id);
        }
    }
}
