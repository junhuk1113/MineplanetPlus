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
import net.pmkjun.mineplanetplus.gui.StretchableBackground;

public class DungeonTypeSettingScreen extends Screen {

    private final Minecraft mc;
    private final DungeonHelperClient client;
    private final StretchableBackground background = new StretchableBackground();

    private Button toggleNormalDungeonButton;
    private Button toggleChaosDungeonButton;
    private Button toggleChallengeTowerButton;

    private final int width, height;
    private final Screen parentScreen;

    protected DungeonTypeSettingScreen (Screen parentScreen) {
        super(Component.literal("DungeonTypeSettingScreen"));

        mc = Minecraft.getInstance();
        client = DungeonHelperClient.getInstance();
        this.parentScreen = parentScreen;

        width = 147;
        height = 74;
    }

    protected void init() {
        super.init();

        toggleNormalDungeonButton = this.addRenderableWidget(new Button.Builder(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.normal"), btn -> onToggleNormalDungeonPress())
                .pos(getRegularX() + 5, getRegularY() + 5)
                .size(137, 20)
                .build());
        setToggleNormalDungeonButtonText();

        toggleChaosDungeonButton = this.addRenderableWidget(new Button.Builder(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.chaos"), btn -> onToggleChaosDungeonPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2))
                .size(137, 20)
                .build());
        setToggleChaosDungeonButtonText();

        toggleChallengeTowerButton = this.addRenderableWidget(new Button.Builder(Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.challengetower"), btn -> onToggleChallengeTowerPress())
                .pos(getRegularX() + 5, getRegularY() + 5 + (20 + 2)*2)
                .size(137, 20)
                .build());
        setToggleChallengeTowerPress();

    }

    public void render(GuiGraphics guiGraphics, int a, int b, float c) {
        super.render(guiGraphics, a, b, c);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(mc.level == null) {
            super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        }
        //guiGraphics.blit(RenderType::guiTextured, BG_LOCATION, getRegularX(), getRegularY(), 0, 0, width, height, 256, 256);
        background.setSize(width, height);
        background.setPosition(getRegularX(), getRegularY());
        background.render(guiGraphics);
    }

    private void onToggleNormalDungeonPress() {
        client.data.toggleNormalDungeon = !client.data.toggleNormalDungeon;
        setToggleNormalDungeonButtonText();
        client.settings.save();
    }
    private void setToggleNormalDungeonButtonText(){
        if(client.data.toggleNormalDungeon) {
            toggleNormalDungeonButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.normal").append(
                            Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                    ));
        }
        else {
            toggleNormalDungeonButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.normal").append(
                            Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))
                    ));
        }
    }

    private void onToggleChaosDungeonPress(){
        client.data.toggleChaosDungeon = !client.data.toggleChaosDungeon;
        setToggleChaosDungeonButtonText();
        client.settings.save();
    }
    private void setToggleChaosDungeonButtonText(){
        if(client.data.toggleChaosDungeon) {
            toggleChaosDungeonButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.chaos").append(
                            Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                    ));
        }
        else {
            toggleChaosDungeonButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.chaos").append(
                            Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))
                    ));
        }
    }

    private void onToggleChallengeTowerPress(){
        client.data.toggleChallengeTower = !client.data.toggleChallengeTower;
        setToggleChallengeTowerPress();
        client.settings.save();
    }
    private void setToggleChallengeTowerPress(){
        if(client.data.toggleChallengeTower) {
            toggleChallengeTowerButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.challengetower").append(
                            Component.translatable("gui.dungeonhelper.settings.on").withStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN).withBold(true))
                    ));
        }
        else {
            toggleChallengeTowerButton.setMessage(
                    Component.translatable("gui.dungeonhelper.dungeon_cooltime_settings.dungeontype.challengetower").append(
                            Component.translatable("gui.dungeonhelper.settings.off").withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withBold(true))
                    ));
        }
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
