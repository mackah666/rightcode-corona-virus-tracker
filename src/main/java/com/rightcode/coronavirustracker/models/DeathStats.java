package com.rightcode.coronavirustracker.models;

public class DeathStats {
    public int getLatestTotalConfirmed() {
        return latestTotalConfirmed;
    }

    public void setLatestTotalConfirmed(int latestTotalConfirmed) {
        this.latestTotalConfirmed = latestTotalConfirmed;
    }

    private int latestTotalConfirmed;

    @Override
    public String toString() {
        return "DeathStats{" +
                "latestTotalConfirmed=" + latestTotalConfirmed +
                ", previousTotalConfirmed=" + previousTotalConfirmed +
                '}';
    }

    public int getPreviousTotalConfirmed() {
        return previousTotalConfirmed;
    }

    public void setPreviousTotalConfirmed(int previousTotalConfirmed) {
        this.previousTotalConfirmed = previousTotalConfirmed;
    }

    private int previousTotalConfirmed;
}
