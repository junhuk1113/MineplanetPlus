package net.pmkjun.mineplanetplus.dungeonhelper.util;

public class Timer {
    private long currentTime;

    public void updateTime() {
        currentTime = System.currentTimeMillis();
    }

    public float getDifference(long time) {
        return (float)(currentTime - time) / 1000f;
    }

    public long getCurrentTime() {
        return currentTime;
    }
}
