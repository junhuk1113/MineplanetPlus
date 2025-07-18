package net.pmkjun.mineplanetplus.planetskilltimer.config;

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
import net.pmkjun.mineplanetplus.planetskilltimer.PlanetSkillTimerClient;

public class SkillTimerConfigScreen extends Screen{

    private final Minecraft mc;
    private final PlanetSkillTimerClient client;
    private final Screen parentScreen;
    private final StretchableBackground background = new StretchableBackground();

    private Button toggleSkillTimerButton;
    private Button toggleAlertSoundButton;
    private Button[] toggleSkillsButton = new Button[4];
    String[] SkillList = {"farming","felling","mining","digging"};
    private Slider XPosSlider;
    private Slider YPosSlider;
    private final int width, height;

    public SkillTimerConfigScreen(Screen parentScreen) {
        super(Component.literal("스킬 타이머 설정"));
        this.parentScreen = parentScreen;
        this.mc = Minecraft.getInstance();
        this.client = PlanetSkillTimerClient.getInstance();

        this.width = 147;
        this.height = 8 + (20 + 2) * 8;
    }
    @Override
    protected void init() {
        toggleSkillTimerButton = Button.builder(Component.empty(),button -> {
            toggleSkilltimer();
        }).pos(5+getRegularX(), 5+getRegularY())
                .size(137, 20)
                .tooltip(Tooltip.create(Component.translatable("planetskilltimer.config.skilltimer.tooltip")))
                .build();
        setSkillTimerButtonText();
        this.addRenderableWidget(toggleSkillTimerButton);

        toggleAlertSoundButton = Button.builder(Component.empty(),button -> {
            toggleAlertSound();
        }).pos(5+getRegularX(),5+getRegularY()+(20+2)*1)
                .size(137,20)
                .tooltip(Tooltip.create(Component.translatable("planetskilltimer.config.sound.tooltip")))
                .build();
        setToggleAlertSoundButtonText();
        this.addRenderableWidget(toggleAlertSoundButton);

        for(int i = 0; i < 4 ; i++){
            switch (i){
                case 0:
                    toggleSkillsButton[i] = Button.builder(Component.empty(),button -> {
                        toggleSkills(0);
                    }).pos(5+getRegularX(),5+getRegularY()+(20+2)*(i+2))
                            .size(137,20)
                            .tooltip(Tooltip.create(Component.translatable("planetskilltimer.config.farming.tooltip")))
                            .build();
                    break;
                case 1:
                    toggleSkillsButton[i] = Button.builder(Component.empty(),button -> {
                        toggleSkills(1);
                    }).pos(5+getRegularX(),5+getRegularY()+(20+2)*(i+2))
                            .size(137,20)
                            .tooltip(Tooltip.create(Component.translatable("planetskilltimer.config.felling.tooltip")))
                            .build();
                    break;
                case 2:
                    toggleSkillsButton[i] = Button.builder(Component.empty(),button -> {
                        toggleSkills(2);
                    }).pos(5+getRegularX(),5+getRegularY()+(20+2)*(i+2))
                            .size(137,20)
                            .tooltip(Tooltip.create(Component.translatable("planetskilltimer.config.mining.tooltip")))
                            .build();
                    break;
                case 3:
                    toggleSkillsButton[i] = Button.builder(Component.empty(),button -> {
                        toggleSkills(3);
                    }).pos(5+getRegularX(),5+getRegularY()+(20+2)*(i+2))
                            .size(137,20)
                            .tooltip(Tooltip.create(Component.translatable("planetskilltimer.config.digging.tooltip")))
                            .build();
                    break;

            }
            setSkillsButtonText(i);
            this.addRenderableWidget(toggleSkillsButton[i]);
        }

        Button exitButton = Button.builder(Component.translatable("planetskilltimer.config.exit"), button -> {
            mc.setScreen(parentScreen);
        }).pos(mc.getWindow().getGuiScaledWidth() / 2 - 35, mc.getWindow().getGuiScaledHeight() - 22).size(70, 20).build();
        this.addRenderableWidget(exitButton);

        XPosSlider = new Slider(5+getRegularX(), 5+getRegularY()+(20+2)*6,137,20,Component.literal("X : "),Component.literal(""),0,1000,this.client.data.SkillTimerXpos,true){
            @Override
            protected void applyValue() {
                client.data.SkillTimerXpos = this.getValueInt();
                client.configManage.save();
            }
        };
        XPosSlider.setTooltip(Tooltip.create(Component.translatable("mineplanetplus.config.xslider.tooltip")));
        this.addRenderableWidget(XPosSlider);
        YPosSlider = new Slider(5+getRegularX(), 5+getRegularY()+(20+2)*7,137,20,Component.literal("Y : "),Component.literal(""),0,1000,this.client.data.SkillTimerYpos,true){
            @Override
            protected void applyValue() {
                client.data.SkillTimerYpos = this.getValueInt();
                client.configManage.save();
            }
        };
        YPosSlider.setTooltip(Tooltip.create(Component.translatable("mineplanetplus.config.yslider.tooltip")));
        this.addRenderableWidget(YPosSlider);
    }
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        //this.renderBackground(guiGraphics);
        XPosSlider.render(guiGraphics,mouseX,mouseY,delta);
        YPosSlider.render(guiGraphics,mouseX,mouseY,delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
    }

    private void toggleSkilltimer(){
        if(client.data.toggleSkilltimer){
            client.data.toggleSkilltimer = false;
        }
        else{
            client.data.toggleSkilltimer = true ;
        }
        setSkillTimerButtonText();
        this.client.configManage.save();
    }
    private void setSkillTimerButtonText(){
        if(client.data.toggleSkilltimer){
            toggleSkillTimerButton.setMessage(Component.translatable("planetskilltimer.config.skilltimer").append(
                    Component.translatable("planetskilltimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
        }
        else{
            toggleSkillTimerButton.setMessage(Component.translatable("planetskilltimer.config.skilltimer").append(
                    Component.translatable("planetskilltimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
        }
    }

    private void toggleAlertSound(){
        if(client.data.toggleAlertSound){
            client.data.toggleAlertSound = false;
        }
        else{
            client.data.toggleAlertSound = true ;
        }
        setToggleAlertSoundButtonText();
        client.configManage.save();
    }
    private void setToggleAlertSoundButtonText(){
        if(client.data.toggleAlertSound){
            toggleAlertSoundButton.setMessage(Component.translatable("planetskilltimer.config.sound").append(
                    Component.translatable("planetskilltimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
        }
        else{
            toggleAlertSoundButton.setMessage(Component.translatable("planetskilltimer.config.sound").append(
                    Component.translatable("planetskilltimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
        }
    }

    private void toggleSkills(int skilltype){
        if(client.data.toggleSkills[skilltype]){
            client.data.toggleSkills[skilltype] = false;
        }
        else{
            client.data.toggleSkills[skilltype] = true ;
        }
        setSkillsButtonText(skilltype);
        client.configManage.save();
    }
    private void setSkillsButtonText(int skilltype){
        if(client.data.toggleSkills[skilltype]){
            toggleSkillsButton[skilltype].setMessage(Component.translatable("planetskilltimer.config."+SkillList[skilltype]).append(
                    Component.translatable("planetskilltimer.config.enable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
        }
        else{
            toggleSkillsButton[skilltype].setMessage(Component.translatable("planetskilltimer.config."+SkillList[skilltype]).append(
                    Component.translatable("planetskilltimer.config.disable").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
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
