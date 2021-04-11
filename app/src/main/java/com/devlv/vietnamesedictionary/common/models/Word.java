package com.devlv.vietnamesedictionary.common.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Word implements Parcelable {
    private static final String TAG = "Word";
    private int id;
    private String title;
    private String content;
    private String description;
    private String photo;
    private int userCreate;
    private int idChar;

    public Word(int id, String title, String content, String photo, int userCreate, int idChar) {
        this.id = id;
        this.title = title;
        this.content = content;
        if (content != null && content.length() > 0)
            this.description = content.substring(0, Math.min(50, content.length())) + "...";
        this.photo = photo;
        this.userCreate = userCreate;
        this.idChar = idChar;
    }

    protected Word(Parcel in) {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
        description = in.readString();
        photo = in.readString();
        idChar = in.readInt();
    }

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

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

    public int getUserCreate() {
        return userCreate;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                ", userCreate=" + userCreate +
                ", idChar=" + idChar +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(description);
        dest.writeString(photo);
        dest.writeInt(idChar);
    }
}