package net.pmkjun.mineplanetplus.fishhelper.util;

import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;

public class ComboCatcher {
    private long lastCaughtFishTime;
    private int comboCount = 0;
    private int maxcomboCount;
    //Minecraft mc = Minecraft.getInstance();
    FishHelperClient client = FishHelperClient.getInstance();

    public ComboCatcher(){
        this.maxcomboCount = client.data.maxComboCount;
    }

    public void resetComboCount(){
        this.comboCount = 0;
    }
    public void addComboCount(){
        if(this.comboCount < this.maxcomboCount || maxcomboCount == 0) this.comboCount++;
        lastCaughtFishTime = System.currentTimeMillis();
        //mc.player.displayClientMessage(Component.literal("콤보 추가!"),false);
    }
    public void calcComboCount(){
        if(System.currentTimeMillis() - lastCaughtFishTime > 1000*60 && comboCount > 0){
            //mc.player.displayClientMessage(Component.literal("콤보 초기화!"),false);
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
        if(comboCount > count) comboCount = count;
        this.client.data.maxComboCount = count;
        this.client.configManage.save();
    }
}
