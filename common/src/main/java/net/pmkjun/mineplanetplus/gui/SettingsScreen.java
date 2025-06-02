package net.pmkjun.mineplanetplus.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.pmkjun.mineplanetplus.dungeonhelper.gui.screen.DungeonHelperSettingsScreen;
import net.pmkjun.mineplanetplus.fishhelper.gui.screen.FishHelperConfigScreen;
import net.pmkjun.mineplanetplus.serverutility.gui.MegaphoneTimerConfigScreen;
import net.pmkjun.mineplanetplus.planetskilltimer.config.SkillTimerConfigScreen;
import net.pmkjun.mineplanetplus.serverutility.gui.ServerUtilityConfigScreen;

public class SettingsScreen extends Screen {
    private final Minecraft mc;

    private static final ResourceLocation BACKGROUND_LOCATION = ResourceLocation.fromNamespaceAndPath("dungeonhelper", "textures/gui/dungeonhelper_settings_background.png");

    private final int width;
    private final int height;
    private final Screen parentScreen;

    public SettingsScreen() {
        super(Component.literal("MineplanetPlusSettingScreen"));

        mc = Minecraft.getInstance();
        this.parentScreen = null;

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
        this.addRenderableWidget(new Button.Builder(Component.translatable("serverutility.key.category"), btn -> onServerUtilitySettingPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2) * 3)
                .size(137, 20)
                .build());
    }


    public void render(GuiGraphics guiGraphics, int a, int b, float c) {
        //RenderSystem.setShader(GameRenderer::getPositionTexShader);

        this.renderBackground(guiGraphics, a, b, c);
        super.render(guiGraphics, a, b, c);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(Minecraft.getInstance().level == null) {
            super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        }
        guiGraphics.blit(RenderType::guiTextured, BACKGROUND_LOCATION, getRegularX(), getRegularY(), 0, 0, width, height, 256, 256);
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

    private void onServerUtilitySettingPress(){
        mc.setScreen(new ServerUtilityConfigScreen(mc.screen));
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
