package net.pmkjun.mineplanetplus.dungeonhelper;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.pmkjun.mineplanetplus.dungeonhelper.file.Data;
import net.pmkjun.mineplanetplus.dungeonhelper.file.Settings;
import net.pmkjun.mineplanetplus.dungeonhelper.file.SkillUIChars;
import net.pmkjun.mineplanetplus.dungeonhelper.gui.DungeonCooltimeGui;
import net.pmkjun.mineplanetplus.dungeonhelper.gui.SkillCooltimeGui;
import net.pmkjun.mineplanetplus.dungeonhelper.gui.screen.DungeonHelperSettingsScreen;
import net.pmkjun.mineplanetplus.input.IKeyMappings;
import net.pmkjun.mineplanetplus.dungeonhelper.util.Timer;

public class DungeonHelperClient {

    private static DungeonHelperClient instance;

    private final IKeyMappings keyMappings;

    public Settings settings;
    public Data data;

    private final DungeonCooltimeGui dungeonCooltimeGui;
    private final SkillCooltimeGui skillCooltimeGui;

    private final DungeonHelperSettingsScreen settingsScreen;

    private final Timer timer = new Timer();

    public boolean ishereDungeon = false;
    public boolean isSlot1Manarunout = false, isSlot3Manarunout = false, isSlot4Manarunout = false, isSlot5Manarunout = false;
    public SkillUIChars skillUIChars = new SkillUIChars();

    public DungeonHelperClient(IKeyMappings keyMappings) {
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
    /*public void updateLastComboSkillTime() {
        skillCooltimeGui.updateLastComboSkillTime(timer);
    }
    public void updateLastLV30SkillTime() {
        skillCooltimeGui.updateLastLV30SkillTime(timer);
    }
    public void updateLastLV30SkillTime(float plustime){
        long plustimeLong = (long)(plustime * 1000);
        skillCooltimeGui.updateLastLV30SkillTime(timer, plustimeLong);
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
    }*/

    public void updateLeftComboSkillTime(float cooldown){
        skillCooltimeGui.updateLeftComboSkillTime(cooldown);
    }
    public void updateLeftLV30SkillTime(float cooldown){
        skillCooltimeGui.updateLeftLV30SkillTime(cooldown);
    }
    public void updateLeftLV40SkillTime(float cooldown){
        skillCooltimeGui.updateLeftLV40SkillTime(cooldown);
    }
    public void updateLeftUltimateTime(float cooldown){
        skillCooltimeGui.updateLeftUltimateTime(cooldown);
    }

    public DungeonHelperSettingsScreen getSettingsScreen() {
        return settingsScreen;
    }
    public void delayLastSkilltime(long delay){
        skillCooltimeGui.delayLastSkilltime(delay);
    }

    public static DungeonHelperClient getInstance(){
        return instance;
    }
}