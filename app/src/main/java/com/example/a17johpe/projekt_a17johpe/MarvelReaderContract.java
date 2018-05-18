package com.example.a17johpe.projekt_a17johpe;

import android.provider.BaseColumns;

/**
 * Created by a17johpe on 2018-05-18.
 */

public class MarvelReaderContract {
    private MarvelReaderContract() {}

    public static class MarvelEntry implements BaseColumns {
        public static final String TABLE_NAME = "marvelcharacter";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_HERO = "hero";
        public static final String COLUMN_NAME_TEAM = "team";
        public static final String COLUMN_NAME_FIRST = "firstappearance";
        public static final String COLUMN_NAME_ACTOR = "actor";
        public static final String COLUMN_NAME_HOME = "home";
        public static final String COLUMN_NAME_WIKI = "wiki";
        public static final String COLUMN_NAME_IMAGE = "image";
    }

    public static final String SQL_CREATE =
            "CREATE TABLE " + MarvelEntry.TABLE_NAME + " (" +
                    MarvelEntry._ID + " INTEGER PRIMARY KEY," +
                    MarvelEntry.COLUMN_NAME_NAME + " TEXT NOT NULL UNIQUE," +
                    MarvelEntry.COLUMN_NAME_HERO + " TEXT," +
                    MarvelEntry.COLUMN_NAME_TEAM + " TEXT," +
                    MarvelEntry.COLUMN_NAME_FIRST + " TEXT," +
                    MarvelEntry.COLUMN_NAME_ACTOR + " TEXT," +
                    MarvelEntry.COLUMN_NAME_HOME + " TEXT," +
                    MarvelEntry.COLUMN_NAME_WIKI + " TEXT," +
                    MarvelEntry.COLUMN_NAME_IMAGE + " TEXT" +
                    ");";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MarvelEntry.TABLE_NAME;
}
