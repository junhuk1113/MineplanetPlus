package net.pmkjun.mineplanetplus.fishhelper.file;

import java.io.Serializable;

import net.pmkjun.mineplanetplus.fishhelper.util.FishCounterMode;
public class Data implements Serializable{
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

    public int Timer_xpos = 1000;
    public int Timer_ypos = 1;
    public int Counter_xpos = 1000;
    public int Counter_ypos = 100;
    public int[] fish_Count = {0,0,0,0,0,0};
}
