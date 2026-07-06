package net.pmkjun.mineplanetplus.fishhelper.util;

public class ConvertActivateTime {
    private ConvertActivateTime(){

    }
    public static int asMinute(int level){
        if(level<=20) return 5+level;
        else return 25 + (2 * (level-20));
    }
    public static int asLevel(int minute){
        if(minute<=25) return minute - 5;
        else return (minute-25)/2+20;
    }
}
