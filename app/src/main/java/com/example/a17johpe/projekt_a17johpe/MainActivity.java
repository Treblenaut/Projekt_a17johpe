package com.example.a17johpe.projekt_a17johpe;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<MarvelCharacter> characterData = new ArrayList<>();
    MarvelReaderDbHelper dbHelper;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new FetchData().execute();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CustomAdapter(characterData, new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MarvelCharacter item) {
                Toast.makeText(getApplicationContext(), item.getInfo(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("title", item.getName());
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        dbHelper = new MarvelReaderDbHelper(getApplicationContext());
        rereadFromDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void rereadFromDatabase() {
        //characterData.clear();
        SQLiteDatabase dbRead = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                MarvelReaderContract.MarvelEntry.COLUMN_NAME_NAME,
                MarvelReaderContract.MarvelEntry.COLUMN_NAME_HERO,
                MarvelReaderContract.MarvelEntry.COLUMN_NAME_ACTOR,
                MarvelReaderContract.MarvelEntry.COLUMN_NAME_FIRST,
                MarvelReaderContract.MarvelEntry.COLUMN_NAME_TEAM,
                MarvelReaderContract.MarvelEntry.COLUMN_NAME_HOME,
                MarvelReaderContract.MarvelEntry.COLUMN_NAME_WIKI,
                MarvelReaderContract.MarvelEntry.COLUMN_NAME_IMAGE
        };

        String sortOrder = MarvelReaderContract.MarvelEntry.COLUMN_NAME_NAME + " ASC";

        Cursor cursor = dbRead.query(
                MarvelReaderContract.MarvelEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        while (cursor.moveToNext()) {
            String mName = cursor.getString(cursor.getColumnIndexOrThrow(MarvelReaderContract.MarvelEntry.COLUMN_NAME_NAME));
            String mHero = cursor.getString(cursor.getColumnIndexOrThrow(MarvelReaderContract.MarvelEntry.COLUMN_NAME_HERO));
            String mActor = cursor.getString(cursor.getColumnIndexOrThrow(MarvelReaderContract.MarvelEntry.COLUMN_NAME_ACTOR));
            String mFirst = cursor.getString(cursor.getColumnIndexOrThrow(MarvelReaderContract.MarvelEntry.COLUMN_NAME_FIRST));
            String mTeam = cursor.getString(cursor.getColumnIndexOrThrow(MarvelReaderContract.MarvelEntry.COLUMN_NAME_TEAM));
            String mHome = cursor.getString(cursor.getColumnIndexOrThrow(MarvelReaderContract.MarvelEntry.COLUMN_NAME_HOME));
            String mWiki = cursor.getString(cursor.getColumnIndexOrThrow(MarvelReaderContract.MarvelEntry.COLUMN_NAME_WIKI));
            String mImage = cursor.getString(cursor.getColumnIndexOrThrow(MarvelReaderContract.MarvelEntry.COLUMN_NAME_IMAGE));

            MarvelCharacter m = new MarvelCharacter(mName, mTeam, mFirst, mHome, mHero, mActor, mWiki, mImage);
            characterData.add(m);
            Log.d("olle1", m.getInfo());
        }
        cursor.close();
    }

    private class FetchData extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... params) {
            // These two variables need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a Java string.
            String jsonStr = null;

            try {
                // Construct the URL for the Internet service
                URL url = new URL("http://wwwlab.iit.his.se/brom/kurser/mobilprog/dbservice/admin/getdataasjson.php?type=a17johpe");

                // Create the request to the PHP-service, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                jsonStr = buffer.toString();
                return jsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in
                // attempting to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Network error", "Error closing stream", e);
                    }
                }
            }
        }
        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            // This code executes after we have received our data. The String object o holds
            // the un-parsed JSON string or is null if we had an IOException during the fetch.

            // Implement a parsing code that loops through the entire JSON and creates objects
            // of our newly created Mountain class.
            try {
                JSONArray json1 = new JSONArray(o);
                mRecyclerView.setAdapter(null);

                SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();
                //mRecyclerView.setAdapter(null);
                //mountainData.clear();

                for (int i = 0; i < json1.length(); i++) {
                    JSONObject marvel = json1.getJSONObject(i);
                    String marvelName = marvel.getString("name");
                    String marvelTeam = marvel.getString("company");
                    String marvelLocation = marvel.getString("location");
                    String marvelFirst = marvel.getString("category");
                    JSONObject marvelAuxdata = new JSONObject(marvel.getString("auxdata"));
                    String marvelHero = marvelAuxdata.getString("supername");
                    String marvelActor = marvelAuxdata.getString("actor");
                    String marvelWiki = marvelAuxdata.getString("wiki");
                    String marvelImage = marvelAuxdata.getString("image");

                    ContentValues values = new ContentValues();
                    values.put(MarvelReaderContract.MarvelEntry.COLUMN_NAME_NAME, marvelName);
                    values.put(MarvelReaderContract.MarvelEntry.COLUMN_NAME_TEAM, marvelTeam);
                    values.put(MarvelReaderContract.MarvelEntry.COLUMN_NAME_HOME, marvelLocation);
                    values.put(MarvelReaderContract.MarvelEntry.COLUMN_NAME_FIRST, marvelFirst);
                    values.put(MarvelReaderContract.MarvelEntry.COLUMN_NAME_HERO, marvelHero);
                    values.put(MarvelReaderContract.MarvelEntry.COLUMN_NAME_ACTOR, marvelActor);
                    values.put(MarvelReaderContract.MarvelEntry.COLUMN_NAME_WIKI, marvelWiki);
                    values.put(MarvelReaderContract.MarvelEntry.COLUMN_NAME_IMAGE, marvelImage);

                    dbWrite.insert(MarvelReaderContract.MarvelEntry.TABLE_NAME, null, values);

                    mRecyclerView.setAdapter(new CustomAdapter(characterData, new CustomAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(MarvelCharacter item) {
                            Toast.makeText(getApplicationContext(), item.getInfo(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, Details.class);
                            intent.putExtra("title", item.getName());
                            intent.putExtra("subtitle", item.getHeroName());
                            intent.putExtra("image", item.getImage());
                            startActivity(intent);
                        }
                    }));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
