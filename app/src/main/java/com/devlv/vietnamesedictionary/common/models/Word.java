package com.devlv.vietnamesedictionary.common.models;

import android.util.Log;

import static android.view.accessibility.AccessibilityEvent.MAX_TEXT_LENGTH;

public class Word {
    private static final String TAG = "Word";
    private int id;
    private String title;
    private String content;
    private String description;
    private String photo;
    private int idChar;

    public Word(int id, String title, String content, String photo, int idChar) {
        this.id = id;
        this.title = title;
        this.content = content;
        if (content != null && content.length() > 0)
            this.description = content.substring(0, Math.min(50, content.length())) + "...";
        this.photo = photo;
        this.idChar = idChar;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPhoto() {
        return photo;
    }

    public int getIdChar() {
        return idChar;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                ", idChar=" + idChar +
                '}';
    }
}