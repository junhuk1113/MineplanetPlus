package net.pmkjun.mineplanetplus.dungeonhelper.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.dungeonhelper.util.ClassCategory;
import net.pmkjun.mineplanetplus.dungeonhelper.util.DungeonSkill;
import net.pmkjun.mineplanetplus.dungeonhelper.util.SkillCategory;
import org.joml.Matrix3x2fStack;

public class SkillCooltimeGui {

    private final Minecraft mc;
    private final DungeonHelperClient client;
    private final DungeonSkill dungeonSkill;

    // ResourceLocation -> Identifier 변경
    private static final Identifier WIDGETS = Identifier.withDefaultNamespace("hud/hotbar_offhand_left");
    private static final Identifier BLACK_ICON = Identifier.fromNamespaceAndPath("dungeonhelper", "textures/icon/black.png");
    private static final Identifier MANA_RUNOUT_ICON = Identifier.fromNamespaceAndPath("dungeonhelper", "textures/icon/mana_runout.png");

    private static final int SKILL_GUI_SIZE = 16;

    private static final float BLADE_SPIN_SKILL_COOLTIME = 7.3f;
    private static final float DRAGON_SMASH_COOLTIME = 6.4f;
    private static final float MARTIAL_DRIVE_COOLTIME = 7.9f;
    private static final float INFERNO_CHAIN_COOLTIME = 10.0f;

    private static final float BLADE_DANCE_COOLTIME = 30f;
    private static final float DRAGON_FURY_COOLTIME = 27.5f;
    private static final float MULTIPLE_BLOW_COOLTIME = 27.0F;
    private static final float ARCANE_DEMOLITION_COOLTIME = 25.7F;

    private float leftComboSkillTime;
    private float leftLV30SkillTime = 0;
    private float leftLV40SkillTime;
    private float leftUltimateTime;

    private static final float DAGGER_THROW_COOLTIME = 0.4f;      // 어쌔신
    private static final float DRAGON_BREATH_COOLTIME = 0.4f;     // 드래곤워리어
    private static final float ARCANE_SPIN_COOLTIME = 6.3f;       // 배틀메이지

    public SkillCooltimeGui() {
        mc = Minecraft.getInstance();
        client = DungeonHelperClient.getInstance();
        dungeonSkill = new DungeonSkill();
    }

    public void updateLeftComboSkillTime(float cooldown) {
        leftComboSkillTime = cooldown;
    }
    public void updateLeftLV30SkillTime(float cooldown) {
        leftLV30SkillTime = cooldown;
    }
    public void updateLeftLV40SkillTime(float cooldown) {
        leftLV40SkillTime = cooldown;
    }
    public void updateLeftUltimateTime(float cooldown) {
        leftUltimateTime = cooldown;
    }

    // renderTick -> extractRenderState
    public void extractRenderState(GuiGraphicsExtractor guiGraphics) {
        if (!client.data.toggleSkillCooltime)
            return;

        extract(guiGraphics);
    }

    // render -> extract
    private void extract(GuiGraphicsExtractor guiGraphics) {
        int xOffset = 98;
        extractSkillTexture(guiGraphics, xOffset);
        extractSkillCooltime(guiGraphics, xOffset);
    }

    // renderSkillTexture -> extractSkillTexture
    private void extractSkillTexture(GuiGraphicsExtractor guiGraphics, int xOffset) {
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        int yOffset;
        if (client.data.toggleCustomSkillGUIPos) {
            xOffset = (screenWidth - (22) * 4) * client.data.SkillCooltimeXpos / 1000;
            yOffset = (screenHeight - 22) * client.data.SkillCooltimeYpos / 1000;
        } else {
            xOffset = screenWidth / 2 + xOffset;
            yOffset = screenHeight - 22;
        }

        int i = 0;
        for (int skillNum = 0; skillNum < 5; skillNum++) {
            if (dungeonSkill.isComboSkill(client.data.classType, skillNum) && isComboSkillUseable()) {
                skillNum++;
            }
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED_PREMULTIPLIED_ALPHA, WIDGETS, xOffset + (22) * i, yOffset - 1, 29, 24);
            Identifier texture = dungeonSkill.getSkillTexture(client.data.classType, skillNum);

            extractSkillTextureIcon(guiGraphics, texture, xOffset + (22) * i + 3, yOffset + 3);

            if (isManaRunout(client.data.classType, dungeonSkill.getSkillCategory(skillNum))
                    && !(skillNum == 3 && isLV40SkillCooltime()) && !(skillNum == 4 && isUltimateCooltime())) {
                extractSkillTextureIcon(guiGraphics, MANA_RUNOUT_ICON, xOffset + (22) * i + 3, yOffset + 3);
            }
            if (dungeonSkill.isComboSkill(client.data.classType, skillNum) && !isComboSkillUseable()) {
                skillNum++;
            }
            i++;
        }
    }

    // renderSkillCooltime -> extractSkillCooltime
    private void extractSkillCooltime(GuiGraphicsExtractor guiGraphics, int xOffset) {
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        int x, y;

        if (client.data.toggleCustomSkillGUIPos) {
            x = (screenWidth - (22) * 4) * client.data.SkillCooltimeXpos / 1000 + 3;
            y = (screenHeight - 22) * client.data.SkillCooltimeYpos / 1000 + 4;
        } else {
            x = screenWidth / 2 + xOffset + 3;
            y = screenHeight - 22 + 4;
        }

        float lv30SkillCooltime = 0;
        if (client.data.classType == ClassCategory.ASSASSIN) {
            lv30SkillCooltime = DAGGER_THROW_COOLTIME;
        } else if (client.data.classType == ClassCategory.DRAGON_WARRIOR) {
            lv30SkillCooltime = DRAGON_BREATH_COOLTIME;
        } else if (this.client.data.classType == ClassCategory.BATTLE_MAGE) {
            lv30SkillCooltime = ARCANE_SPIN_COOLTIME;
        }

        float lv40SkillCooltime = 0;

        if (client.data.classType == ClassCategory.ASSASSIN) {
            lv40SkillCooltime = BLADE_SPIN_SKILL_COOLTIME;
        } else if (client.data.classType == ClassCategory.DRAGON_WARRIOR) {
            lv40SkillCooltime = DRAGON_SMASH_COOLTIME;
        } else if (this.client.data.classType == ClassCategory.MARTIAL_ARTIST) {
            lv40SkillCooltime = MARTIAL_DRIVE_COOLTIME;
        } else if (this.client.data.classType == ClassCategory.BATTLE_MAGE) {
            lv40SkillCooltime = INFERNO_CHAIN_COOLTIME;
        }

        float ultimateCooltime = 10;
        if (client.data.classType == ClassCategory.ASSASSIN) {
            ultimateCooltime = BLADE_DANCE_COOLTIME;
        } else if (client.data.classType == ClassCategory.DRAGON_WARRIOR) {
            ultimateCooltime = DRAGON_FURY_COOLTIME;
        } else if (this.client.data.classType == ClassCategory.MARTIAL_ARTIST) {
            ultimateCooltime = MULTIPLE_BLOW_COOLTIME;
        } else if (this.client.data.classType == ClassCategory.BATTLE_MAGE) {
            ultimateCooltime = ARCANE_DEMOLITION_COOLTIME;
        }

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED_PREMULTIPLIED_ALPHA, BLACK_ICON, x + 22 * 1, y + (int) (SKILL_GUI_SIZE * (1 - leftLV30SkillTime / lv30SkillCooltime)), 0, 0, SKILL_GUI_SIZE, (int) (SKILL_GUI_SIZE * (leftLV30SkillTime / lv30SkillCooltime)), 256, 256);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED_PREMULTIPLIED_ALPHA, BLACK_ICON, x + 22 * 2, y + (int) (SKILL_GUI_SIZE * (1 - leftLV40SkillTime / lv40SkillCooltime)), 0, 0, SKILL_GUI_SIZE, (int) (SKILL_GUI_SIZE * (leftLV40SkillTime / lv40SkillCooltime)),256, 256);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED_PREMULTIPLIED_ALPHA, BLACK_ICON, x + 22 * 3, y + (int) (SKILL_GUI_SIZE * (1 - leftUltimateTime / ultimateCooltime)), 0, 0, SKILL_GUI_SIZE, (int) (SKILL_GUI_SIZE * (leftUltimateTime / ultimateCooltime)),256, 256);

        Matrix3x2fStack poseStack = guiGraphics.pose();
        if (isComboSkillUseable()) {
            poseStack.pushMatrix();
            poseStack.translate(x + 8 + 22 * dungeonSkill.getComboSkill(client.data.classType), y + 4);
            poseStack.scale(1f/1.1f, 1f/1.1f);

            String cooldownText = String.format("%.1f", leftComboSkillTime);

            // drawCenteredString -> centeredText
            guiGraphics.centeredText(mc.font, Component.literal(cooldownText), 0, 0, ARGB.white(255));
            poseStack.popMatrix();
        }

        if (isLV30SkillCooltime()) {
            poseStack.pushMatrix();
            poseStack.translate(x + 2 + 22 * 1 + 6, y + 4);
            poseStack.scale(1f/1.1f, 1f/1.1f);

            String cooldownText = String.format("%.1f", leftLV30SkillTime);

            guiGraphics.centeredText(mc.font, Component.literal(cooldownText), 0, 0, ARGB.white(255));
            poseStack.popMatrix();
        }

        if (isLV40SkillCooltime()) {
            poseStack.pushMatrix();
            poseStack.translate(x + 2 + 22 * 2 + 6, y + 4);
            poseStack.scale(1f/1.1f, 1f/1.1f);

            String cooldownText = String.format("%.1f", leftLV40SkillTime);

            guiGraphics.centeredText(mc.font, Component.literal(cooldownText), 0, 0, ARGB.white(255));
            poseStack.popMatrix();
        }

        if (isUltimateCooltime()) {
            poseStack.pushMatrix();
            poseStack.translate(x + 2 + 22 * 3 + 6, y + 4);
            poseStack.scale(1f/1.1f, 1f/1.1f);

            String cooldownText = String.format("%.1f", leftUltimateTime);

            guiGraphics.centeredText(mc.font, Component.literal(cooldownText), 0, 0, ARGB.white(255));
            poseStack.popMatrix();
        }
    }

    // drawSkillTexture -> extractSkillTextureIcon
    private void extractSkillTextureIcon(GuiGraphicsExtractor guiGraphics, Identifier texture, int x, int y) {
        float scaleRatio = SKILL_GUI_SIZE / 256f;

        Matrix3x2fStack poseStack = guiGraphics.pose();
        poseStack.pushMatrix();
        poseStack.translate(x, y);
        poseStack.scale(scaleRatio, scaleRatio);

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, 0, 0, 0, 0, 256, 256, 256, 256);

        poseStack.popMatrix();
    }

    public boolean isManaRunout(ClassCategory classtype, SkillCategory skilltype) {
        if (!(classtype == ClassCategory.MARTIAL_ARTIST)) { // 어새신, 용기사, 배틀메이지
            if(skilltype == SkillCategory.DASH) return this.client.isSlot1Manarunout;
            else if(skilltype == SkillCategory.LV20) return this.client.isSlot1Manarunout;
            else if(skilltype == SkillCategory.LV30) return this.client.isSlot3Manarunout;
            else if(skilltype == SkillCategory.LV40) return this.client.isSlot4Manarunout;
            else if(skilltype == SkillCategory.ULTIMATE) return this.client.isSlot5Manarunout;
        } else { // 무투가
            if(skilltype == SkillCategory.DASH) return this.client.isSlot1Manarunout;
            else if(skilltype == SkillCategory.LV20) return this.client.isSlot4Manarunout;
            else if(skilltype == SkillCategory.LV30) return this.client.isSlot4Manarunout;
            else if(skilltype == SkillCategory.LV40) return this.client.isSlot3Manarunout;
            else if(skilltype == SkillCategory.ULTIMATE) return this.client.isSlot5Manarunout;
        }
        return false;
    }

    public boolean isLV40SkillCooltime() {
        return leftLV40SkillTime > 0;
    }
    public boolean isUltimateCooltime() {
        return leftUltimateTime > 0;
    }
    public boolean isComboSkillUseable() {
        return leftComboSkillTime > 0;
    }
    public boolean isLV30SkillCooltime() {
        return leftLV30SkillTime > 0;
    }
}