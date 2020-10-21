package com.hellohasan.android_file_upload_tutorial.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.hellohasan.android_file_upload_tutorial.ModelClass.ModelAllOrders;
import com.hellohasan.android_file_upload_tutorial.ModelClass.ModelUser;
import com.hellohasan.android_file_upload_tutorial.NetworkRelatedClass.ApiInterface;
import com.hellohasan.android_file_upload_tutorial.NetworkRelatedClass.RetrofitApiClient;
import com.hellohasan.android_file_upload_tutorial.R;
import com.hellohasan.android_file_upload_tutorial.adapter.UserAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UsersActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    List<ModelUser> userList;
    UserAdapter userAdapter;
RecyclerView recyclerView;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        recyclerView = findViewById(R.id.recyclerView);

        userList = new ArrayList<>();
        userAdapter = new UserAdapter(userList,UsersActivity.this);
        initRecyclerViewAllproduct(userAdapter, recyclerView);


    }

    private void initRecyclerViewAllproduct(RecyclerView.Adapter adapter, RecyclerView view) {
        view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        view.setAdapter(adapter);
        userAdapter.notifyDataSetChanged();

        datasetallproduct();
    }

    private void datasetallproduct(){

        //todo get All user
        Retrofit instance = RetrofitApiClient.getClient();
        apiInterface =instance.create(ApiInterface.class);

     apiInterface.getAlluser().enqueue(new Callback<List<ModelUser>>() {
         @Override
         public void onResponse(Call<List<ModelUser>> call, Response<List<ModelUser>> response) {
             userList.addAll(response.body());
             userAdapter.notifyDataSetChanged();
         }

         @Override
         public void onFailure(Call<List<ModelUser>> call, Throwable t) {

         }
     });


    }

    public void backbtn(View view) {
        onBackPressed();
    }
}