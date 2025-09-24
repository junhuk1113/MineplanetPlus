package net.pmkjun.mineplanetplus.serverutility;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.pmkjun.mineplanetplus.serverutility.file.Data;
import net.pmkjun.mineplanetplus.serverutility.file.Settings;
import net.pmkjun.mineplanetplus.serverutility.gui.DivingCounterGui;
import net.pmkjun.mineplanetplus.serverutility.gui.ServerUtilityGui;
import net.pmkjun.mineplanetplus.planetskilltimer.util.Timer;

public class ServerUtilityClient {
    private static ServerUtilityClient instance;
    private final Minecraft mc = Minecraft.getInstance();
    public Data data;
    public Settings settings;

    private final ServerUtilityGui gui;
    private final DivingCounterGui divingCounterGui;
    private final Timer timer = new Timer();
    public DivingCounter divingCounter = null;

    public Component money_component, coin_component, credit_component;
    public long money;
    public int coin;
    private String current_ip;
    private boolean isDiving = false;

    public ServerUtilityClient(){
        instance = this;
        this.settings = new Settings();
        this.data = this.settings.load();
        if(this.data == null){
            this.data = new Data();
            this.settings.save();
        }
        this.gui = new ServerUtilityGui();
        this.divingCounterGui = new DivingCounterGui();
    }

    public void renderEvent(GuiGraphics context) {
        this.gui.renderTick(context,this.timer);
        if(isDiving){
            this.divingCounterGui.renderTick(context, this.timer);
        }
        this.timer.updateTime();
    }

    public void updateLastUsedMegaphonetime(){
        this.data.lastUsedTime = this.timer.getCurrentTime();
        this.settings.save();
    }

    public void updateCurrentIP(String ip){
        this.current_ip = ip;
    }

    public boolean isHereMineplanet(){
        if(this.data.toggleForceModEnable) return true;
        try {
            return this.current_ip.equals("mineplanet.kr");
        }
        catch (NullPointerException e){
            return false;
        }
    }

    public boolean isDiving(){
        return this.isDiving;
    }
    public void setDiving(){
        this.isDiving = !this.isDiving;
        if(this.isDiving) {
            divingCounter = new DivingCounter();
            divingCounter.update();
        }
        else{
            mc.player.displayClientMessage(Component.literal("잠수탐사 완료!\n획득한 골드 : "+divingCounter.getEarnedMoney()+"\uE1BE, 획득한 실링 : "
            +divingCounter.getEarnedShilling()+"\uE3B7"),false);
            divingCounter = null;
        }
        mc.player.displayClientMessage(Component.literal("잠수탐사 상태 : " + this.isDiving), false);

    }

    public void setMoneyComponent(Component money_component){
        this.money_component = money_component;
        try{
            String intStr = money_component.getString().replaceAll("[^0-9]", "");
            this.money = Long.parseLong(intStr);
        }
        catch (Exception ignored){
        }
    }
    public void setCoinComponent(Component coin_component){
        this.coin_component = coin_component;
        try{
            String intStr = coin_component.getString().replaceAll("[^0-9]", "");
            this.coin = Integer.parseInt(intStr);
        }
        catch (Exception ignored){
        }
    }

    public void setDivingTargetMoney(long money){
        money = Math.max(0, money);
        data.targetMoney = money;
        settings.save();
        if(isDiving){
            divingCounter.setTargetMoney(money);
        }
    }

    public void setDivingTargetShilling(int coin){
        coin = Math.max(0, coin);
        data.targetShilling = coin;
        settings.save();
        if(isDiving){
            divingCounter.setTargetShilling(coin);
        }
    }

    public static ServerUtilityClient getInstance(){
        return instance;
    }
}
