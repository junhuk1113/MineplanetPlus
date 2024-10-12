package net.pmkjun.mineplanetplus.fishhelper.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.util.Timer;



public class totemCooltimeGui {
    private Minecraft mc;
    private FishHelperClient client;
    private Font font;

    private static final ResourceLocation TOTEM_ICON = new ResourceLocation("pyrofishinghelper","totem.png");
    private static final ResourceLocation TOTEM_SLEEP_ICON = new ResourceLocation("pyrofishinghelper","sleepingtotem3.png");

    public totemCooltimeGui(){
        this.mc = Minecraft.getInstance();
        this.client = FishHelperClient.getInstance();
    }

    public void renderTick(GuiGraphics context, Timer timer){
        int activesecond,cooldownsecond;

        activesecond = this.client.data.currentValueTotemActivetime * 60 - (int)timer.getDifference(this.client.data.lastTotemTime);
        cooldownsecond = this.client.data.currentValueTotemCooldown * 60 - (int)timer.getDifference(this.client.data.lastTotemCooldownTime);

        if (activesecond < 0)
            activesecond = 0;


        if (cooldownsecond>0&&cooldownsecond<this.client.data.valueTotemCooldown*60)
            this.client.data.isTotemCooldown = true;
        else
            this.client.data.isTotemCooldown = false;

        if(this.client.data.toggleTotemtime&&this.client.data.isTotemCooldown){
            render(context, TOTEM_SLEEP_ICON, cooldownsecond);
        }
        else if(this.client.data.toggleTotemtime){
            render(context, TOTEM_ICON, activesecond);
        }
    }

    private void render(GuiGraphics context,ResourceLocation texture, int second){
        PoseStack poseStack = context.pose();
        int Timer_xpos, Timer_ypos;
        Timer_xpos = getXpos();
        Timer_ypos = getYpos();

        poseStack.pushPose();
        poseStack.translate(Timer_xpos,Timer_ypos,0.0D);
        poseStack.scale(0.0625F, 0.0625F, 0.0625F);

        RenderSystem.setShaderTexture(0,texture);
        context.blit(texture, 0, 0, 0, 0, 256, 256);
        poseStack.scale(16.0F, 16.0F, 16.0F);
        poseStack.popPose();

        if (this.client.data.toggleTotemtimeText) {
            this.font = this.mc.font;
            int minute = second / 60;
            second -= minute * 60;
            poseStack.pushPose();
            poseStack.translate((Timer_xpos + 16 + 2), Timer_ypos+4, 0.0D);
            poseStack.scale(0.9090909F, 0.9090909F, 0.9090909F);
            context.drawString(this.font, Component.literal(String.format("%02d:%02d", new Object[] { Integer.valueOf(minute), Integer.valueOf(second) })), 0, 0, 16777215);
            poseStack.scale(1.1F, 1.1F, 1.1F);

            poseStack.popPose();
        }
    }
    private int getXpos(){
        return 2 + (this.mc.getWindow().getGuiScaledWidth()-43-2) * this.client.data.Timer_xpos / 1000;
    }
    private int getYpos(){
        return 2 + (this.mc.getWindow().getGuiScaledHeight()-18-2) * this.client.data.Timer_ypos / 1000;
    }

}