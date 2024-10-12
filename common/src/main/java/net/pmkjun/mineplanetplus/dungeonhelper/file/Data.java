package net.pmkjun.mineplanetplus.dungeonhelper.file;

import net.pmkjun.mineplanetplus.dungeonhelper.gui.DungeonCooltimeGui;
import net.pmkjun.mineplanetplus.dungeonhelper.util.ClassCategory;
import net.pmkjun.mineplanetplus.dungeonhelper.util.DungeonCategory;
import net.pmkjun.mineplanetplus.dungeonhelper.util.DungeonCoolAxis;

import java.io.Serializable;

public class Data implements Serializable {

    public boolean toggleDungeonCooltime = true;
    public boolean toggleDungeonCooltimeText = true;
    public boolean toggleDungeonCooltimeFade = true;

    public boolean toggleCustomEnchantRender = true;
    public boolean toggleRuneOFFortuneRender = true;
    public boolean toggleRuneArrowEmpty = false;

    public boolean toggleSkillCooltime = true;
    public boolean toggleCustomSkillGUIPos = false;
    public boolean toggleVanillaLevelView = false;

    public ClassCategory classType = ClassCategory.ASSASSIN;
    public DungeonCategory dungeontype = DungeonCategory.ALL;
    public DungeonCoolAxis coolAxis = DungeonCoolAxis.VERTICAL;
    public int DungeonCooltimeXpos = 0;
    public int DungeonCooltimeYpos = 0;
    public int SkillCooltimeXpos = 0;
    public int SkillCooltimeYpos = 0;

    public long[] lastDungeonTime = new long[DungeonCooltimeGui.DUNGEON_COUNT];
}