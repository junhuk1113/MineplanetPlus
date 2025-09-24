package net.pmkjun.mineplanetplus.serverutility.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.pmkjun.mineplanetplus.planetskilltimer.util.Timer;
import net.pmkjun.mineplanetplus.serverutility.ServerUtilityClient;

import java.util.ArrayList;

public class DivingCounterGui {
    private final Minecraft mc = Minecraft.getInstance();
    private final ServerUtilityClient client = ServerUtilityClient.getInstance();

    public DivingCounterGui() {
    }

    public void renderTick(GuiGraphics guiGraphics, Timer timer) {
        int x, y;
        x = mc.getWindow().getGuiScaledWidth()/2;
        y = mc.getWindow().getGuiScaledHeight()/2;
        long timeUntilReward;

        if(client.divingCounter.isFirstTime)
            timeUntilReward = 10 * 1000  * 60 - timer.getDifference(client.divingCounter.getLastRewardedTime());
        else
            timeUntilReward = 5 * 1000  * 60 - timer.getDifference(client.divingCounter.getLastRewardedTime());

        timeUntilReward = timeUntilReward < 0 ? 0 : timeUntilReward;

        int minutes = (int) (timeUntilReward / 1000 / 60);
        int seconds = (int) (timeUntilReward / 1000 % 60);

        String timeUntilReward_String = String.format("%02d:%02d", minutes, seconds);

        if(client.data.toggleDivingCounter)
            render(guiGraphics, timeUntilReward_String, timer, x, y);
    }

    private void render(GuiGraphics guiGraphics, String timeUntilReward, Timer timer, int x, int y){
        PoseStack poseStack = guiGraphics.pose();
        Font font = this.mc.font;
        ArrayList<Component> renderList = new ArrayList<>();

        String timeMoneyReward = calcTimeReward(client.divingCounter.getEstimatedMoneyRewardTime(), timer);
        String timeShillingReward = calcTimeReward(client.divingCounter.getEstimatedShillingRewardTime(), timer);

        if(client.divingCounter.isFirstTime) {
            renderList.add(Component.literal("다음 획득까지 약 " + timeUntilReward+" 남음"));
            renderList.add(Component.literal("(첫 잠수보상 획득은 5~10분 소요)"));
            renderList.add(Component.literal(client.divingCounter.getTargetMoney()+"\uE1BE"+"도달까지 약 "+timeMoneyReward+" 남음"));
            renderList.add(Component.literal(client.divingCounter.getTargetShilling()+"\uE3B7"+"도달까지 약 "+timeShillingReward+" 남음"));
        }
        else{
            renderList.add(Component.literal("다음 획득까지 " + timeUntilReward+" 남음"));
            renderList.add(Component.literal(client.divingCounter.getTargetMoney()+"\uE1BE"+"도달까지 "+timeMoneyReward+" 남음"));
            renderList.add(Component.literal(client.divingCounter.getTargetShilling()+"\uE3B7"+"도달까지 "+timeShillingReward+" 남음"));
        }
        renderList.add(Component.literal("현재 획득량 : " + client.divingCounter.getEarnedMoney() +
                "\uE1BE, "+ client.divingCounter.getEarnedShilling()+"\uE3B7"));
        renderList.add(Component.literal(""));
        renderList.add(Component.literal("/잠수탐사골드, /잠수탐사실링 [금액]"));
        renderList.add(Component.literal("명령어로 목표치를 설정하세요."));

        int length = maxFontLength(renderList);
        int height = renderList.size() * 12;

        poseStack.pushPose();

        poseStack.translate(x-(length* 1.2/2), y-(height*1.2/2), 0.0D);
        //poseStack.scale(1f/1.1f, 1f/1.1f, 1f/1.1f);
        poseStack.scale(1.2f, 1.2f, 1.2f);

        for(int line = 0; line < renderList.size(); line++){
            guiGraphics.drawString(font, renderList.get(line), 0, 12*line, 0xFFFFFF);
        }

        //poseStack.scale(1.1f, 1.1f, 1.1f);

        poseStack.popPose();
    }

    private String calcTimeReward(long time, Timer timer){
        long timeUntilReward;

        timeUntilReward = -timer.getDifference(time);

        timeUntilReward = timeUntilReward < 0 ? 0 : timeUntilReward;

        if(timeUntilReward / 1000 / 3600 / 24 > 0){
            int days = (int) (timeUntilReward / 1000 / 3600 / 24);
            int hours = (int) (timeUntilReward / 1000 / 3600) % 24;
            int minutes = (int) (timeUntilReward / 1000 / 60) % 60;
            int seconds = (int) (timeUntilReward / 1000 % 60);

            return String.format("%d일:%02d:%02d:%02d",days, hours, minutes, seconds);
        }

        else if((int) (timeUntilReward / 1000 / 3600)>0) {
            int hours = (int) (timeUntilReward / 1000 / 3600);
            int minutes = (int) (timeUntilReward / 1000 / 60) % 60;
            int seconds = (int) (timeUntilReward / 1000 % 60);

            return String.format("%02d:%02d:%02d",hours, minutes, seconds);
        }
        else{
            int minutes = (int) (timeUntilReward / 1000 / 60);
            int seconds = (int) (timeUntilReward / 1000 % 60);

            return String.format("%02d:%02d", minutes, seconds);
        }

    }

    private int maxFontLength(ArrayList<Component> renderList){
        int max = 0;
        for(Component c : renderList){
            if(max < mc.font.width(c))
                max = mc.font.width(c);
        }
        return max;
    }
}
