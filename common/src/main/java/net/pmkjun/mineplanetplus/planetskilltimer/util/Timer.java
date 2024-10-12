package net.pmkjun.mineplanetplus.planetskilltimer.util;

public class Timer {
    private long currentTime;

    public void updateTime() {
        this.currentTime = System.currentTimeMillis();
    }

    public long getDifference(long time) {
        return this.currentTime - time;
    }

    public long getCurrentTime() {
        return this.currentTime;
    }
}
