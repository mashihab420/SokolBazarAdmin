package com.hellohasan.android_file_upload_tutorial.adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hellohasan.android_file_upload_tutorial.ModelClass.ModelAddProduct;
import com.hellohasan.android_file_upload_tutorial.NetworkRelatedClass.ApiInterface;
import com.hellohasan.android_file_upload_tutorial.NetworkRelatedClass.RetrofitApiClient;
import com.hellohasan.android_file_upload_tutorial.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AllProductAdapter extends RecyclerView.Adapter<AllProductAdapter.ViewHolder> {
    private List<ModelAddProduct> products;
    Context context;
    public AllProductAdapter(List<ModelAddProduct> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_product,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(products.get(position).getName());
        holder.price.setText(products.get(position).getPrice());
        holder.category.setText(products.get(position).getCategorys());
        holder.offers.setText(products.get(position).getOffer());
        holder.quantity.setText(products.get(position).getQuantitys());

        String imageurl = "http://shihab.techdevbd.com/sokol_bazar/file_upload_api/"+products.get(position).getUrl();

        Glide
                .with(context)
                .load(imageurl)
                .into(holder.p_pic);


    /*    holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,products.get(position).getId() , Toast.LENGTH_SHORT).show();
            }
        });*/




        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




             Retrofit instance = RetrofitApiClient.getClient();
                ApiInterface apiInterface =instance.create(ApiInterface.class);

                ModelAddProduct modelAddProduct = new ModelAddProduct();
                modelAddProduct.setId(products.get(position).getId());
                modelAddProduct.setUrl(products.get(position).getUrl());

                apiInterface.delete_product(modelAddProduct).enqueue(new Callback<ModelAddProduct>() {
                    @Override
                    public void onResponse(Call<ModelAddProduct> call, Response<ModelAddProduct> response) {
                        Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();

                        try{
                            products.clear();
                            int id = Integer.parseInt(products.get(position).getId())-1;
                            products.remove(id);
                            notifyDataSetChanged();

                        }catch (Exception e){
                            // Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ModelAddProduct> call, Throwable t) {

                    }
                });
            }
        });



    }



    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,price,category,offers,quantity;
        ImageView p_pic,edit,delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView2);
            price = itemView.findViewById(R.id.textView3);
            category = itemView.findViewById(R.id.textView4);
            offers = itemView.findViewById(R.id.textView4);
            quantity = itemView.findViewById(R.id.textView6);
            p_pic = itemView.findViewById(R.id.imageView4);
           // edit = itemView.findViewById(R.id.imageView5);
            delete = itemView.findViewById(R.id.deletebtn);
        }
    }
}
