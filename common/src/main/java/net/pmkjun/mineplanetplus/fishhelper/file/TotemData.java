package net.pmkjun.mineplanetplus.fishhelper.file;

public class TotemData {
    public String username ;
    public int valueTotemCooldown ;
    public int valueTotemActiveTime ;
    public int valueTotemRange ;
    public long lastTotemtime ;
    public int totem_X ;
    public int totem_Z ;
    public String totemWorld;

    public TotemData(String username, int valueTotemCooldown, int valueTotemActiveTime, int valueTotemRange, long lastTotemtime, int totem_X, int totem_Z, String totemWorld){
        this.username = username;
        this.valueTotemCooldown = valueTotemCooldown;
        this.valueTotemActiveTime = valueTotemActiveTime;
        this.valueTotemRange = valueTotemRange;
        this.lastTotemtime = lastTotemtime;
        this.totem_X = totem_X;
        this.totem_Z = totem_Z;
        this.totemWorld = totemWorld;
    }
}