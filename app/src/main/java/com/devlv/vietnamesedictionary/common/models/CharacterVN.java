package com.devlv.vietnamesedictionary.common.models;

public class CharacterVN {
    private int id;
    private String character;

    public CharacterVN(int id, String character) {
        this.id = id;
        this.character = character;
    }

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
}