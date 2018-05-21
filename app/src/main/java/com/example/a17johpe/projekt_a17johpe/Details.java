package com.example.a17johpe.projekt_a17johpe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        //Character name
        String title = intent.getStringExtra("title");
        TextView textView = (TextView) findViewById(R.id.detail_title);
        textView.setText(title);

        //Hero name
        String subtitle = intent.getStringExtra("subtitle");
        TextView textView2 = (TextView) findViewById(R.id.detail_subtitle);
        textView2.setText(subtitle);
    }

}
