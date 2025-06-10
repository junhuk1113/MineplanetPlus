package net.pmkjun.mineplanetplus.serverutility;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.pmkjun.mineplanetplus.serverutility.file.Data;
import net.pmkjun.mineplanetplus.serverutility.file.Settings;
import net.pmkjun.mineplanetplus.serverutility.gui.ServerUtilityGui;
import net.pmkjun.mineplanetplus.planetskilltimer.util.Timer;

public class ServerUtilityClient {
    private static ServerUtilityClient instance;
    public Data data;
    public Settings settings;

    private final ServerUtilityGui gui;
    private Timer timer = new Timer();

    public Component money, coin, credit;

    public ServerUtilityClient(){
        instance = this;
        this.settings = new Settings();
        this.data = this.settings.load();
        if(this.data == null){
            this.data = new Data();
            this.settings.save();
        }
        this.gui = new ServerUtilityGui();
    }

    public void renderEvent(GuiGraphics context) {
        this.gui.renderTick(context,this.timer);
        this.timer.updateTime();
    }

    public void updateLastUsedMegaphonetime(){
        this.data.lastUsedTime = this.timer.getCurrentTime();
        this.settings.save();
    }

    public static ServerUtilityClient getInstance(){
        return instance;
    }
}
