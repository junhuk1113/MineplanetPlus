package net.pmkjun.mineplanetplus.fishhelper.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.gui.widget.Slider;
import net.pmkjun.mineplanetplus.fishhelper.util.FishCounterMode;

public class FishCounterConfigScreen extends Screen{
    private Minecraft mc;
    private FishHelperClient client;
    private final Screen parentScreen;
    private Button toggleFishCounterButton;
    private Button toggleCounterModeButton;
    private Button toggleEarningCalculatorButton;

    private Slider counterXSlider;
    private Slider counterYSlider;

    private final int width;
    private final int height;

    String toggleFishCounter, toggleCounterMode, toggleEarningCalculator, resetCounter;

    public FishCounterConfigScreen(Screen parentScreen) {
        super(Component.translatable("fishhelper.config.title"));
        this.parentScreen = parentScreen;
        this.mc = Minecraft.getInstance();
        this.client = FishHelperClient.getInstance();

        width = 160;
        height = 152;//140+12
    }

    @Override
    protected void init() {
        super.init();
        initButtonkey();

        toggleFishCounterButton = Button.builder(Component.translatable(toggleFishCounter),button -> {
            toggleFishCounter();
        }).pos(getRegularX() + 5, getRegularY()).size(150, 20).build();
        this.addRenderableWidget(toggleFishCounterButton);

        toggleCounterModeButton = Button.builder(Component.translatable(toggleCounterMode),button -> {
            toggleCounterMode();
        }).pos(getRegularX() + 5, getRegularY()+(20+2)).size(150, 20).build();
        this.addRenderableWidget(toggleCounterModeButton);

        toggleEarningCalculatorButton = Button.builder(Component.translatable(toggleEarningCalculator), button ->{
            toggleEarningCalculator();
        }).pos(getRegularX()+5,getRegularY()+(20+2)*2).size(150,20).build();
        this.addRenderableWidget(toggleEarningCalculatorButton);

        this.addRenderableWidget(Button.builder(Component.translatable("fishhelper.config.fishcounter_reset"), button ->{
            client.resetFishCounter();
        }).pos(getRegularX()+5, getRegularY()+(20+2)*3).size(150, 20).build());

        counterXSlider = new Slider(getRegularX() + 5, getRegularY()+(20+2)*5, 150, 20, Component.literal("X : "),1,1000,this.client.data.Counter_xpos){
            @Override
            protected void applyValue() {
                client.data.Counter_xpos = getValueInt();
                client.configManage.save();
            }
        };
        this.addRenderableWidget(counterXSlider);

        counterYSlider = new Slider(getRegularX() + 5, getRegularY()+(20+2)*6, 150, 20, Component.literal("Y : "),1,1000,this.client.data.Counter_ypos){
            @Override
            protected void applyValue() {
                client.data.Counter_ypos = getValueInt();
                client.configManage.save();
            }
        };
        this.addRenderableWidget(counterYSlider);

        this.addRenderableWidget(Button.builder(Component.translatable("fishhelper.config.backbutton"),button -> {
            mc.setScreen(parentScreen);
        }).pos(mc.getWindow().getGuiScaledWidth() / 2 - 25 ,super.height-30).size(50,20).build());
    }

    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawString(this.font, Component.translatable("fishhelper.config.changepos"), getRegularX() + 5, getRegularY()+(20 + 2)*5-10, 0xFFFFFF);

        this.counterXSlider.render(context, mouseX, mouseY, delta);
        this.counterYSlider.render(context, mouseX, mouseY, delta);

        super.render(context, mouseX, mouseY, delta);
    }

    //버튼 눌렀을 때 동작
    private void toggleFishCounter(){
        if(client.data.toggleFishCounter){
            toggleFishCounterButton.setMessage(Component.translatable("fishhelper.config.fishcounter_disable"));
            client.data.toggleFishCounter = false;
        }
        else{
            toggleFishCounterButton.setMessage(Component.translatable("fishhelper.config.fishcounter_enable"));
            client.data.toggleFishCounter = true;
        }
        client.configManage.save();
    }
    
    private void toggleCounterMode(){
        if(client.data.toggleCounterMode == FishCounterMode.PERCENTAGE){
            toggleCounterModeButton.setMessage(Component.translatable("fishhelper.config.fishcounter_setting.mode.count"));
            client.data.toggleCounterMode = FishCounterMode.COUNT;
        }
        else if(client.data.toggleCounterMode == FishCounterMode.COUNT){
            toggleCounterModeButton.setMessage(Component.translatable("fishhelper.config.fishcounter_setting.mode.all"));
            client.data.toggleCounterMode = FishCounterMode.ALL;
        }
        else if(client.data.toggleCounterMode == FishCounterMode.ALL){
            toggleCounterModeButton.setMessage(Component.translatable("fishhelper.config.fishcounter_setting.mode.percentage"));
            client.data.toggleCounterMode = FishCounterMode.PERCENTAGE;
        }
        client.configManage.save();
    }

    private void toggleEarningCalculator(){
        if(client.data.toggleEarningCalculator){
            toggleEarningCalculatorButton.setMessage(Component.translatable("fishhelper.config.fishcounter_setting.toggleEarningCalculator_disable"));
            client.data.toggleEarningCalculator = false;
        }
        else{
            toggleEarningCalculatorButton.setMessage(Component.translatable("fishhelper.config.fishcounter_setting.toggleEarningCalculator_enable"));
            client.data.toggleEarningCalculator = true;
        }
        client.configManage.save();
    }

    void initButtonkey(){
        if(client.data.toggleFishCounter){
            toggleFishCounter = "fishhelper.config.fishcounter_enable";
        }
        else {
            toggleFishCounter = "fishhelper.config.fishcounter_disable";
        }

        if(client.data.toggleCounterMode == FishCounterMode.PERCENTAGE){
            toggleCounterMode = "fishhelper.config.fishcounter_setting.mode.percentage";
        }
        else if(client.data.toggleCounterMode == FishCounterMode.COUNT){
            toggleCounterMode = "fishhelper.config.fishcounter_setting.mode.count";
        }
        else{
            toggleCounterMode = "fishhelper.config.fishcounter_setting.mode.all";
        }

        if(client.data.toggleEarningCalculator){
            toggleEarningCalculator = "fishhelper.config.fishcounter_setting.toggleEarningCalculator_enable";
        }
        else{
            toggleEarningCalculator = "fishhelper.config.fishcounter_setting.toggleEarningCalculator_disable";
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
