package com.example.myapplication.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import com.bumptech.glide.Glide;
import com.example.myapplication.DataModel.ImageDataModel;
import com.example.myapplication.R;
import de.hdodenhof.circleimageview.CircleImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Register extends AppCompatActivity {
    TextInputEditText fName, lName, address;
    Button btnRegister;
    CircleImageView photo;
    String pathToFile;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fName = findViewById(R.id.input_first_name);
        lName = findViewById(R.id.input_last_name);
        address = findViewById(R.id.input_address);
        btnRegister = findViewById(R.id.btn_register);
        photo = findViewById(R.id.profile_photo);
        context = Register.this;

        photo.setOnClickListener(view -> {
            try {
                if (Build.VERSION.SDK_INT >= 23){
                    requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE}
                            ,2);
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            final CharSequence[] options = { "گرفتن عکس", "انتخاب از گالری","لفو" };

            AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);

            builder.setTitle("انتخاب عکس");

            builder.setItems(options, (dialog, item) -> {

                if (options[item].equals("گرفتن عکس"))

                {
                    choseImageFromCamera();
                }

                else if (options[item].equals("انتخاب از گالری"))

                {
                    chooseImageFromGallery();
                }
                else if (options[item].equals("لفو")) {

                    dialog.dismiss();
                }
            });

            builder.show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageDataModel list = new ImageDataModel();
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                //choose image from Camera
                Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                //image as bitmap
                list.setImagePath(pathToFile);
                Glide.with(context).load(list.getImagePath()).into(photo);


            }
            if (requestCode == 3) {

                assert data != null;
                Uri pickedImage = data.getData();

                String[] proj = {MediaStore.Images.Media.DATA};
                assert pickedImage != null;
                Cursor cursor = getContentResolver().query(pickedImage, proj, null, null, null);
                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                list.setImagePath(cursor.getString(column_index));
                cursor.close();
                //Image from gallery
                //image as string
                Glide.with(context).load(list.getImagePath()).into(photo);

            }

        }

    }

    private void chooseImageFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        File pictureDirectorys = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectorys.getPath();
        Uri uri = Uri.parse(pictureDirectoryPath);
        intent.setDataAndType(uri, "image/*");
        startActivityForResult(intent, 3);

    }

    private void choseImageFromCamera() {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePic.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            photoFile = createPhotoFileDIrection();
            if (photoFile != null) {
                pathToFile = photoFile.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(getApplicationContext()
                        , "com.example.myapplication.Activity.Register", photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePic, 1);
            }

        }
    }

    private File createPhotoFileDIrection() {
        String name = new SimpleDateFormat("yyyymm").format(new Date());
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
