package com.devlv.vietnamesedictionary.common.db;

public class DBTable {
    public static class VN {
        public static final String VN_TABLE_NAME = "Vietnam";
        public static final String ID = "id";
        public static final String CHARACTER = "character";
    }

    public static class MEANING {
        public static final String MEANING_TABLE_NAME = "MEANING";
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String CONTENT = "content";
        public static final String PHOTO = "photo";
        public static final String USER_CREATE = "userCreate";
        public static final String VN_CHART = "vnChar";
    }
}