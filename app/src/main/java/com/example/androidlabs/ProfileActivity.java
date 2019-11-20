package com.example.androidlabs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String KEY_Email = "KEY_Email";
    private ImageButton mImageButton;
    Button toolbar;
    Button btn;
    Button weatherforecast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_linear);

        Intent it;
        it = getIntent();
        it.getStringExtra(KEY_Email);
        EditText editEmail = findViewById(R.id.editEmails);
        editEmail.setText(it.getStringExtra(KEY_Email));
        btn = findViewById(R.id.chatButton);
        toolbar = findViewById(R.id.toolBar);
        weatherforecast = findViewById(R.id.weatherButton);

        mImageButton = findViewById(R.id.picture);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         dispatchTakePictureIntent();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ProfileActivity.this, ChatRoomActivity.class);
                startActivity(it);
            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ProfileActivity.this, TestToolbar.class);
                startActivity(it);
            }
        });

        weatherforecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ProfileActivity.this, WeatherForecast.class);
                startActivity(it);
            }
        });
    }

    private void dispatchTakePictureIntent() {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            mImageButton.setImageBitmap(imageBitmap);
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
