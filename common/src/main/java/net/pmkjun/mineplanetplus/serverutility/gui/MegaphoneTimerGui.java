package net.pmkjun.mineplanetplus.serverutility.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.pmkjun.mineplanetplus.serverutility.ServerUtilityClient;
import net.pmkjun.mineplanetplus.planetskilltimer.util.Timeformat;
import net.pmkjun.mineplanetplus.planetskilltimer.util.Timer;

public class MegaphoneTimerGui {
    private final Minecraft mc;
    private final ServerUtilityClient client;

    public int last = 0;
    public int coolend = 0;

    private static final ResourceLocation MEGAPHONE_ICON = ResourceLocation.fromNamespaceAndPath("mineplanetplus", "chat_broadcast.png");
    private static final ResourceLocation MEGAPHONE_ICON_COOLDOWN = ResourceLocation.fromNamespaceAndPath("mineplanetplus", "chat_broadcast_cooldown.png");
        
    private static final ResourceLocation WIDGETS = ResourceLocation.withDefaultNamespace("hud/hotbar_offhand_left");

    public MegaphoneTimerGui(){
        this.mc = Minecraft.getInstance();
        this.client = ServerUtilityClient.getInstance();
    }

    public void renderTick(GuiGraphics guiGraphics, Timer timer){
        long remaining_cooldowntime;
        int cooldowntime = 1200000; //20min

        remaining_cooldowntime = cooldowntime - timer.getDifference(client.data.lastUsedTime);

        if(!this.client.data.toggleMegaphonetimer) return; //스킬타이머를 껏을때 실행x
        if(remaining_cooldowntime > 0)
            render(guiGraphics, MEGAPHONE_ICON_COOLDOWN, remaining_cooldowntime);
        else
            render(guiGraphics, MEGAPHONE_ICON, remaining_cooldowntime);
    }


    private void render(GuiGraphics guiGraphics,ResourceLocation texture, long remaining_cooldowntime) {
        PoseStack poseStack = guiGraphics.pose();

        RenderSystem.enableBlend(); // 블렌딩 활성화
        RenderSystem.defaultBlendFunc();
        guiGraphics.blitSprite(RenderType::guiTextured ,WIDGETS, getXpos(),getYpos()-1, 29, 24);
        RenderSystem.disableBlend();

        poseStack.pushPose();
        poseStack.translate(3+getXpos(),getYpos()+4-1,0.0D);
        poseStack.scale(0.0625F, 0.0625F, 0.0625F);

        RenderSystem.setShaderTexture(0,texture);
        guiGraphics.blit(RenderType::guiTextured, texture, 0, 0, 0, 0, 256, 256, 256, 256);
        poseStack.scale(16.0F, 16.0F, 16.0F);
        poseStack.popPose();
        //System.out.println("남은 스킬 지속시간 : "+ (remaining_activatetime/(double)1000) +"초");
            //System.out.println("남은 스킬 쿨타임 : "+(remaining_cooldowntime/(double)1000)+"초");
        if(remaining_cooldowntime > 0){
            poseStack.pushPose();
            poseStack.translate((getXpos() + 2 + 9), getYpos()+7, 0.0D);
            poseStack.scale(1F/1.1F, 1F/1.1F, 1F/1.1F);
            guiGraphics.drawCenteredString(this.mc.font, Component.literal(Timeformat.getString(remaining_cooldowntime)), 0, 0, ChatFormatting.WHITE.getColor());
            poseStack.popPose();
            if (client.data.toggleAlertSound) {
                if (remaining_cooldowntime / (double) 1000 < 0.1 && remaining_cooldowntime / (double) 1000 > 0.05 && coolend == 0) {
                    this.mc.level.playSound(this.mc.player, this.mc.player.getX(), this.mc.player.getY(), this.mc.player.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1f, 1f);
                    coolend = 1;
                }

                if(remaining_cooldowntime / (double) 1000 < 0.05) {
                    coolend = 0;
                }
            }
        }
    }
    private int getXpos(){
        return (this.mc.getWindow().getGuiScaledWidth()-22) * this.client.data.MegaphonetimerXpos / 1000;
    }
    private int getYpos(){
        return (this.mc.getWindow().getGuiScaledHeight()-22) * this.client.data.MegaphonetimerYpos / 1000;
    }

}
