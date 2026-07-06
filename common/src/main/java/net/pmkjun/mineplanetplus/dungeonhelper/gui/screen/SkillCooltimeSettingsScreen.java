package net.pmkjun.mineplanetplus.dungeonhelper.gui.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.dungeonhelper.util.ClassCategory;
import net.pmkjun.mineplanetplus.dungeonhelper.util.DefaultSkillUI;
import net.pmkjun.mineplanetplus.gui.StretchableBackground;
import net.pmkjun.mineplanetplus.gui.components.Slider;

public class SkillCooltimeSettingsScreen extends Screen {

    private final DungeonHelperClient client;
    private final StretchableBackground background = new StretchableBackground();

    private Button toggleSkillCooltimeButton;
    private Button classTypeButton;
    private Button debugModeButton;
    private Button toggleCustomGUIPosButton;
    private Button toggleAutoClassDetectButton;
    private Button toggleDefaultSkillUIButton;
    private Slider XPosSlider, YPosSlider;

    private final int bgWidth, bgHeight;
    private final Screen parentScreen;

    public static boolean ENABLE_DEBUG_MODE = false;
    public static boolean DEBUG_MODE = false;

    public SkillCooltimeSettingsScreen(Screen parentScreen) {
        super(Component.literal("SkillCooltimeSettingScreen"));

        this.client = DungeonHelperClient.getInstance();
        this.parentScreen = parentScreen;

        this.bgWidth = 147;
        this.bgHeight = 140 + 22;
    }

    @Override
    protected void init() {
        super.init();
        MutableComponent toggleSkillCooltimeButtonComponent, toggleCustomGUIPosComponent, toggleAutoClassDetectButtonComponent, toggleDefaultSkillUIButtonComponent;

        if (this.client.data.toggleSkillCooltime) {
            toggleSkillCooltimeButtonComponent = Component.translatable("gui.dungeonhelper.skill_cooltime_settings.main").append(Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true)));
        } else {
            toggleSkillCooltimeButtonComponent = Component.translatable("gui.dungeonhelper.skill_cooltime_settings.main").append(Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true)));
        }

        if (this.client.data.toggleCustomSkillGUIPos) {
            toggleCustomGUIPosComponent = Component.translatable("gui.dungeonhelper.skill_cooltime_settings.customguipos").append(Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true)));
        } else {
            toggleCustomGUIPosComponent = Component.translatable("gui.dungeonhelper.skill_cooltime_settings.customguipos").append(Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true)));
        }

        if (this.client.data.toggleAutoClassDetect) {
            toggleAutoClassDetectButtonComponent = Component.translatable("gui.dungeonhelper.skill_cooltime_settings.autoclassdetect").append(Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true)));
        } else {
            toggleAutoClassDetectButtonComponent = Component.translatable("gui.dungeonhelper.skill_cooltime_settings.autoclassdetect").append(Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true)));
        }

        if (this.client.data.toggleDefaultSkillUI == DefaultSkillUI.AUTO) {
            toggleDefaultSkillUIButtonComponent = Component.translatable("gui.dungeonhelper.skill_cooltime_settings.defaultskillui").append(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.defaultskillui.auto").withStyle(Style.EMPTY.applyFormat(ChatFormatting.BLUE).withBold(true)));
        } else if (this.client.data.toggleDefaultSkillUI == DefaultSkillUI.ON) {
            toggleDefaultSkillUIButtonComponent = Component.translatable("gui.dungeonhelper.skill_cooltime_settings.defaultskillui").append(Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true)));
        } else {
            toggleDefaultSkillUIButtonComponent = Component.translatable("gui.dungeonhelper.skill_cooltime_settings.defaultskillui").append(Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true)));
        }

        this.toggleSkillCooltimeButton = this.addRenderableWidget((new Button.Builder(toggleSkillCooltimeButtonComponent, (btn) -> {
            this.onToggleSkillCooltimePress();
        }).tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.main.tooltip")))).pos(this.getRegularX() + 5, this.getRegularY() + 5).size(137, 20).build());

        Component classTypeButtonComponent = Component.empty();
        if (this.client.data.classType == ClassCategory.ASSASSIN) {
            classTypeButtonComponent = Component.translatable("gui.dungeonhelper.skill_cooltime_settings.classType").append(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.classType.assassin").withStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_RED).withBold(true)));
        } else if (this.client.data.classType == ClassCategory.DRAGON_WARRIOR) {
            classTypeButtonComponent = Component.translatable("gui.dungeonhelper.skill_cooltime_settings.classType").append(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.classType.dragon_warrior").withStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_PURPLE).withBold(true)));
        } else if (this.client.data.classType == ClassCategory.MARTIAL_ARTIST) {
            classTypeButtonComponent = Component.translatable("gui.dungeonhelper.skill_cooltime_settings.classType").append(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.classType.martial_artist").withStyle(Style.EMPTY.applyFormat(ChatFormatting.AQUA).withBold(true)));
        } else if (this.client.data.classType == ClassCategory.BATTLE_MAGE) {
            classTypeButtonComponent = Component.translatable("gui.dungeonhelper.skill_cooltime_settings.classType").append(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.classType.battle_mage").withStyle(Style.EMPTY.applyFormat(ChatFormatting.BLUE).withBold(true)));
        }

        this.classTypeButton = this.addRenderableWidget((new Button.Builder(classTypeButtonComponent, (btn) -> {
            this.onClassTypePress();
        }).tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.classType.tooltip")))).pos(this.getRegularX() + 5, this.getRegularY() + 5 + 20 + 2).size(137, 20).build());

        this.toggleCustomGUIPosButton = this.addRenderableWidget((new Button.Builder(toggleCustomGUIPosComponent, (btn) -> {
            this.onCustomGUIPosPress();
        }).tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.customguipos.tooltip")))).pos(this.getRegularX() + 5, this.getRegularY() + 5 + (20 + 2) * 2).size(137, 20).build());

        XPosSlider = this.addRenderableWidget(new Slider(getRegularX() + 5, getRegularY() + 5 + (20 + 2)*3, 137, 20, Component.literal("X : "), Component.literal(""), 0, 1000, client.data.SkillCooltimeXpos, true) {
            @Override
            protected void applyValue() {
                client.data.SkillCooltimeXpos = this.getValueInt();
                client.settings.save();
            }
        });
        XPosSlider.setTooltip(Tooltip.create(Component.translatable("mineplanetplus.config.xslider.tooltip")));

        YPosSlider = this.addRenderableWidget(new Slider(getRegularX() + 5, getRegularY() + 5 + (20 + 2)*4, 137, 20, Component.literal("Y : "), Component.literal(""), 0, 1000, client.data.SkillCooltimeYpos, true) {
            @Override
            protected void applyValue() {
                client.data.SkillCooltimeYpos = this.getValueInt();
                client.settings.save();
            }
        });
        YPosSlider.setTooltip(Tooltip.create(Component.translatable("mineplanetplus.config.yslider.tooltip")));

        this.toggleAutoClassDetectButton = this.addRenderableWidget((new Button.Builder(toggleAutoClassDetectButtonComponent, (btn) -> {
            this.onAutoClassDetectPress();
        }).tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.autoclassdetect.tooltip")))).pos(this.getRegularX() + 5, this.getRegularY() + 5 + (20 + 2) * 5).size(137, 20).build());

        toggleDefaultSkillUIButton = this.addRenderableWidget((new Button.Builder(toggleDefaultSkillUIButtonComponent, (btn) -> {
            this.onDefaultSkillUIPress();
        }).tooltip(Tooltip.create(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.defaultskillui.tooltip")))).pos(this.getRegularX() + 5, this.getRegularY() + 5 + (20 + 2) * 6).size(137, 20).build());

        if (ENABLE_DEBUG_MODE) {
            MutableComponent debugModeButtonComponent;
            if (DEBUG_MODE) {
                debugModeButtonComponent = Component.literal("DEBUG MODE").append(Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true)));
            } else {
                debugModeButtonComponent = Component.literal("DEBUG MODE").append(Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true)));
            }

            this.debugModeButton = this.addRenderableWidget((new Button.Builder(debugModeButtonComponent, (btn) -> {
                this.onDebugModePress();
            }).tooltip(Tooltip.create(Component.literal("개발용 기능입니다")))).pos(this.getRegularX() + 5, this.getRegularY() + 5 + (20 + 2)*7).size(137, 20).build());
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.minecraft.level == null) {
            super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        }
        background.setSize(this.bgWidth, this.bgHeight);
        background.setPosition(getRegularX(), getRegularY());
        background.extractRenderState(guiGraphics);
    }

    @Override
    protected void extractBlurredBackground(GuiGraphicsExtractor guiGraphics) {
        // pass
    }

    private void onToggleSkillCooltimePress() {
        this.client.data.toggleSkillCooltime = !this.client.data.toggleSkillCooltime;
        if (this.client.data.toggleSkillCooltime) {
            this.toggleSkillCooltimeButton.setMessage(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.main").append(Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
        } else {
            this.toggleSkillCooltimeButton.setMessage(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.main").append(Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
        }
        this.client.settings.save();
    }

    private void onClassTypePress() {
        if (this.client.data.classType == ClassCategory.ASSASSIN) {
            this.client.data.classType = ClassCategory.DRAGON_WARRIOR;
        } else if (this.client.data.classType == ClassCategory.DRAGON_WARRIOR) {
            this.client.data.classType = ClassCategory.MARTIAL_ARTIST;
        } else if (this.client.data.classType == ClassCategory.MARTIAL_ARTIST) {
            this.client.data.classType = ClassCategory.BATTLE_MAGE;
        } else if (this.client.data.classType == ClassCategory.BATTLE_MAGE) {
            this.client.data.classType = ClassCategory.ASSASSIN;
        }

        if (this.client.data.classType == ClassCategory.ASSASSIN) {
            this.classTypeButton.setMessage(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.classType").append(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.classType.assassin").withStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_RED).withBold(true))));
        } else if (this.client.data.classType == ClassCategory.DRAGON_WARRIOR) {
            this.classTypeButton.setMessage(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.classType").append(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.classType.dragon_warrior").withStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_PURPLE).withBold(true))));
        } else if (this.client.data.classType == ClassCategory.MARTIAL_ARTIST) {
            this.classTypeButton.setMessage(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.classType").append(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.classType.martial_artist").withStyle(Style.EMPTY.applyFormat(ChatFormatting.AQUA).withBold(true))));
        } else if (this.client.data.classType == ClassCategory.BATTLE_MAGE) {
            this.classTypeButton.setMessage(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.classType").append(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.classType.battle_mage").withStyle(Style.EMPTY.applyFormat(ChatFormatting.BLUE).withBold(true))));
        }
        this.client.settings.save();
    }

    private void onCustomGUIPosPress() {
        this.client.data.toggleCustomSkillGUIPos = !this.client.data.toggleCustomSkillGUIPos;
        if (this.client.data.toggleCustomSkillGUIPos) {
            this.toggleCustomGUIPosButton.setMessage(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.customguipos").append(Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
        } else {
            this.toggleCustomGUIPosButton.setMessage(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.customguipos").append(Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
        }
        this.client.settings.save();
    }

    private void onAutoClassDetectPress() {
        this.client.data.toggleAutoClassDetect = !this.client.data.toggleAutoClassDetect;
        if (this.client.data.toggleAutoClassDetect) {
            this.toggleAutoClassDetectButton.setMessage(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.autoclassdetect").append(Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
        } else {
            this.toggleAutoClassDetectButton.setMessage(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.autoclassdetect").append(Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
        }
        this.client.settings.save();
    }

    private void onDefaultSkillUIPress() {
        if (this.client.data.toggleDefaultSkillUI == DefaultSkillUI.AUTO) {
            this.client.data.toggleDefaultSkillUI = DefaultSkillUI.ON;
            this.toggleDefaultSkillUIButton.setMessage(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.defaultskillui").append(Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
        } else if (this.client.data.toggleDefaultSkillUI == DefaultSkillUI.ON) {
            this.client.data.toggleDefaultSkillUI = DefaultSkillUI.OFF;
            this.toggleDefaultSkillUIButton.setMessage(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.defaultskillui").append(Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
        } else {
            this.client.data.toggleDefaultSkillUI = DefaultSkillUI.AUTO;
            this.toggleDefaultSkillUIButton.setMessage(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.defaultskillui").append(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.defaultskillui.auto").withStyle(Style.EMPTY.applyFormat(ChatFormatting.BLUE).withBold(true))));
        }
        this.client.settings.save();
    }

    private void onDebugModePress() {
        DEBUG_MODE = !DEBUG_MODE;
        if (DEBUG_MODE) {
            this.debugModeButton.setMessage(Component.literal("DEBUG MODE").append(Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
        } else {
            this.debugModeButton.setMessage(Component.literal("DEBUG MODE").append(Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
        }
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