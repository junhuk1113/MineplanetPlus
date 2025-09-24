package net.pmkjun.mineplanetplus.serverutility;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class DivingCounter {
    private long currentMoney, targetMoney, earnedMoney = 0, lastRewardedTime = 0,
            estimatedMoneyRewardTime, estimatedShillingRewardTime;
    private int currentShilling, earnedShilling=0, targetShilling;
    public boolean isFirstTime = true;
    private final ServerUtilityClient client = ServerUtilityClient.getInstance();

    public DivingCounter() {
        updateCurrency();
        this.targetMoney = client.data.targetMoney;
        this.targetShilling = client.data.targetShilling;
    }

    public void update() {

        if(lastRewardedTime != 0) {
            /*long time = System.currentTimeMillis() - lastRewardedTime;

            int minutes = (int) (time / 1000 / 60);
            int seconds = (int) (time / 1000 % 60);

            Minecraft.getInstance().player.displayClientMessage(Component.literal("잠수보상지급 소요시간 : " + minutes+":"+seconds),false);*/

            isFirstTime = false;
        }
        lastRewardedTime = System.currentTimeMillis();
        estimateRewardTime();
    }
    public long getLastRewardedTime() {
        return lastRewardedTime;
    }

    public void setTargetMoney(long money) {
        targetMoney = money;
        estimateRewardTime();
    }
    public void setTargetShilling(int shilling) {
        targetShilling = shilling;
        estimateRewardTime();
    }
    public long getTargetMoney() {
        return targetMoney;
    }
    public int getTargetShilling() {
        return targetShilling;
    }


    public void addEarnedMoney(long money) {
        earnedMoney += money;
    }
    public void addEarnedShilling(int shilling) {
        earnedShilling += shilling;
    }
    public long getEarnedMoney() {
        return earnedMoney;
    }
    public int getEarnedShilling() {
        return earnedShilling;
    }

    private void estimateRewardTime() {
        int plus = 0;

        if((targetMoney - (currentMoney+earnedMoney)) % 500 != 0 && (targetMoney - (currentMoney+earnedMoney)) > 0)
            plus++;
        if(isFirstTime && (targetMoney - (currentMoney+earnedMoney)) > 0) plus++;

        estimatedMoneyRewardTime = lastRewardedTime + ((targetMoney - (currentMoney + earnedMoney)) / 500 + plus) * 5 * 60 * 1000;

        plus = 0;

        if((targetShilling - (currentShilling+earnedShilling)) % 4 != 0 && (targetShilling - (currentShilling+earnedShilling)) > 0)
            plus++;
        if(isFirstTime && (targetShilling - (currentShilling+earnedShilling)) > 0) plus++;

        estimatedShillingRewardTime = lastRewardedTime + (long) ((targetShilling - (currentShilling+earnedShilling)) / 4 + plus) * 5 * 60 * 1000;
    }

    public long getEstimatedMoneyRewardTime() {
        return estimatedMoneyRewardTime;
    }
    public long getEstimatedShillingRewardTime() {
        return estimatedShillingRewardTime;
    }

    private void updateCurrency() {
        currentMoney = client.money;
        currentShilling = client.coin;
    }
}
