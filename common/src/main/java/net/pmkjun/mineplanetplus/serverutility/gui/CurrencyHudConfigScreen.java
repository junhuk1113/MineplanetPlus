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
import net.pmkjun.mineplanetplus.gui.components.Slider;
import net.pmkjun.mineplanetplus.serverutility.ServerUtilityClient;

public class CurrencyHudConfigScreen extends Screen {
    private final Minecraft mc;
    private final ServerUtilityClient client;
    private final StretchableBackground background = new StretchableBackground();
    private final Screen parentScreen;

    private Button toggleCurrencyDisplayButton;
    private Button toggleCurrencyDisplayCustomposButton;

    private Slider XPosSlider;
    private Slider YPosSlider;

    // 변수 가림 방지
    private final int bgWidth, bgHeight;

    public CurrencyHudConfigScreen(Screen parentScreen) {
        super(Component.literal("재화 표시 설정"));
        this.parentScreen = parentScreen;
        this.mc = Minecraft.getInstance();
        this.client = ServerUtilityClient.getInstance();

        this.bgWidth = 147;
        this.bgHeight = 8 + (20+2) * 4;
    }

    @Override
    protected void init() {
        Component text;
        if(client.data.toggleCurrencyDisplay) {
            text = Component.translatable("serverutility.currency_display.main").append(
                    Component.translatable("megaphonetimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true)));
        } else {
            text =  Component.translatable("serverutility.currency_display.main").append(
                    Component.translatable("megaphonetimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true)));
        }
        toggleCurrencyDisplayButton = Button.builder(text, button -> toggleCurrencyDisplay())
                .pos(5+getRegularX(),5+getRegularY()+(20+2)*0)
                .size(137,20)
                .tooltip(Tooltip.create(Component.translatable("serverutility.currency_display.main.tooltip")))
                .build();
        this.addRenderableWidget(toggleCurrencyDisplayButton);

        Component text2;
        if(client.data.toggleCurrencyDisplayCustompos) {
            text2 = Component.translatable("serverutility.currency_display.customguipos").append(
                    Component.translatable("megaphonetimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true)));
        } else {
            text2 =  Component.translatable("serverutility.currency_display.customguipos").append(
                    Component.translatable("megaphonetimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true)));
        }
        toggleCurrencyDisplayCustomposButton = Button.builder(text2, button -> toggleCurrencyDisplayCustompos())
                .pos(5+getRegularX(),5+getRegularY()+(20+2)*1)
                .size(137,20)
                .tooltip(Tooltip.create(Component.translatable("serverutility.currency_display.customguipos.tooltip")))
                .build();
        this.addRenderableWidget(toggleCurrencyDisplayCustomposButton);

        Button exitButton = Button.builder(Component.translatable("planetskilltimer.config.exit"), button -> {
            mc.setScreen(parentScreen);
        }).pos(mc.getWindow().getGuiScaledWidth() / 2 - 35, mc.getWindow().getGuiScaledHeight() - 22).size(70, 20).build();
        this.addRenderableWidget(exitButton);

        XPosSlider = new Slider(5+getRegularX(), 5+getRegularY()+(20+2)*2,137,20,Component.literal("X : "),Component.literal(""),0,1000,this.client.data.CurrencyDisplayXpos,true) {
            @Override
            protected void applyValue() {
                client.data.CurrencyDisplayXpos = this.getValueInt();
                client.settings.save();
            }
        };
        XPosSlider.setTooltip(Tooltip.create(Component.translatable("mineplanetplus.config.xslider.tooltip")));
        this.addRenderableWidget(XPosSlider);

        YPosSlider = new Slider(5+getRegularX(), 5+getRegularY()+(20+2)*3,137,20,Component.literal("Y : "),Component.literal(""),0,1000,this.client.data.CurrencyDisplayYpos,true) {
            @Override
            protected void applyValue() {
                client.data.CurrencyDisplayYpos = this.getValueInt();
                client.settings.save();
            }
        };
        YPosSlider.setTooltip(Tooltip.create(Component.translatable("mineplanetplus.config.yslider.tooltip")));
        this.addRenderableWidget(YPosSlider);
    }

    private void toggleCurrencyDisplay() {
        if(client.data.toggleCurrencyDisplay) {
            toggleCurrencyDisplayButton.setMessage(Component.translatable("serverutility.currency_display.main").append(
                    Component.translatable("megaphonetimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
            client.data.toggleCurrencyDisplay = false;
        } else {
            toggleCurrencyDisplayButton.setMessage( Component.translatable("serverutility.currency_display.main").append(
                    Component.translatable("megaphonetimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
            client.data.toggleCurrencyDisplay = true;
        }
        client.settings.save();
    }

    private void toggleCurrencyDisplayCustompos() {
        if(client.data.toggleCurrencyDisplayCustompos) {
            toggleCurrencyDisplayCustomposButton.setMessage(Component.translatable("serverutility.currency_display.customguipos").append(
                    Component.translatable("megaphonetimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
            client.data.toggleCurrencyDisplayCustompos = false;
        } else {
            toggleCurrencyDisplayCustomposButton.setMessage( Component.translatable("serverutility.currency_display.customguipos").append(
                    Component.translatable("megaphonetimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
            client.data.toggleCurrencyDisplayCustompos = true;
        }
        client.settings.save();
    }

    // 26.1: render -> extractRenderState
    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float delta) {
        this.extractBackground(guiGraphics, mouseX, mouseY, delta);
        super.extractRenderState(guiGraphics, mouseX, mouseY, delta); // 위젯 자동 추출
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