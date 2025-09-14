package net.pmkjun.mineplanetplus.fishhelper.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;

public class ComboCatcherGui {
    private final Minecraft mc;
    private final FishHelperClient client;

    private static final ResourceLocation FISHING_ROD_ICON = ResourceLocation.withDefaultNamespace("textures/item/fishing_rod.png");
    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("pyrofishinghelper","combocounter_background.png");
    private static final ResourceLocation NOTIFY_ICON = ResourceLocation.fromNamespaceAndPath("pyrofishinghelper","notify.png");


    public ComboCatcherGui(){
        this.mc = Minecraft.getInstance();
        this.client = FishHelperClient.getInstance();
    }

    public void renderTick(GuiGraphics guiGraphics){
        client.comboCatcher.calcComboCount();
        int x, y;

        x = mc.getWindow().getGuiScaledWidth()/2;
        y = mc.getWindow().getGuiScaledHeight()/2;

        renderBackground(guiGraphics, x, y);
        renderFishingRod(guiGraphics, FISHING_ROD_ICON, x + 2, y + 1);
        renderComboCount(guiGraphics, x+ 2, y + 1);
        renderResetTime(guiGraphics, x + 2, y + 1);

        if(client.isBiting){
            renderNotifyIcon(guiGraphics, x + 2 + 3, y + 1 + 1);
        }
    }

    public void renderFishingRod(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y){
        PoseStack poseStack = guiGraphics.pose();

        poseStack.pushPose();
        poseStack.translate(x,y,0.0D);
        poseStack.scale(0.0625F, 0.0625F, 0.0625F);

        RenderSystem.setShaderTexture(0,texture);
        guiGraphics.blit(RenderType::guiTextured, texture, 0, 0, 0, 0, 256, 256, 256, 256);
        poseStack.scale(16.0F, 16.0F, 16.0F);
        poseStack.popPose();
    }


    public void renderComboCount(GuiGraphics guiGraphics, int x, int y){
        PoseStack poseStack = guiGraphics.pose();
        Font font = this.mc.font;

        String combo_text;
        int combo_text_width, reset_text_width;

        reset_text_width = font.width("초기화까지 : ")+8;

        combo_text = client.comboCatcher.getComboCount() + "/" +  client.comboCatcher.getMaxComboCount();
        combo_text_width = font.width(combo_text);

        poseStack.pushPose();
        poseStack.translate(x + reset_text_width, y + 4, 0.0D);
        poseStack.scale(1f/1.1f, 1f/1.1f, 1f/1.1f);
        guiGraphics.drawString(font, Component.literal(combo_text), -combo_text_width, 0, 0xFFFFFF);
        poseStack.scale(1.1f, 1.1f, 1.1f);

        poseStack.popPose();
    }

    public void renderResetTime(GuiGraphics guiGraphics, int x, int y){
        PoseStack poseStack = guiGraphics.pose();
        Font font = this.mc.font;
        int text_width, reset_time_text_width;

        text_width = font.width("초기화까지 : ");

        poseStack.pushPose();
        poseStack.translate(x, y+16+3, 0.0D);
        poseStack.scale(1f/1.1f, 1f/1.1f, 1f/1.1f);

        guiGraphics.drawString(font, Component.literal("초기화까지 : "), 0, 0, 0xFFFFFF);
        poseStack.scale(1.1f, 1.1f, 1.1f);

        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(x + text_width + 8, y+16+3, 0.0D);
        poseStack.scale(1f/1.1f, 1f/1.1f, 1f/1.1f);

        guiGraphics.drawString(font, Component.literal(""+client.comboCatcher.getTimeUntilStackReset()), -font.width(""+client.comboCatcher.getTimeUntilStackReset()), 0, 0xFFFFFF);
        poseStack.scale(1.1f, 1.1f, 1.1f);

        poseStack.popPose();

    }

    public void renderBackground(GuiGraphics guiGraphics, int x, int y){
        RenderSystem.enableBlend(); // 블렌딩 활성화
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(RenderType::guiTextured ,BACKGROUND, x, y, 0, 0, 60, 30, 60, 30);
        RenderSystem.disableBlend();
    }

    public void renderNotifyIcon(GuiGraphics guiGraphics, int x, int y){
        guiGraphics.blit(RenderType::guiTextured ,NOTIFY_ICON, x, y, 0, 0, 10, 14, 10, 14);
    }
}