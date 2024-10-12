package net.pmkjun.mineplanetplus.dungeonhelper.gui.screen;

import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

public class SettingsScreen extends Screen {
    private Minecraft mc;
    private DungeonHelperClient client;

    private static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation("dungeonhelper", "textures/gui/dungeonhelper_settings_background.png");

    private final int width;
    private final int height;
    private final Screen parentScreen;

    private Button toggleVanillaLevelViewButton;

    public SettingsScreen() {
        super(Component.literal("DungeonHelperSettingScreen"));

        mc = Minecraft.getInstance();
        client = DungeonHelperClient.getInstance();
        this.parentScreen = (Screen)null;

        width = 147;
        height = 96;
    }

    public SettingsScreen(Screen parentScreen) {
        super(Component.literal("DungeonHelperSettingScreen"));

        mc = Minecraft.getInstance();
        client = DungeonHelperClient.getInstance();
        this.parentScreen = parentScreen;

        width = 147;
        height = 96;
    }

    protected void init(){
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
        .build();
        setToggleVanillaLevelViewButtonText();
        this.addRenderableWidget(toggleVanillaLevelViewButton);
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

    private void onDungeonCooltimeSettingsPress() {
        mc.setScreen(new DungeonCooltimeSettingsScreen(mc.screen));
    }

    private void onCustomEnchantRenderSettingsPress() {
        mc.setScreen(new CustomTextureRenderSetingsScreen(mc.screen));
    }

    private void onSkillCooltimeSettingsPress() {
        mc.setScreen(new SkillCooltimeSettingsScreen(mc.screen));
    }

    private void setToggleVanillaLevelViewButtonText(){
        if(client.data.toggleVanillaLevelView){
            toggleVanillaLevelViewButton.setMessage(Component.translatable("gui.dungeonhelper.settings.vanilla_level_view")
            .append(Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
        }
        else{
            toggleVanillaLevelViewButton.setMessage(Component.translatable("gui.dungeonhelper.settings.vanilla_level_view")
            .append(Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
        }
    }
    
    private void onToggleVanillaLevelViewPress(){
        client.data.toggleVanillaLevelView = !client.data.toggleVanillaLevelView;
        setToggleVanillaLevelViewButtonText();
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
