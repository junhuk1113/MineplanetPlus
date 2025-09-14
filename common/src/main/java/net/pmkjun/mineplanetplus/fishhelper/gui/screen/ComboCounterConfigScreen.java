package net.pmkjun.mineplanetplus.fishhelper.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.util.ComboCounterMode;
import net.pmkjun.mineplanetplus.gui.StretchableBackground;
import net.pmkjun.mineplanetplus.gui.components.Slider;

public class ComboCounterConfigScreen extends Screen {
    private final Minecraft mc;
    private final FishHelperClient client;
    private final StretchableBackground background = new StretchableBackground();
    private final Screen parentScreen;

    private Button toggleComboCounterButton;
    private Slider counterXSlider;
    private Slider counterYSlider;

    private final int width;
    private final int height;

    public ComboCounterConfigScreen(Screen parentScreen) {
        super(Component.translatable("fishhelper.config.title"));
        this.parentScreen = parentScreen;
        this.mc = Minecraft.getInstance();
        this.client = FishHelperClient.getInstance();

        width = 147;
        height = 8 + 22*4;
    }

    @Override
    protected void init() {
        super.init();

        toggleComboCounterButton = this.addRenderableWidget(Button.builder(Component.translatable("fishhelper.config.combocounter.enable"), btn -> {
            onComboCounterButtonPress();
        }).pos(getRegularX() + 5, getRegularY() + 5).size(137, 20).build());
        setToggleComboCounterButtonText();

        counterXSlider = new Slider(getRegularX() + 5, getRegularY() + 5 + (20 + 2)*2, 137, 20, Component.literal("X : "),Component.literal(""),1,1000,this.client.data.ComboCounter_xpos,true){
            @Override
            protected void applyValue() {
                client.data.ComboCounter_xpos = getValueInt();
                client.configManage.save();
            }
        };
        counterXSlider.setTooltip(Tooltip.create(Component.translatable("mineplanetplus.config.xslider.tooltip")));
        this.addRenderableWidget(counterXSlider);

        counterYSlider = new Slider(getRegularX() + 5, getRegularY() + 5 + (20 + 2)*3, 137, 20, Component.literal("Y : "),Component.literal(""),1,1000,this.client.data.ComboCounter_ypos,true){
            @Override
            protected void applyValue() {
                client.data.ComboCounter_ypos = getValueInt();
                client.configManage.save();
            }
        };
        counterYSlider.setTooltip(Tooltip.create(Component.translatable("mineplanetplus.config.yslider.tooltip")));
        this.addRenderableWidget(counterYSlider);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        this.counterXSlider.render(context, mouseX, mouseY, delta);
        this.counterYSlider.render(context, mouseX, mouseY, delta);
        context.drawString(this.font, Component.translatable("fishhelper.config.changepos"), getRegularX() + 5, 4+getRegularY()+(20 + 2)*2-10, 0xFFFFFF);
    }

    private void onComboCounterButtonPress(){
        if(client.data.toggleComboCounter == ComboCounterMode.OFF){
            client.data.toggleComboCounter = ComboCounterMode.ON;
        }
        else if(client.data.toggleComboCounter == ComboCounterMode.ON){
            client.data.toggleComboCounter = ComboCounterMode.ON_FISHING;
        }
        else if(client.data.toggleComboCounter == ComboCounterMode.ON_FISHING){
            client.data.toggleComboCounter = ComboCounterMode.OFF;
        }
        setToggleComboCounterButtonText();
        client.configManage.save();
    }

    private void setToggleComboCounterButtonText(){
        if(client.data.toggleComboCounter == ComboCounterMode.ON){
            toggleComboCounterButton.setMessage(Component.translatable("fishhelper.config.combocounter.enable"));
            toggleComboCounterButton.setTooltip(Tooltip.create(Component.translatable("fishhelper.config.combocounter.enable.tooltip")));
        }
        else if(client.data.toggleComboCounter == ComboCounterMode.ON_FISHING){
            toggleComboCounterButton.setMessage(Component.translatable("fishhelper.config.combocounter.onfishing"));
            toggleComboCounterButton.setTooltip(Tooltip.create(Component.translatable("fishhelper.config.combocounter.onfishing.tooltip")));
        }
        else if(client.data.toggleComboCounter == ComboCounterMode.OFF){
            toggleComboCounterButton.setMessage(Component.translatable("fishhelper.config.combocounter.disable"));
            toggleComboCounterButton.setTooltip(Tooltip.create(Component.translatable("fishhelper.config.combocounter.disable.tooltip")));
        }
    }
    @Override
    public void onClose() {
        this.mc.setScreen(parentScreen);
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

    int getRegularX() {
        return mc.getWindow().getGuiScaledWidth() / 2 - width / 2;
    }

    int getRegularY() {
        return mc.getWindow().getGuiScaledHeight() / 2 - height / 2;
    }
}
