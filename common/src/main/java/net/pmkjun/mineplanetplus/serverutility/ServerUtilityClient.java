package net.pmkjun.mineplanetplus.serverutility;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.pmkjun.mineplanetplus.planetskilltimer.util.Timer;
import net.pmkjun.mineplanetplus.serverutility.file.Data;
import net.pmkjun.mineplanetplus.serverutility.file.Settings;
import net.pmkjun.mineplanetplus.serverutility.gui.ServerUtilityGui;

public class ServerUtilityClient {
    private static ServerUtilityClient instance;
    public Data data;
    public Settings settings;

    private final ServerUtilityGui gui;
    private final Timer timer = new Timer();

    public Component money, coin, credit;
    private String current_ip;

    public ServerUtilityClient() {
        instance = this;
        this.settings = new Settings();
        this.data = this.settings.load();
        if (this.data == null) {
            this.data = new Data();
            this.settings.save();
        }
        this.gui = new ServerUtilityGui();
    }

    // 26.1: renderEvent -> extractRenderEvent
    public void extractRenderEvent(GuiGraphicsExtractor context) {
        this.gui.extractRenderState(context, this.timer);
        this.timer.updateTime();
    }

    public void updateLastUsedMegaphonetime() {
        this.data.lastUsedTime = this.timer.getCurrentTime();
        this.settings.save();
    }

    public void updateCurrentIP(String ip) {
        this.current_ip = ip;
    }

    public boolean isHereMineplanet() {
        if(this.data.toggleForceModEnable) return true;
        try {
            return this.current_ip.equals("mineplanet.kr");
        }
        catch (NullPointerException e) {
            return false;
        }
    }

    public static ServerUtilityClient getInstance() {
        return instance;
    }
}