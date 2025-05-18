package net.pmkjun.mineplanetplus.megaphonetimer.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.pmkjun.mineplanetplus.megaphonetimer.MegaphoneTimerClient;

public class AdjustMegaphoneTimerPosScreen extends Screen{
    private final Minecraft mc = Minecraft.getInstance();
    private final MegaphoneTimerClient client = MegaphoneTimerClient.getInstance();
    private final Screen parentScreen;
    private double lastMouseX, lastMouseY;
    private int lastTimerX, lastTimerY;
    private boolean isTimerClicked = false;

    public AdjustMegaphoneTimerPosScreen(Screen parentScreen){
        super(Component.literal("확성기 타이머 설정"));
        this.parentScreen = parentScreen;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        //mc.player.displayClientMessage(Component.literal("<click>x:"+mouseX+"/y:"+mouseY), false);
        lastMouseX = mouseX; lastMouseY = mouseY;

        if((getXpos()<=mouseX && getXpos()+22 >= mouseX) && (getYpos()<=mouseY && getYpos()+22 >= mouseY))
        {
            isTimerClicked = true;
            lastTimerX = getXpos(); lastTimerY = getYpos();
            //mc.player.displayClientMessage(Component.literal("timer clicked"), false);
        }
        else{
            isTimerClicked = false;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        double movedX = mouseX-lastMouseX;
        double movedY = mouseY-lastMouseY;
        //mc.player.displayClientMessage(Component.literal("<drag>x:"+(mouseX-lastMouseX)+"/y:"+(mouseY-lastMouseY)), false);
        if(isTimerClicked){
            client.data.MegaphonetimerXpos = Mth.clamp((int)((lastTimerX + movedX)/(this.mc.getWindow().getGuiScaledWidth()-22) * 1000), 0, 1000);
            client.data.MegaphonetimerYpos = Mth.clamp((int)((lastTimerY + movedY)/(this.mc.getWindow().getGuiScaledHeight()-22) * 1000), 0, 1000);
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.client.settings.save();
        return super.mouseReleased(mouseX, mouseY, button);
    }
    @Override
    public void onClose(){
        this.mc.setScreen(parentScreen);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(mc.level == null) {
            super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
            guiGraphics.drawCenteredString(mc.font,"서버 접속 후 설정 할 수 있습니다.",mc.getWindow().getGuiScaledWidth()/2, mc.getWindow().getGuiScaledHeight()/2, 0xFFFFFF);
        }
    }

    private int getXpos(){
        return (this.mc.getWindow().getGuiScaledWidth()-22) * this.client.data.MegaphonetimerXpos / 1000;
    }
    private int getYpos(){
        return (this.mc.getWindow().getGuiScaledHeight()-22) * this.client.data.MegaphonetimerYpos / 1000;
    }
}
