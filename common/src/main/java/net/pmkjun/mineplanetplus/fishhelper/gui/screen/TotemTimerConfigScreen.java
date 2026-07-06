package net.pmkjun.mineplanetplus.fishhelper.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
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

    private final int bgWidth;
    private final int bgHeight;

    public TotemTimerConfigScreen(Screen parentScreen){
        super(Component.translatable("fishhelper.config.title"));
        this.parentScreen = parentScreen;
        this.mc = Minecraft.getInstance();
        this.client = FishHelperClient.getInstance();

        this.bgWidth = 147;
        this.bgHeight = 152;
    }

    @Override
    protected void init() {
        super.init();
        String toggleTotem;

        if(client.data.toggleTotemtime){
            toggleTotem = "fishhelper.config.enable";
        } else{
            toggleTotem = "fishhelper.config.disable";
        }

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

        // 토템 쿨감시간
        this.CooldownReduction_TextField = new EditBox(this.font, this.width / 2 + 34, 5+getRegularY()+(20 + 2)*2, 35, 12, this.CooldownReduction_TextField, Component.translatable("fishhelper.config.cooldownreductionfield"));
        this.CooldownReduction_TextField.setValue(Double.toString(this.client.data.valueCooldownReduction/(double)1000));
        this.CooldownReduction_TextField.setTooltip(Tooltip.create(Component.translatable("fishhelper.config.cooldownreductionfield.tooltip")));
        this.addRenderableWidget(this.CooldownReduction_TextField);

        // ※ 주의: addRenderableWidget을 사용하셨으므로 수동 키보드 입력 오버라이드(keyPressed, charTyped)는
        // 26.1에서 컴파일 에러를 유발할 뿐만 아니라 불필요하므로 완전히 제거되었습니다.

        toggleTotemButton = Button.builder(Component.translatable(toggleTotem),button -> {
            toggleTotemtime();
        }).pos(getRegularX() + 5,5+getRegularY()+(20 + 2)*2+(10+4)).size(137,20).tooltip(Tooltip.create(Component.translatable("fishhelper.config.totemtimer.tooltip"))).build();
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
        }).pos(this.width / 2 - 75, this.height - 30).size(50,20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("fishhelper.config.savebutton"),button -> {
            changeSetting();
            mc.setScreen(parentScreen);
        }).pos(this.width / 2 + 25, this.height - 30).size(50,20).build());
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);

        // drawString -> text
        guiGraphics.text(this.font, Component.translatable("fishhelper.config.cooldownreductionfield"), getRegularX() + 5, 5+getRegularY()+(20 + 2)*2+2, 0xFFFFFF);
        guiGraphics.text(this.font, Component.translatable("fishhelper.config.changepos"), getRegularX() + 5, 5+getRegularY()+(20 + 2)*4, 0xFFFFFF);
    }

    private void changeSetting(){
        try{
            client.data.valueTotemActivetime = ConvertActivateTime.asMinute(activateTimeSlider.getValueInt());
            client.data.valueTotemCooldown = ConvertCooldown.asMinute(cooldownSlider.getValueInt());
            client.data.valueCooldownReduction = (long)(Double.parseDouble(CooldownReduction_TextField.getValue())*1000);

            client.configManage.save();
        } catch (NumberFormatException e){
            System.out.println("NumberFormatException!");
        }
    }

    private void toggleTotemtime(){
        if(client.data.toggleTotemtime){
            toggleTotemButton.setMessage(Component.translatable("fishhelper.config.disable"));
            client.data.toggleTotemtime = false;
        } else{
            toggleTotemButton.setMessage(Component.translatable("fishhelper.config.enable"));
            client.data.toggleTotemtime = true;
        }
        client.configManage.save();
    }

    int getRegularX() {
        return this.width / 2 - this.bgWidth / 2;
    }

    int getRegularY() {
        return this.height / 2 - this.bgHeight / 2;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(mc.level == null) {
            super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        }
        background.setSize(this.bgWidth, this.bgHeight);
        background.setPosition(getRegularX(), getRegularY());
        background.extractRenderState(guiGraphics);
    }

    @Override
    protected void extractBlurredBackground(GuiGraphicsExtractor guiGraphics) {
        //pass
    }

    @Override
    public void onClose() {
        this.mc.setScreen(parentScreen);
    }
}