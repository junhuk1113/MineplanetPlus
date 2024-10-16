package net.pmkjun.mineplanetplus.planetskilltimer.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.pmkjun.mineplanetplus.planetskilltimer.PlanetSkillTimerClient;
import net.pmkjun.mineplanetplus.planetskilltimer.file.Stat;
import net.pmkjun.mineplanetplus.planetskilltimer.util.SkillLevel;
import net.pmkjun.mineplanetplus.planetskilltimer.util.Timeformat;
import net.pmkjun.mineplanetplus.planetskilltimer.util.Timer;

public class SkillTimerGui {
    private Minecraft mc;
    private PlanetSkillTimerClient client;

    public int one = 0;
    public int two = 0;
    public int thr = 0;
    public int last = 0;
    public int coolend = 0;


    private static final ResourceLocation[] SKILL_ICONS = {
        new ResourceLocation("minecraft", "textures/item/golden_hoe.png"),
        new ResourceLocation("minecraft", "textures/item/diamond_axe.png"),
        new ResourceLocation("minecraft", "textures/item/netherite_pickaxe.png"),
        new ResourceLocation("minecraft", "textures/item/iron_shovel.png")
    };
    private static final ResourceLocation WIDGETS = new ResourceLocation("textures/gui/widgets.png");

    public SkillTimerGui(){
        this.mc = Minecraft.getInstance();
        this.client = PlanetSkillTimerClient.getInstance();
    }

    public void renderTick(GuiGraphics context, Timer timer){
        int i = 0;
        if(!this.client.data.toggleSkilltimer) return; //스킬타이머를 껏을때 실행x

        for(int skilltype = 0; skilltype < Stat.list.length ; skilltype++){
            if(client.data.toggleSkills[skilltype]) {
                render(context, SKILL_ICONS[skilltype], i, skilltype, timer.getDifference(client.data.lastSkillTime[skilltype]));
                i++;
            }
        }
    }


    private void render(GuiGraphics context,ResourceLocation texture,int i,int skilltype, long ms) {
        PoseStack poseStack = context.pose();
        long remaining_activatetime, remaining_cooldowntime;
        int activatetime, cooldowntime;

        activatetime = SkillLevel.getActivateTime(skilltype,Stat.level[skilltype]);
        cooldowntime = SkillLevel.getCooldownTime(skilltype, Stat.level[skilltype]);
        remaining_activatetime = activatetime - ms;
        remaining_cooldowntime = cooldowntime - (ms - activatetime);

        RenderSystem.enableBlend(); // 블렌딩 활성화
        RenderSystem.defaultBlendFunc();
        context.blit(WIDGETS, getXpos()+22*i,getYpos(), 24, 23, 22, 22);
        RenderSystem.disableBlend();

        poseStack.pushPose();
        poseStack.translate(3+getXpos()+22*i,getYpos()+4-1,0.0D);
        poseStack.scale(0.0625F, 0.0625F, 0.0625F);

        RenderSystem.setShaderTexture(0,texture);
        context.blit(texture, 0, 0, 0, 0, 256, 256);
        poseStack.scale(16.0F, 16.0F, 16.0F);
        poseStack.popPose();
        //System.out.println("남은 스킬 지속시간 : "+ (remaining_activatetime/(double)1000) +"초");
        if(remaining_activatetime > 0){
            //남은 지속시간
            //System.out.println("남은 스킬 지속시간 : "+ (remaining_activatetime/(double)1000) +"초");
            poseStack.pushPose();
            poseStack.translate((3+getXpos()+22*i+8), (getYpos() + 8-1), 0.0F);
            poseStack.scale(0.9090909F, 0.9090909F, 0.9090909F);
            context.drawCenteredString(this.mc.font, Component.literal(Timeformat.getString(remaining_activatetime)), 0, 0, ChatFormatting.WHITE.getColor());
            if (client.data.toggleAlertSound) {
                if(remaining_activatetime/(double)1000 <= 1 && one == 0 && remaining_activatetime/(double)1000 > 0.2){
                    this.mc.level.playSound(this.mc.player, this.mc.player.getX(), this.mc.player.getY(), this.mc.player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 1f, 1f);
                    one = 1;
                }
                if(remaining_activatetime/(double)1000 <= 2 && two == 0 && remaining_activatetime/(double)1000 > 0.2){
                    this.mc.level.playSound(this.mc.player, this.mc.player.getX(), this.mc.player.getY(), this.mc.player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 1f, 1f);
                    two = 1;
                }
                if(remaining_activatetime/(double)1000 <= 3 && thr == 0 && remaining_activatetime/(double)1000 > 0.2) {
                    this.mc.level.playSound(this.mc.player, this.mc.player.getX(), this.mc.player.getY(), this.mc.player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 1f, 1f);
                    thr = 1;
                }

                if(remaining_activatetime/(double)1000 < 0.1 && last == 0 && remaining_activatetime/(double)1000 >= 0.05){
                    this.mc.level.playSound(this.mc.player, this.mc.player.getX(), this.mc.player.getY(), this.mc.player.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1f, 1f);
                    last = 1;

                }
                if(remaining_activatetime/(double)1000 < 0.05){
                    one = 0;
                    two = 0;
                    thr = 0;
                    last = 0;
                }
            }

            //System.out.println("one: " + one + " two:" + two + " three: " + thr);
            poseStack.popPose();
        }
        else if(remaining_cooldowntime > 0){

            //System.out.println("남은 스킬 쿨타임 : "+(remaining_cooldowntime/(double)1000)+"초");
            poseStack.pushPose();
            poseStack.translate((3+getXpos()+22*i+8), (getYpos() + 8-1), 0.0F);
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
    private int getEnabledSkillCount(){
        int count = 0;
        for(boolean toggleskill : this.client.data.toggleSkills){
            if(toggleskill) count++;
        }
        return count;
    }
    public boolean isSkillCooldown(int skilltype, Timer timer){
        long ms = timer.getDifference(client.data.lastSkillTime[skilltype]);

        long remaining_activatetime, remaining_cooldowntime;
        int activatetime, cooldowntime;

        activatetime = SkillLevel.getActivateTime(skilltype,Stat.level[skilltype]);
        cooldowntime = SkillLevel.getCooldownTime(skilltype, Stat.level[skilltype]);
        remaining_activatetime = activatetime - ms;
        remaining_cooldowntime = cooldowntime - (ms - activatetime);

        return remaining_activatetime > 0 || remaining_cooldowntime > 0;
    }
    private int getXpos(){
        return (this.mc.getWindow().getGuiScaledWidth()-(22*getEnabledSkillCount())) * this.client.data.SkillTimerXpos / 1000;
    }
    private int getYpos(){
        return (this.mc.getWindow().getGuiScaledHeight()-(22)) * this.client.data.SkillTimerYpos / 1000;
    }

}
