package net.pmkjun.mineplanetplus.dungeonhelper.gui.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.config.ConfigManage;
import net.pmkjun.mineplanetplus.gui.StretchableBackground;

public class CustomTextureRenderSetingsScreen extends Screen {

    private final Minecraft mc;
    private final DungeonHelperClient client;
    private final StretchableBackground background = new StretchableBackground();
    //public static final ResourceLocation BG_LOCATION = ResourceLocation.fromNamespaceAndPath("dungeonhelper", "textures/gui/custom_enchant_render_settings_background.png");

    private Button toggleCustomEnchantRenderButton;
    private Button toggleRuneOfFortuneRenderButton;
    private Button toggleRuneArrowEmptyButton;

    private final int width, height;
    private final Screen parentScreen;

    protected CustomTextureRenderSetingsScreen(Screen parentScreen) {
        super(Component.literal("CustomTextureRenderSetingsScreen"));

        mc = Minecraft.getInstance();
        client = DungeonHelperClient.getInstance();
        this.parentScreen = parentScreen;

        width = 147;
        height = 74;
    }

    protected void init() {
        super.init();

        toggleCustomEnchantRenderButton = this.addRenderableWidget(new Button.Builder(Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.main"), btn -> onToggleCustomEnchantRenderPress())
                .pos(getRegularX() + 5, getRegularY() + 5)
                .size(137, 20)
                .tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.main.tooltip")))
                .build());
        setToggleCustomEnchantRenderButtonText();

        toggleRuneOfFortuneRenderButton = this.addRenderableWidget(new Button.Builder(Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runeoffortune"), btn -> onToggleRuneOfFortuneRenderPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2))
                .size(137, 20)
                .tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runeoffortune.tooltip")))
                .build());
        setToggleRuneOfFortuneRenderButtonText();


        toggleRuneArrowEmptyButton = this.addRenderableWidget(new Button.Builder(Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runearrow"), btn -> onToggleRuneArrowEmptyPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2)*2)
                .size(137, 20)
                .tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runearrow.tooltip")))
                .build());
        setToggleRuneArrowEmptyButtonText();

    }

    public void render(GuiGraphics guiGraphics, int a, int b, float c) {
        //RenderSystem.setShader(GameRenderer::getPositionTexShader);

        //this.renderBackground(guiGraphics);
        super.render(guiGraphics, a, b, c);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(mc.level == null) {
            super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        }
        //guiGraphics.blit(RenderType::guiTextured, BG_LOCATION, getRegularX(), getRegularY(), 0, 0, width, height, 256, 256);
        background.setSize(width, height);
        background.setPosition(getRegularX(), getRegularY());
        background.render(guiGraphics);
    }

    private void onToggleCustomEnchantRenderPress() {
        client.data.toggleCustomEnchantRender = !client.data.toggleCustomEnchantRender;
        setToggleCustomEnchantRenderButtonText();
        client.settings.save();
    }
    private void setToggleCustomEnchantRenderButtonText(){
        if(client.data.toggleCustomEnchantRender) {
            toggleCustomEnchantRenderButton.setMessage(
                    Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.main").append(
                            Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                    ));
        }
        else {
            toggleCustomEnchantRenderButton.setMessage(
                    Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.main").append(
                            Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))
                    ));
        }
    }

    private void onToggleRuneOfFortuneRenderPress() {
        client.data.toggleRuneOFFortuneRender = !client.data.toggleRuneOFFortuneRender;
        setToggleRuneOfFortuneRenderButtonText();
        client.settings.save();
    }
    private void setToggleRuneOfFortuneRenderButtonText(){
        if(client.data.toggleRuneOFFortuneRender) {
            toggleRuneOfFortuneRenderButton.setMessage(
                    Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runeoffortune").append(
                            Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                    ));
        }
        else {
            toggleRuneOfFortuneRenderButton.setMessage(
                    Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runeoffortune").append(
                            Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))
                    ));
        }
    }

    private void onToggleRuneArrowEmptyPress() {
        client.data.toggleRuneArrowEmpty = !client.data.toggleRuneArrowEmpty;
        setToggleRuneArrowEmptyButtonText();

        client.settings.save();
    }
    private void setToggleRuneArrowEmptyButtonText(){
        if(!client.data.toggleRuneArrowEmpty) {
            toggleRuneArrowEmptyButton.setMessage(
                    Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runearrow").append(
                            Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runearrow.fill").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                    ));
        }
        else {
            toggleRuneArrowEmptyButton.setMessage(
                    Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runearrow").append(
                            Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runearrow.empty").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))
                    ));
        }
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
