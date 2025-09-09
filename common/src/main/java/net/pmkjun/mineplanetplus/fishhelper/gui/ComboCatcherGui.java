package net.pmkjun.mineplanetplus.fishhelper.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;

public class ComboCatcherGui {
    private final Minecraft mc;
    private final FishHelperClient client;

    public ComboCatcherGui(){
        this.mc = Minecraft.getInstance();
        this.client = FishHelperClient.getInstance();
    }

    public void renderTick(GuiGraphics context){
        client.comboCatcher.calcComboCount();

        PoseStack poseStack = context.pose();
        Font font = this.mc.font;
        int x, y;

        String combo_text = "";

        combo_text = client.comboCatcher.getComboCount() + "/" +  client.comboCatcher.getMaxComboCount() +" 초기화까지 남은시간 : " + client.comboCatcher.getTimeUntilStackReset();

        x = mc.getWindow().getGuiScaledWidth()/2;
        y = mc.getWindow().getGuiScaledHeight()/2;



        poseStack.pushPose();
        poseStack.translate(x, y, 0.0D);
        poseStack.scale(1f/1.1f, 1f/1.1f, 1f/1.1f);
        context.drawString(font, Component.literal(combo_text), 0, 0, 0xFFFFFF);
        poseStack.scale(1.1f, 1.1f, 1.1f);

        poseStack.popPose();
    }
}
