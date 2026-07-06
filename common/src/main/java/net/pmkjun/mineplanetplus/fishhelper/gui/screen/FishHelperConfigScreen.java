package net.pmkjun.mineplanetplus.fishhelper.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.gui.StretchableBackground;

public class FishHelperConfigScreen extends Screen{
    Screen parentScreen;
    Minecraft mc;
    FishHelperClient client;
    private final StretchableBackground background = new StretchableBackground();

    private final int bgWidth;
    private final int bgHeight;
    private Button toggleCustomTextureButton;
    private Button toggleDeliveryHelperButton;

    public FishHelperConfigScreen(Screen parentScreen){
        super(Component.translatable("fishhelper.config.title"));
        this.parentScreen = parentScreen;
        this.mc = Minecraft.getInstance();
        this.client = FishHelperClient.getInstance();

        this.bgWidth = 147;
        this.bgHeight = 8 + 22*4;
    }

    @Override
    protected void init(){
        super.init();
        String toggleTexture;

        if(client.data.toggleCustomTexture){
            toggleTexture = "fishhelper.config.customtexture_enable";
        } else{
            toggleTexture = "fishhelper.config.customtexture_disable";
        }

        this.addRenderableWidget(Button.builder(Component.translatable("fishhelper.config.totemtimer_setting"), btn -> {
            mc.setScreen(new TotemTimerConfigScreen(mc.screen));
        }).pos(getRegularX() + 5, getRegularY() + 5).size(137, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("fishhelper.config.fishcounter_setting"), btn -> {
            mc.setScreen(new FishCounterConfigScreen(mc.screen));
        }).pos(getRegularX() + 5, getRegularY() + 5 + 20 + 2).size(137, 20).build());

        toggleCustomTextureButton = Button.builder(Component.translatable(toggleTexture), btn -> {
                    onCustomTexturePress();
                }).pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2) * 2)
                .size(137, 20)
                .tooltip(Tooltip.create(Component.translatable("fishhelper.config.customtexture.tooltip")))
                .build();
        addRenderableWidget(toggleCustomTextureButton);

        Component deliveryHelperComponent;
        if(this.client.data.toggleDeliveryHelper){
            deliveryHelperComponent = Component.translatable("fishhelper.config.deliveryhelper_enable");
        } else{
            deliveryHelperComponent = Component.translatable("fishhelper.config.deliveryhelper_disable");
        }

        toggleDeliveryHelperButton = Button.builder(deliveryHelperComponent, btn -> {
                    onDeliveryHelperPress();
                }).pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2) * 3)
                .size(137, 20)
                .tooltip(Tooltip.create(Component.translatable("fishhelper.config.deliveryhelper.tooltip")))
                .build();
        addRenderableWidget(toggleDeliveryHelperButton);

        this.addRenderableWidget(Button.builder(Component.translatable("fishhelper.config.backbutton"),button -> {
            mc.setScreen(parentScreen);
        }).pos(this.width / 2 - 25, this.height - 30).size(50,20).build());
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
    }

    int getRegularX() {
        return this.width / 2 - this.bgWidth / 2;
    }

    int getRegularY() {
        return this.height / 2 - this.bgHeight / 2;
    }

    private void onCustomTexturePress(){
        if(client.data.toggleCustomTexture){
            toggleCustomTextureButton.setMessage(Component.translatable("fishhelper.config.customtexture_disable"));
            client.data.toggleCustomTexture = false;
        } else{
            toggleCustomTextureButton.setMessage(Component.translatable("fishhelper.config.customtexture_enable"));
            client.data.toggleCustomTexture = true;
        }
        client.configManage.save();
    }

    private void onDeliveryHelperPress(){
        if(client.data.toggleDeliveryHelper){
            toggleDeliveryHelperButton.setMessage(Component.translatable("fishhelper.config.deliveryhelper_disable"));
            client.data.toggleDeliveryHelper = false;
            client.deliveryQuests.resetQuestData();
        } else{
            toggleDeliveryHelperButton.setMessage(Component.translatable("fishhelper.config.deliveryhelper_enable"));
            client.data.toggleDeliveryHelper = true;
        }
        client.configManage.save();
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