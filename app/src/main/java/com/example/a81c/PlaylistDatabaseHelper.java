package com.example.a81c;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlaylistDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "playlist.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_PLAYLIST = "playlist";
    public static final String COLUMN_VIDEO_ID = "video_id";
    public static final String COLUMN_YOUTUBE_LINK = "youtube_link";

    public PlaylistDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_PLAYLIST + "(" +
                COLUMN_VIDEO_ID + " TEXT PRIMARY KEY, " +
                COLUMN_YOUTUBE_LINK + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYLIST);
        onCreate(db);
    }

    public String getTablePlaylist() {
        return TABLE_PLAYLIST;
    }

    public String getColumnVideoId() {
        return COLUMN_VIDEO_ID;
    }

    public String getColumnYoutubeLink() {
        return COLUMN_YOUTUBE_LINK;
    }
}
