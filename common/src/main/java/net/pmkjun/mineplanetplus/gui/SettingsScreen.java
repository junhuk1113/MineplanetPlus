package net.pmkjun.mineplanetplus.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier; // ResourceLocation에서 Identifier로 변경
import net.pmkjun.mineplanetplus.dungeonhelper.gui.screen.DungeonHelperSettingsScreen;
import net.pmkjun.mineplanetplus.fishhelper.gui.screen.FishHelperConfigScreen;
import net.pmkjun.mineplanetplus.planetskilltimer.config.SkillTimerConfigScreen;
import net.pmkjun.mineplanetplus.serverutility.gui.ServerUtilityConfigScreen;

public class SettingsScreen extends Screen {

    // ResourceLocation.fromNamespaceAndPath -> Identifier.fromNamespaceAndPath 로 변경
    private static final Identifier BACKGROUND_LOCATION = Identifier.fromNamespaceAndPath("dungeonhelper", "textures/gui/dungeonhelper_settings_background.png");

    // 부모 Screen 클래스의 변수(width, height) 가림 방지
    private final int bgWidth;
    private final int bgHeight;
    private final Screen parentScreen;

    public SettingsScreen() {
        super(Component.literal("MineplanetPlusSettingScreen"));

        this.parentScreen = null;
        this.bgWidth = 147;
        this.bgHeight = 96;
    }

    public SettingsScreen(Screen parentScreen) {
        super(Component.literal("MineplanetPlusSettingScreen"));

        this.parentScreen = parentScreen;
        this.bgWidth = 147;
        this.bgHeight = 96;
    }

    @Override
    protected void init() {
        super.init();

        this.addRenderableWidget(new Button.Builder(Component.translatable("key.dungeonhelper.category"), btn -> onDungeonHelperSettingsPress())
                .pos(getRegularX() + 5, getRegularY() + 5)
                .size(137, 20)
                .build());

        this.addRenderableWidget(new Button.Builder(Component.translatable("planetskilltimer.key.category"), btn -> onSkillTimerSettingsPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + 20 + 2)
                .size(137, 20)
                .build());

        this.addRenderableWidget(new Button.Builder(Component.translatable("fishhelper.key.category"), btn -> onFishHelperSettingsPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2) * 2)
                .size(137, 20)
                .build());

        this.addRenderableWidget(new Button.Builder(Component.translatable("serverutility.key.category"), btn -> onServerUtilitySettingPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2) * 3)
                .size(137, 20)
                .build());
    }

    // render -> extractRenderState
    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
    }

    // renderBackground -> extractBackground
    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.minecraft.level == null) {
            super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        }
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND_LOCATION, getRegularX(), getRegularY(), 0, 0, this.bgWidth, this.bgHeight, 256, 256);
    }

    // renderBlurredBackground -> extractBlurredBackground
    @Override
    protected void extractBlurredBackground(GuiGraphicsExtractor guiGraphics) {
        // pass
    }

    private void onDungeonHelperSettingsPress() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(new DungeonHelperSettingsScreen(this));
        }
    }

    private void onSkillTimerSettingsPress() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(new SkillTimerConfigScreen(this));
        }
    }

    private void onFishHelperSettingsPress() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(new FishHelperConfigScreen(this));
        }
    }

    private void onServerUtilitySettingPress() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(new ServerUtilityConfigScreen(this));
        }
    }

    @Override
    public void onClose() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(parentScreen);
        }
    }

    int getRegularX() {
        return this.width / 2 - this.bgWidth / 2;
    }

    int getRegularY() {
        return this.height / 2 - this.bgHeight / 2;
    }
}