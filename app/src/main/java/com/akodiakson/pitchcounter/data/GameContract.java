package com.akodiakson.pitchcounter.data;

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

    public static final String NUM_GAMES = "numGames";
    public static final String NUM_GAMES_SELECTION = "count(*) as numGames";

    public static final String[] PROJECTION_ALL_COLUMNS = new String[]{
            _ID, DATE, PITCHES, STRIKES, BALLS, HITS, STRIKEOUTS, WALKS
    };

    public static final String[] PROJECTION_GAME_COUNT_FOR_DATE = new String[]{
            NUM_GAMES_SELECTION
    };

    public static final String SEASON_NUM_PITCHES = "seasonPitches";
    public static final String SEASON_NUM_PITCHES_SELECTION = "sum(" + PITCHES + ") as seasonPitches";

    public static final String SEASON_NUM_BALLS = "seasonBalls";
    public static final String SEASON_NUM_BALLS_SELECTION = "sum(" + BALLS + ") as seasonBalls";

    public static final String SEASON_NUM_STRIKES = "seasonStrikes";
    public static final String SEASON_NUM_STRIKES_SELECTION = "sum(" + STRIKES + ") as seasonStrikes";

    public static final String[] PROJECTION_SUMMARY_CALCULATIONS = new String[]{
            NUM_GAMES_SELECTION, SEASON_NUM_PITCHES_SELECTION, SEASON_NUM_BALLS_SELECTION, SEASON_NUM_STRIKES_SELECTION
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
                STRIKEOUTS + " INTEGER " +
            ")";
}
