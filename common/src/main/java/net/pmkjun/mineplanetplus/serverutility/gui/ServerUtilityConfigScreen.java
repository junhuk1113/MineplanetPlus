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
    private Button toggleForceModEnableButton;

    private final int width, height;

    public ServerUtilityConfigScreen(Screen parentScreen){
        super(Component.literal("편의 기능 설정"));
        this.parentScreen = parentScreen;
        this.mc = Minecraft.getInstance();
        this.client = ServerUtilityClient.getInstance();

        this.width = 147;
        this.height = 8 + (20+2) * 4;
    }

    @Override
    protected void init(){
        this.addRenderableWidget(new Button.Builder(Component.translatable("megaphonetimer.key.category"), btn -> onMegaphoneTimerSettingPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2) * 0)
                .size(137, 20)
                .build());

        toggleCurrencyDisplayButton = Button.builder(Component.translatable("serverutility.currency_display"), button -> {
                    toggleCurrencyDisplay();
                }).pos(5+getRegularX(),5+getRegularY()+(20+2)*1)
                .size(137,20)
                .build();
        this.addRenderableWidget(toggleCurrencyDisplayButton);

        toggleFeeCalculatorButton = Button.builder(Component.empty(), button -> {
            toggleFeeCalculator();
        }).pos(5+getRegularX(),5+getRegularY()+(20+2)*2)
                .size(137,20)
                .tooltip(Tooltip.create(Component.translatable("serverutility.fee_calculator.tooltip")))
                .build();
        setToggleFeeCalculatorButtonText();
        this.addRenderableWidget(toggleFeeCalculatorButton);

        toggleForceModEnableButton = Button.builder(Component.empty(), button -> {
                    toggleForceModEnable();
                }).pos(5+getRegularX(),5+getRegularY()+(20+2)*3)
                .size(137,20)
                .tooltip(Tooltip.create(Component.translatable("serverutility.forcemodenable.tooltip")))
                .build();
        setToggleForceModEnableButtonText();
        this.addRenderableWidget(toggleForceModEnableButton);
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);
    }

    private void onMegaphoneTimerSettingPress(){
        mc.setScreen(new MegaphoneTimerConfigScreen(mc.screen));
    }

    private void toggleCurrencyDisplay(){
        mc.setScreen(new CurrencyHudConfigScreen(mc.screen));
    }
    private void toggleFeeCalculator(){
        client.data.toggleFeeCalcalator = !client.data.toggleFeeCalcalator;
        setToggleFeeCalculatorButtonText();
        client.settings.save();
    }
    private void setToggleFeeCalculatorButtonText(){
        if(client.data.toggleFeeCalcalator){
            toggleFeeCalculatorButton.setMessage( Component.translatable("serverutility.fee_calculator").append(
                    Component.translatable("megaphonetimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
        }
        else{
            toggleFeeCalculatorButton.setMessage(Component.translatable("serverutility.fee_calculator").append(
                    Component.translatable("megaphonetimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
        }
    }

    private void toggleForceModEnable(){
        client.data.toggleForceModEnable = !client.data.toggleForceModEnable;
        setToggleForceModEnableButtonText();
        client.settings.save();
    }
    private void setToggleForceModEnableButtonText(){
        if(client.data.toggleForceModEnable){
            toggleForceModEnableButton.setMessage( Component.translatable("serverutility.forcemodenable").append(
                    Component.translatable("megaphonetimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
        }
        else{
            toggleForceModEnableButton.setMessage(Component.translatable("serverutility.forcemodenable").append(
                    Component.translatable("megaphonetimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
        }
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
