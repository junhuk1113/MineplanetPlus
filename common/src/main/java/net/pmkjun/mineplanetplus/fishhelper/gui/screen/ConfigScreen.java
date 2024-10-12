package net.pmkjun.mineplanetplus.fishhelper.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;

public class ConfigScreen extends Screen{
    Screen parentScreen;
    Minecraft mc;
    FishHelperClient client;
    private final int width;
    private final int height;
    private Button toggleCustomTextureButton;

    public ConfigScreen(Screen parentScreen){
        super(Component.translatable("fishhelper.config.title"));
        this.parentScreen = parentScreen;
        this.mc = Minecraft.getInstance();
        this.client = FishHelperClient.getInstance();

        width = 160;
        height = 74;
    }

    protected void init(){
        super.init();
        String toggleTexture;

        if(client.data.toggleCustomTexture){
            toggleTexture = "fishhelper.config.customtexture_enable";
        }
        else{
            toggleTexture = "fishhelper.config.customtexture_disable";
        }


        // translatable 키로 변경 필요
        this.addRenderableWidget(Button.builder(Component.translatable("fishhelper.config.totemtimer_setting"), btn -> {
            mc.setScreen(new TotemTimerConfigScreen(mc.screen));
        }).pos(getRegularX() + 5, getRegularY() + 5).size(150, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("fishhelper.config.fishcounter_setting"), btn -> {
            mc.setScreen(new FishCounterConfigScreen(mc.screen));
        }).pos(getRegularX() + 5, getRegularY() + 5 + 20 + 2).size(150, 20).build());

        toggleCustomTextureButton = Button.builder(Component.translatable(toggleTexture), btn -> {
            onCustomTexturePress();
        }).pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2) * 2).size(150, 20).build();
        addRenderableWidget(toggleCustomTextureButton);

        this.addRenderableWidget(Button.builder(Component.translatable("fishhelper.config.backbutton"),button -> {
            mc.setScreen(parentScreen);
        }).pos(mc.getWindow().getGuiScaledWidth() / 2 - 25 ,super.height-30).size(50,20).build());
    }

    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
    }

    int getRegularX() {
        return  mc.getWindow().getGuiScaledWidth() / 2 - width / 2;
    }

    int getRegularY() {
        return mc.getWindow().getGuiScaledHeight() / 2 - height / 2;
    }

    private void onCustomTexturePress(){
        if(client.data.toggleCustomTexture){
            toggleCustomTextureButton.setMessage(Component.translatable("fishhelper.config.customtexture_disable"));
            client.data.toggleCustomTexture = false;
        }
        else{
            toggleCustomTextureButton.setMessage(Component.translatable("fishhelper.config.customtexture_enable"));
            client.data.toggleCustomTexture = true;
        }
        client.configManage.save();
    }

    @Override
    public void onClose() {
        this.mc.setScreen(parentScreen);
    }
}
