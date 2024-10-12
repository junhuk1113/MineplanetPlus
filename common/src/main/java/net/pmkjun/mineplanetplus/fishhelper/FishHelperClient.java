package net.pmkjun.mineplanetplus.fishhelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.pmkjun.mineplanetplus.fishhelper.config.ConfigManage;
import net.pmkjun.mineplanetplus.fishhelper.file.Data;
import net.pmkjun.mineplanetplus.fishhelper.gui.FishCounterGui;
import net.pmkjun.mineplanetplus.fishhelper.gui.totemCooltimeGui;
import net.pmkjun.mineplanetplus.fishhelper.util.Timer;

public class FishHelperClient {
    private final Minecraft mc;
    private static FishHelperClient instance;
    public Data data;
    public ConfigManage configManage;

    private final totemCooltimeGui totemcooltimeGui;
    private final FishCounterGui fishCounterGui;
    private final Timer timer = new Timer();
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

    public static  FishHelperClient getInstance(){
        return instance;
    }
}