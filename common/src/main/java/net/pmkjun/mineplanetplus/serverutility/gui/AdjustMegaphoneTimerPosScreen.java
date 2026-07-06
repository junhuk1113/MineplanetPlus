package net.pmkjun.mineplanetplus.serverutility.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent; // 26.1: 새로운 마우스 이벤트 클래스 Import
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.pmkjun.mineplanetplus.serverutility.ServerUtilityClient;

public class AdjustMegaphoneTimerPosScreen extends Screen {
    private final Minecraft mc = Minecraft.getInstance();
    private final ServerUtilityClient client = ServerUtilityClient.getInstance();
    private final Screen parentScreen;
    private double lastMouseX, lastMouseY;
    private int lastTimerX, lastTimerY;
    private boolean isTimerClicked = false;

    public AdjustMegaphoneTimerPosScreen(Screen parentScreen) {
        super(Component.literal("확성기 타이머 설정"));
        this.parentScreen = parentScreen;
    }

    // 26.1: mouseClicked(double, double, int) -> mouseClicked(MouseButtonEvent, boolean)
    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        double mouseX = event.x();
        double mouseY = event.y();

        lastMouseX = mouseX;
        lastMouseY = mouseY;

        if((getXpos() <= mouseX && getXpos() + 22 >= mouseX) && (getYpos() <= mouseY && getYpos() + 22 >= mouseY)) {
            isTimerClicked = true;
            lastTimerX = getXpos();
            lastTimerY = getYpos();
        } else {
            isTimerClicked = false;
        }

        return super.mouseClicked(event, doubleClick);
    }

    // 26.1: mouseDragged(double, double, int, double, double) -> mouseDragged(MouseButtonEvent)
    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dragX, double dragY) {
        double mouseX = event.x();
        double mouseY = event.y();

        double movedX = mouseX - lastMouseX;
        double movedY = mouseY - lastMouseY;

        if (isTimerClicked) {
            client.data.MegaphonetimerXpos = Mth.clamp((int)((lastTimerX + movedX)/(this.mc.getWindow().getGuiScaledWidth()-22) * 1000), 0, 1000);
            client.data.MegaphonetimerYpos = Mth.clamp((int)((lastTimerY + movedY)/(this.mc.getWindow().getGuiScaledHeight()-22) * 1000), 0, 1000);
        }

        // 부모 클래스 호출 시 드래그 이동량(dragX, dragY)을 함께 전달합니다.
        return super.mouseDragged(event, dragX, dragY);
    }

    // 26.1: mouseReleased(double, double, int) -> mouseReleased(MouseButtonEvent)
    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        this.client.settings.save();
        return super.mouseReleased(event);
    }

    @Override
    public void onClose() {
        this.mc.setScreen(parentScreen);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(mc.level == null) {
            super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
            guiGraphics.centeredText(mc.font, Component.literal("서버 접속 후 설정 할 수 있습니다."), mc.getWindow().getGuiScaledWidth()/2, mc.getWindow().getGuiScaledHeight()/2, ARGB.white(255));
        }
    }

    @Override
    protected void extractBlurredBackground(GuiGraphicsExtractor guiGraphics) {
        //pass
    }

    private int getXpos() {
        return (this.mc.getWindow().getGuiScaledWidth()-22) * this.client.data.MegaphonetimerXpos / 1000;
    }
    private int getYpos() {
        return (this.mc.getWindow().getGuiScaledHeight()-22) * this.client.data.MegaphonetimerYpos / 1000;
    }
}