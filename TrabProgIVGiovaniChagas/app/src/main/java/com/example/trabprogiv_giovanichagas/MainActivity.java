package com.example.trabprogiv_giovanichagas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarMainActivity();
            }
        }, 6500);
    }

    private void mostrarMainActivity() {
        Intent intent = new Intent(this, SunsetActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onResume(){
        super.onResume();

    }
}