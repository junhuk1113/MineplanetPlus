package net.pmkjun.mineplanetplus.fishhelper.util;

import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;

public class Earning {
    private static final FishHelperClient client = FishHelperClient.getInstance();
    public static double getMoney(){ // 6 > 11 > 15 > 19 > 25
        double sum = 0;
        int cost[] = {40,270,900,2700};
        double coefficient[] = {1, 1.06, 1.11, 1.15, 1.19, 1.25};

        for(int i = 0 ; i < 4 ; i++)
            sum += client.data.fish_Count[i] * cost[i] * coefficient[client.data.valueSolarRage];

        return sum;
    }
    public static double getEntropy(){// 8%>11%>15%>20%>30%>45%>60%>70%
        double sum = 0;
        int cost[] = {40,120,200,500};
        double coefficient[] = {1, 1.08, 1.11, 1.15, 1.20, 1.30, 1.45, 1.60, 1.70};

        for(int i = 0 ; i < 4 ; i++)
            sum += client.data.fish_Count[i] * cost[i] * coefficient[client.data.valuePrecisionCutting];

        return sum;
    }
}
