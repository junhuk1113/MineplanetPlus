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

public class MegaphoneTimerConfigScreen extends Screen{
    private final Minecraft mc;
    private final ServerUtilityClient client;
    private final StretchableBackground background = new StretchableBackground();
    private final Screen parentScreen;
    
    private Button toggleMegaphonetimerButton;
    private Button toggleAlertSoundButton;
    private Button openPosScreenButton;

    private Slider XPosSlider;
    private Slider YPosSlider;
    private final int width, height;

    public MegaphoneTimerConfigScreen(Screen parentScreen){
        super(Component.literal("확성기 타이머 설정"));
        this.parentScreen = parentScreen;
        this.mc = Minecraft.getInstance();
        this.client = ServerUtilityClient.getInstance();

        this.width = 147;
        this.height = 8 + (20+2) * 5;
    }

    @Override
    protected void init(){
        Component text;
        if(client.data.toggleMegaphonetimer){
            text = Component.translatable("megaphonetimer.config.megaphonetimer").append(
                    Component.translatable("megaphonetimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true)));
        }
        else{
            text = Component.translatable("megaphonetimer.config.megaphonetimer").append(
                    Component.translatable("megaphonetimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true)));
        }
        toggleMegaphonetimerButton = Button.builder(text,button -> {
            toggleMegaphonetimer();
        }).pos(5+getRegularX(), 5+getRegularY())
                .size(137, 20)
                .tooltip(Tooltip.create(Component.translatable("megaphonetimer.config.megaphonetimer.tooltip")))
                .build();
        this.addRenderableWidget(toggleMegaphonetimerButton);

        Component text2;
        if(client.data.toggleAlertSound){
            text2 = Component.translatable("megaphonetimer.config.sound").append(
                    Component.translatable("megaphonetimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true)));
        }
        else{
            text2 = Component.translatable("megaphonetimer.config.sound").append(
                    Component.translatable("megaphonetimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true)));
        }
        toggleAlertSoundButton = Button.builder(text2,button -> {
            toggleAlertSound();
        }).pos(5+getRegularX(),5+getRegularY()+(20+2)*1)
                .size(137,20)
                .tooltip(Tooltip.create(Component.translatable("megaphonetimer.config.sound.tooltip")))
                .build();
        this.addRenderableWidget(toggleAlertSoundButton);

        Button exitButton = Button.builder(Component.translatable("planetskilltimer.config.exit"), button -> {
            mc.setScreen(parentScreen);
        }).pos(mc.getWindow().getGuiScaledWidth() / 2 - 35, mc.getWindow().getGuiScaledHeight() - 22).size(70, 20).build();
        this.addRenderableWidget(exitButton);

        XPosSlider = new Slider(5+getRegularX(), 5+getRegularY()+(20+2)*2,137,20,Component.literal("X : "),Component.literal(""),0,1000,this.client.data.MegaphonetimerXpos,true){
            @Override
            protected void applyValue() {
                client.data.MegaphonetimerXpos = this.getValueInt();
                client.settings.save();
            }
        };
        XPosSlider.setTooltip(Tooltip.create(Component.translatable("mineplanetplus.config.xslider.tooltip")));
        this.addRenderableWidget(XPosSlider);
        YPosSlider = new Slider(5+getRegularX(), 5+getRegularY()+(20+2)*3,137,20,Component.literal("Y : "),Component.literal(""),0,1000,this.client.data.MegaphonetimerYpos,true){
            @Override
            protected void applyValue() {
                client.data.MegaphonetimerYpos = this.getValueInt();
                client.settings.save();
            }
        };
        YPosSlider.setTooltip(Tooltip.create(Component.translatable("mineplanetplus.config.yslider.tooltip")));
        this.addRenderableWidget(YPosSlider);
        openPosScreenButton = Button.builder(Component.translatable("megaphonetimer.config.movepos"), button -> {
            mc.setScreen(new AdjustMegaphoneTimerPosScreen(mc.screen));
        }).pos(5+getRegularX(), 5+getRegularY()+(20+2)*4)
                .size(137, 20)
                .tooltip(Tooltip.create(Component.translatable("megaphonetimer.config.movepos.tooltip")))
                .build();
        this.addRenderableWidget(openPosScreenButton);
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        //this.renderBackground(guiGraphics);
        XPosSlider.render(guiGraphics,mouseX,mouseY,delta);
        YPosSlider.render(guiGraphics,mouseX,mouseY,delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
    }

    private void toggleMegaphonetimer(){
        if(client.data.toggleMegaphonetimer){
            toggleMegaphonetimerButton.setMessage(Component.translatable("megaphonetimer.config.megaphonetimer").append(
                    Component.translatable("megaphonetimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
            client.data.toggleMegaphonetimer = false;
            client.settings.save();
        }
        else{
            toggleMegaphonetimerButton.setMessage(Component.translatable("megaphonetimer.config.megaphonetimer").append(
                    Component.translatable("megaphonetimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
            client.data.toggleMegaphonetimer = true ;
            client.settings.save();
        }
    }

    private void toggleAlertSound(){
        if(client.data.toggleAlertSound){
            toggleAlertSoundButton.setMessage(Component.translatable("megaphonetimer.config.sound").append(
                    Component.translatable("megaphonetimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
            client.data.toggleAlertSound = false;
            client.settings.save();
        }
        else{
            toggleAlertSoundButton.setMessage(Component.translatable("megaphonetimer.config.sound").append(
                    Component.translatable("megaphonetimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
            client.data.toggleAlertSound = true ;
            client.settings.save();
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
    protected void renderBlurredBackground(GuiGraphics guiGraphics) {
        //pass
    }

    @Override
    public void onClose() {
        this.mc.setScreen(parentScreen);
    }
}
