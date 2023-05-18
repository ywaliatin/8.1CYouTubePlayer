package com.example.a81c;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PlayListActivity extends AppCompatActivity {
    private PlaylistDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_list_activity);

        databaseHelper = new PlaylistDatabaseHelper(this);

        List<String> playlistItems = retrievePlaylistItems();

        ListView playlistListView = findViewById(R.id.playlistListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playlistItems);
        playlistListView.setAdapter(adapter);
    }

    private ArrayList<String> retrievePlaylistItems() {
        ArrayList<String> playlistItems = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(PlaylistDatabaseHelper.TABLE_PLAYLIST, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndexVideoId = cursor.getColumnIndex(PlaylistDatabaseHelper.COLUMN_VIDEO_ID);
            while (!cursor.isAfterLast()) {
                String videoId = cursor.getString(columnIndexVideoId);
                if (videoId != null) {
                    String youtubeLink = "https://www.youtube.com/watch?v=" + videoId;
                    playlistItems.add(youtubeLink);
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return playlistItems;
    }


}
