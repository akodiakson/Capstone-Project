package com.akodiakson.pitchcounter.data;

import android.provider.CalendarContract;

/**
 * Created by ace0808 on 4/19/2016.
 */
public final class GameContract {

    public static final String TABLE_NAME = "Games";

    public static final String _ID = "_id";
    public static final String DATE = "date";
    public static final String PITCHES = "pitches";
    public static final String STRIKES = "strikes";
    public static final String BALLS = "balls";
    public static final String HITS = "hits";
    public static final String STRIKEOUTS = "strikeouts";
    public static final String WALKS = "walks";

    public static final String[] PROJECTION_ALL_COLUMNS = new String[]{
            _ID, DATE, PITCHES, STRIKES, BALLS, HITS, STRIKEOUTS, WALKS
    };

    public static final String SQL_CREATE = "CREATE TABLE " +
            TABLE_NAME + " " +
            "(" +
                _ID + " INTEGER PRIMARY KEY, " +
                DATE + " TEXT, " +
                PITCHES + " INTEGER, " +
                STRIKES + " INTEGER, " +
                BALLS + " INTEGER, " +
                HITS + " INTEGER, " +
                WALKS + " INTEGER, " +
                STRIKES + " INTEGER " +
            ")";
}
