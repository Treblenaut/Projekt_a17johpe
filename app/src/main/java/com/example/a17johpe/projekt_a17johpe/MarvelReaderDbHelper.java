package com.example.a17johpe.projekt_a17johpe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by a17johpe on 2018-05-18.
 */

public class MarvelReaderDbHelper extends SQLiteOpenHelper {
    MarvelReaderDbHelper (Context c) {
        super (c, "marvel.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MarvelReaderContract.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MarvelReaderContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
