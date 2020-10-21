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

import com.hellohasan.android_file_upload_tutorial.ModelClass.ModelAllOrders;
import com.hellohasan.android_file_upload_tutorial.ModelClass.ModelUser;
import com.hellohasan.android_file_upload_tutorial.NetworkRelatedClass.ApiInterface;
import com.hellohasan.android_file_upload_tutorial.NetworkRelatedClass.RetrofitApiClient;
import com.hellohasan.android_file_upload_tutorial.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
List<ModelUser> userList;
Context context;
    String spinnertext;
    public UserAdapter(List<ModelUser> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(userList.get(position).getName());
        holder.phone.setText(userList.get(position).getPhone());
        holder.address.setText(userList.get(position).getAddress());
        holder.status.setText(userList.get(position).getStatus());

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(context,R.array.user_status,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(adapter2);


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
                    ModelUser modelUser = new ModelUser();
                    String phone = userList.get(position).getPhone();
                    modelUser.setPhone(phone);
                    modelUser.setStatus(spinnertext);

                    apiInterface.updateUserStatus(modelUser).enqueue(new Callback<ModelUser>() {
                        @Override
                        public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {

                        }

                        @Override
                        public void onFailure(Call<ModelUser> call, Throwable t) {

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
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,phone,address,status;
        Spinner spinner;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nametv);
            phone = itemView.findViewById(R.id.phonetv);
            address = itemView.findViewById(R.id.addresstv);
            status = itemView.findViewById(R.id.statustv);
            spinner = itemView.findViewById(R.id.spinner1);
        }
    }
}
