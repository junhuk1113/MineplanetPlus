package net.pmkjun.mineplanetplus.fishhelper.util;

public class ConvertCooldown {
    private ConvertCooldown(){

    }
    public static int asMinute(int level){
        return 60-(level*2);
    }
    public static int asLevel(int minute){
        return (60-minute)/2;
    }
}
