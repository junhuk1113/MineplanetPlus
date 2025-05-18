package net.pmkjun.mineplanetplus.dungeonhelper.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.dungeonhelper.util.DungeonCategory;
import net.pmkjun.mineplanetplus.dungeonhelper.util.DungeonCoolAxis;
import net.pmkjun.mineplanetplus.dungeonhelper.util.Timer;

public class DungeonCooltimeGui {
    private final Minecraft mc;
    private final DungeonHelperClient client;

    public static final int DUNGEON_COUNT = 8;
    private static final ResourceLocation[] DUNGEON_ICONS = new ResourceLocation[] {
            ResourceLocation.fromNamespaceAndPath("dungeonhelper", "textures/icon/dungeon/one_dungeon_icon.png"),
            ResourceLocation.fromNamespaceAndPath("dungeonhelper", "textures/icon/dungeon/two_dungeon_icon.png"),
            ResourceLocation.fromNamespaceAndPath("dungeonhelper", "textures/icon/dungeon/three_dungeon_icon.png"),
            ResourceLocation.fromNamespaceAndPath("dungeonhelper", "textures/icon/dungeon/four_dungeon_icon.png"),
            ResourceLocation.fromNamespaceAndPath("dungeonhelper", "textures/icon/dungeon/n_one_dungeon_icon.png"),
            ResourceLocation.fromNamespaceAndPath("dungeonhelper", "textures/icon/dungeon/n_two_dungeon_icon.png"),
            ResourceLocation.fromNamespaceAndPath("dungeonhelper", "textures/icon/dungeon/n_three_dungeon_icon.png"),
            ResourceLocation.fromNamespaceAndPath("dungeonhelper", "textures/icon/dungeon/n_four_dungeon_icon.png")
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

    private static final ResourceLocation BLACK_ICON = ResourceLocation.fromNamespaceAndPath("dungeonhelper", "textures/icon/black.png");

    private Component lastTitle = null;

    public DungeonCooltimeGui(){
        mc = Minecraft.getInstance();
        client = DungeonHelperClient.getInstance();
    }

    public void renderTick(GuiGraphics guiGraphics, Component title, Timer timer) {
        if(title != null) {
            try
            {
                if (title != lastTitle) {
                    for (int i = 0; i < DUNGEON_COUNT; i++) {
                        if (title.getString().contains(DUNGEON_NAMES[i])) {
                            client.data.lastDungeonTime[i] = timer.getCurrentTime();
                            client.settings.save();
                            break;
                        }
                    }
                }
            }
            catch(Exception e) {
                e.printStackTrace();
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
            render(guiGraphics, seconds);
    }

    private void render(GuiGraphics guiGraphics, int[] seconds) {
        float scale = client.data.uiScale;
        int dungeon_count = DUNGEON_COUNT;
        int id = 0;

        if(client.data.dungeontype == DungeonCategory.NORMAL) dungeon_count = 4;
        else if(client.data.dungeontype == DungeonCategory.CHAOS) {
            id = 4;
            dungeon_count = 4;
        }

        // scale에 따른 텍스트 공간 계산 (27/16 비율 적용)
        float textOffset = 1.458333f * scale + 3.66667f;
        //int textOffset = 97;
        int x, y;
        for(int i = 0; i < dungeon_count; i++) {
            if(client.data.coolAxis == DungeonCoolAxis.VERTICAL) {
                x = 2 + (mc.getWindow().getGuiScaledWidth() - (int)(scale + 2 + textOffset)) * client.data.DungeonCooltimeXpos / 1000;
                y = 2 + (mc.getWindow().getGuiScaledHeight() - (int)((scale + 2) * dungeon_count - 2)) * client.data.DungeonCooltimeYpos / 1000;
            }
            else {
                x = 2 + (mc.getWindow().getGuiScaledWidth() - (int)((scale + 2) * dungeon_count - 2)) * client.data.DungeonCooltimeXpos / 1000;
                y = 2 + (mc.getWindow().getGuiScaledHeight() - (int)(2 + textOffset)) * client.data.DungeonCooltimeYpos / 1000;
            }

            renderDungeonsAndCooltime(i, id, guiGraphics, x, y, seconds[id]);
            id++;
        }
    }

    private void renderDungeonsAndCooltime(int i, int id, GuiGraphics guiGraphics, int x, int y, int second) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        
        // 수정된 위치 계산
        if(client.data.coolAxis == DungeonCoolAxis.VERTICAL)
            poseStack.translate(x, y + (client.data.uiScale + 2) * i, 0);
        else {
            poseStack.translate(x + (client.data.uiScale + 2) * i, y, 0);
        }
        
        // 수정된 스케일 적용
        poseStack.scale(client.data.uiScale/256f, client.data.uiScale/256f, client.data.uiScale/256f);

        ResourceLocation texture;
        if((texture = getDungeonTexture(id)) == null)
            return;

        guiGraphics.blit(RenderType::guiTextured, texture, 0, 0, 0, 0, 256, 256, 256, 256);

        if(client.data.toggleDungeonCooltimeFade) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            guiGraphics.blit(RenderType::guiTextured, BLACK_ICON, 0, 0, 0, 0, 256, (int)(256 * (float)second / 3600f), 256, 256);
        }

        poseStack.scale(256f/client.data.uiScale, 256f/client.data.uiScale, 256f/client.data.uiScale);
        poseStack.popPose();

        // 텍스트 크기도 UI 크기에 맞게 조정
        if(client.data.toggleDungeonCooltimeText) {
            Font font = mc.font;

            int minute = second / 60;
            second -= minute * 60;

            poseStack.pushPose();
            if(client.data.coolAxis == DungeonCoolAxis.VERTICAL)
                poseStack.translate(x + client.data.uiScale + 2, y + (client.data.uiScale/4) + (client.data.uiScale + 2) * i, 0);
            else {
                poseStack.translate(x + (client.data.uiScale/2) + (client.data.uiScale + 2) * i, y + client.data.uiScale + 2, 0);
            }

            float textScale = client.data.uiScale/16f; // UI 크기에 비례하여 텍스트 크기 조정
            poseStack.scale(textScale/1.1f, textScale/1.1f, textScale/1.1f);

            if(client.data.coolAxis == DungeonCoolAxis.VERTICAL)
                guiGraphics.drawString(font, Component.literal(String.format("%d:%d", minute, second)), 0, 0, 0xFFFFFF);
            else
                guiGraphics.drawCenteredString(font, Component.literal(String.format("%d", minute)), 0, 0, 0xFFFFFF);

            poseStack.scale(1.1f, 1.1f, 1.1f);
            poseStack.popPose();
        }
    }

    private ResourceLocation getDungeonTexture(int dungeon_id) {
        if(dungeon_id >= DUNGEON_COUNT)
            return null;

        return DUNGEON_ICONS[dungeon_id];
    }
}