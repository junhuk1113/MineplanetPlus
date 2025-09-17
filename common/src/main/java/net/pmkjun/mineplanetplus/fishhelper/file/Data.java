package net.pmkjun.mineplanetplus.fishhelper.file;

import net.pmkjun.mineplanetplus.fishhelper.util.ComboCounterMode;
import net.pmkjun.mineplanetplus.fishhelper.util.FishCounterMode;
import net.pmkjun.mineplanetplus.fishhelper.util.ShareMode;

public class Data{
    public String userName;

    public boolean toggleTotemtime = true;

    public boolean toggleTotemtimeText = true;

    public boolean isTotemCooldown = false;
    public int valueTotemCooldown = 60;
    public int valueTotemActivetime = 5;
    public long valueCooldownReduction = 0;
    public int currentValueTotemCooldown = 60;
    public int currentValueTotemActivetime = 5;
    public long lastTotemTime=0;
    public long lastTotemCooldownTime = 0;
    public int valueSolarRage = 0; // 6 > 11 > 15 > 19 > 25
    public int valuePrecisionCutting = 0; // 8%>11%>15%>20%>30%>45%>60%>70%

    public boolean toggleCustomTexture = true;
    public boolean toggleMuteotherfishingbobber = false;
    public boolean toggleChattinglog = false;
    public boolean toggleFishCounter = false;
    public boolean toggleGradeProbability = false;
    public boolean toggleEarningCalculator = false;
    public boolean toggleLog = false;
    public FishCounterMode toggleCounterMode = FishCounterMode.PERCENTAGE;
    public boolean toggleDeliveryHelper = true;
    public ShareMode toggleShareTotemDataMode = ShareMode.ON;
    public boolean toggleViewRemoteTotemData = true;

    public int Timer_xpos = 1000;
    public int Timer_ypos = 1;
    public int Counter_xpos = 1000;
    public int Counter_ypos = 100;
    public int[] fish_Count = {0,0,0,0,0,0};

    public int maxComboCount = 0;
    public String shareTotemPercentage;

    public ComboCounterMode toggleComboCounter = ComboCounterMode.OFF;
    public int ComboCounter_xpos = 1000;
    public int ComboCounter_ypos = 800;

    public boolean isMythicalWaterActive = false;
    public boolean isExpBoosterActive = false;
    public boolean isOverHotspotActive = false;
    public boolean isTreasureHunterActive = false;
    public boolean isEntropyHoarder = false;
    public String totemSlotString;
}
