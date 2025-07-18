package net.pmkjun.mineplanetplus.fishhelper.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.util.FishCounterMode;
import net.pmkjun.mineplanetplus.gui.StretchableBackground;
import net.pmkjun.mineplanetplus.gui.components.Slider;

public class FishCounterConfigScreen extends Screen{
    private final Minecraft mc;
    private final FishHelperClient client;
    private final StretchableBackground background = new StretchableBackground();
    private final Screen parentScreen;
    private Button toggleFishCounterButton;
    private Button toggleCounterModeButton;
    private Button toggleEarningCalculatorButton;

    private Slider counterXSlider;
    private Slider counterYSlider;

    private final int width;
    private final int height;

    public FishCounterConfigScreen(Screen parentScreen) {
        super(Component.translatable("fishhelper.config.title"));
        this.parentScreen = parentScreen;
        this.mc = Minecraft.getInstance();
        this.client = FishHelperClient.getInstance();

        width = 147;
        height = 162;
    }

    @Override
    protected void init() {
        super.init();

        toggleFishCounterButton = Button.builder(Component.empty(),button -> {
            toggleFishCounter();
        }).pos(getRegularX() + 5, 5+getRegularY())
                .size(137, 20)
                .tooltip(Tooltip.create(Component.translatable("fishhelper.config.fishcounter.tooltip")))
                .build();
        setToggleFishCounterButtonText();
        this.addRenderableWidget(toggleFishCounterButton);

        toggleCounterModeButton = Button.builder(Component.empty(),button -> {
            toggleCounterMode();
        }).pos(getRegularX() + 5, 5+getRegularY()+(20+2))
                .size(137, 20)
                .tooltip(Tooltip.create(Component.translatable("fishhelper.config.fishcounter.mode.tooltip")))
                .build();
        setToggleCounterModeButtonText();
        this.addRenderableWidget(toggleCounterModeButton);

        toggleEarningCalculatorButton = Button.builder(Component.empty(), button ->{
            toggleEarningCalculator();
        }).pos(getRegularX()+5,5+getRegularY()+(20+2)*2)
                .size(137,20)
                .tooltip(Tooltip.create(Component.translatable("fishhelper.config.fishcounter.toggleEarningCalculator.tooltip")))
                .build();
        setToggleEarningCalculatorButtonText();
        this.addRenderableWidget(toggleEarningCalculatorButton);

        this.addRenderableWidget(Button.builder(Component.translatable("fishhelper.config.fishcounter_reset"), button ->{
            client.resetFishCounter();
        }).pos(getRegularX()+5, 5+getRegularY()+(20+2)*3).size(137, 20)
                .tooltip(Tooltip.create(Component.translatable("fishhelper.config.fishcounter_reset.tooltip")))
                .build());

        counterXSlider = new Slider(getRegularX() + 5, 5+getRegularY()+(20+2)*5, 137, 20, Component.literal("X : "),Component.literal(""),1,1000,this.client.data.Counter_xpos,true){
            @Override
            protected void applyValue() {
                client.data.Counter_xpos = getValueInt();
                client.configManage.save();
            }
        };
        counterXSlider.setTooltip(Tooltip.create(Component.translatable("mineplanetplus.config.xslider.tooltip")));
        this.addRenderableWidget(counterXSlider);

        counterYSlider = new Slider(getRegularX() + 5, 5+getRegularY()+(20+2)*6, 137, 20, Component.literal("Y : "),Component.literal(""),1,1000,this.client.data.Counter_ypos,true){
            @Override
            protected void applyValue() {
                client.data.Counter_ypos = getValueInt();
                client.configManage.save();
            }
        };
        counterYSlider.setTooltip(Tooltip.create(Component.translatable("mineplanetplus.config.yslider.tooltip")));
        this.addRenderableWidget(counterYSlider);

        this.addRenderableWidget(Button.builder(Component.translatable("fishhelper.config.backbutton"),button -> {
            mc.setScreen(parentScreen);
        }).pos(mc.getWindow().getGuiScaledWidth() / 2 - 25 ,super.height-30).size(50,20).build());
    }

    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        this.counterXSlider.render(context, mouseX, mouseY, delta);
        this.counterYSlider.render(context, mouseX, mouseY, delta);

        super.render(context, mouseX, mouseY, delta);
        context.drawString(this.font, Component.translatable("fishhelper.config.changepos"), getRegularX() + 5, 4+getRegularY()+(20 + 2)*5-10, 0xFFFFFF);
    }

    //버튼 눌렀을 때 동작
    private void toggleFishCounter(){
        if(client.data.toggleFishCounter){
            client.data.toggleFishCounter = false;
        }
        else{
            client.data.toggleFishCounter = true;
        }
        setToggleFishCounterButtonText();
        client.configManage.save();
    }
    private void setToggleFishCounterButtonText(){
        if(client.data.toggleFishCounter){
            toggleFishCounterButton.setMessage(Component.translatable("fishhelper.config.fishcounter_enable"));
        }
        else{
            toggleFishCounterButton.setMessage(Component.translatable("fishhelper.config.fishcounter_disable"));
        }
    }
    
    private void toggleCounterMode(){
        if(client.data.toggleCounterMode == FishCounterMode.PERCENTAGE){
            client.data.toggleCounterMode = FishCounterMode.COUNT;
        }
        else if(client.data.toggleCounterMode == FishCounterMode.COUNT){
            client.data.toggleCounterMode = FishCounterMode.ALL;
        }
        else if(client.data.toggleCounterMode == FishCounterMode.ALL){
            client.data.toggleCounterMode = FishCounterMode.PERCENTAGE;
        }
        setToggleCounterModeButtonText();
        client.configManage.save();
    }
    private void setToggleCounterModeButtonText(){
        if(client.data.toggleCounterMode == FishCounterMode.COUNT){
            toggleCounterModeButton.setMessage(Component.translatable("fishhelper.config.fishcounter_setting.mode.count"));
        }
        else if(client.data.toggleCounterMode == FishCounterMode.ALL){
            toggleCounterModeButton.setMessage(Component.translatable("fishhelper.config.fishcounter_setting.mode.all"));
        }
        else if(client.data.toggleCounterMode == FishCounterMode.PERCENTAGE){
            toggleCounterModeButton.setMessage(Component.translatable("fishhelper.config.fishcounter_setting.mode.percentage"));
        }
    }

    private void toggleEarningCalculator(){
        if(client.data.toggleEarningCalculator){
            client.data.toggleEarningCalculator = false;
        }
        else{
            client.data.toggleEarningCalculator = true;
        }
        setToggleEarningCalculatorButtonText();
        client.configManage.save();
    }
    private void setToggleEarningCalculatorButtonText(){
        if(client.data.toggleEarningCalculator){
            toggleEarningCalculatorButton.setMessage(Component.translatable("fishhelper.config.fishcounter_setting.toggleEarningCalculator_enable"));
        }
        else{
            toggleEarningCalculatorButton.setMessage(Component.translatable("fishhelper.config.fishcounter_setting.toggleEarningCalculator_disable"));
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
