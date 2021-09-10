package com.example.cost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.*;

import android.os.Bundle;

public class Home extends AppCompatActivity {
    public void clickStates(View view){
        Intent stInt = new Intent(this,states.class);
        startActivity(stInt);
    }
    public void GetNews(View view){
        Intent NwInt = new Intent(this,news.class);
        startActivity(NwInt);
    }
    public void clickDaily(View view){
        Intent DlInt = new Intent(this,daily_change.class);
        startActivity(DlInt);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Button states = findViewById(R.id.statesBtn);


    }
}
