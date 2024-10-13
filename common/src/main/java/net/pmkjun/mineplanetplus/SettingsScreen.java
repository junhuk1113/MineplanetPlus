package net.pmkjun.mineplanetplus;

import net.pmkjun.mineplanetplus.dungeonhelper.gui.screen.DungeonHelperSettingsScreen;
import net.pmkjun.mineplanetplus.fishhelper.gui.screen.FishHelperConfigScreen;
import net.pmkjun.mineplanetplus.megaphonetimer.gui.MegaphoneTimerConfigScreen;
import net.pmkjun.mineplanetplus.planetskilltimer.config.SkillTimerConfigScreen;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SettingsScreen extends Screen {
    private Minecraft mc;

    private static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation("dungeonhelper", "textures/gui/dungeonhelper_settings_background.png");

    private final int width;
    private final int height;
    private final Screen parentScreen;

    public SettingsScreen() {
        super(Component.literal("MineplanetPlusSettingScreen"));

        mc = Minecraft.getInstance();
        this.parentScreen = (Screen)null;

        width = 147;
        height = 96;
    }

    public SettingsScreen(Screen parentScreen) {
        super(Component.literal("MineplanetPlusSettingScreen"));

        mc = Minecraft.getInstance();
        this.parentScreen = parentScreen;

        width = 147;
        height = 96;
    }

    protected void init(){
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
        this.addRenderableWidget(new Button.Builder(Component.translatable("megaphonetimer.key.category"), btn -> onMegaphoneTimerSettingPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2) * 3)
                .size(137, 20)
                .build());
    }


    public void render(GuiGraphics guiGraphics, int a, int b, float c) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        this.renderBackground(guiGraphics);
        super.render(guiGraphics, a, b, c);
    }
    
    public void renderBackground(GuiGraphics guiGraphics) {
        super.renderBackground(guiGraphics);

        guiGraphics.blit(BACKGROUND_LOCATION, getRegularX(), getRegularY(), 0, 0, width, height);
    }

    private void onDungeonHelperSettingsPress() {
        mc.setScreen(new DungeonHelperSettingsScreen(mc.screen));
    }

    private void onSkillTimerSettingsPress() {
        mc.setScreen(new SkillTimerConfigScreen(mc.screen));
    }

    private void onFishHelperSettingsPress() {
        mc.setScreen(new FishHelperConfigScreen(mc.screen));
    }

    private void onMegaphoneTimerSettingPress(){
        mc.setScreen(new MegaphoneTimerConfigScreen(mc.screen));
    }

    @Override
    public void onClose(){
        assert this.minecraft != null;
        this.minecraft.setScreen(parentScreen);
    }

    int getRegularX() {
        return  mc.getWindow().getGuiScaledWidth() / 2 - width / 2;
    }

    int getRegularY() {
        return mc.getWindow().getGuiScaledHeight() / 2 - height / 2;
    }
}
