package net.pmkjun.mineplanetplus.serverutility.gui;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.pmkjun.mineplanetplus.gui.StretchableBackground;
import net.pmkjun.mineplanetplus.serverutility.ServerUtilityClient;

public class ServerUtilityConfigScreen extends Screen{
    private final Minecraft mc;
    private final ServerUtilityClient client;
    private final StretchableBackground background = new StretchableBackground();
    private final Screen parentScreen;

    private Button toggleCurrencyDisplayButton;
    private Button toggleFeeCalculatorButton;

    private final int width, height;

    public ServerUtilityConfigScreen(Screen parentScreen){
        super(Component.literal("편의 기능 설정"));
        this.parentScreen = parentScreen;
        this.mc = Minecraft.getInstance();
        this.client = ServerUtilityClient.getInstance();

        this.width = 147;
        this.height = 8 + (20+2) * 3;
    }

    @Override
    protected void init(){
        this.addRenderableWidget(new Button.Builder(Component.translatable("megaphonetimer.key.category"), btn -> onMegaphoneTimerSettingPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2) * 0)
                .size(137, 20)
                .build());


        Component text;
        if(client.data.toggleCurrencyDisplay){
            text = Component.translatable("serverutility.currency_display").append(
                    Component.translatable("megaphonetimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true)));
        }
        else{
            text =  Component.translatable("serverutility.currency_display").append(
                    Component.translatable("megaphonetimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true)));
        }
        toggleCurrencyDisplayButton = Button.builder(text, button -> {
                    toggleCurrencyDisplay();
                }).pos(5+getRegularX(),5+getRegularY()+(20+2)*1)
                .size(137,20)
                .tooltip(Tooltip.create(Component.translatable("serverutility.currency_display.tooltip")))
                .build();
        this.addRenderableWidget(toggleCurrencyDisplayButton);

        Component text2;
        if (client.data.toggleFeeCalcalator) {
            text2 = Component.translatable("serverutility.fee_calculator").append(
                    Component.translatable("megaphonetimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true)));
        }
        else{
            text2 = Component.translatable("serverutility.fee_calculator").append(
                    Component.translatable("megaphonetimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true)));
        }
        toggleFeeCalculatorButton = Button.builder(text2, button -> {
            toggleFeeCalculator();
        }).pos(5+getRegularX(),5+getRegularY()+(20+2)*2)
                .size(137,20)
                .tooltip(Tooltip.create(Component.translatable("serverutility.fee_calculator.tooltip")))
                .build();
        this.addRenderableWidget(toggleFeeCalculatorButton);
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);
    }

    private void onMegaphoneTimerSettingPress(){
        mc.setScreen(new MegaphoneTimerConfigScreen(mc.screen));
    }

    private void toggleCurrencyDisplay(){
        if(client.data.toggleCurrencyDisplay){
            toggleCurrencyDisplayButton.setMessage(Component.translatable("serverutility.currency_display").append(
                    Component.translatable("megaphonetimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
            client.data.toggleCurrencyDisplay = false;
        }
        else{
            toggleCurrencyDisplayButton.setMessage( Component.translatable("serverutility.currency_display").append(
                    Component.translatable("megaphonetimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
            client.data.toggleCurrencyDisplay = true;
        }
        client.settings.save();
    }
    private void toggleFeeCalculator(){
        if(client.data.toggleFeeCalcalator){
            toggleFeeCalculatorButton.setMessage(Component.translatable("serverutility.fee_calculator").append(
                    Component.translatable("megaphonetimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
            client.data.toggleFeeCalcalator = false;
        }
        else{
            toggleFeeCalculatorButton.setMessage( Component.translatable("serverutility.fee_calculator").append(
                    Component.translatable("megaphonetimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
            client.data.toggleFeeCalcalator = true;
        }
        client.settings.save();
    }

    int getRegularX() {
        return  mc.getWindow().getGuiScaledWidth() / 2 - width / 2;
    }

    int getRegularY() {
        return mc.getWindow().getGuiScaledHeight() / 2 - height / 2;
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(mc.level == null) {
            super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        }
        background.setSize(width, height);
        background.setPosition(getRegularX(), getRegularY());
        background.render(guiGraphics);
    }

    @Override
    public void onClose() {
        this.mc.setScreen(parentScreen);
    }
}
