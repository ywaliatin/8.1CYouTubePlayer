package com.example.a81c;

public class PlaylistItem {
    private String videoId;
    private String youtubeLink;

    public PlaylistItem(String videoId, String youtubeLink) {
        this.videoId = videoId;
        this.youtubeLink = youtubeLink;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }
}

