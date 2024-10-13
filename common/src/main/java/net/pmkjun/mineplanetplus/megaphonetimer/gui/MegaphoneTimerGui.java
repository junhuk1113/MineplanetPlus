package net.pmkjun.mineplanetplus.megaphonetimer.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.pmkjun.mineplanetplus.megaphonetimer.MegaphoneTimerClient;
import net.pmkjun.mineplanetplus.planetskilltimer.util.Timeformat;
import net.pmkjun.mineplanetplus.planetskilltimer.util.Timer;

public class MegaphoneTimerGui {
    private Minecraft mc;
    private MegaphoneTimerClient client;

    public int one = 0;
    public int two = 0;
    public int thr = 0;
    public int last = 0;
    public int coolend = 0;


    private static final ResourceLocation MEGAPHONE_ICON = new ResourceLocation("mineplanetplus", "chat_broadcast.png");
        
    private static final ResourceLocation WIDGETS = new ResourceLocation("textures/gui/widgets.png");

    public MegaphoneTimerGui(){
        this.mc = Minecraft.getInstance();
        this.client = MegaphoneTimerClient.getInstance();
    }

    public void renderTick(GuiGraphics context, Timer timer){
        if(!this.client.data.toggleMegaphonetimer) return; //스킬타이머를 껏을때 실행x

        render(context, MEGAPHONE_ICON, timer.getDifference(client.data.lastUsedTime));
    }


    private void render(GuiGraphics context,ResourceLocation texture, long ms) {
        PoseStack poseStack = context.pose();
        long remaining_cooldowntime;
        int cooldowntime = 1200000; //20min

        remaining_cooldowntime = cooldowntime - ms;

        RenderSystem.enableBlend(); // 블렌딩 활성화
        RenderSystem.defaultBlendFunc();
        context.blit(WIDGETS, getXpos(),getYpos(), 24, 23, 22, 22);
        RenderSystem.disableBlend();

        poseStack.pushPose();
        poseStack.translate(3+getXpos(),getYpos()+4-1,0.0D);
        poseStack.scale(0.0625F, 0.0625F, 0.0625F);

        RenderSystem.setShaderTexture(0,texture);
        context.blit(texture, 0, 0, 0, 0, 256, 256);
        poseStack.scale(16.0F, 16.0F, 16.0F);
        poseStack.popPose();
        //System.out.println("남은 스킬 지속시간 : "+ (remaining_activatetime/(double)1000) +"초");
            //System.out.println("남은 스킬 쿨타임 : "+(remaining_cooldowntime/(double)1000)+"초");
        if(remaining_cooldowntime > 0){
            poseStack.pushPose();
            poseStack.translate((3+getXpos()+8), (getYpos() + 8-1), 0.0F);
            poseStack.scale(0.9090909F, 0.9090909F, 0.9090909F);
            context.drawCenteredString(this.mc.font, Component.literal(Timeformat.getString(remaining_cooldowntime)), 0, 0, ChatFormatting.WHITE.getColor());
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
        return (this.mc.getWindow().getGuiScaledWidth()) * this.client.data.MegaphonetimerXpos / 1000;
    }
    private int getYpos(){
        return (this.mc.getWindow().getGuiScaledHeight()-(22)) * this.client.data.MegaphonetimerYpos / 1000;
    }

}
