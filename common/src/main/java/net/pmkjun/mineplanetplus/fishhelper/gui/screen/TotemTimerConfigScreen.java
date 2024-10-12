package net.pmkjun.mineplanetplus.fishhelper.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.gui.widget.Slider;
import net.pmkjun.mineplanetplus.fishhelper.util.ConvertActivateTime;
import net.pmkjun.mineplanetplus.fishhelper.util.ConvertCooldown;

public class TotemTimerConfigScreen extends Screen {

    private Minecraft mc;
    private FishHelperClient client;
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

        width = 160;
        height = 132;
    }

    @Override
    protected void init() {
        super.init();
        String toggleTotem;

        if(client.data.toggleTotemtime){
            toggleTotem = "fishhelper.config.enable";
        }
        else{
            toggleTotem = "fishhelper.config.disable";
        }

        activateTimeSlider = new Slider(getRegularX() + 5, getRegularY(), 150, 20, Component.literal(""),0,25,ConvertActivateTime.asLevel(this.client.data.valueTotemActivetime)){
            @Override
            protected void updateMessage() {
                int level = getValueInt();
                this.setMessage(Component.literal(Component.translatable("fishhelper.config.activatefield").getString()
                        +" : "+ level +"lv ("+
                        ConvertActivateTime.asMinute(level)+
                        Component.translatable("fishhelper.config.minute").getString()+")"));
            }
        };
        cooldownSlider = new Slider(getRegularX() + 5, getRegularY() + (20 + 2), 150, 20, Component.literal(""),0,10,ConvertCooldown.asLevel(this.client.data.valueTotemCooldown)){
            @Override
            protected void updateMessage() {
                int level = getValueInt();
                this.setMessage(Component.literal(Component.translatable("fishhelper.config.cooldownfield").getString()
                        +" : "+ level +"lv ("+ ConvertCooldown.asMinute(level)+
                        Component.translatable("fishhelper.config.minute").getString()+")"));
            }
        };

        this.addRenderableWidget(activateTimeSlider);
        this.addRenderableWidget(cooldownSlider);

        //토템 쿨감시간
        this.CooldownReduction_TextField = new EditBox(this.font,getRegularX() + 5 + 90,getRegularY()+(20 + 2)*2,35,10,this.CooldownReduction_TextField,Component.translatable("fishhelper.config.cooldownreductionfield"));
        this.CooldownReduction_TextField.setValue(Double.toString(this.client.data.valueCooldownReduction/(double)1000));
        this.addWidget(this.CooldownReduction_TextField);

        toggleTotemButton = Button.builder(Component.translatable(toggleTotem),button -> {
            toggleTotemtime();
        }).pos(getRegularX() + 5,getRegularY()+(20 + 2)*2+(10+4)).size(150,20).build();
        this.addRenderableWidget(toggleTotemButton);

        timerXSlider = new Slider(getRegularX() + 5,getRegularY()+(20 + 2)*4+(10+2),150,20,Component.literal("X : "),1,1000,this.client.data.Timer_xpos){
            @Override
            protected void applyValue() {
                client.data.Timer_xpos = getValueInt();
                client.configManage.save();
            }
        };
        this.addRenderableWidget(timerXSlider);
        timerYSlider = new Slider(getRegularX() + 5,getRegularY()+(20 + 2)*5+(10+2),150,20,Component.literal("Y : "),1,1000,this.client.data.Timer_ypos){
            @Override
            protected void applyValue() {
                client.data.Timer_ypos = getValueInt();
                client.configManage.save();
            }
        };
        this.addRenderableWidget(timerYSlider);

        this.addRenderableWidget(Button.builder(Component.translatable("fishhelper.config.backbutton"),button -> {
            mc.setScreen(parentScreen);
        }).pos(10,super.height-30).size(50,20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("fishhelper.config.savebutton"),button -> {
            changeSetting();
            mc.setScreen(parentScreen);
        }).pos(super.width-60,super.height-30).size(50,20).build());
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
        this.renderBackground(context);
        context.drawString(this.font, Component.translatable("fishhelper.config.cooldownreductionfield"), getRegularX() + 5, getRegularY()+(20 + 2)*2+2, 0xFFFFFF);
        context.drawString(this.font,Component.translatable("fishhelper.config.changepos"),getRegularX() + 5,getRegularY()+(20 + 2)*4,0xFFFFFF);

        this.activateTimeSlider.render(context, mouseX, mouseY, delta);
        this.cooldownSlider.render(context, mouseX, mouseY, delta);

        this.timerXSlider.render(context, mouseX, mouseY, delta);
        this.timerYSlider.render(context, mouseX, mouseY, delta);

        this.CooldownReduction_TextField.render(context, mouseX, mouseY, delta);

        super.render(context, mouseX, mouseY, delta);
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
            toggleTotemButton.setMessage(Component.translatable("fishhelper.config.disable"));
            client.data.toggleTotemtime = false;
        }
        else{
            toggleTotemButton.setMessage(Component.translatable("fishhelper.config.enable"));
            client.data.toggleTotemtime = true;
        }
        client.configManage.save();
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
