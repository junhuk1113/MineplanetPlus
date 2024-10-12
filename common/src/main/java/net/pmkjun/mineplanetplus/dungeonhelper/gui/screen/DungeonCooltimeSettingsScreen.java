package net.pmkjun.mineplanetplus.dungeonhelper.gui.screen;

import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.dungeonhelper.util.DungeonCategory;
import net.pmkjun.mineplanetplus.dungeonhelper.util.DungeonCoolAxis;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.pmkjun.mineplanetplus.dungeonhelper.gui.components.Slider;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

public class DungeonCooltimeSettingsScreen extends Screen {

    private Minecraft mc;
    private DungeonHelperClient client;
    
    public static final ResourceLocation BG_LOCATION = new ResourceLocation("dungeonhelper", "textures/gui/dungeon_cooltime_settings_background.png");

    private Button toggleDungeonCooltimeButton;
    private Button DungeonTypeButton;
    private Button CooltimeAxisButton;
    private Button[] toggleDungeonCooltimeOptionButtons = new Button[2];	// text, fade
    private Slider XPosSlider, YPosSlider;
    private final Screen parentScreen;

    private int width, height;

    public DungeonCooltimeSettingsScreen(Screen parentScreen) {
        super(Component.literal("DungeonCooltimeSettingScreen"));

        mc = Minecraft.getInstance();
        client = DungeonHelperClient.getInstance();
        this.parentScreen = parentScreen;

        width = 147;
        height = 180;
    }


    protected void init(){
        super.init();

        //던전 쿨타임 표시 :
        Component toggleDungeonCooltimeButtonComponent;
        if(client.data.toggleDungeonCooltime)
            toggleDungeonCooltimeButtonComponent = 
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.main").append(
                    Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
            );
        else
            toggleDungeonCooltimeButtonComponent = 
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.main").append(
                    Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))
            );

        toggleDungeonCooltimeButton = this.addRenderableWidget(new Button.Builder(toggleDungeonCooltimeButtonComponent, btn -> onToggleDungeonCooltimePress())
                .pos(getRegularX() + 5, getRegularY() + 5)
                .size(137, 20)
                .build());

        //던전 쿨타임 방향
        Component CooltimeAxisComponent;
        if(client.data.coolAxis == DungeonCoolAxis.VERTICAL)
            CooltimeAxisComponent =
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.axis").append(
                    Component.translatable("gui.dungeonhelper.settings.vertical").withStyle(Style.EMPTY.applyFormat(ChatFormatting.WHITE).withBold(true))
                    );

        else
            CooltimeAxisComponent =
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.axis").append(
                    Component.translatable("gui.dungeonhelper.settings.horizontal").withStyle(Style.EMPTY.applyFormat(ChatFormatting.WHITE).withBold(true))
                    );

        CooltimeAxisButton = this.addRenderableWidget(new Button.Builder(CooltimeAxisComponent, btn -> onAxisTogglePress())
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2)*5)
                .size(137, 20)
                .build());

        //일반/악몽레이드 표시 :
        Component DungeonTypeButtonComponent;
        if(client.data.dungeontype == DungeonCategory.ALL)
            DungeonTypeButtonComponent =
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype").append(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.all").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
            );
        else if(client.data.dungeontype == DungeonCategory.NORMAL)
            DungeonTypeButtonComponent =
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype").append(
                            Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.normal").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD).withBold(true))
                    );
        else
            DungeonTypeButtonComponent =
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype").append(
                            Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.chaos").withStyle(Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE).withBold(true))
                    );

        DungeonTypeButton = this.addRenderableWidget(new Button.Builder(DungeonTypeButtonComponent, btn -> onDungeonTypeButtonPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + 20 + 2)
                .size(137,20)
                .build());

        // Text
        Component toggleDungeonCooltimeTextButtonComponent;
        if(client.data.toggleDungeonCooltimeText)
            toggleDungeonCooltimeTextButtonComponent = 
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.text").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
            ;
        else
            toggleDungeonCooltimeTextButtonComponent = 
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.text").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))
            ;

        toggleDungeonCooltimeOptionButtons[0] = this.addRenderableWidget(new Button.Builder(toggleDungeonCooltimeTextButtonComponent, btn -> onToggleDungeonCooltimeOptionPress(0))
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2)*2)
                .size(67, 20)
                .build());

        // Fade
        Component toggleDungeonCooltimeFadeButtonComponent;
        if(client.data.toggleDungeonCooltimeFade)
            toggleDungeonCooltimeFadeButtonComponent = 
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.fade").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
            ;
        else
            toggleDungeonCooltimeFadeButtonComponent = 
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.fade").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))
            ;

        toggleDungeonCooltimeOptionButtons[1] = this.addRenderableWidget(new Button.Builder(toggleDungeonCooltimeFadeButtonComponent, btn -> onToggleDungeonCooltimeOptionPress(1))
                .pos(getRegularX() + 5 + 67 + 2, getRegularY() + 5 + (20 + 2)*2)
                .size(68, 20)
                .build());
        //X pos Slider
        XPosSlider = this.addRenderableWidget(new Slider(getRegularX() + 5, getRegularY() + 5 + (20 + 2)*3, 137, 20,Component.literal("X : "),Component.literal(""),0,1000,client.data.DungeonCooltimeXpos,true){
            @Override
            protected void applyValue() {
                client.data.DungeonCooltimeXpos = this.getValueInt();
                client.settings.save();
            }
        });
        YPosSlider = this.addRenderableWidget(new Slider(getRegularX() + 5, getRegularY() + 5 + (20 + 2)*4, 137, 20,Component.literal("Y : "),Component.literal(""),0,1000,client.data.DungeonCooltimeYpos,true){
            @Override
            protected void applyValue() {
                client.data.DungeonCooltimeYpos = this.getValueInt();
                client.settings.save();
            }
        });
        this.addRenderableWidget(new Button.Builder(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.resetpos"), btn -> onResetPosButtonPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2)*6)
                .size(137, 20)
                .build());
    }

    public void render(GuiGraphics guiGraphics, int a, int b, float c) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        this.renderBackground(guiGraphics);
        XPosSlider.render(guiGraphics,a,b,c);
        YPosSlider.render(guiGraphics,a,b,c);
        super.render(guiGraphics, a, b, c);
    }

    public void renderBackground(GuiGraphics guiGraphics) {
        super.renderBackground(guiGraphics);

        guiGraphics.blit(BG_LOCATION, getRegularX(), getRegularY(), 0, 0, width, height);
    }

    private void onToggleDungeonCooltimePress() {
        client.data.toggleDungeonCooltime = !client.data.toggleDungeonCooltime;

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

        client.settings.save();
    }

    private void onAxisTogglePress() {
        if(this.client.data.coolAxis == DungeonCoolAxis.VERTICAL){
            this.client.data.coolAxis = DungeonCoolAxis.HORIZONTAL;
            this.CooltimeAxisButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.axis").append(
                            Component.translatable("gui.dungeonhelper.settings.horizontal").withStyle(Style.EMPTY.applyFormat(ChatFormatting.WHITE).withBold(true))
                    ));
        }
        else{
            this.client.data.coolAxis = DungeonCoolAxis.VERTICAL;
            this.CooltimeAxisButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.axis").append(
                            Component.translatable("gui.dungeonhelper.settings.vertical").withStyle(Style.EMPTY.applyFormat(ChatFormatting.WHITE).withBold(true))
                    ));
        }
    }

    private void onDungeonTypeButtonPress(){
        if(this.client.data.dungeontype == DungeonCategory.ALL){
            this.client.data.dungeontype = DungeonCategory.NORMAL;
            this.DungeonTypeButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype").append(
                            Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.normal").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD).withBold(true))
                    ));
        }
        else if(this.client.data.dungeontype == DungeonCategory.NORMAL){
            this.client.data.dungeontype = DungeonCategory.CHAOS;
            this.DungeonTypeButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype").append(
                            Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.chaos").withStyle(Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE).withBold(true))
                    ));
        }
        else if(this.client.data.dungeontype == DungeonCategory.CHAOS){
            this.client.data.dungeontype = DungeonCategory.ALL;
            this.DungeonTypeButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype").append(
                            Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.all").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                    ));
        }
    }

    private void onToggleDungeonCooltimeOptionPress(int id) {
        if(id == 0) {
            client.data.toggleDungeonCooltimeText = !client.data.toggleDungeonCooltimeText;

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
            client.data.toggleDungeonCooltimeFade = !client.data.toggleDungeonCooltimeFade;

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

        client.settings.save();
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
