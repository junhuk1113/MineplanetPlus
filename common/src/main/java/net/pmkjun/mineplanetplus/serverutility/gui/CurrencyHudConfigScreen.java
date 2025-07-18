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
import net.pmkjun.mineplanetplus.gui.components.Slider;
import net.pmkjun.mineplanetplus.serverutility.ServerUtilityClient;

public class CurrencyHudConfigScreen extends Screen{
    private final Minecraft mc;
    private final ServerUtilityClient client;
    private final StretchableBackground background = new StretchableBackground();
    private final Screen parentScreen;

    private Button toggleCurrencyDisplayButton;
    private Button toggleCurrencyDisplayCustomposButton;

    private Slider XPosSlider;
    private Slider YPosSlider;
    private final int width, height;

    public CurrencyHudConfigScreen(Screen parentScreen){
        super(Component.literal("재화 표시 설정"));
        this.parentScreen = parentScreen;
        this.mc = Minecraft.getInstance();
        this.client = ServerUtilityClient.getInstance();

        this.width = 147;
        this.height = 8 + (20+2) * 4;
    }

    @Override
    protected void init(){
        toggleCurrencyDisplayButton = Button.builder(Component.empty(), button -> {
                    toggleCurrencyDisplay();
                }).pos(5+getRegularX(),5+getRegularY()+(20+2)*0)
                .size(137,20)
                .tooltip(Tooltip.create(Component.translatable("serverutility.currency_display.main.tooltip")))
                .build();
        setToggleCurrencyDisplayButtonText();
        this.addRenderableWidget(toggleCurrencyDisplayButton);

        toggleCurrencyDisplayCustomposButton = Button.builder(Component.empty(), button -> {
                    toggleCurrencyDisplayCustompos();
                }).pos(5+getRegularX(),5+getRegularY()+(20+2)*1)
                .size(137,20)
                .tooltip(Tooltip.create(Component.translatable("serverutility.currency_display.customguipos.tooltip")))
                .build();
        setToggleCurrencyDisplayCustomposButtonText();
        this.addRenderableWidget(toggleCurrencyDisplayCustomposButton);

        Button exitButton = Button.builder(Component.translatable("planetskilltimer.config.exit"), button -> {
            mc.setScreen(parentScreen);
        }).pos(mc.getWindow().getGuiScaledWidth() / 2 - 35, mc.getWindow().getGuiScaledHeight() - 22).size(70, 20).build();
        this.addRenderableWidget(exitButton);

        XPosSlider = new Slider(5+getRegularX(), 5+getRegularY()+(20+2)*2,137,20,Component.literal("X : "),Component.literal(""),0,1000,this.client.data.CurrencyDisplayXpos,true){
            @Override
            protected void applyValue() {
                client.data.CurrencyDisplayXpos = this.getValueInt();
                client.settings.save();
            }
        };
        XPosSlider.setTooltip(Tooltip.create(Component.translatable("mineplanetplus.config.xslider.tooltip")));
        this.addRenderableWidget(XPosSlider);
        YPosSlider = new Slider(5+getRegularX(), 5+getRegularY()+(20+2)*3,137,20,Component.literal("Y : "),Component.literal(""),0,1000,this.client.data.CurrencyDisplayYpos,true){
            @Override
            protected void applyValue() {
                client.data.CurrencyDisplayYpos = this.getValueInt();

                client.settings.save();
            }
        };
        YPosSlider.setTooltip(Tooltip.create(Component.translatable("mineplanetplus.config.yslider.tooltip")));
        this.addRenderableWidget(YPosSlider);
    }

    private void toggleCurrencyDisplay(){
        client.data.toggleCurrencyDisplay = !client.data.toggleCurrencyDisplay;
        setToggleCurrencyDisplayButtonText();
        client.settings.save();
    }
    private void setToggleCurrencyDisplayButtonText(){
        if(client.data.toggleCurrencyDisplay){
            toggleCurrencyDisplayButton.setMessage( Component.translatable("serverutility.currency_display.main").append(
                    Component.translatable("megaphonetimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
        }
        else{
            toggleCurrencyDisplayButton.setMessage(Component.translatable("serverutility.currency_display.main").append(
                    Component.translatable("megaphonetimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
        }
    }

    private void toggleCurrencyDisplayCustompos(){
        client.data.toggleCurrencyDisplayCustompos = !client.data.toggleCurrencyDisplayCustompos;
        setToggleCurrencyDisplayCustomposButtonText();
        client.settings.save();
    }
    private void setToggleCurrencyDisplayCustomposButtonText(){
        if(client.data.toggleCurrencyDisplayCustompos){
            toggleCurrencyDisplayCustomposButton.setMessage(Component.translatable("serverutility.currency_display.customguipos").append(
                    Component.translatable("megaphonetimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
        }
        else{
            toggleCurrencyDisplayCustomposButton.setMessage(Component.translatable("serverutility.currency_display.customguipos").append(
                    Component.translatable("megaphonetimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
        }
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        //this.renderBackground(guiGraphics);
        XPosSlider.render(guiGraphics,mouseX,mouseY,delta);
        YPosSlider.render(guiGraphics,mouseX,mouseY,delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
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
