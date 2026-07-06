package net.pmkjun.mineplanetplus.serverutility.gui;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.pmkjun.mineplanetplus.gui.StretchableBackground;
import net.pmkjun.mineplanetplus.serverutility.ServerUtilityClient;

public class ServerUtilityConfigScreen extends Screen {
    private final Minecraft mc;
    private final ServerUtilityClient client;
    private final StretchableBackground background = new StretchableBackground();
    private final Screen parentScreen;

    private Button toggleCurrencyDisplayButton;
    private Button toggleFeeCalculatorButton;
    private Button toggleForceModEnableButton;

    // 변수 가림 방지
    private final int bgWidth, bgHeight;

    public ServerUtilityConfigScreen(Screen parentScreen) {
        super(Component.literal("편의 기능 설정"));
        this.parentScreen = parentScreen;
        this.mc = Minecraft.getInstance();
        this.client = ServerUtilityClient.getInstance();

        this.bgWidth = 147;
        this.bgHeight = 8 + (20+2) * 4;
    }

    @Override
    protected void init() {
        this.addRenderableWidget(new Button.Builder(Component.translatable("megaphonetimer.key.category"), btn -> onMegaphoneTimerSettingPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2) * 0)
                .size(137, 20)
                .build());

        toggleCurrencyDisplayButton = Button.builder(Component.translatable("serverutility.currency_display"), button -> toggleCurrencyDisplay())
                .pos(5+getRegularX(),5+getRegularY()+(20+2)*1)
                .size(137,20)
                .build();
        this.addRenderableWidget(toggleCurrencyDisplayButton);

        Component text2;
        if (client.data.toggleFeeCalcalator) {
            text2 = Component.translatable("serverutility.fee_calculator").append(
                    Component.translatable("megaphonetimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true)));
        } else {
            text2 = Component.translatable("serverutility.fee_calculator").append(
                    Component.translatable("megaphonetimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true)));
        }
        toggleFeeCalculatorButton = Button.builder(text2, button -> toggleFeeCalculator())
                .pos(5+getRegularX(),5+getRegularY()+(20+2)*2)
                .size(137,20)
                .tooltip(Tooltip.create(Component.translatable("serverutility.fee_calculator.tooltip")))
                .build();
        this.addRenderableWidget(toggleFeeCalculatorButton);

        Component text3;
        if (client.data.toggleForceModEnable) {
            text3 = Component.translatable("serverutility.forcemodenable").append(
                    Component.translatable("megaphonetimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true)));
        } else {
            text3 = Component.translatable("serverutility.forcemodenable").append(
                    Component.translatable("megaphonetimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true)));
        }
        toggleForceModEnableButton = Button.builder(text3, button -> toggleForceModEnable())
                .pos(5+getRegularX(),5+getRegularY()+(20+2)*3)
                .size(137,20)
                .tooltip(Tooltip.create(Component.translatable("serverutility.forcemodenable.tooltip")))
                .build();
        this.addRenderableWidget(toggleForceModEnableButton);
    }

    // 26.1: render -> extractRenderState
    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float delta) {
        this.extractBackground(guiGraphics, mouseX, mouseY, delta);
        super.extractRenderState(guiGraphics, mouseX, mouseY, delta);
    }

    private void onMegaphoneTimerSettingPress() {
        mc.setScreen(new MegaphoneTimerConfigScreen(mc.screen));
    }

    private void toggleCurrencyDisplay() {
        mc.setScreen(new CurrencyHudConfigScreen(mc.screen));
    }

    private void toggleFeeCalculator() {
        if(client.data.toggleFeeCalcalator) {
            toggleFeeCalculatorButton.setMessage(Component.translatable("serverutility.fee_calculator").append(
                    Component.translatable("megaphonetimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
            client.data.toggleFeeCalcalator = false;
        } else {
            toggleFeeCalculatorButton.setMessage( Component.translatable("serverutility.fee_calculator").append(
                    Component.translatable("megaphonetimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
            client.data.toggleFeeCalcalator = true;
        }
        client.settings.save();
    }

    private void toggleForceModEnable() {
        if(client.data.toggleForceModEnable) {
            toggleForceModEnableButton.setMessage(Component.translatable("serverutility.forcemodenable").append(
                    Component.translatable("megaphonetimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
            client.data.toggleForceModEnable = false;
        } else {
            toggleForceModEnableButton.setMessage(Component.translatable("serverutility.forcemodenable").append(
                    Component.translatable("megaphonetimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
            client.data.toggleForceModEnable = true;
        }
        client.settings.save();
    }

    int getRegularX() {
        return this.mc.getWindow().getGuiScaledWidth() / 2 - this.bgWidth / 2;
    }

    int getRegularY() {
        return this.mc.getWindow().getGuiScaledHeight() / 2 - this.bgHeight / 2;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(mc.level == null) {
            super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        }
        background.setSize(this.bgWidth, this.bgHeight);
        background.setPosition(getRegularX(), getRegularY());
        background.extractRenderState(guiGraphics);
    }

    @Override
    protected void extractBlurredBackground(GuiGraphicsExtractor guiGraphics) {
        //pass
    }

    @Override
    public void onClose() {
        this.mc.setScreen(parentScreen);
    }
}