package com.akodiakson.pitchcounter.model;

/**
 * Created by ace0808 on 4/22/2016.
 */
public class SeasonStatsTO {

    private int totalBalls;
    private int totalStrikes;
    private int totalHits;
    private int totalStrikeouts;
    private int totalWalks;
    private int totalGames;

    public int getTotalPitches() {
        return this.totalBalls + this.totalStrikes;
    }

    public int getTotalBalls() {
        return totalBalls;
    }

    public void addToTotalBalls(int totalBalls) {
        this.totalBalls += totalBalls;
    }

    public int getTotalStrikes() {
        return totalStrikes;
    }

    public void addToTotalStrikes(int totalStrikes) {
        this.totalStrikes += totalStrikes;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public void addToTotalHits(int totalHits) {
        this.totalHits += totalHits;
    }

    public int getTotalStrikeouts() {
        return totalStrikeouts;
    }

    public void addToTotalStrikeouts(int totalStrikeouts) {
        this.totalStrikeouts = totalStrikeouts;
    }

    public int getTotalWalks() {
        return totalWalks;
    }

    public void addToTotalWalks(int totalWalks) {
        this.totalWalks += totalWalks;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames += totalGames;
    }

    public float calculateAveragePitchesPerGame(){
        if(this.totalGames == 0){
            return 0;
        }
        return (float)this.getTotalPitches()/(float)this.totalGames;
    }

    public float calculatePercentBalls(){
        if(this.getTotalPitches() == 0){
            return 0;
        }
        return (float)this.getTotalBalls()/(float)this.getTotalPitches();
    }

    public float calculatePercentStrikes(){
        if(this.getTotalPitches() == 0){
            return 0;
        }
        return (float)this.totalStrikes/(float)this.getTotalPitches();
    }


    public void addGameStats(Game game) {
        addToTotalBalls(game.getBalls());
        addToTotalStrikes(game.getStrikes());
        addToTotalHits(game.getHits());
        addToTotalStrikeouts(game.getStrikeouts());
        addToTotalWalks(game.getWalks());
        setTotalGames(1);
    }

    @Override
    public String toString() {
        return "SeasonStatsTO{" +
                "totalPitches=" + getTotalPitches() +
                ", totalBalls=" + totalBalls +
                ", totalStrikes=" + totalStrikes +
                ", totalHits=" + totalHits +
                ", totalStrikeouts=" + totalStrikeouts +
                ", totalWalks=" + totalWalks +
                ", totalGames=" + totalGames +
                '}';
    }
}
