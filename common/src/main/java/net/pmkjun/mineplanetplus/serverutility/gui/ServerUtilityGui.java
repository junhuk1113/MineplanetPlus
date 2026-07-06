package net.pmkjun.mineplanetplus.serverutility.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ARGB;
import net.pmkjun.mineplanetplus.planetskilltimer.util.Timeformat;
import net.pmkjun.mineplanetplus.planetskilltimer.util.Timer;
import net.pmkjun.mineplanetplus.serverutility.ServerUtilityClient;
import org.joml.Matrix3x2fStack;

public class ServerUtilityGui {
    private final Minecraft mc;
    private final ServerUtilityClient client;

    public int last = 0;
    public int coolend = 0;

    // ResourceLocation -> Identifier
    private static final Identifier MEGAPHONE_ICON = Identifier.fromNamespaceAndPath("mineplanetplus", "chat_broadcast.png");
    private static final Identifier MEGAPHONE_ICON_COOLDOWN = Identifier.fromNamespaceAndPath("mineplanetplus", "chat_broadcast_cooldown.png");
    private static final Identifier Currency_Background = Identifier.fromNamespaceAndPath("mythichud", "textures/assets/planet/status_background.png");

    private static final Identifier WIDGETS = Identifier.withDefaultNamespace("hud/hotbar_offhand_left");

    public ServerUtilityGui() {
        this.mc = Minecraft.getInstance();
        this.client = ServerUtilityClient.getInstance();
    }

    // renderTick -> extractRenderState
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, Timer timer) {
        long remaining_cooldowntime;
        int cooldowntime = 1200000; //20min

        remaining_cooldowntime = cooldowntime - timer.getDifference(client.data.lastUsedTime);

        if(this.client.data.toggleMegaphonetimer) { // 확성기 타이머 활성화 시
            if (remaining_cooldowntime > 0)
                extractMegaphone(guiGraphics, MEGAPHONE_ICON_COOLDOWN, remaining_cooldowntime);
            else
                extractMegaphone(guiGraphics, MEGAPHONE_ICON, remaining_cooldowntime);
        }
        if(this.client.data.toggleCurrencyDisplay && this.client.data.toggleCurrencyDisplayCustompos)
            extractCurrency(guiGraphics, Currency_Background);
    }

    // renderMegaphone -> extractMegaphone
    private void extractMegaphone(GuiGraphicsExtractor guiGraphics, Identifier texture, long remaining_cooldowntime) {
        Matrix3x2fStack poseStack = guiGraphics.pose();

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED_PREMULTIPLIED_ALPHA, WIDGETS, getMegaphoneXpos(), getMegaphoneYpos()-1, 29, 24);

        poseStack.pushMatrix();
        poseStack.translate(3+ getMegaphoneXpos(), getMegaphoneYpos()+4-1);
        poseStack.scale(0.0625F, 0.0625F);

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, 0, 0, 0, 0, 256, 256, 256, 256);
        poseStack.scale(16.0F, 16.0F);
        poseStack.popMatrix();

        if(remaining_cooldowntime > 0) {
            poseStack.pushMatrix();
            poseStack.translate((getMegaphoneXpos() + 2 + 9), getMegaphoneYpos()+7);
            poseStack.scale(1F/1.1F, 1F/1.1F);
            // drawCenteredString -> centeredText
            guiGraphics.centeredText(this.mc.font, Component.literal(Timeformat.getString(remaining_cooldowntime)), 0, 0, ARGB.white(255));
            poseStack.popMatrix();

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

    // renderCurrency -> extractCurrency
    private void extractCurrency(GuiGraphicsExtractor guiGraphics, Identifier texture) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED_PREMULTIPLIED_ALPHA ,texture, getCurrencyDisplayXpos(), getCurrencyDisplayYpos(), 0, 0, 80, 41, 80, 41);

        try {
            // drawString -> text
            // text(font, component, x, y, color, shadow_boolean) 파라미터 구조
            guiGraphics.text(mc.font, Component.literal(client.money.getString()), getCurrencyDisplayXpos() + 16, getCurrencyDisplayYpos() + 5, ARGB.color(1, client.money.getStyle().getColor().getValue()), false);
            guiGraphics.text(mc.font, Component.literal(client.coin.getString()), getCurrencyDisplayXpos() + 16, getCurrencyDisplayYpos() + 5 + 12, ARGB.color(1, client.coin.getStyle().getColor().getValue()), false);
            guiGraphics.text(mc.font, Component.literal(client.credit.getString()), getCurrencyDisplayXpos() + 16, getCurrencyDisplayYpos() + 5 + 24, ARGB.color(1, client.credit.getStyle().getColor().getValue()), false);
        } catch (NullPointerException e) {
            // 오류 무시
        }
    }

    private int getMegaphoneXpos() {
        return (this.mc.getWindow().getGuiScaledWidth()-22) * this.client.data.MegaphonetimerXpos / 1000;
    }
    private int getMegaphoneYpos() {
        return (this.mc.getWindow().getGuiScaledHeight()-22) * this.client.data.MegaphonetimerYpos / 1000;
    }

    private int getCurrencyDisplayXpos() {
        return (this.mc.getWindow().getGuiScaledWidth()-80) * this.client.data.CurrencyDisplayXpos / 1000;
    }
    private int getCurrencyDisplayYpos() {
        return (this.mc.getWindow().getGuiScaledHeight()-41) * this.client.data.CurrencyDisplayYpos / 1000;
    }
}