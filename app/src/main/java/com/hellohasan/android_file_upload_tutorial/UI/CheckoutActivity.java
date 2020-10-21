package com.hellohasan.android_file_upload_tutorial.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.hellohasan.android_file_upload_tutorial.ModelClass.ModelAddProduct;
import com.hellohasan.android_file_upload_tutorial.ModelClass.ModelAllOrders;
import com.hellohasan.android_file_upload_tutorial.ModelClass.ModelUser;
import com.hellohasan.android_file_upload_tutorial.NetworkRelatedClass.ApiInterface;
import com.hellohasan.android_file_upload_tutorial.NetworkRelatedClass.RetrofitApiClient;
import com.hellohasan.android_file_upload_tutorial.R;
import com.hellohasan.android_file_upload_tutorial.SendData;
import com.hellohasan.android_file_upload_tutorial.adapter.CheckoutAdapter;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.security.AccessController.getContext;

public class CheckoutActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, SendData {
    CheckoutAdapter checkoutAdapter;
    List<ModelAllOrders> checkoutdata;
    RecyclerView recyclerView;
    ApiInterface apiInterface;
    private ZXingScannerView mScannerView;
    String s1;
    String invoice="";
    String phone;
    FrameLayout frameLayout;
    Boolean popup = true;
    TextView subtotal,discount,total;
    EditText editText;
    ConstraintLayout constraintLayouttwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        recyclerView = findViewById(R.id.recyclerView);
        subtotal = findViewById(R.id.subtotalid);
        discount = findViewById(R.id.textView25);
        total = findViewById(R.id.textView27);

        frameLayout = findViewById(R.id.content_frame);
        editText = findViewById(R.id.textView19);
        constraintLayouttwo = findViewById(R.id.constraintLayout2);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 5);
            }
        }


        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                frameLayout.setVisibility(View.GONE);
                constraintLayouttwo.setVisibility(View.GONE);
                return false;
            }
        });

    }
    private void initRecyclerViewAllproduct(RecyclerView.Adapter adapter, RecyclerView view) {
        view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        view.setAdapter(adapter);
        checkoutAdapter.notifyDataSetChanged();

        datasetallproduct();
    }

    private void initRecyclerViewAllproductsearch(RecyclerView.Adapter adapter, RecyclerView view) {
        view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        view.setAdapter(adapter);
        checkoutAdapter.notifyDataSetChanged();

        datasetallproductsearch();
    }

    private void datasetallproductsearch(){

        //todo get All product
        Retrofit instance = RetrofitApiClient.getClient();
        apiInterface =instance.create(ApiInterface.class);

        String invoicenum = editText.getText().toString();

        ModelAllOrders modelAllOrders = new ModelAllOrders();
        modelAllOrders.setInvoiceNumber(invoicenum);


        apiInterface.checkoutdatasearch(modelAllOrders).enqueue(new Callback<List<ModelAllOrders>>() {
            @Override
            public void onResponse(Call<List<ModelAllOrders>> call, Response<List<ModelAllOrders>> response) {
                checkoutdata.addAll(response.body());
                checkoutAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ModelAllOrders>> call, Throwable t) {

            }
        });



    }


    private void datasetallproduct(){

        //todo get All product
        Retrofit instance = RetrofitApiClient.getClient();
        apiInterface =instance.create(ApiInterface.class);

        ModelAllOrders modelAllOrders = new ModelAllOrders();
        modelAllOrders.setInvoiceNumber(""+invoice);
        modelAllOrders.setPhone(""+phone);

        apiInterface.checkoutdata(modelAllOrders).enqueue(new Callback<List<ModelAllOrders>>() {
            @Override
            public void onResponse(Call<List<ModelAllOrders>> call, Response<List<ModelAllOrders>> response) {
                checkoutdata.addAll(response.body());
                checkoutAdapter.notifyDataSetChanged();
                mScannerView.stopCamera();
                frameLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<ModelAllOrders>> call, Throwable t) {
                mScannerView.stopCamera();
                constraintLayouttwo.setVisibility(View.GONE);
                frameLayout.setVisibility(View.GONE);
            }
        });



    }

    public void scannner(View view) {

        InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

        if (popup == true) {
            constraintLayouttwo.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.VISIBLE);

            mScannerView = new ZXingScannerView(getApplicationContext());
            frameLayout.addView(mScannerView);

            mScannerView.setResultHandler(CheckoutActivity.this);
            mScannerView.startCamera();
            popup = false;
        } else if (popup == false) {
            constraintLayouttwo.setVisibility(View.GONE);
            mScannerView.stopCamera();
            frameLayout.setVisibility(View.GONE);
            popup = true;
        }



    }

    @Override
    public void handleResult(Result result) {
        s1 = result.getText();

        String[] array = s1.split(",");

        invoice = array[0];
         phone = array[1];

        checkoutdata = new ArrayList<>();
        checkoutAdapter = new CheckoutAdapter(checkoutdata,this,getApplicationContext());
        initRecyclerViewAllproduct(checkoutAdapter, recyclerView);


      //  Toast.makeText(this, ""+invoice+" "+phone, Toast.LENGTH_SHORT).show();



        if (!s1.equals("")) {
            onPause();
            onResume();
        }
    }





    public void searchbtn(View view) {

        String searchtext = editText.getText().toString();

        if (searchtext.equals("")){
            Toast.makeText(this, "Enter Envoice Number", Toast.LENGTH_SHORT).show();
        }else{
            checkoutdata = new ArrayList<>();
            checkoutAdapter = new CheckoutAdapter(checkoutdata,this,getApplicationContext());
            initRecyclerViewAllproductsearch(checkoutAdapter,recyclerView);
            frameLayout.setVisibility(View.GONE);
            constraintLayouttwo.setVisibility(View.VISIBLE);
            InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }




    }

    @Override
    public void totalPrice(String subtotall, String totall, String discountt) {
        subtotal.setText(subtotall+" BDT");
        discount.setText(discountt+" BDT");
        total.setText(totall+" BDT");
    }

    public void checkoutbtn(View view) {

        String subtotall = subtotal.getText().toString();

        if (subtotall.equals("0 BDT")){
            Toast.makeText(this, "Scan QR code or Search", Toast.LENGTH_SHORT).show();
        }else {

            if (invoice.equals("")){
             String invoicenum = editText.getText().toString();
               // Toast.makeText(this, ""+invoicenum, Toast.LENGTH_SHORT).show();

                ApiInterface apiInterface;
                Retrofit instance = RetrofitApiClient.getClient();
                apiInterface =instance.create(ApiInterface.class);
                ModelAllOrders modelAllOrders = new ModelAllOrders();

                modelAllOrders.setInvoiceNumber(invoicenum);
                modelAllOrders.setOrderStatus("delivered");

                apiInterface.updateOrderStatus(modelAllOrders).enqueue(new Callback<ModelAllOrders>() {
                    @Override
                    public void onResponse(Call<ModelAllOrders> call, Response<ModelAllOrders> response) {

                        String message = response.body().getMessage();
                        if (message.equals("Succesful")){
                            Toast.makeText(getApplicationContext(), "Payment Complete ", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ModelAllOrders> call, Throwable t) {

                    }
                });



            }else {
               // Toast.makeText(this, ""+invoice, Toast.LENGTH_SHORT).show();


                ApiInterface apiInterface;
                Retrofit instance = RetrofitApiClient.getClient();
                apiInterface =instance.create(ApiInterface.class);
                ModelAllOrders modelAllOrders = new ModelAllOrders();

                modelAllOrders.setInvoiceNumber(invoice);
                modelAllOrders.setOrderStatus("delivered");

                apiInterface.updateOrderStatus(modelAllOrders).enqueue(new Callback<ModelAllOrders>() {
                    @Override
                    public void onResponse(Call<ModelAllOrders> call, Response<ModelAllOrders> response) {
                        String message = response.body().getMessage();
                        if (message.equals("Succesful")){
                            Toast.makeText(getApplicationContext(), "Payment Complete ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelAllOrders> call, Throwable t) {

                    }
                });


            }

           // Toast.makeText(this, "Delivered", Toast.LENGTH_SHORT).show();
        }

    }

    public void backbtn(View view) {
        onBackPressed();
    }
}