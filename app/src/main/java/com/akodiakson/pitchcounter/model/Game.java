package com.akodiakson.pitchcounter.model;

import java.io.Serializable;

/**
 * Created by ace0808 on 4/19/2016.
 */
public class Game implements Serializable {
    private static final long serialVersionUID = -260700216534172231L;

    private String date; //YYYYMMDD
    private int pitches;
    private int strikes;
    private int balls;
    private int hits;
    private int strikeouts;
    private int walks;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPitches() {
        return pitches;
    }

    public void setPitches(int pitches) {
        this.pitches = pitches;
    }

    public int getStrikes() {
        return strikes;
    }

    public void setStrikes(int strikes) {
        this.strikes = strikes;
    }

    public int getBalls() {
        return balls;
    }

    public void setBalls(int balls) {
        this.balls = balls;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getStrikeouts() {
        return strikeouts;
    }

    public void setStrikeouts(int strikeouts) {
        this.strikeouts = strikeouts;
    }

    public int getWalks() {
        return walks;
    }

    public void setWalks(int walks) {
        this.walks = walks;
    }
}
