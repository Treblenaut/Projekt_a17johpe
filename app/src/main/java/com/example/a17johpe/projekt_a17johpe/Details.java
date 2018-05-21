package com.example.a17johpe.projekt_a17johpe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class Details extends AppCompatActivity {
    protected ImageView bmImage;

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

        //Team name
        String team = "Team: " + intent.getStringExtra("team");
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setText(team);

        //First appearance
        String first = "First appeared in: " + intent.getStringExtra("first");
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        textView4.setText(first);

        //Actor name
        String actor = "Played by: " + intent.getStringExtra("actor");
        TextView textView5 = (TextView) findViewById(R.id.textView5);
        textView5.setText(actor);

        //Home name
        String home = "Home/birthplace: " + intent.getStringExtra("home");
        TextView textView6 = (TextView) findViewById(R.id.textView6);
        textView6.setText(home);

        //Character image
        String image = intent.getStringExtra("image");
        new DownloadImageTask((ImageView) findViewById(R.id.detail_image))
                .execute(image);
        Log.d("olle2", image);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {


        public DownloadImageTask(ImageView bmImageIn) {
            bmImage = bmImageIn;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
