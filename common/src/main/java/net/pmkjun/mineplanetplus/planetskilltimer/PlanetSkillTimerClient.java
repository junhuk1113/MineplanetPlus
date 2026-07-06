package net.pmkjun.mineplanetplus.planetskilltimer;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.pmkjun.mineplanetplus.planetskilltimer.config.ConfigManage;
import net.pmkjun.mineplanetplus.planetskilltimer.file.Data;
import net.pmkjun.mineplanetplus.planetskilltimer.gui.SkillTimerGui;
import net.pmkjun.mineplanetplus.planetskilltimer.util.Timer;

public class PlanetSkillTimerClient {
    private static PlanetSkillTimerClient instance;
    public Data data;
    public ConfigManage configManage;

    private final SkillTimerGui skillTimerGui;
    private final Timer timer = new Timer();

    public PlanetSkillTimerClient() {
        instance = this;
        this.configManage = new ConfigManage();
        this.data = this.configManage.load();
        if(this.data == null) {
            this.data = new Data();
            this.configManage.save();
        }
        this.skillTimerGui = new SkillTimerGui();
    }

    public void init() {

    }

    // 26.1: renderEvent -> extractRenderEvent
    public void extractRenderEvent(GuiGraphicsExtractor guiGraphics) {
        this.skillTimerGui.extractRenderState(guiGraphics, this.timer);
        this.timer.updateTime();
    }

    public void updateLastSkilltime(int skilltype) {
        this.data.lastSkillTime[skilltype] = this.timer.getCurrentTime();
        this.configManage.save();
    }

    public static PlanetSkillTimerClient getInstance() {
        return instance;
    }
}