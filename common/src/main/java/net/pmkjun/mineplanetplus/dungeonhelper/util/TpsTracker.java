package net.pmkjun.mineplanetplus.dungeonhelper.util;

import java.util.Arrays;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;

public class TpsTracker {

    public static TpsTracker INSTANCE = new TpsTracker();

    private final float[] tickRates = new float[20];
    private int nextIndex = 0;
    private long timeLastTimeUpdate = -1;
    private long timeGameJoined;
    private long tickDuration;
    private final DungeonHelperClient client = DungeonHelperClient.getInstance();

    public float serverProvidedTps = -1;

    public <T extends PacketListener> void onHandlePacket(Packet<T> packet, PacketListener packetListener) {
        if (packet instanceof ClientboundSetTimePacket) {
            long now = System.currentTimeMillis();
            float timeElapsed = (float) (now - timeLastTimeUpdate) / 1000.0F;
            tickDuration = now - timeLastTimeUpdate;
            tickRates[nextIndex] = clamp(20.0f / timeElapsed, 0.0f, 20.0f);
            nextIndex = (nextIndex + 1) % tickRates.length;
            timeLastTimeUpdate = now;
            if(tickDuration > 1000 && tickDuration < 100000){
                client.delayLastSkilltime(tickDuration - 1000);
            }
        }
    }

    public void onGameJoined() {
        serverProvidedTps = -1;
        Arrays.fill(tickRates, 0);
        nextIndex = 0;
        timeGameJoined = timeLastTimeUpdate = System.currentTimeMillis();
    }

    public float getTickRate() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return 0;
        if (System.currentTimeMillis() - timeGameJoined < 4000) return 20;

        int numTicks = 0;
        float sumTickRates = 0.0f;
        for (float tickRate : tickRates) {
            if (tickRate > 0) {
                sumTickRates += tickRate;
                numTicks++;
            }
        }
        return sumTickRates / numTicks;
    }

    public long getTickDuration() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return 0;
        //if (System.currentTimeMillis() - timeGameJoined < 4000) return 4000;

        return tickDuration;
    }

    private float clamp(float value, float min, float max) {
        if (value < min) return min;
        return Math.min(value, max);
    }
}