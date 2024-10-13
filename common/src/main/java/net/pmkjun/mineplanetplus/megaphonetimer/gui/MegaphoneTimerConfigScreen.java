package net.pmkjun.mineplanetplus.megaphonetimer.gui;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.pmkjun.mineplanetplus.megaphonetimer.MegaphoneTimerClient;
import net.pmkjun.mineplanetplus.planetskilltimer.gui.widget.Slider;

public class MegaphoneTimerConfigScreen extends Screen{
    private Minecraft mc;
    private MegaphoneTimerClient client;
    private final Screen parentScreen;
    
    private Button toggleMegaphonetimerButton;
    private Button toggleAlertSoundButton;

    private Slider XPosSlider;
    private Slider YPosSlider;

    public MegaphoneTimerConfigScreen(Screen parentScreen){
        super(Component.literal("확성기 타이머 설정"));
        this.parentScreen = parentScreen;
        this.mc = Minecraft.getInstance();
        this.client = MegaphoneTimerClient.getInstance();

        this.width = 150;
        this.height = (20+2) * 4;
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
        }).pos(getRegularX(), getRegularY()).size(150, 20).build();
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
        }).pos(getRegularX(),getRegularY()+(20+2)*1).size(150,20).build();
        this.addRenderableWidget(toggleAlertSoundButton);

        Button exitButton = Button.builder(Component.translatable("planetskilltimer.config.exit"), button -> {
            mc.setScreen(parentScreen);
        }).pos(mc.getWindow().getGuiScaledWidth() / 2 - 35, mc.getWindow().getGuiScaledHeight() - 22).size(70, 20).build();
        this.addRenderableWidget(exitButton);

        XPosSlider = new Slider(getRegularX(), getRegularY()+(20+2)*2,150,20,Component.literal("X : "),0,1000,this.client.data.MegaphonetimerXpos){
            @Override
            protected void applyValue() {
                client.data.MegaphonetimerXpos = this.getValueInt();
                client.settings.save();
            }
        };
        this.addRenderableWidget(XPosSlider);
        YPosSlider = new Slider(getRegularX(), getRegularY()+(20+2)*3,150,20,Component.literal("Y : "),0,1000,this.client.data.MegaphonetimerYpos){
            @Override
            protected void applyValue() {
                client.data.MegaphonetimerYpos = this.getValueInt();
                client.settings.save();
            }
        };
        this.addRenderableWidget(YPosSlider);
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics);
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
    public void onClose() {
        this.mc.setScreen(parentScreen);
    }
}
