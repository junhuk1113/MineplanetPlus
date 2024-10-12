package net.pmkjun.mineplanetplus.dungeonhelper.gui.screen;

import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.dungeonhelper.gui.components.Slider;
import net.pmkjun.mineplanetplus.dungeonhelper.util.ClassCategory;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

public class SkillCooltimeSettingsScreen extends Screen {
    private Minecraft mc;
    private DungeonHelperClient client;
    public static final ResourceLocation BG_LOCATION = new ResourceLocation("dungeonhelper", "textures/gui/skill_cooltime_settings_background.png");
    private Button toggleSkillCooltimeButton;
    private Button classTypeButton;
    private Button debugModeButton;
    private Button toggleCustomGUIPosButton;
    private Slider XPosSlider, YPosSlider;
    private int width, height;
    private final Screen parentScreen;
    public static boolean ENABLE_DEBUG_MODE = false;
    public static boolean DEBUG_MODE = false;

    public SkillCooltimeSettingsScreen(Screen parentScreen) {
        super(Component.literal("SkillCooltimeSettingScreen"));
        mc = Minecraft.getInstance();
        client = DungeonHelperClient.getInstance();

        this.parentScreen = parentScreen;

        width = 147;
        height = 118;
    }

    protected void init() {
        super.init();
        MutableComponent toggleSkillCooltimeButtonComponent, toggleCustomGUIPosComponent;
        if (this.client.data.toggleSkillCooltime) {
            toggleSkillCooltimeButtonComponent = Component.translatable("gui.dungeonhelper.skill_cooltime_settings.main").append(Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true)));
        } else {
            toggleSkillCooltimeButtonComponent = Component.translatable("gui.dungeonhelper.skill_cooltime_settings.main").append(Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true)));
        }
        if (this.client.data.toggleCustomSkillGUIPos){
            toggleCustomGUIPosComponent = Component.translatable("gui.dungeonhelper.skill_cooltime_settings.customguipos").append(Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true)));
        } else {
            toggleCustomGUIPosComponent = Component.translatable("gui.dungeonhelper.skill_cooltime_settings.customguipos").append(Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true)));
        }

        this.toggleSkillCooltimeButton = (Button)this.addRenderableWidget((new Button.Builder(toggleSkillCooltimeButtonComponent, (btn) -> {
            this.onToggleSkillCooltimePress();
        })).pos(this.getRegularX() + 5, this.getRegularY() + 5).size(137, 20).build());
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

        this.classTypeButton = (Button)this.addRenderableWidget((new Button.Builder(classTypeButtonComponent, (btn) -> {
            this.onClassTypePress();
        })).pos(this.getRegularX() + 5, this.getRegularY() + 5 + 20 + 2).size(137, 20).build());

        this.toggleCustomGUIPosButton = (Button)this.addRenderableWidget((new Button.Builder(toggleCustomGUIPosComponent, (btn) -> {
            this.onCustomGUIPosPress();
        })).pos(this.getRegularX() + 5, this.getRegularY() + 5 + (20 + 2) * 2).size(137, 20).build());

        XPosSlider = this.addRenderableWidget(new Slider(getRegularX() + 5, getRegularY() + 5 + (20 + 2)*3, 137, 20,Component.literal("X : "),Component.literal(""),0,1000,client.data.SkillCooltimeXpos,true){
            @Override
            protected void applyValue() {
                client.data.SkillCooltimeXpos = this.getValueInt();
                client.settings.save();
            }
        });
        YPosSlider = this.addRenderableWidget(new Slider(getRegularX() + 5, getRegularY() + 5 + (20 + 2)*4, 137, 20,Component.literal("Y : "),Component.literal(""),0,1000,client.data.SkillCooltimeXpos,true){
            @Override
            protected void applyValue() {
                client.data.SkillCooltimeYpos = this.getValueInt();
                client.settings.save();
            }
        });

        if (ENABLE_DEBUG_MODE) {
            MutableComponent debugModeButtonComponent;
            if (DEBUG_MODE) {
                debugModeButtonComponent = Component.literal("DEBUG MODE").append(Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true)));
            } else {
                debugModeButtonComponent = Component.literal("DEBUG MODE").append(Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true)));
            }

            this.debugModeButton = (Button)this.addRenderableWidget((new Button.Builder(debugModeButtonComponent, (btn) -> {
                this.onDebugModePress();
            })).pos(this.getRegularX() + 5, this.getRegularY() + 5 + (20 + 2)*5).size(137, 20).build());
        }

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

        guiGraphics.blit(BG_LOCATION, this.getRegularX(), this.getRegularY(), 0, 0, this.width, this.height);
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

    private void onCustomGUIPosPress(){
        this.client.data.toggleCustomSkillGUIPos = !this.client.data.toggleCustomSkillGUIPos;
        if (this.client.data.toggleCustomSkillGUIPos) {
            this.toggleCustomGUIPosButton.setMessage(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.customguipos").append(Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))));
        } else {
            this.toggleCustomGUIPosButton.setMessage(Component.translatable("gui.dungeonhelper.skill_cooltime_settings.customguipos").append(Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))));
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
    public void onClose(){
        assert this.minecraft != null;
        this.minecraft.setScreen(parentScreen);
    }

    int getRegularX() {
        return this.mc.getWindow().getGuiScaledWidth() / 2 - this.width / 2;
    }

    int getRegularY() {
        return this.mc.getWindow().getGuiScaledHeight() / 2 - this.height / 2;
    }
}