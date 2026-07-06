package net.pmkjun.mineplanetplus.planetskilltimer.gui;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.pmkjun.mineplanetplus.planetskilltimer.PlanetSkillTimerClient;
import net.pmkjun.mineplanetplus.planetskilltimer.file.Stat;
import net.pmkjun.mineplanetplus.planetskilltimer.util.SkillLevel;
import net.pmkjun.mineplanetplus.planetskilltimer.util.Timeformat;
import net.pmkjun.mineplanetplus.planetskilltimer.util.Timer;
import org.joml.Matrix3x2fStack;

public class SkillTimerGui {
    private final Minecraft mc;
    private final PlanetSkillTimerClient client;

    public int one = 0;
    public int two = 0;
    public int thr = 0;
    public int last = 0;
    public int coolend = 0;

    // ResourceLocation -> Identifier
    private static final Identifier[] SKILL_ICONS = {
            Identifier.withDefaultNamespace("textures/item/golden_hoe.png"),
            Identifier.withDefaultNamespace("textures/item/diamond_axe.png"),
            Identifier.withDefaultNamespace("textures/item/netherite_pickaxe.png"),
            Identifier.withDefaultNamespace("textures/item/iron_shovel.png")
    };
    private static final Identifier WIDGETS = Identifier.withDefaultNamespace("hud/hotbar_offhand_left");

    public SkillTimerGui() {
        this.mc = Minecraft.getInstance();
        this.client = PlanetSkillTimerClient.getInstance();
    }

    // 26.1: renderTick -> extractRenderState
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, Timer timer) {
        int i = 0;
        if(!this.client.data.toggleSkilltimer) return; // 스킬타이머를 껏을때 실행x

        for(int skilltype = 0; skilltype < Stat.list.length; skilltype++) {
            if(client.data.toggleSkills[skilltype]) {
                extract(guiGraphics, SKILL_ICONS[skilltype], i, skilltype, timer.getDifference(client.data.lastSkillTime[skilltype]));
                i++;
            }
        }
    }


    // 26.1: render -> extract
    private void extract(GuiGraphicsExtractor guiGraphics, Identifier texture, int i, int skilltype, long ms) {
        Matrix3x2fStack poseStack = guiGraphics.pose();
        long remaining_activatetime, remaining_cooldowntime;
        int activatetime, cooldowntime;

        activatetime = SkillLevel.getActivateTime(skilltype, Stat.level[skilltype]);
        cooldowntime = SkillLevel.getCooldownTime(skilltype, Stat.level[skilltype]);
        remaining_activatetime = activatetime - ms;
        remaining_cooldowntime = cooldowntime - (ms - activatetime);

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED_PREMULTIPLIED_ALPHA, WIDGETS, getXpos() + 22 * i, getYpos() - 1, 29, 24);

        poseStack.pushMatrix();
        poseStack.translate(3 + getXpos() + 22 * i, getYpos() + 4 - 1);
        poseStack.scale(0.0625F, 0.0625F);

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED_PREMULTIPLIED_ALPHA, texture, 0, 0, 0, 0, 256, 256, 256, 256);
        poseStack.scale(16.0F, 16.0F);
        poseStack.popMatrix();

        if (remaining_activatetime > 0) {
            // 남은 지속시간
            poseStack.pushMatrix();
            poseStack.translate((3 + getXpos() + 22 * i + 8), (getYpos() + 8 - 1));
            poseStack.scale(0.9090909F, 0.9090909F);
            // 26.1: drawCenteredString -> centeredText
            guiGraphics.centeredText(this.mc.font, Component.literal(Timeformat.getString(remaining_activatetime)), 0, 0, ChatFormatting.WHITE.getColor());

            if (client.data.toggleAlertSound) {
                if (remaining_activatetime / (double) 1000 <= 1 && one == 0 && remaining_activatetime / (double) 1000 > 0.2) {
                    this.mc.level.playSound(this.mc.player, this.mc.player.getX(), this.mc.player.getY(), this.mc.player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 1f, 1f);
                    one = 1;
                }
                if (remaining_activatetime / (double) 1000 <= 2 && two == 0 && remaining_activatetime / (double) 1000 > 0.2) {
                    this.mc.level.playSound(this.mc.player, this.mc.player.getX(), this.mc.player.getY(), this.mc.player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 1f, 1f);
                    two = 1;
                }
                if (remaining_activatetime / (double) 1000 <= 3 && thr == 0 && remaining_activatetime / (double) 1000 > 0.2) {
                    this.mc.level.playSound(this.mc.player, this.mc.player.getX(), this.mc.player.getY(), this.mc.player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 1f, 1f);
                    thr = 1;
                }

                if (remaining_activatetime / (double) 1000 < 0.1 && last == 0 && remaining_activatetime / (double) 1000 >= 0.05) {
                    this.mc.level.playSound(this.mc.player, this.mc.player.getX(), this.mc.player.getY(), this.mc.player.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1f, 1f);
                    last = 1;
                }
                if (remaining_activatetime / (double) 1000 < 0.05) {
                    one = 0;
                    two = 0;
                    thr = 0;
                    last = 0;
                }
            }
            poseStack.popMatrix();
        } else if (remaining_cooldowntime > 0) {
            poseStack.pushMatrix();
            poseStack.translate((3 + getXpos() + 22 * i + 8), (getYpos() + 8 - 1));
            poseStack.scale(0.9090909F, 0.9090909F);
            // 26.1: drawCenteredString -> centeredText
            guiGraphics.centeredText(this.mc.font, Component.literal(Timeformat.getString(remaining_cooldowntime)), 0, 0, 0xFFFFFFFF);
            poseStack.popMatrix();

            if (client.data.toggleAlertSound) {
                if (remaining_cooldowntime / (double) 1000 < 0.1 && remaining_cooldowntime / (double) 1000 > 0.05 && coolend == 0) {
                    this.mc.level.playSound(this.mc.player, this.mc.player.getX(), this.mc.player.getY(), this.mc.player.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1f, 1f);
                    coolend = 1;
                }

                if (remaining_cooldowntime / (double) 1000 < 0.05) {
                    coolend = 0;
                }
            }
        }
    }

    private int getEnabledSkillCount() {
        int count = 0;
        for(boolean toggleskill : this.client.data.toggleSkills) {
            if(toggleskill) count++;
        }
        return count;
    }

    private int getXpos() {
        return (this.mc.getWindow().getGuiScaledWidth() - (22 * getEnabledSkillCount())) * this.client.data.SkillTimerXpos / 1000;
    }

    private int getYpos() {
        return (this.mc.getWindow().getGuiScaledHeight() - (22)) * this.client.data.SkillTimerYpos / 1000;
    }
}