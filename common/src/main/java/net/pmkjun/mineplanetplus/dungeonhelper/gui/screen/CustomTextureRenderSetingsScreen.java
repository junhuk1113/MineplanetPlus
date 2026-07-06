package net.pmkjun.mineplanetplus.dungeonhelper.gui.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.gui.StretchableBackground;

public class CustomTextureRenderSetingsScreen extends Screen {

    private final DungeonHelperClient client;
    private final StretchableBackground background = new StretchableBackground();

    private Button toggleCustomEnchantRenderButton;
    private Button toggleRuneOfFortuneRenderButton;
    private Button toggleRuneArrowEmptyButton;

    // 변수 가림(Shadowing) 방지를 위해 이름 변경
    private final int bgWidth, bgHeight;
    private final Screen parentScreen;

    protected CustomTextureRenderSetingsScreen(Screen parentScreen) {
        super(Component.literal("CustomTextureRenderSetingsScreen"));

        this.client = DungeonHelperClient.getInstance();
        this.parentScreen = parentScreen;

        this.bgWidth = 147;
        this.bgHeight = 74;
    }

    @Override
    protected void init() {
        super.init();

        Component toggleCustomEnchantRenderButtonComponent;
        if (client.data.toggleCustomEnchantRender)
            toggleCustomEnchantRenderButtonComponent =
                    Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.main").append(
                            Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                    );
        else
            toggleCustomEnchantRenderButtonComponent =
                    Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.main").append(
                            Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))
                    );

        toggleCustomEnchantRenderButton = this.addRenderableWidget(new Button.Builder(toggleCustomEnchantRenderButtonComponent, btn -> onToggleCustomEnchantRenderPress())
                .pos(getRegularX() + 5, getRegularY() + 5)
                .size(137, 20)
                .tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.main.tooltip")))
                .build());

        Component toggleRuneOfFortuneRenderButtonComponent;
        if (client.data.toggleRuneOFFortuneRender)
            toggleRuneOfFortuneRenderButtonComponent =
                    Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runeoffortune").append(
                            Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                    );
        else
            toggleRuneOfFortuneRenderButtonComponent =
                    Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runeoffortune").append(
                            Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))
                    );

        toggleRuneOfFortuneRenderButton = this.addRenderableWidget(new Button.Builder(toggleRuneOfFortuneRenderButtonComponent, btn -> onToggleRuneOfFortuneRenderPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2))
                .size(137, 20)
                .tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runeoffortune.tooltip")))
                .build());

        Component toggleRuneArrowEmptyButtonComponent;
        if (!client.data.toggleRuneArrowEmpty)
            toggleRuneArrowEmptyButtonComponent =
                    Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runearrow").append(
                            Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runearrow.fill").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                    );
        else
            toggleRuneArrowEmptyButtonComponent =
                    Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runearrow").append(
                            Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runearrow.empty").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))
                    );

        toggleRuneArrowEmptyButton = this.addRenderableWidget(new Button.Builder(toggleRuneArrowEmptyButtonComponent, btn -> onToggleRuneArrowEmptyPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2)*2)
                .size(137, 20)
                .tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runearrow.tooltip")))
                .build());
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
        background.setSize(this.bgWidth, this.bgHeight);
        background.setPosition(getRegularX(), getRegularY());
        background.extractRenderState(guiGraphics);
    }

    @Override
    protected void extractBlurredBackground(GuiGraphicsExtractor guiGraphics) {
        // pass
    }

    private void onToggleCustomEnchantRenderPress() {
        client.data.toggleCustomEnchantRender = !client.data.toggleCustomEnchantRender;

        if (client.data.toggleCustomEnchantRender) {
            toggleCustomEnchantRenderButton.setMessage(
                    Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.main").append(
                            Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                    ));
        } else {
            toggleCustomEnchantRenderButton.setMessage(
                    Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.main").append(
                            Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))
                    ));
        }
        client.settings.save();
    }

    private void onToggleRuneOfFortuneRenderPress() {
        client.data.toggleRuneOFFortuneRender = !client.data.toggleRuneOFFortuneRender;

        if (client.data.toggleRuneOFFortuneRender) {
            toggleRuneOfFortuneRenderButton.setMessage(
                    Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runeoffortune").append(
                            Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                    ));
        } else {
            toggleRuneOfFortuneRenderButton.setMessage(
                    Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runeoffortune").append(
                            Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))
                    ));
        }
        client.settings.save();
    }

    private void onToggleRuneArrowEmptyPress() {
        client.data.toggleRuneArrowEmpty = !client.data.toggleRuneArrowEmpty;

        if (!client.data.toggleRuneArrowEmpty) {
            toggleRuneArrowEmptyButton.setMessage(
                    Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runearrow").append(
                            Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runearrow.fill").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                    ));
        } else {
            toggleRuneArrowEmptyButton.setMessage(
                    Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runearrow").append(
                            Component.translatable("gui.dungeonhelper.custom_enchant_render_settings.runearrow.empty").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))
                    ));
        }
        client.settings.save();
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