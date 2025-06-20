package net.pmkjun.mineplanetplus.fishhelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.pmkjun.mineplanetplus.fishhelper.config.ConfigManage;
import net.pmkjun.mineplanetplus.fishhelper.file.Data;
import net.pmkjun.mineplanetplus.fishhelper.gui.FishCounterGui;
import net.pmkjun.mineplanetplus.fishhelper.gui.totemCooltimeGui;
import net.pmkjun.mineplanetplus.fishhelper.util.DeliveryQuest;
import net.pmkjun.mineplanetplus.fishhelper.util.Timer;

public class FishHelperClient {
    private final Minecraft mc;
    private static FishHelperClient instance;
    public Data data;
    public ConfigManage configManage;

    private final totemCooltimeGui totemcooltimeGui;
    private final FishCounterGui fishCounterGui;
    private final Timer timer = new Timer();

    private DeliveryQuest lastSelectedQuest;

    public FishHelperClient(){
        this.mc = Minecraft.getInstance();
        instance = this;
        this.configManage = new ConfigManage();
        this.data = this.configManage.load();
        if(this.data == null){
            this.data = new Data();
            this.configManage.save();
        }
        this.totemcooltimeGui = new totemCooltimeGui();
        this.fishCounterGui = new FishCounterGui();
    }
    public void init(){

    }
    public void renderEvent(GuiGraphics context) {
        this.totemcooltimeGui.renderTick(context,this.timer);
        this.timer.updateTime();
        this.fishCounterGui.renderTick(context);
    }
    public void updateTotemtime(){
        this.data.lastTotemTime = this.timer.getCurrentTime();
        this.data.lastTotemCooldownTime = this.timer.getCurrentTime()+(long)this.data.valueTotemActivetime * 60 * 1000;
        this.data.currentValueTotemActivetime = this.data.valueTotemActivetime;
        this.data.currentValueTotemCooldown = this.data.valueTotemCooldown;
        this.configManage.save();
    }

    public void resetFishCounter(){
        for(int i = 0; i < this.data.fish_Count.length ; i++){
            this.data.fish_Count[i] = 0;
        }
    }

    public String getUsername(){
        return this.mc.getUser().getName();
    }

    public void updateLastSelectedQuest(DeliveryQuest lastSelectedQuest) {
        if(this.lastSelectedQuest == null) this.lastSelectedQuest = lastSelectedQuest;
        else if(lastSelectedQuest.getQuestTooltip().equals(this.lastSelectedQuest.getQuestTooltip())){
            mc.player.displayClientMessage(Component.literal("퀘스트 선택이 해제되었습니다."), false);
            this.lastSelectedQuest = null;
        }
        else{
            mc.player.displayClientMessage(Component.literal("선택된 퀘스트가 변경되었습니다."), false);
            this.lastSelectedQuest = lastSelectedQuest;
        }
    }

    public DeliveryQuest getLastSelectedQuest() {
        return lastSelectedQuest;
    }

    public static  FishHelperClient getInstance(){
        return instance;
    }
}