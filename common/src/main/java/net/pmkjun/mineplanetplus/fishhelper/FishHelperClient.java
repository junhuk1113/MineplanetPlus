package net.pmkjun.mineplanetplus.fishhelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.pmkjun.mineplanetplus.fishhelper.config.ConfigManage;
import net.pmkjun.mineplanetplus.fishhelper.file.Data;
import net.pmkjun.mineplanetplus.fishhelper.file.TotemData;
import net.pmkjun.mineplanetplus.fishhelper.gui.FishCounterGui;
import net.pmkjun.mineplanetplus.fishhelper.gui.totemCooltimeGui;
import net.pmkjun.mineplanetplus.fishhelper.util.DeliveryQuest;
import net.pmkjun.mineplanetplus.fishhelper.util.Timer;

import java.util.ArrayList;

public class FishHelperClient {
    private final Minecraft mc;
    private static FishHelperClient instance;
    public Data data;
    public ConfigManage configManage;

    private final totemCooltimeGui totemcooltimeGui;
    private final FishCounterGui fishCounterGui;
    private final Timer timer = new Timer();

    public final DeliveryQuest deliveryQuests;
    private final ArrayList<TotemData> emptyList = new ArrayList<>();
    private ArrayList<TotemData> remoteTotemDataList = emptyList;

    private int totem_X, totem_Z;
    private int totemRange;
    private String currentWorld;

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
        deliveryQuests = new DeliveryQuest();
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

    public void setTotempos(int x, int z){
        this.totem_X = x;
        this.totem_Z = z;
    }
    public int getTotemposX(){
        return this.totem_X;
    }
    public int getTotemposZ(){
        return this.totem_Z;
    }
    public void setTotemRange(int range){
        this.totemRange = range;
    }
    public int getTotemRange(){
        return this.totemRange;
    }
    public void setCurrentWorld(String world){
        this.currentWorld = world.trim();
    }
    public String getCurrentWorld(){
        return this.currentWorld;
    }

    public void updateFishCounter(int fish_type){}

    public void setRemoteTotemDataList(ArrayList<TotemData> remoteTotemDataList){
        if(remoteTotemDataList == null){
            this.remoteTotemDataList = emptyList;
            return;
        }
        for(TotemData totemData : remoteTotemDataList){
            if(totemData.username.equals(this.mc.player.getName().getString())){
                remoteTotemDataList.remove(totemData);
                break;
            }
        }

        this.remoteTotemDataList = remoteTotemDataList;
    }
    public ArrayList<TotemData> getRemoteTotemDataList(){
        return this.remoteTotemDataList;
    }

    public TotemData getPrimaryRemoteTotemData(){
        int i = 0, maxTimeIndex, minTimeIndex = 0;
        long maxTime = 0, minTime = 0, time;
        TotemData totemData = null;

        if(!this.remoteTotemDataList.isEmpty()){
            maxTimeIndex = -1;
            for(i = 0; i < remoteTotemDataList.size(); i++){
                totemData = remoteTotemDataList.get(i);
                if(remoteTotemDataList.get(i).isActive()){
                    time = totemData.lastTotemtime + (long) totemData.valueTotemActiveTime * 60 * 1000;
                    if(time > maxTime){
                        maxTime = time;
                        maxTimeIndex = i;
                    }
                }
            }
            if(maxTimeIndex != -1){
                return remoteTotemDataList.get(maxTimeIndex);
            }
            for(i = 0; i < remoteTotemDataList.size(); i++){
                time = totemData.lastTotemCooldownTime + (long) totemData.valueTotemCooldown * 60 * 1000;
                if(i == 0){
                    minTime = time;
                    minTimeIndex = i;
                    continue;
                }
                if(time < minTime){
                    minTime = time;
                    minTimeIndex = i;
                }
            }
            return remoteTotemDataList.get(minTimeIndex);
        }

        return null;
    }

    public static FishHelperClient getInstance(){
        return instance;
    }
}