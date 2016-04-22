package com.akodiakson.pitchcounter.data;

import android.database.Cursor;

import com.akodiakson.pitchcounter.model.Game;

/**
 * Created by ace0808 on 4/21/2016.
 */
public class GameCursorUtil {
    public static Game buildGame(Cursor data){
        if(data == null || data.getCount() <= 0){
            return null;
        }

        Game game = new Game();
        int dateIndex = data.getColumnIndex(GameContract.DATE);
        String date = data.getString(dateIndex);
        game.setDate(date);

        int strikeIndex = data.getColumnIndex(GameContract.STRIKES);
        int strikes = data.getInt(strikeIndex);
        game.setStrikes(strikes);

        int ballIndex = data.getColumnIndex(GameContract.BALLS);
        int balls = data.getInt(ballIndex);
        game.setBalls(balls);

        int strikeoutsIndex = data.getColumnIndex(GameContract.STRIKEOUTS);
        int strikeouts = data.getInt(strikeoutsIndex);
        game.setStrikeouts(strikeouts);

        int hitsIndex = data.getColumnIndex(GameContract.HITS);
        int hits = data.getInt(hitsIndex);
        game.setHits(hits);

        int walkIndex = data.getColumnIndex(GameContract.WALKS);
        int walks = data.getInt(walkIndex);
        game.setWalks(walks);

        int pitchesIndex = data.getColumnIndex(GameContract.PITCHES);
        int pitches = data.getInt(pitchesIndex);
        game.setPitches(pitches);

        return game;
    }
}
