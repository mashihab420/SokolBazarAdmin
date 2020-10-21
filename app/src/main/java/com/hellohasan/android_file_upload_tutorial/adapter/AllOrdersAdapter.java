package com.hellohasan.android_file_upload_tutorial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hellohasan.android_file_upload_tutorial.ModelClass.ModelAddProduct;
import com.hellohasan.android_file_upload_tutorial.ModelClass.ModelAllOrders;
import com.hellohasan.android_file_upload_tutorial.NetworkRelatedClass.ApiInterface;
import com.hellohasan.android_file_upload_tutorial.NetworkRelatedClass.RetrofitApiClient;
import com.hellohasan.android_file_upload_tutorial.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AllOrdersAdapter extends RecyclerView.Adapter<AllOrdersAdapter.MyViewHolder> {
    String spinnertext;
    private List<ModelAllOrders> orders;
    Context context;

    public AllOrdersAdapter(List<ModelAllOrders> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.ordernumber.setText("#"+orders.get(position).getInvoiceNumber());

        holder.status.setText(orders.get(position).getOrderStatus());
        holder.phone.setText(orders.get(position).getPhone());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,R.array.order_status,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(adapter);

        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               spinnertext = adapterView.getItemAtPosition(i).toString();
               // Toast.makeText(context, ""+spinnertext, Toast.LENGTH_SHORT).show();



                if (spinnertext.equals("select one")){

                }else {

                    ApiInterface apiInterface;
                    Retrofit instance = RetrofitApiClient.getClient();
                    apiInterface =instance.create(ApiInterface.class);
                    ModelAllOrders modelAllOrders = new ModelAllOrders();
                    String ordernum = orders.get(position).getInvoiceNumber();
                    modelAllOrders.setInvoiceNumber(ordernum);
                    modelAllOrders.setOrderStatus(spinnertext);

                    apiInterface.updateOrderStatus(modelAllOrders).enqueue(new Callback<ModelAllOrders>() {
                        @Override
                        public void onResponse(Call<ModelAllOrders> call, Response<ModelAllOrders> response) {
                            Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<ModelAllOrders> call, Throwable t) {

                        }
                    });

                    holder.status.setText(spinnertext);

                }







            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ordernumber,status,phone;
        Spinner spinner;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ordernumber = itemView.findViewById(R.id.orderid);
            phone = itemView.findViewById(R.id.textView11);
            status = itemView.findViewById(R.id.textView12);
            spinner = itemView.findViewById(R.id.sp_orders_status);
        }
    }
}
