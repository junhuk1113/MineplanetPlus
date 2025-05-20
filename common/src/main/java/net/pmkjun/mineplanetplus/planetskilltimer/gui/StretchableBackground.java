package net.pmkjun.mineplanetplus.planetskilltimer.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class StretchableBackground {
    private final ResourceLocation BACKGROUND_LOCATION = ResourceLocation.fromNamespaceAndPath("dungeonhelper", "textures/gui/dungeonhelper_settings_background.png");;
    private final int originalWidth = 147;
    private final int originalHeight = 96;
    private int currentWidth;
    private int currentHeight;
    private int x;
    private int y;

    private static final int CORNER_SIZE = 4;
    private static final int TOP_HEIGHT = 4;
    private static final int BOTTOM_HEIGHT = 4;

    public StretchableBackground() {
        this.currentWidth = originalWidth;
        this.currentHeight = originalHeight;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height) {
        this.currentWidth = Math.max(width, originalWidth);
        this.currentHeight = Math.max(height, TOP_HEIGHT + BOTTOM_HEIGHT);
    }

    public void render(GuiGraphics guiGraphics) {
        // 상단 부분
        guiGraphics.blit(RenderType::guiTextured, BACKGROUND_LOCATION,
                x, y,
                0, 0,
                currentWidth, TOP_HEIGHT,
                256, 256);

        // 중간 부분
        int middleHeight = currentHeight - (TOP_HEIGHT + BOTTOM_HEIGHT);
        int textureY = TOP_HEIGHT;
        int remainingHeight = middleHeight;

        while (remainingHeight > 0) {
            int stretchHeight = Math.min(remainingHeight, originalHeight - (TOP_HEIGHT + BOTTOM_HEIGHT));
            guiGraphics.blit(RenderType::guiTextured, BACKGROUND_LOCATION,
                    x, y + (currentHeight - remainingHeight) - TOP_HEIGHT,
                    0, textureY,
                    currentWidth, stretchHeight,
                    256, 256);
            remainingHeight -= stretchHeight;
        }

        // 하단 부분
        guiGraphics.blit(RenderType::guiTextured, BACKGROUND_LOCATION,
                x, y + currentHeight - BOTTOM_HEIGHT,
                0, originalHeight - BOTTOM_HEIGHT,
                currentWidth, BOTTOM_HEIGHT,
                256, 256);
    }

    // 게터 메서드들
    public int getWidth() {
        return currentWidth;
    }

    public int getHeight() {
        return currentHeight;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

