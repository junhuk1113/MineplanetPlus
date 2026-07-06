package net.pmkjun.mineplanetplus.dungeonhelper.gui.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.dungeonhelper.util.DungeonCategory;
import net.pmkjun.mineplanetplus.dungeonhelper.util.DungeonCoolAxis;
import net.pmkjun.mineplanetplus.gui.StretchableBackground;
import net.pmkjun.mineplanetplus.gui.components.Slider;

public class DungeonCooltimeSettingsScreen extends Screen {

    private final DungeonHelperClient client;
    private final StretchableBackground background = new StretchableBackground();

    private Button toggleDungeonCooltimeButton;
    private Button DungeonTypeButton;
    private Button CooltimeAxisButton;
    private final Button[] toggleDungeonCooltimeOptionButtons = new Button[2];  // text, fade
    private Slider XPosSlider, YPosSlider; // ScaleSlider는 주석 처리되어 있으므로 그대로 둡니다.
    private final Screen parentScreen;

    // 부모 클래스의 width, height와 겹치지 않도록 이름 변경
    private final int bgWidth, bgHeight;

    public DungeonCooltimeSettingsScreen(Screen parentScreen) {
        super(Component.literal("DungeonCooltimeSettingScreen"));

        // Screen 부모 클래스에 이미 this.minecraft가 있으므로 별도 mc 할당 생략
        this.client = DungeonHelperClient.getInstance();
        this.parentScreen = parentScreen;

        this.bgWidth = 147;
        this.bgHeight = 8 + 22 * 7;
    }

    @Override
    protected void init() {
        super.init();

        // 던전 쿨타임 표시 :
        Component toggleDungeonCooltimeButtonComponent;
        if (client.data.toggleDungeonCooltime)
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
                .tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.main.tooltip")))
                .build());

        // 일반/악몽레이드 표시 :
        Component DungeonTypeButtonComponent;
        if (client.data.dungeontype == DungeonCategory.ALL)
            DungeonTypeButtonComponent =
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype").append(
                            Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.all").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                    );
        else if (client.data.dungeontype == DungeonCategory.NORMAL)
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
                .size(137, 20)
                .tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.tooltip")))
                .build());

        // Text
        Component toggleDungeonCooltimeTextButtonComponent;
        if (client.data.toggleDungeonCooltimeText)
            toggleDungeonCooltimeTextButtonComponent =
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.text").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true));
        else
            toggleDungeonCooltimeTextButtonComponent =
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.text").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true));

        toggleDungeonCooltimeOptionButtons[0] = this.addRenderableWidget(new Button.Builder(toggleDungeonCooltimeTextButtonComponent, btn -> onToggleDungeonCooltimeOptionPress(0))
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2) * 2)
                .size(67, 20)
                .tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.text.tooltip")))
                .build());

        // Fade
        Component toggleDungeonCooltimeFadeButtonComponent;
        if (client.data.toggleDungeonCooltimeFade)
            toggleDungeonCooltimeFadeButtonComponent =
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.fade").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true));
        else
            toggleDungeonCooltimeFadeButtonComponent =
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.fade").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true));

        toggleDungeonCooltimeOptionButtons[1] = this.addRenderableWidget(new Button.Builder(toggleDungeonCooltimeFadeButtonComponent, btn -> onToggleDungeonCooltimeOptionPress(1))
                .pos(getRegularX() + 5 + 67 + 2, getRegularY() + 5 + (20 + 2) * 2)
                .size(68, 20)
                .tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.fade.tooltip")))
                .build());

        // X Pos Slider
        XPosSlider = this.addRenderableWidget(new Slider(getRegularX() + 5, getRegularY() + 5 + (20 + 2) * 3, 137, 20, Component.literal("X : "), Component.literal(""), 0, 1000, client.data.DungeonCooltimeXpos, true) {
            @Override
            protected void applyValue() {
                client.data.DungeonCooltimeXpos = this.getValueInt();
                client.settings.save();
            }
        });
        XPosSlider.setTooltip(Tooltip.create(Component.translatable("mineplanetplus.config.xslider.tooltip")));

        // Y Pos Slider
        YPosSlider = this.addRenderableWidget(new Slider(getRegularX() + 5, getRegularY() + 5 + (20 + 2) * 4, 137, 20, Component.literal("Y : "), Component.literal(""), 0, 1000, client.data.DungeonCooltimeYpos, true) {
            @Override
            protected void applyValue() {
                client.data.DungeonCooltimeYpos = this.getValueInt();
                client.settings.save();
            }
        });
        YPosSlider.setTooltip(Tooltip.create(Component.translatable("mineplanetplus.config.yslider.tooltip")));

        // 던전 쿨타임 방향
        Component CooltimeAxisComponent;
        if (client.data.coolAxis == DungeonCoolAxis.VERTICAL)
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
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2) * 5)
                .size(137, 20)
                .tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.axis.tooltip")))
                .build());

        this.addRenderableWidget(new Button.Builder(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.resetpos"), btn -> onResetPosButtonPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2) * 6)
                .size(137, 20)
                .tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.resetpos.toolip")))
                .build());
    }

    // 26.1 변경점: render -> extractRenderState
    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.extractBackground(guiGraphics, mouseX, mouseY, partialTick);

        // addRenderableWidget을 통해 등록된 위젯들은 super.extractRenderState에서 자동으로 렌더링 됩니다.
        // 기존의 XPosSlider.render(...), YPosSlider.render(...) 수동 호출은 중복 렌더링이므로 삭제했습니다.
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
    }

    // 26.1 변경점: renderBackground -> extractBackground
    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.minecraft.level == null) {
            super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        }

        background.setPosition(getRegularX(), getRegularY());
        background.setSize(this.bgWidth, this.bgHeight);

        // 주의: StretchableBackground 클래스의 render 메서드도 26.1 구조에 맞게
        // extractRenderState(GuiGraphicsExtractor)로 변경하셨다면 메서드 명을 맞춰주세요.
        background.extractRenderState(guiGraphics);
    }

    // 26.1 변경점: renderBlurredBackground -> extractBlurredBackground
    @Override
    protected void extractBlurredBackground(GuiGraphicsExtractor guiGraphics) {
        // pass
    }

    private void onToggleDungeonCooltimePress() {
        client.data.toggleDungeonCooltime = !client.data.toggleDungeonCooltime;

        if (client.data.toggleDungeonCooltime) {
            toggleDungeonCooltimeButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.main").append(
                            Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                    ));
        } else {
            toggleDungeonCooltimeButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.main").append(
                            Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))
                    ));
        }
        client.settings.save();
    }

    private void onAxisTogglePress() {
        if (this.client.data.coolAxis == DungeonCoolAxis.VERTICAL) {
            this.client.data.coolAxis = DungeonCoolAxis.HORIZONTAL;
            this.CooltimeAxisButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.axis").append(
                            Component.translatable("gui.dungeonhelper.settings.horizontal").withStyle(Style.EMPTY.applyFormat(ChatFormatting.WHITE).withBold(true))
                    ));
        } else {
            this.client.data.coolAxis = DungeonCoolAxis.VERTICAL;
            this.CooltimeAxisButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.axis").append(
                            Component.translatable("gui.dungeonhelper.settings.vertical").withStyle(Style.EMPTY.applyFormat(ChatFormatting.WHITE).withBold(true))
                    ));
        }
    }

    private void onDungeonTypeButtonPress() {
        if (this.client.data.dungeontype == DungeonCategory.ALL) {
            this.client.data.dungeontype = DungeonCategory.NORMAL;
            this.DungeonTypeButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype").append(
                            Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.normal").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD).withBold(true))
                    ));
        } else if (this.client.data.dungeontype == DungeonCategory.NORMAL) {
            this.client.data.dungeontype = DungeonCategory.CHAOS;
            this.DungeonTypeButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype").append(
                            Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.chaos").withStyle(Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE).withBold(true))
                    ));
        } else if (this.client.data.dungeontype == DungeonCategory.CHAOS) {
            this.client.data.dungeontype = DungeonCategory.ALL;
            this.DungeonTypeButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype").append(
                            Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.all").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                    ));
        }
    }

    private void onToggleDungeonCooltimeOptionPress(int id) {
        if (id == 0) {
            client.data.toggleDungeonCooltimeText = !client.data.toggleDungeonCooltimeText;

            if (client.data.toggleDungeonCooltimeText) {
                toggleDungeonCooltimeOptionButtons[0].setMessage(
                        Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.text").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                );
            } else {
                toggleDungeonCooltimeOptionButtons[0].setMessage(
                        Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.text").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))
                );
            }
        } else if (id == 1) {
            client.data.toggleDungeonCooltimeFade = !client.data.toggleDungeonCooltimeFade;

            if (client.data.toggleDungeonCooltimeFade) {
                toggleDungeonCooltimeOptionButtons[1].setMessage(
                        Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.fade").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                );
            } else {
                toggleDungeonCooltimeOptionButtons[1].setMessage(
                        Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.fade").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))
                );
            }
        }
        client.settings.save();
    }

    private void onResetPosButtonPress() {
        XPosSlider.setValue(0);
        YPosSlider.setValue(0);
        client.data.DungeonCooltimeXpos = 0;
        client.data.DungeonCooltimeYpos = 0;
        client.settings.save();
    }

    @Override
    public void onClose() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(parentScreen);
        }
    }

    int getRegularX() {
        return this.width / 2 - this.bgWidth / 2;
    }

    int getRegularY() {
        return this.height / 2 - this.bgHeight / 2;
    }
}