package com.hellohasan.android_file_upload_tutorial.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hellohasan.android_file_upload_tutorial.ModelClass.EventModel;
import com.hellohasan.android_file_upload_tutorial.ModelClass.ImageSenderInfo;
import com.hellohasan.android_file_upload_tutorial.ModelClass.ModelAddProduct;
import com.hellohasan.android_file_upload_tutorial.NetworkRelatedClass.ApiInterface;
import com.hellohasan.android_file_upload_tutorial.NetworkRelatedClass.NetworkCall;
import com.hellohasan.android_file_upload_tutorial.NetworkRelatedClass.NetworkCallMain;
import com.hellohasan.android_file_upload_tutorial.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddProductActivity extends AppCompatActivity {
    // if you want to upload only image, make it true. Otherwise to allow any file- false
    boolean isOnlyImageAllowed = true;

    private EditText nameEditText;
    private EditText ageEditText;
    private EditText phoneEditText;
    private Button uploadButton;
    private TextView responseTextView;
    ApiInterface apiInterface;
    private String filePath;
    private static final int PICK_PHOTO = 1958;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    Spinner spinner;
    String spinnertext;
    EditText p_name,p_price,p_category,quantity,description,shopname;
    ImageView imageView;


    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventModel event) throws ClassNotFoundException {
        if (event.isTagMatchWith("response")) {
            String responseMessage = "Data insert " + event.getMessage();
           // responseTextView.setText(responseMessage);
            Toast.makeText(this, responseMessage, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        spinner = findViewById(R.id.sp_offers);
        p_name = findViewById(R.id.editTextTextPersonName4);
        p_price = findViewById(R.id.editTextTextPersonName5);
        p_category = findViewById(R.id.editTextTextPersonName6);
        description = findViewById(R.id.descriptionid);
        shopname = findViewById(R.id.shopnameid);
        quantity = findViewById(R.id.quantityid);
        imageView = findViewById(R.id.imageView3);
        verifyStoragePermissions(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.offerstyep,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnertext = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_PHOTO) {
            Uri imageUri = data.getData();
            filePath = getPath(imageUri);
            imageView.setImageURI(imageUri);

        }
    }

    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public void uploadAllproduct(View view) {
        String name = p_name.getText().toString();
        String price = p_price.getText().toString();
        String category = p_category.getText().toString();
        String quantityy = quantity.getText().toString();
        String descriptionn = description.getText().toString();
        String shopnamee = shopname.getText().toString();

        if (name.equals("")){
           // Toast.makeText(this, "You must enter name", Toast.LENGTH_SHORT).show();
            p_name.setError("You must enter name");
        }
       else if (price.equals("")){
            p_price.setError("You must enter price");
        }
        else if (category.equals("")){
            p_category.setError("You must enter category");

        }
        else if (quantityy.equals("")){
            quantity.setError("You must enter quantity");

        }
        else if (shopnamee.equals("")){
            shopname.setError("You must enter shop name");
        }
        else {
               NetworkCallMain.fileUpload(filePath, new ModelAddProduct(name, price,descriptionn,shopnamee,category,quantityy,spinnertext));
        }








    }

    public void pictureselectionClicks(View view) {

        Intent intent;

        if (isOnlyImageAllowed) {
            // only image can be selected
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        } else {
            // any type of files including image can be selected
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("file/*");
        }

        startActivityForResult(intent, PICK_PHOTO);


    }

    public void backbtn(View view) {
        onBackPressed();
    }
}