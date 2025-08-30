package net.pmkjun.mineplanetplus.fishhelper.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.file.TotemData;
import net.pmkjun.mineplanetplus.fishhelper.util.Timer;



public class totemCooltimeGui {
    private final Minecraft mc;
    private final FishHelperClient client;

    private static final ResourceLocation TOTEM_ICON = ResourceLocation.fromNamespaceAndPath("pyrofishinghelper","totem.png");
    private static final ResourceLocation TOTEM_SLEEP_ICON = ResourceLocation.fromNamespaceAndPath("pyrofishinghelper","sleepingtotem3.png");
    private static final ResourceLocation TOTEM_ICON_SHARE = ResourceLocation.fromNamespaceAndPath("pyrofishinghelper","totem_share.png");
    private static final ResourceLocation TOTEM_SLEEP_ICON_SHARE = ResourceLocation.fromNamespaceAndPath("pyrofishinghelper","sleepingtotem_share.png");

    public totemCooltimeGui(){
        this.mc = Minecraft.getInstance();
        this.client = FishHelperClient.getInstance();
    }

    public void renderTick(GuiGraphics guiGraphics, Timer timer){
        int activesecond,cooldownsecond;
        boolean isTotemNonActive;

        activesecond = this.client.data.currentValueTotemActivetime * 60 - (int)timer.getDifference(this.client.data.lastTotemTime);
        cooldownsecond = this.client.data.currentValueTotemCooldown * 60 - (int)timer.getDifference(this.client.data.lastTotemCooldownTime);

        if (activesecond < 0){
            activesecond = 0;
        }

        this.client.data.isTotemCooldown = cooldownsecond > 0 && cooldownsecond < this.client.data.valueTotemCooldown * 60;
        isTotemNonActive = System.currentTimeMillis() - this.client.data.lastTotemTime >= (this.client.data.currentValueTotemActivetime) * 1000 * 60L; //자신의 토템이 가동 중인지 판별

        try {
            if (!client.getRemoteTotemDataList().isEmpty() && isTotemNonActive && client.data.toggleViewRemoteTotemData) {
                TotemData totemData;
                int i;
                totemData = client.getPrimaryRemoteTotemData();

                activesecond = totemData.valueTotemActiveTime * 60 - (int) timer.getDifference(totemData.lastTotemtime);
                cooldownsecond = totemData.valueTotemCooldown * 60 - (int) timer.getDifference(totemData.lastTotemCooldownTime);
                if (activesecond < 0){
                    activesecond = 0;
                }

                boolean isTotemCooldown = cooldownsecond > 0 && cooldownsecond < totemData.valueTotemCooldown * 60;
                if (this.client.data.toggleTotemtime && isTotemCooldown) {
                    render(guiGraphics, TOTEM_SLEEP_ICON_SHARE, cooldownsecond);
                } else if (this.client.data.toggleTotemtime) {
                    render(guiGraphics, TOTEM_ICON_SHARE, activesecond);
                }
                if(mc.options.keyPlayerList.isDown()){
                    renderUsername(guiGraphics, totemData.username);
                }
                return;
            }
        }
        catch(NullPointerException ignored){
        }
        if(this.client.data.toggleTotemtime&&this.client.data.isTotemCooldown){
            render(guiGraphics, TOTEM_SLEEP_ICON, cooldownsecond);
        }
        else if(this.client.data.toggleTotemtime){
            render(guiGraphics, TOTEM_ICON, activesecond);
        }
    }

    private void render(GuiGraphics guiGraphics,ResourceLocation texture, int second){
        PoseStack poseStack = guiGraphics.pose();
        int Timer_xpos, Timer_ypos;
        Timer_xpos = getXpos();
        Timer_ypos = getYpos();

        poseStack.pushPose();
        poseStack.translate(Timer_xpos,Timer_ypos,0.0D);
        poseStack.scale(0.0625F, 0.0625F, 0.0625F);

        RenderSystem.setShaderTexture(0,texture);
        guiGraphics.blit(RenderType::guiTextured, texture, 0, 0, 0, 0, 256, 256, 256, 256);
        poseStack.scale(16.0F, 16.0F, 16.0F);
        poseStack.popPose();

        if (this.client.data.toggleTotemtimeText) {
            Font font = this.mc.font;
            int minute = second / 60;
            second -= minute * 60;
            poseStack.pushPose();
            poseStack.translate((Timer_xpos + 16 + 2), Timer_ypos+4, 0.0D);
            poseStack.scale(1F/1.1F, 1F/1.1F, 1F/1.1F);
            guiGraphics.drawString(font, Component.literal(String.format("%02d:%02d", minute, second)), 0, 0, 16777215);
            poseStack.scale(1.1F, 1.1F, 1.1F);

            poseStack.popPose();
        }
    }

    private void renderUsername(GuiGraphics guiGraphics,String username){
        PoseStack poseStack = guiGraphics.pose();
        Font font = this.mc.font;
        int Timer_xpos, Timer_ypos;
        Timer_xpos = getXpos();
        Timer_ypos = getYpos();
        int usernameStringWidth = 0;
        int timerStringWidth = 0;

        if (client.data.Timer_xpos > 500) {
            usernameStringWidth = font.width(username);
            timerStringWidth = font.width("00:00") + 16;
        }

        poseStack.pushPose();
        poseStack.translate((Timer_xpos) + timerStringWidth, Timer_ypos + 16 + 2, 0.0D);
        poseStack.scale(1F/1.1F, 1F/1.1F, 1F/1.1F);
        guiGraphics.drawString(font, Component.literal(username), -usernameStringWidth, 0, 16777215);
        poseStack.scale(1.1F, 1.1F, 1.1F);

        poseStack.popPose();
    }
    private int getXpos(){
        return 2 + (this.mc.getWindow().getGuiScaledWidth()-43-2) * this.client.data.Timer_xpos / 1000;
    }
    private int getYpos(){
        return 2 + (this.mc.getWindow().getGuiScaledHeight()-18-2) * this.client.data.Timer_ypos / 1000;
    }

}