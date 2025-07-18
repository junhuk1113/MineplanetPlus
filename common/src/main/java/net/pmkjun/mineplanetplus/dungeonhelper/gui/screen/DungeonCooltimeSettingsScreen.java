package net.pmkjun.mineplanetplus.dungeonhelper.gui.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.dungeonhelper.util.DungeonCategory;
import net.pmkjun.mineplanetplus.dungeonhelper.util.DungeonCoolAxis;
import net.pmkjun.mineplanetplus.gui.components.Slider;
import net.pmkjun.mineplanetplus.gui.StretchableBackground;

public class DungeonCooltimeSettingsScreen extends Screen {

    private final Minecraft mc;
    private final DungeonHelperClient client;
    private final StretchableBackground background = new StretchableBackground();

    private Button toggleDungeonCooltimeButton;
    private Button DungeonTypeButton;
    private Button CooltimeAxisButton;
    private Button[] toggleDungeonCooltimeOptionButtons = new Button[2];	// text, fade
    private Slider XPosSlider, YPosSlider, ScaleSlider;
    private final Screen parentScreen;

    private final int width, height;

    public DungeonCooltimeSettingsScreen(Screen parentScreen) {
        super(Component.literal("DungeonCooltimeSettingScreen"));

        mc = Minecraft.getInstance();
        client = DungeonHelperClient.getInstance();
        this.parentScreen = parentScreen;

        width = 147;
        height = 8 + 22*7;
    }


    protected void init(){
        super.init();

        //던전 쿨타임 표시 :

        toggleDungeonCooltimeButton = this.addRenderableWidget(new Button.Builder(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.main"), btn -> onToggleDungeonCooltimePress())
                .pos(getRegularX() + 5, getRegularY() + 5)
                .size(137, 20)
                .tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.main.tooltip")))
                .build());
        setDungeonCooltimeButtonText();

        //일반/악몽레이드 표시 :

        DungeonTypeButton = this.addRenderableWidget(new Button.Builder(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype"), btn -> onDungeonTypeButtonPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + 20 + 2)
                .size(137,20)
                .tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.tooltip")))
                .build());
        setDungeonTypeButtonText();

        // Text
        toggleDungeonCooltimeOptionButtons[0] = this.addRenderableWidget(new Button.Builder(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.text"), btn -> onToggleDungeonCooltimeOptionPress(0))
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2)*2)
                .size(67, 20)
                .tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.text.tooltip")))
                .build());
        setToggleDungeonCooltimeOptionButtonText(0);

        // Fade

        toggleDungeonCooltimeOptionButtons[1] = this.addRenderableWidget(new Button.Builder(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.fade"), btn -> onToggleDungeonCooltimeOptionPress(1))
                .pos(getRegularX() + 5 + 67 + 2, getRegularY() + 5 + (20 + 2)*2)
                .size(68, 20)
                .tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.fade.tooltip")))
                .build());
        setToggleDungeonCooltimeOptionButtonText(1);

        //X pos Slider
        XPosSlider = this.addRenderableWidget(new Slider(getRegularX() + 5, getRegularY() + 5 + (20 + 2)*3, 137, 20,Component.literal("X : "),Component.literal(""),0,1000,client.data.DungeonCooltimeXpos,true){
            @Override
            protected void applyValue() {
                client.data.DungeonCooltimeXpos = this.getValueInt();
                client.settings.save();
            }
        });
        XPosSlider.setTooltip(Tooltip.create(Component.translatable("mineplanetplus.config.xslider.tooltip")));

        YPosSlider = this.addRenderableWidget(new Slider(getRegularX() + 5, getRegularY() + 5 + (20 + 2)*4, 137, 20,Component.literal("Y : "),Component.literal(""),0,1000,client.data.DungeonCooltimeYpos,true){
            @Override
            protected void applyValue() {
                client.data.DungeonCooltimeYpos = this.getValueInt();
                client.settings.save();
            }
        });
        YPosSlider.setTooltip(Tooltip.create(Component.translatable("mineplanetplus.config.yslider.tooltip")));

        /*ScaleSlider = this.addRenderableWidget(new Slider(getRegularX() + 5, getRegularY() + 5 + (20 + 2)*5, 137, 20,Component.literal("UI 크기 : "),Component.literal(""),8,64,client.data.dungeonCooltime_uiScale,true){
            @Override
            protected void applyValue() {
                client.data.dungeonCooltime_uiScale = this.getValueInt();
                client.settings.save();
            }
        });*/

        //던전 쿨타임 방향

        CooltimeAxisButton = this.addRenderableWidget(new Button.Builder(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.axis"), btn -> onAxisTogglePress())
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2)*5)
                .size(137, 20)
                        .tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.axis.tooltip")))
                .build());
        setAxisToggleText();

        this.addRenderableWidget(new Button.Builder(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.resetpos"), btn -> onResetPosButtonPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2)*6)
                .size(137, 20)
                .tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.resetpos.toolip")))
                .build());
    }

    public void render(GuiGraphics guiGraphics, int a, int b, float c) {
        //RenderSystem.setShader(GameRenderer::getPositionTexShader);
        this.renderBackground(guiGraphics, a, b, c);
        XPosSlider.render(guiGraphics,a,b,c);
        YPosSlider.render(guiGraphics,a,b,c);
        //ScaleSlider.render(guiGraphics,a,b,c);
        super.render(guiGraphics, a, b, c);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        //super.renderBackground(guiGraphics);
        if(mc.level == null) {
            super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        }
        //guiGraphics.blit(RenderType::guiTextured, BG_LOCATION, getRegularX(), getRegularY(), 0, 0, width, height, 256, 256);
        background.setPosition(getRegularX(), getRegularY());
        background.setSize(width, height);
        background.render(guiGraphics);
    }

    private void onToggleDungeonCooltimePress() {
        client.data.toggleDungeonCooltime = !client.data.toggleDungeonCooltime;
        setDungeonCooltimeButtonText();
        client.settings.save();
    }

    private void setDungeonCooltimeButtonText(){
        if(client.data.toggleDungeonCooltime) {
            toggleDungeonCooltimeButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.main").append(
                            Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                    ));
        }
        else {
            toggleDungeonCooltimeButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.main").append(
                            Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))
                    ));
        }
    }

    private void onAxisTogglePress() {
        if(this.client.data.coolAxis == DungeonCoolAxis.VERTICAL){
            this.client.data.coolAxis = DungeonCoolAxis.HORIZONTAL;
        }
        else{
            this.client.data.coolAxis = DungeonCoolAxis.VERTICAL;
        }
        setAxisToggleText();
        client.settings.save();
    }
    private void setAxisToggleText(){
        if(this.client.data.coolAxis == DungeonCoolAxis.HORIZONTAL){
            this.CooltimeAxisButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.axis").append(
                            Component.translatable("gui.dungeonhelper.settings.horizontal").withStyle(Style.EMPTY.applyFormat(ChatFormatting.WHITE).withBold(true))
                    ));
        }
        else{
            this.CooltimeAxisButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.axis").append(
                            Component.translatable("gui.dungeonhelper.settings.vertical").withStyle(Style.EMPTY.applyFormat(ChatFormatting.WHITE).withBold(true))
                    ));
        }
    }

    private void onDungeonTypeButtonPress(){
        if(this.client.data.dungeontype == DungeonCategory.ALL){
            this.client.data.dungeontype = DungeonCategory.NORMAL;
        }
        else if(this.client.data.dungeontype == DungeonCategory.NORMAL){
            this.client.data.dungeontype = DungeonCategory.CHAOS;
        }
        else if(this.client.data.dungeontype == DungeonCategory.CHAOS){
            this.client.data.dungeontype = DungeonCategory.ALL;
        }
        setDungeonTypeButtonText();
        client.settings.save();
    }

    private void setDungeonTypeButtonText(){
        if(this.client.data.dungeontype == DungeonCategory.NORMAL){
            this.DungeonTypeButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype").append(
                            Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.normal").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD).withBold(true))
                    ));
        }
        else if(this.client.data.dungeontype == DungeonCategory.CHAOS){
            this.DungeonTypeButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype").append(
                            Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.chaos").withStyle(Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE).withBold(true))
                    ));
        }
        else if(this.client.data.dungeontype == DungeonCategory.ALL){
            this.DungeonTypeButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype").append(
                            Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.all").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                    ));
        }
    }

    private void onToggleDungeonCooltimeOptionPress(int id) {
        if(id == 0) {
            client.data.toggleDungeonCooltimeText = !client.data.toggleDungeonCooltimeText;
        }
        else if(id == 1) {
            client.data.toggleDungeonCooltimeFade = !client.data.toggleDungeonCooltimeFade;
        }

        setToggleDungeonCooltimeOptionButtonText(id);

        client.settings.save();
    }

    private void setToggleDungeonCooltimeOptionButtonText(int id) {
        if(id == 0) {
            if(client.data.toggleDungeonCooltimeText) {
                toggleDungeonCooltimeOptionButtons[0].setMessage(
                        Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.text").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                );
            }
            else {
                toggleDungeonCooltimeOptionButtons[0].setMessage(
                        Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.text").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))
                );
            }
        }
        else if(id == 1) {
            if(client.data.toggleDungeonCooltimeFade) {
                toggleDungeonCooltimeOptionButtons[1].setMessage(
                        Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.fade").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                );
            }
            else {
                toggleDungeonCooltimeOptionButtons[1].setMessage(
                        Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.fade").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))
                );
            }
        }
    }

    private void onResetPosButtonPress(){
        XPosSlider.setValue(0);
        YPosSlider.setValue(0);
        client.data.DungeonCooltimeXpos = 0;
        client.data.DungeonCooltimeYpos = 0;
        client.settings.save();
    }

    @Override
    public void onClose(){
        assert this.minecraft != null;
        this.minecraft.setScreen(parentScreen);
    }

    int getRegularX() {
        return  mc.getWindow().getGuiScaledWidth() / 2 - width / 2;
    }

    int getRegularY() {
        return mc.getWindow().getGuiScaledHeight() / 2 - height / 2;
    }

}