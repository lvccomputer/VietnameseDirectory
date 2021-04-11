package com.devlv.vietnamesedictionary.common.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CharacterVN implements Parcelable {
    private int id;
    private String character;

    public CharacterVN(int id, String character) {
        this.id = id;
        this.character = character;
    }

    protected CharacterVN(Parcel in) {
        id = in.readInt();
        character = in.readString();
    }

    public static final Creator<CharacterVN> CREATOR = new Creator<CharacterVN>() {
        @Override
        public CharacterVN createFromParcel(Parcel in) {
            return new CharacterVN(in);
        }

        @Override
        public CharacterVN[] newArray(int size) {
            return new CharacterVN[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getCharacter() {
        return character;
    }

    @Override
    public String toString() {
        return "CharacterVN{" +
                "id=" + id +
                ", character='" + character + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(character);
    }
}