package net.pmkjun.mineplanetplus.fishhelper.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ComboCatcher {
    private long lastCaughtFishTime;
    private int comboCount = 0;
    private int maxcomboCount = 30;
    Minecraft mc = Minecraft.getInstance();

    public void resetComboCount(){
        this.comboCount = 0;
    }
    public void addComboCount(){
        if(this.comboCount < this.maxcomboCount) this.comboCount++;
        lastCaughtFishTime = System.currentTimeMillis();
        mc.player.displayClientMessage(Component.literal("콤보 추가!"),false);
    }
    public void calcComboCount(){
        if(System.currentTimeMillis() - lastCaughtFishTime > 1000*60 && comboCount > 0){
            mc.player.displayClientMessage(Component.literal("콤보 초기화!"),false);
            resetComboCount();
        }
    }
    public int getComboCount(){
        return this.comboCount;
    }
    public int getMaxComboCount(){
        return this.maxcomboCount;
    }

    public int getTimeUntilStackReset(){
        return Math.max(60 - (int) ((System.currentTimeMillis() - lastCaughtFishTime) / 1000), 0);
    }
    public void setMaxComboCount(int count){
        this.maxcomboCount = count;
    }
}
