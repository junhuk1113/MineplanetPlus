package net.pmkjun.mineplanetplus.dungeonhelper;

import net.minecraft.client.gui.GuiGraphicsExtractor; // GuiGraphics ➔ GuiGraphicsExtractor 로 수정
import net.minecraft.network.chat.Component;
import net.pmkjun.mineplanetplus.dungeonhelper.file.Data;
import net.pmkjun.mineplanetplus.dungeonhelper.file.Settings;
import net.pmkjun.mineplanetplus.dungeonhelper.file.SkillUIChars;
import net.pmkjun.mineplanetplus.dungeonhelper.gui.DungeonCooltimeGui;
import net.pmkjun.mineplanetplus.dungeonhelper.gui.SkillCooltimeGui;
import net.pmkjun.mineplanetplus.dungeonhelper.gui.screen.DungeonHelperSettingsScreen;
import net.pmkjun.mineplanetplus.dungeonhelper.util.Timer;
import net.pmkjun.mineplanetplus.input.IKeyMappings;

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

    // 26.1 명명 규칙에 맞게 renderEvent ➔ extractRenderEvent 로 변경
    public void extractRenderEvent(GuiGraphicsExtractor guiGraphics, Component title, Component message) {
        // 이전에 수정했던 하위 클래스들의 추출 메서드를 올바르게 호출합니다.
        dungeonCooltimeGui.extractRenderState(guiGraphics, title, timer);
        skillCooltimeGui.extractRenderState(guiGraphics);
        timer.updateTime();
    }

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

    public static DungeonHelperClient getInstance(){
        return instance;
    }
}