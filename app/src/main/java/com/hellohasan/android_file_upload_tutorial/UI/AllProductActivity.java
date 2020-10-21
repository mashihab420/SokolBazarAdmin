package com.hellohasan.android_file_upload_tutorial.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.hellohasan.android_file_upload_tutorial.ModelClass.ModelAddProduct;
import com.hellohasan.android_file_upload_tutorial.NetworkRelatedClass.ApiInterface;
import com.hellohasan.android_file_upload_tutorial.NetworkRelatedClass.RetrofitApiClient;
import com.hellohasan.android_file_upload_tutorial.R;
import com.hellohasan.android_file_upload_tutorial.adapter.AllProductAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AllProductActivity extends AppCompatActivity {

    private AllProductAdapter allProductAdapter;
    List<ModelAddProduct> productList;
    ApiInterface apiInterface;
RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);
        recyclerView = findViewById(R.id.recyclerId);
        productList = new ArrayList<>();
        allProductAdapter = new AllProductAdapter(productList,getApplicationContext());

        initRecyclerViewAllproduct(allProductAdapter, recyclerView);



    }

    private void initRecyclerViewAllproduct(RecyclerView.Adapter adapter, RecyclerView view) {
        view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        view.setAdapter(adapter);
        allProductAdapter.notifyDataSetChanged();

        datasetallproduct();
    }

    private void datasetallproduct(){

        //todo get All product
        Retrofit instance = RetrofitApiClient.getClient();
        apiInterface =instance.create(ApiInterface.class);

        apiInterface.getAlldata().enqueue(new Callback<List<ModelAddProduct>>() {
            @Override
            public void onResponse(Call<List<ModelAddProduct>> call, Response<List<ModelAddProduct>> response) {
                productList.addAll(response.body());
                allProductAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ModelAddProduct>> call, Throwable t) {

            }
        });




    }

    public void backbtn(View view) {
        onBackPressed();
    }
}