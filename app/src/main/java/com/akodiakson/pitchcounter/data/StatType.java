package com.akodiakson.pitchcounter.data;

/**
 * Created by ace0808 on 4/21/2016.
 */
public enum StatType {
    STRIKE(GameContract.STRIKES), BALL(GameContract.BALLS), HIT(GameContract.HITS), WALK(GameContract.WALKS), STRIKEOUT(GameContract.STRIKEOUTS), TOTAL_PITCHES(GameContract.PITCHES);

    private String associatedStatColumn;

    StatType(String associatedStatColumn) {
        this.associatedStatColumn = associatedStatColumn;
    }

    public String getAssociatedStatColumn() {
        return associatedStatColumn;
    }
}
