package net.pmkjun.mineplanetplus.megaphonetimer;

import net.minecraft.client.gui.GuiGraphics;
import net.pmkjun.mineplanetplus.megaphonetimer.file.Data;
import net.pmkjun.mineplanetplus.megaphonetimer.file.Settings;
import net.pmkjun.mineplanetplus.megaphonetimer.gui.MegaphoneTimerGui;
import net.pmkjun.mineplanetplus.planetskilltimer.util.Timer;

public class MegaphoneTimerClient {
    private static MegaphoneTimerClient instance;
    public Data data;
    public Settings settings;

    private final MegaphoneTimerGui gui;
    private Timer timer = new Timer();

    public MegaphoneTimerClient(){
        instance = this;
        this.settings = new Settings();
        this.data = this.settings.load();
        if(this.data == null){
            this.data = new Data();
            this.settings.save();
        }
        this.gui = new MegaphoneTimerGui();
    }

    public void renderEvent(GuiGraphics context) {
        this.gui.renderTick(context,this.timer);
        this.timer.updateTime();
    }

    public void updateLastUsedtime(){
        this.data.lastUsedTime = this.timer.getCurrentTime();
        this.settings.save();
    }

    public static MegaphoneTimerClient getInstance(){
        return instance;
    }
}
