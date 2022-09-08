package com.example.gymmamagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gymmamagement.modals.Member;
import com.example.gymmamagement.remote.AppUtils;
import com.example.gymmamagement.remote.UserService;

import java.io.File;
import java.io.FileOutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class UploadActivity extends AppCompatActivity {

    private Button upload;
    private Context context;
    Integer id=0;
    String imagePath="";
    UserService userService;

    private final int camera_req_code = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        upload = findViewById(R.id.upload);
        id = getIntent().getExtras().getInt("id");
        userService = AppUtils.getUserService();

        ActivityCompat.requestPermissions(UploadActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    upload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent,camera_req_code);

                        }
                    });
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(UploadActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            if(requestCode==camera_req_code){
               //Get image from data
                Uri selectedImage = data.getData();
                String selectedFilePath = selectedImage.getPath();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,filePathColumn,null,null,null);
                assert cursor!=null;
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath =cursor.getString(columnIndex);
                cursor.close();
                uploadImage();

            }else{
                Toast.makeText(this, "Something went worng", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Something went worng", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading image ....");
        pd.setCancelable(false);
        pd.show();

        //creating file
        File f = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),f);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image",f.getName(),requestFile);
        Log.i("request file",requestFile.toString());

        Call<Member> call = userService.uploadImage(body,id);
        call.enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                if(response.isSuccessful()){
                    Member member = response.body();
                    if(member!=null){
                        Toast.makeText(UploadActivity.this, "uploaded successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(UploadActivity.this,MemberActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(UploadActivity.this, "member is null", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(UploadActivity.this, "error in the api call", Toast.LENGTH_SHORT).show();
                }
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                Toast.makeText(UploadActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }
}