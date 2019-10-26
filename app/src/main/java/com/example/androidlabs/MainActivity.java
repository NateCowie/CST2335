package com.example.androidlabs;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private EditText editEmail;
    private Button btn;

    public static final String MyPREFERENCES = "USER_PREF";
    public static final String KEY_Email = "KEY_Email";
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linearlab3);

        editEmail = findViewById(R.id.editEmail);
        btn = findViewById(R.id.login);

        sp = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        sp.getString("KEY_Email", "");

        editEmail.setText(sp.getString("KEY_Email", ""));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, ProfileActivity.class);
                it.putExtra(KEY_Email, editEmail.getText().toString());
                startActivity(it);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        String email  = editEmail.getText().toString();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("KEY_Email", email);
        editor.apply();

    }




}
