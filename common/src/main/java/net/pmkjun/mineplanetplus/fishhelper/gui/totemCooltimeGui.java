package net.pmkjun.mineplanetplus.fishhelper.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.util.Timer;
import org.joml.Matrix3x2fStack;


public class totemCooltimeGui {
    private final Minecraft mc;
    private final FishHelperClient client;

    private static final ResourceLocation TOTEM_ICON = ResourceLocation.fromNamespaceAndPath("pyrofishinghelper","totem.png");
    private static final ResourceLocation TOTEM_SLEEP_ICON = ResourceLocation.fromNamespaceAndPath("pyrofishinghelper","sleepingtotem3.png");

    public totemCooltimeGui(){
        this.mc = Minecraft.getInstance();
        this.client = FishHelperClient.getInstance();
    }

    public void renderTick(GuiGraphics guiGraphics, Timer timer){
        int activesecond,cooldownsecond;

        activesecond = this.client.data.currentValueTotemActivetime * 60 - (int)timer.getDifference(this.client.data.lastTotemTime);
        cooldownsecond = this.client.data.currentValueTotemCooldown * 60 - (int)timer.getDifference(this.client.data.lastTotemCooldownTime);

        if (activesecond < 0)
            activesecond = 0;


        this.client.data.isTotemCooldown = cooldownsecond > 0 && cooldownsecond < this.client.data.valueTotemCooldown * 60;

        if(this.client.data.toggleTotemtime&&this.client.data.isTotemCooldown){
            render(guiGraphics, TOTEM_SLEEP_ICON, cooldownsecond);
        }
        else if(this.client.data.toggleTotemtime){
            render(guiGraphics, TOTEM_ICON, activesecond);
        }
    }

    private void render(GuiGraphics guiGraphics,ResourceLocation texture, int second){
        Matrix3x2fStack poseStack = guiGraphics.pose();
        int Timer_xpos, Timer_ypos;
        Timer_xpos = getXpos();
        Timer_ypos = getYpos();

        poseStack.pushMatrix();
        poseStack.translate(Timer_xpos,Timer_ypos);
        poseStack.scale(0.0625F, 0.0625F);

        //RenderSystem.setShaderTexture(0,texture);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, 0, 0, 0, 0, 256, 256, 256, 256);
        poseStack.scale(16.0F, 16.0F);
        poseStack.popMatrix();

        if (this.client.data.toggleTotemtimeText) {
            Font font = this.mc.font;
            int minute = second / 60;
            second -= minute * 60;
            poseStack.pushMatrix();
            poseStack.translate((Timer_xpos + 16 + 2), Timer_ypos+4);
            poseStack.scale(1F/1.1F, 1F/1.1F);
            guiGraphics.drawString(font, Component.literal(String.format("%02d:%02d", minute, second)), 0, 0, ARGB.white(1));
            poseStack.scale(1.1F, 1.1F);

            poseStack.popMatrix();
        }
    }
    private int getXpos(){
        return 2 + (this.mc.getWindow().getGuiScaledWidth()-43-2) * this.client.data.Timer_xpos / 1000;
    }
    private int getYpos(){
        return 2 + (this.mc.getWindow().getGuiScaledHeight()-18-2) * this.client.data.Timer_ypos / 1000;
    }

}