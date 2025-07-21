package net.pmkjun.mineplanetplus.dungeonhelper.file;

import net.pmkjun.mineplanetplus.dungeonhelper.gui.DungeonCooltimeGui;
import net.pmkjun.mineplanetplus.dungeonhelper.util.*;

public class Data{

    public boolean toggleDungeonCooltime = true;
    public boolean toggleDungeonCooltimeText = true;
    public boolean toggleDungeonCooltimeFade = true;
    public boolean toggleNormalDungeon = true;
    public boolean toggleChaosDungeon = true;
    public boolean toggleChallengeTower = true;

    public boolean toggleCustomEnchantRender = true;
    public boolean toggleRuneOFFortuneRender = true;
    public boolean toggleRuneArrowEmpty = false;

    public SkillCooltimeState skillCooltimeState = SkillCooltimeState.OFF;
    public boolean toggleCustomSkillGUIPos = false;
    public boolean toggleVanillaLevelView = false;
    public boolean toggleAutoClassDetect = true;
    public DefaultSkillUI toggleDefaultSkillUI = DefaultSkillUI.AUTO;

    public ClassCategory classType = ClassCategory.ASSASSIN;
    public DungeonCategory dungeontype = DungeonCategory.ALL;
    public DungeonCoolAxis coolAxis = DungeonCoolAxis.VERTICAL;
    public int DungeonCooltimeXpos = 0;
    public int DungeonCooltimeYpos = 0;
    public int SkillCooltimeXpos = 0;
    public int SkillCooltimeYpos = 0;

    public long[] lastDungeonTime = new long[DungeonCooltimeGui.DUNGEON_COUNT];

    public float dungeonCooltime_uiScale = 16.0f;
    public float skillColltime_uiScale = 16.0f;
}