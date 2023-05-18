package com.example.a81c;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Home0Activity extends AppCompatActivity {
    private EditText editTextLink;
    private Button buttonPlayLink;
    private ArrayList<String> playlistItems = new ArrayList<>();
    private PlaylistDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home0_activity);

        editTextLink = findViewById(R.id.editTextLink);
        buttonPlayLink = findViewById(R.id.buttonPlayLink);

        databaseHelper = new PlaylistDatabaseHelper(this);

        Button buttonAddToList = findViewById(R.id.buttonAddToList);
        buttonAddToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playlistItem = editTextLink.getText().toString().trim();
                if (!playlistItem.isEmpty()) {
                    insertPlaylistItem(playlistItem);
                    editTextLink.setText("");
                    Toast.makeText(getApplicationContext(), "Added to playlist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        editTextLink.setTextIsSelectable(true); // Enable text selection

        buttonPlayLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String youtubeLink = editTextLink.getText().toString().trim();

                if (!youtubeLink.isEmpty()) {
                    String videoId = getYouTubeVideoId(youtubeLink);
                    if (videoId != null) {
                        Intent intent = new Intent(Home0Activity.this, HomeActivity.class);
                        intent.putExtra("videoId", videoId);
                        startActivity(intent);
                    } else {
                        showToast("Invalid YouTube link");
                    }
                } else {
                    showToast("Please enter a YouTube link");
                }
            }
        });

        Button buttonMyPlaylist = findViewById(R.id.buttonMyPlaylist);
        buttonMyPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home0Activity.this, PlayListActivity.class);
                intent.putStringArrayListExtra("playlistItems", retrievePlaylistItems());
                startActivity(intent);
            }
        });
    }

    // Method to insert a playlist item into the database
    private void insertPlaylistItem(String playlistItem) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String videoId = getYouTubeVideoId(playlistItem);
        values.put(PlaylistDatabaseHelper.COLUMN_VIDEO_ID, videoId);
        values.put(PlaylistDatabaseHelper.COLUMN_YOUTUBE_LINK, playlistItem);
        db.insert(PlaylistDatabaseHelper.TABLE_PLAYLIST, null, values);
        db.close();
    }

    // Method to retrieve playlist items from the database
    private ArrayList<String> retrievePlaylistItems() {
        ArrayList<String> playlistItems = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(PlaylistDatabaseHelper.TABLE_PLAYLIST, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                //String youtubeLink = cursor.getString(cursor.getColumnIndex(PlaylistDatabaseHelper.COLUMN_YOUTUBE_LINK));
                //playlistItems.add(youtubeLink);
            }
            cursor.close();
        }
        db.close();
        return playlistItems;
    }


    // Method to extract the YouTube video ID from a YouTube link
    private String getYouTubeVideoId(String youtubeLink) {
        String videoId = null;
        if (youtubeLink != null && youtubeLink.trim().length() > 0) {
            String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
            String[] split = youtubeLink.split(pattern);
            if (split.length > 0 && split[0] != null) {
                videoId = split[0];
            }
        }
        return videoId;
    }

    // Method to display a toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
