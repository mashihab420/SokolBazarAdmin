package com.hellohasan.android_file_upload_tutorial.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.hellohasan.android_file_upload_tutorial.ModelClass.ModelAddProduct;
import com.hellohasan.android_file_upload_tutorial.ModelClass.ModelAllOrders;
import com.hellohasan.android_file_upload_tutorial.NetworkRelatedClass.ApiInterface;
import com.hellohasan.android_file_upload_tutorial.NetworkRelatedClass.RetrofitApiClient;
import com.hellohasan.android_file_upload_tutorial.R;
import com.hellohasan.android_file_upload_tutorial.adapter.AllOrdersAdapter;
import com.hellohasan.android_file_upload_tutorial.adapter.AllProductAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ViewOrdersActivity extends AppCompatActivity {

    private AllOrdersAdapter allOrdersAdapter;
    List<ModelAllOrders> orders;
    ApiInterface apiInterface;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        recyclerView = findViewById(R.id.recyclerView);
        orders = new ArrayList<>();
        allOrdersAdapter = new AllOrdersAdapter(orders,getApplicationContext());

        initRecyclerViewAllproduct(allOrdersAdapter, recyclerView);
    }
    private void initRecyclerViewAllproduct(RecyclerView.Adapter adapter, RecyclerView view) {
        view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        view.setAdapter(adapter);
        allOrdersAdapter.notifyDataSetChanged();

        datasetallproduct();
    }

    private void datasetallproduct(){

        //todo get All product
        Retrofit instance = RetrofitApiClient.getClient();
        apiInterface =instance.create(ApiInterface.class);

        apiInterface.getAllOrders().enqueue(new Callback<List<ModelAllOrders>>() {
            @Override
            public void onResponse(Call<List<ModelAllOrders>> call, Response<List<ModelAllOrders>> response) {
                orders.addAll(response.body());
                allOrdersAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<ModelAllOrders>> call, Throwable t) {

            }
        });



    }


    public void backbtn(View view) {
        onBackPressed();
    }
}