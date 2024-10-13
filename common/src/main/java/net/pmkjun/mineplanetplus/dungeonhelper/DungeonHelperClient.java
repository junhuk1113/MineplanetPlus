package net.pmkjun.mineplanetplus.dungeonhelper;

import net.pmkjun.mineplanetplus.dungeonhelper.file.Data;
import net.pmkjun.mineplanetplus.dungeonhelper.file.Settings;
import net.pmkjun.mineplanetplus.dungeonhelper.gui.DungeonCooltimeGui;
import net.pmkjun.mineplanetplus.dungeonhelper.gui.SkillCooltimeGui;
import net.pmkjun.mineplanetplus.dungeonhelper.gui.screen.DungeonHelperSettingsScreen;
import net.pmkjun.mineplanetplus.dungeonhelper.input.IKeyMappings;
import net.pmkjun.mineplanetplus.dungeonhelper.sound.ISoundManager;
import net.pmkjun.mineplanetplus.dungeonhelper.util.Timer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class DungeonHelperClient {

    private static DungeonHelperClient instance;

    private ISoundManager soundManager;
    private IKeyMappings keyMappings;

    public Settings settings;
    public Data data;

    private DungeonCooltimeGui dungeonCooltimeGui;
    private SkillCooltimeGui skillCooltimeGui;

    private DungeonHelperSettingsScreen settingsScreen;

    private Timer timer = new Timer();

    public boolean ishereDungeon = false;

    public DungeonHelperClient(ISoundManager soundManager, IKeyMappings keyMappings) {
        this.soundManager = soundManager;
        this.keyMappings = keyMappings;

        instance = this;
        settings = new Settings();
        data = settings.load();
        if(data == null || data.lastDungeonTime.length != DungeonCooltimeGui.DUNGEON_COUNT) {
            data = new Data();
            settings.save();
        }

        dungeonCooltimeGui = new DungeonCooltimeGui();
        skillCooltimeGui = new SkillCooltimeGui();
        settingsScreen = new DungeonHelperSettingsScreen();
    }

    public void init() {

    }

    public void renderEvent(GuiGraphics guiGraphics, Component title, Component message) {
        dungeonCooltimeGui.renderTick(guiGraphics, title, timer);
        skillCooltimeGui.renderTick(guiGraphics, timer);

        timer.updateTime();
    }
    public void updateLastComboSkillTime() {
        skillCooltimeGui.updateLastComboSkillTime(timer);
    }
    public void updateLastLV40SkillTime(){
        skillCooltimeGui.updateLastLV40SkillTime(timer);
    }
    public void updateLastLV40SkillTime(float plustime){
        long plustimeLong = (long)(plustime * 1000);
        skillCooltimeGui.updateLastLV40SkillTime(timer, plustimeLong);
    }
    public void updateLastUltimateTime() {
        skillCooltimeGui.updateLastUltimateTime(timer);
    }
    public void resetLastComboSkillTime() {
        skillCooltimeGui.resetLastComboSkillTime();
    }

    public DungeonHelperSettingsScreen getSettingsScreen() {
        return settingsScreen;
    }

    public ISoundManager getSoundManager() {
        return soundManager;
    }
    public void delayLastSkilltime(long delay){
        skillCooltimeGui.delayLastSkilltime(delay);
    }

    public static DungeonHelperClient getInstance(){
        return instance;
    }
}