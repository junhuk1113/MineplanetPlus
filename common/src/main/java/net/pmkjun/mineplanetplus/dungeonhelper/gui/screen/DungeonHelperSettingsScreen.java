package net.pmkjun.mineplanetplus.dungeonhelper.gui.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;

public class DungeonHelperSettingsScreen extends Screen {

    private final DungeonHelperClient client;

    private static final Identifier BACKGROUND_LOCATION = Identifier.fromNamespaceAndPath("dungeonhelper", "textures/gui/dungeonhelper_settings_background.png");

    private final int bgWidth;
    private final int bgHeight;
    private final Screen parentScreen;

    private Button toggleVanillaLevelViewButton;

    public DungeonHelperSettingsScreen() {
        super(Component.literal("DungeonHelperSettingScreen"));

        this.client = DungeonHelperClient.getInstance();
        this.parentScreen = null;

        this.bgWidth = 147;
        this.bgHeight = 96;
    }

    public DungeonHelperSettingsScreen(Screen parentScreen) {
        super(Component.literal("DungeonHelperSettingScreen"));

        this.client = DungeonHelperClient.getInstance();
        this.parentScreen = parentScreen;

        this.bgWidth = 147;
        this.bgHeight = 96;
    }

    @Override
    protected void init() {
        super.init();

        this.addRenderableWidget(new Button.Builder(Component.translatable("gui.dungeonhelper.settings.dungeon_cooltime_settings"), btn -> onDungeonCooltimeSettingsPress())
                .pos(getRegularX() + 5, getRegularY() + 5)
                .size(137, 20)
                .build());

        this.addRenderableWidget(new Button.Builder(Component.translatable("gui.dungeonhelper.settings.custom_texture_render_settings"), btn -> onCustomEnchantRenderSettingsPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + 20 + 2)
                .size(137, 20)
                .build());

        this.addRenderableWidget(new Button.Builder(Component.translatable("gui.dungeonhelper.settings.skill_cooltime_settings"), btn -> onSkillCooltimeSettingsPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2) * 2)
                .size(137, 20)
                .build());

        toggleVanillaLevelViewButton = new Button.Builder(Component.translatable("gui.dungeonhelper.settings.vanilla_level_view"), btn -> onToggleVanillaLevelViewPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2) * 3)
                .size(137, 20)
                .tooltip(Tooltip.create(Component.literal("현재 작동하지 않습니다.")))
                .build();
        setToggleVanillaLevelViewButtonText();
        this.addRenderableWidget(toggleVanillaLevelViewButton);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.minecraft.level == null) {
            super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        }
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND_LOCATION, getRegularX(), getRegularY(), 0, 0, this.bgWidth, this.bgHeight, 256, 256);
    }

    @Override
    protected void extractBlurredBackground(GuiGraphicsExtractor guiGraphics) {
        // pass
    }

    private void onDungeonCooltimeSettingsPress() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(new DungeonCooltimeSettingsScreen(this));
        }
    }

    private void onCustomEnchantRenderSettingsPress() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(new CustomTextureRenderSetingsScreen(this));
        }
    }

    private void onSkillCooltimeSettingsPress() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(new SkillCooltimeSettingsScreen(this));
        }
    }

    private void setToggleVanillaLevelViewButtonText() {
        if (client.data.toggleVanillaLevelView) {
            toggleVanillaLevelViewButton.setMessage(Component.translatable("gui.dungeonhelper.settings.vanilla_level_view")
                    .append(Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
        } else {
            toggleVanillaLevelViewButton.setMessage(Component.translatable("gui.dungeonhelper.settings.vanilla_level_view")
                    .append(Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
        }
    }

    private void onToggleVanillaLevelViewPress() {
        client.data.toggleVanillaLevelView = !client.data.toggleVanillaLevelView;
        setToggleVanillaLevelViewButtonText();
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