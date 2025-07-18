package net.pmkjun.mineplanetplus.fishhelper.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.util.ConvertActivateTime;
import net.pmkjun.mineplanetplus.fishhelper.util.ConvertCooldown;
import net.pmkjun.mineplanetplus.gui.StretchableBackground;
import net.pmkjun.mineplanetplus.gui.components.Slider;

public class TotemTimerConfigScreen extends Screen {

    private final Minecraft mc;
    private final FishHelperClient client;
    private final StretchableBackground background = new StretchableBackground();
    private final Screen parentScreen;
    private EditBox CooldownReduction_TextField;

    private Button toggleTotemButton;

    private Slider activateTimeSlider;
    private Slider cooldownSlider;

    private Slider timerXSlider;
    private Slider timerYSlider;

    private final int width;
    private final int height;

    public TotemTimerConfigScreen(Screen parentScreen){
        super(Component.translatable("fishhelper.config.title"));
        this.parentScreen = parentScreen;
        this.mc = Minecraft.getInstance();
        this.client = FishHelperClient.getInstance();

        width = 147;
        height = 152;
    }

    @Override
    protected void init() {
        super.init();

        activateTimeSlider = new Slider(getRegularX() + 5, getRegularY() + 5, 137, 20,Component.literal("") , Component.literal(""),0,25,ConvertActivateTime.asLevel(this.client.data.valueTotemActivetime), true){
            @Override
            protected void updateMessage() {
                int level = getValueInt();
                this.setMessage(Component.literal(Component.translatable("fishhelper.config.activatefield").getString()
                        +" : "+ level +"lv ("+
                        ConvertActivateTime.asMinute(level)+
                        Component.translatable("fishhelper.config.minute").getString()+")"));
            }
        };
        activateTimeSlider.setTooltip(Tooltip.create(Component.translatable("fishhelper.config.totemtimer.slider.tooltip")));
        cooldownSlider = new Slider(getRegularX() + 5, getRegularY() + (20 + 2) + 5, 137, 20, Component.literal(""), Component.literal(""), 0,10,ConvertCooldown.asLevel(this.client.data.valueTotemCooldown), true){
            @Override
            protected void updateMessage() {
                int level = getValueInt();
                this.setMessage(Component.literal(Component.translatable("fishhelper.config.cooldownfield").getString()
                        +" : "+ level +"lv ("+ ConvertCooldown.asMinute(level)+
                        Component.translatable("fishhelper.config.minute").getString()+")"));
            }
        };
        cooldownSlider.setTooltip(Tooltip.create(Component.translatable("fishhelper.config.totemtimer.slider.tooltip")));

        this.addRenderableWidget(activateTimeSlider);
        this.addRenderableWidget(cooldownSlider);

        //토템 쿨감시간
        this.CooldownReduction_TextField = new EditBox(this.font,super.width/2+34,5+getRegularY()+(20 + 2)*2,35,12,this.CooldownReduction_TextField,Component.translatable("fishhelper.config.cooldownreductionfield"));
        this.CooldownReduction_TextField.setValue(Double.toString(this.client.data.valueCooldownReduction/(double)1000));
        this.CooldownReduction_TextField.setTooltip(Tooltip.create(Component.translatable("fishhelper.config.cooldownreductionfield.tooltip")));
        this.addRenderableWidget(this.CooldownReduction_TextField);

        toggleTotemButton = Button.builder(Component.empty(),button -> {
            toggleTotemtime();
        }).pos(getRegularX() + 5,5+getRegularY()+(20 + 2)*2+(10+4)).size(137,20).tooltip(Tooltip.create(Component.translatable("fishhelper.config.totemtimer.tooltip"))).build();
        setToggleTotemButtonText();
        this.addRenderableWidget(toggleTotemButton);

        timerXSlider = new Slider(getRegularX() + 5,5+getRegularY()+(20 + 2)*4+(10+2),137,20,Component.literal("X : "), Component.literal(""),1,1000,this.client.data.Timer_xpos, true){
            @Override
            protected void applyValue() {
                client.data.Timer_xpos = getValueInt();
                client.configManage.save();
            }
        };
        timerXSlider.setTooltip(Tooltip.create(Component.translatable("mineplanetplus.config.xslider.tooltip")));
        this.addRenderableWidget(timerXSlider);
        timerYSlider = new Slider(getRegularX() + 5,5+getRegularY()+(20 + 2)*5+(10+2),137,20,Component.literal("Y : "),Component.literal(""),1,1000,this.client.data.Timer_ypos,true){
            @Override
            protected void applyValue() {
                client.data.Timer_ypos = getValueInt();
                client.configManage.save();
            }
        };
        timerYSlider.setTooltip(Tooltip.create(Component.translatable("mineplanetplus.config.yslider.tooltip")));
        this.addRenderableWidget(timerYSlider);

        this.addRenderableWidget(Button.builder(Component.translatable("fishhelper.config.backbutton"),button -> {
            mc.setScreen(parentScreen);
        }).pos(super.width/2-75,super.height-30).size(50,20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("fishhelper.config.savebutton"),button -> {
            changeSetting();
            mc.setScreen(parentScreen);
        }).pos(super.width/2+25,super.height-30).size(50,20).build());
    }
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers)
                || this.CooldownReduction_TextField.keyPressed(keyCode, scanCode, modifiers);
    }
    @Override
    public boolean charTyped(char chr, int keyCode) {
        return this.CooldownReduction_TextField.charTyped(chr, keyCode);
    }

    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        context.drawString(this.font, Component.translatable("fishhelper.config.cooldownreductionfield"), getRegularX() + 5, 5+getRegularY()+(20 + 2)*2+2, 0xFFFFFF);
        context.drawString(this.font,Component.translatable("fishhelper.config.changepos"),getRegularX() + 5,5+getRegularY()+(20 + 2)*4,0xFFFFFF);

        this.activateTimeSlider.render(context, mouseX, mouseY, delta);
        this.cooldownSlider.render(context, mouseX, mouseY, delta);

        this.timerXSlider.render(context, mouseX, mouseY, delta);
        this.timerYSlider.render(context, mouseX, mouseY, delta);

        this.CooldownReduction_TextField.render(context, mouseX, mouseY, delta);
    }
    private void changeSetting(){
        try{
            client.data.valueTotemActivetime = ConvertActivateTime.asMinute(activateTimeSlider.getValueInt());
            client.data.valueTotemCooldown = ConvertCooldown.asMinute(cooldownSlider.getValueInt());
            client.data.valueCooldownReduction = (long)(Double.parseDouble(CooldownReduction_TextField.getValue())*1000);

            client.configManage.save();
        }
        catch (NumberFormatException e){
            System.out.println("NumberFormatException!");
        }

    }

    private void toggleTotemtime(){
        if(client.data.toggleTotemtime){
            client.data.toggleTotemtime = false;
        }
        else{
            client.data.toggleTotemtime = true;
        }
        setToggleTotemButtonText();
        client.configManage.save();
    }
    private void setToggleTotemButtonText(){
        if(client.data.toggleTotemtime){
            toggleTotemButton.setMessage(Component.translatable("fishhelper.config.enable"));
        }
        else{
            toggleTotemButton.setMessage(Component.translatable("fishhelper.config.disable"));
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