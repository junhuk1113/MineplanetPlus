package net.pmkjun.mineplanetplus.dungeonhelper.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.dungeonhelper.util.DungeonCategory;
import net.pmkjun.mineplanetplus.dungeonhelper.util.DungeonCoolAxis;
import net.pmkjun.mineplanetplus.dungeonhelper.util.Timer;
import org.joml.Matrix3x2fStack;

public class DungeonCooltimeGui {
    private final Minecraft mc;
    private final DungeonHelperClient client;

    public static final int DUNGEON_COUNT = 8;
    // ResourceLocation -> Identifier
    private static final Identifier[] DUNGEON_ICONS = new Identifier[] {
            Identifier.fromNamespaceAndPath("dungeonhelper", "textures/icon/dungeon/one_dungeon_icon.png"),
            Identifier.fromNamespaceAndPath("dungeonhelper", "textures/icon/dungeon/two_dungeon_icon.png"),
            Identifier.fromNamespaceAndPath("dungeonhelper", "textures/icon/dungeon/three_dungeon_icon.png"),
            Identifier.fromNamespaceAndPath("dungeonhelper", "textures/icon/dungeon/four_dungeon_icon.png"),
            Identifier.fromNamespaceAndPath("dungeonhelper", "textures/icon/dungeon/n_one_dungeon_icon.png"),
            Identifier.fromNamespaceAndPath("dungeonhelper", "textures/icon/dungeon/n_two_dungeon_icon.png"),
            Identifier.fromNamespaceAndPath("dungeonhelper", "textures/icon/dungeon/n_three_dungeon_icon.png"),
            Identifier.fromNamespaceAndPath("dungeonhelper", "textures/icon/dungeon/n_four_dungeon_icon.png")
    };
    private static final String[] DUNGEON_NAMES = {
            "그루트의 골짜기",
            "망령의 무덤",
            "혹한의 성역",
            "군단장의 요새",
            "망자의 묘지",
            "고블린의 요새",
            "마천루 : 네온시티",
            "헤럴드의 성채"
    };

    private static final Identifier BLACK_ICON = Identifier.fromNamespaceAndPath("dungeonhelper", "textures/icon/black.png");

    private Component lastTitle = null;

    public DungeonCooltimeGui() {
        mc = Minecraft.getInstance();
        client = DungeonHelperClient.getInstance();
    }

    // renderTick -> extractRenderState
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, Component title, Timer timer) {
        if(title != null) {
            try {
                if (title != lastTitle) {
                    for (int i = 0; i < DUNGEON_COUNT; i++) {
                        if (title.getString().contains(DUNGEON_NAMES[i])) {
                            client.data.lastDungeonTime[i] = timer.getCurrentTime();
                            client.settings.save();
                            break;
                        }
                    }
                }
            } catch(Exception ignored) {
            }

            lastTitle = title;
        }

        int[] seconds = new int[DUNGEON_COUNT];
        for(int i = 0; i < seconds.length; i++) {
            seconds[i] = 3600 - (int) timer.getDifference(client.data.lastDungeonTime[i]);
            if(seconds[i] < 0)
                seconds[i] = 0;
        }

        if(client.data.toggleDungeonCooltime)
            extract(guiGraphics, seconds);
    }

    // render -> extract
    private void extract(GuiGraphicsExtractor guiGraphics, int[] seconds) {
        float scale = client.data.dungeonCooltime_uiScale;
        int dungeon_count = DUNGEON_COUNT;
        int id = 0;

        if(client.data.dungeontype == DungeonCategory.NORMAL) dungeon_count = 4;
        else if(client.data.dungeontype == DungeonCategory.CHAOS) {
            id = 4;
            dungeon_count = 4;
        }

        float textOffset = 1.458333f * scale + 3.66667f;
        int x, y;
        for(int i = 0; i < dungeon_count; i++) {
            if(client.data.coolAxis == DungeonCoolAxis.VERTICAL) {
                x = 2 + (mc.getWindow().getGuiScaledWidth() - (int)(scale + 2 + textOffset)) * client.data.DungeonCooltimeXpos / 1000;
                y = 2 + (mc.getWindow().getGuiScaledHeight() - (int)((scale + 2) * dungeon_count - 2)) * client.data.DungeonCooltimeYpos / 1000;
            } else {
                x = 2 + (mc.getWindow().getGuiScaledWidth() - (int)((scale + 2) * dungeon_count - 2)) * client.data.DungeonCooltimeXpos / 1000;
                y = 2 + (mc.getWindow().getGuiScaledHeight() - (int)(2 + textOffset)) * client.data.DungeonCooltimeYpos / 1000;
            }

            extractDungeonsAndCooltime(i, id, guiGraphics, x, y, seconds[id]);
            id++;
        }
    }

    // renderDungeonsAndCooltime -> extractDungeonsAndCooltime
    private void extractDungeonsAndCooltime(int i, int id, GuiGraphicsExtractor guiGraphics, int x, int y, int second) {
        Matrix3x2fStack poseStack = guiGraphics.pose();
        poseStack.pushMatrix();

        if(client.data.coolAxis == DungeonCoolAxis.VERTICAL)
            poseStack.translate(x, y + (client.data.dungeonCooltime_uiScale + 2) * i);
        else {
            poseStack.translate(x + (client.data.dungeonCooltime_uiScale + 2) * i, y);
        }

        poseStack.scale(client.data.dungeonCooltime_uiScale /256f, client.data.dungeonCooltime_uiScale /256f);

        Identifier texture;
        if((texture = getDungeonTexture(id)) == null)
            return;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, 0, 0, 0, 0, 256, 256, 256, 256);

        if(client.data.toggleDungeonCooltimeFade) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED_PREMULTIPLIED_ALPHA, BLACK_ICON, 0, 0, 0, 0, 256, (int)(256 * (float)second / 3600f), 256, 256);
        }

        poseStack.scale(256f/client.data.dungeonCooltime_uiScale, 256f/client.data.dungeonCooltime_uiScale);
        poseStack.popMatrix();

        if(client.data.toggleDungeonCooltimeText) {
            Font font = mc.font;

            int minute = second / 60;
            second -= minute * 60;

            poseStack.pushMatrix();
            if(client.data.coolAxis == DungeonCoolAxis.VERTICAL)
                poseStack.translate(x + client.data.dungeonCooltime_uiScale + 2, y + (client.data.dungeonCooltime_uiScale /4) + (client.data.dungeonCooltime_uiScale + 2) * i);
            else {
                poseStack.translate(x + (client.data.dungeonCooltime_uiScale /2) + (client.data.dungeonCooltime_uiScale + 2) * i, y + client.data.dungeonCooltime_uiScale + 2);
            }

            float textScale = client.data.dungeonCooltime_uiScale /16f;
            poseStack.scale(textScale/1.1f, textScale/1.1f);

            // drawString -> text, drawCenteredString -> centeredText
            if(client.data.coolAxis == DungeonCoolAxis.VERTICAL)
                guiGraphics.text(font, Component.literal(String.format("%d:%d", minute, second)), 0, 0, ARGB.white(255));
            else
                guiGraphics.centeredText(font, Component.literal(String.format("%d", minute)), 0, 0, ARGB.white(255));

            poseStack.scale(1.1f, 1.1f);
            poseStack.popMatrix();
        }
    }

    private Identifier getDungeonTexture(int dungeon_id) {
        if(dungeon_id >= DUNGEON_COUNT)
            return null;

        return DUNGEON_ICONS[dungeon_id];
    }
}