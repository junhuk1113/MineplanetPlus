package net.pmkjun.mineplanetplus.dungeonhelper.gui;

import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.dungeonhelper.file.Mana;
import net.pmkjun.mineplanetplus.dungeonhelper.util.ClassCategory;
import net.pmkjun.mineplanetplus.dungeonhelper.util.SkillCategory;
import net.pmkjun.mineplanetplus.dungeonhelper.util.Timer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.pmkjun.mineplanetplus.dungeonhelper.util.DungeonSkill;

public class SkillCooltimeGui {

    private Minecraft mc;
    private DungeonHelperClient client;
    private DungeonSkill dungeonSkill;

    private static final ResourceLocation WIDGETS = new ResourceLocation("textures/gui/widgets.png");
    private static final ResourceLocation BLACK_ICON = new ResourceLocation("dungeonhelper", "textures/icon/black.png");
    private static final ResourceLocation MANA_RUNOUT_ICON = new ResourceLocation("dungeonhelper", "textures/icon/mana_runout.png");

    private static final int SKILL_GUI_SIZE = 16;
    private static final float ASSASSIN_SKILL_REACTIVATION_TIME = 0.5f;
    private static final float DRAGON_WARRIOR_SKILL_REACTIVATION_TIME = 1;
    private static final float MARTIAL_ARTIST_SKILL_REACTIVATION_TIME = 1;
    private static final float BATTLE_MAGE_SKILL_REACTIVATION_TIME = 0.7f;

    private static final float BLADE_SPIN_SKILL_COOLTIME = 7.3f;
    private static final float DRAGON_SMASH_COOLTIME = 6.4f;
    private static final float MARTIAL_DRIVE_COOLTIME = 7.9f;
    private static final float INFERNO_CHAIN_COOLTIME = 7.9f;

    private static final float BLADE_DANCE_COOLTIME = 30f;
    private static final float DRAGON_FURY_COOLTIME = 27.5f;
    private static final float MULTIPLE_BLOW_COOLTIME = 27.0F;
    private static final float ARCANE_DEMOLITION_COOLTIME = 25.7F;

    private long lastComboSkillTime = 0;
    private long lastLV40SkillTime = 0;
    private long lastUltimateTime = 0;

    private float leftComboSkillTime;
    private float leftLV40SkillTime;
    private float leftUltimateTime;

    public SkillCooltimeGui() {
        mc = Minecraft.getInstance();
        client = DungeonHelperClient.getInstance();
        dungeonSkill = new DungeonSkill();
    }

    public void updateLastComboSkillTime(Timer timer){
        lastComboSkillTime = timer.getCurrentTime();
    }
    public void updateLastLV40SkillTime(Timer timer){
        lastLV40SkillTime = timer.getCurrentTime();
    }
    public void updateLastLV40SkillTime(Timer timer, long plustime)
    {
        lastLV40SkillTime = timer.getCurrentTime() + plustime;
    }
    public void updateLastUltimateTime(Timer timer) {
        lastUltimateTime = timer.getCurrentTime();
    }
    public void resetLastComboSkillTime() {
        lastComboSkillTime = 0;
    }
    public void delayLastSkilltime(long delay){
        if(isComboSkillUseable()){
            lastComboSkillTime += delay;
        }
        if(isLV40SkillCooltime()){
            lastLV40SkillTime += delay;
        }
        if(isUltimateCooltime()){
            lastUltimateTime += delay;
        }
    }

    public void renderTick(GuiGraphics guiGraphics, Timer timer) {
        if(!client.data.toggleSkillCooltime)
            return;

        if(client.data.classType == ClassCategory.ASSASSIN) {
            leftComboSkillTime = ASSASSIN_SKILL_REACTIVATION_TIME - timer.getDifference(lastComboSkillTime);
            leftLV40SkillTime = BLADE_SPIN_SKILL_COOLTIME - timer.getDifference(lastLV40SkillTime);
            leftUltimateTime = BLADE_DANCE_COOLTIME - timer.getDifference(lastUltimateTime);
        }
        else if(client.data.classType == ClassCategory.DRAGON_WARRIOR) {
            leftComboSkillTime = DRAGON_WARRIOR_SKILL_REACTIVATION_TIME - timer.getDifference(lastComboSkillTime);
            leftLV40SkillTime = DRAGON_SMASH_COOLTIME - timer.getDifference(lastLV40SkillTime);
            leftUltimateTime = DRAGON_FURY_COOLTIME - timer.getDifference(lastUltimateTime);
        }
        else if (this.client.data.classType == ClassCategory.MARTIAL_ARTIST) {
            leftComboSkillTime = MARTIAL_ARTIST_SKILL_REACTIVATION_TIME - timer.getDifference(lastComboSkillTime);
            leftLV40SkillTime = MARTIAL_DRIVE_COOLTIME - timer.getDifference(lastLV40SkillTime);
            this.leftUltimateTime = MULTIPLE_BLOW_COOLTIME - timer.getDifference(this.lastUltimateTime);
        }
        else if (this.client.data.classType == ClassCategory.BATTLE_MAGE){
            leftComboSkillTime = BATTLE_MAGE_SKILL_REACTIVATION_TIME - timer.getDifference(lastComboSkillTime);
            leftLV40SkillTime = INFERNO_CHAIN_COOLTIME - timer.getDifference(lastLV40SkillTime);
            this.leftUltimateTime = ARCANE_DEMOLITION_COOLTIME - timer.getDifference(this.lastUltimateTime);
        }

        leftComboSkillTime = Math.max(leftComboSkillTime, 0);
        leftLV40SkillTime = Math.max(leftLV40SkillTime, 0);
        leftUltimateTime = Math.max(leftUltimateTime, 0);

        render(guiGraphics);
    }

    private void render(GuiGraphics guiGraphics) {
        int xOffset = 98;
        renderSkillTexture(guiGraphics, xOffset);
        renderSkillCooltime(guiGraphics, xOffset);
    }

    private void renderSkillTexture(GuiGraphics guiGraphics, int xOffset) {
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        int yOffset;
        if(client.data.toggleCustomSkillGUIPos){
            xOffset = (screenWidth - (22)* 4) * client.data.SkillCooltimeXpos/1000;
            yOffset = (screenHeight - 22) * client.data.SkillCooltimeYpos/1000;
        }
        else
        {
            xOffset = screenWidth / 2 + xOffset;
            yOffset = screenHeight - 22;
        }

        RenderSystem.setShaderTexture(0, WIDGETS);

        int i = 0;
        for(int skillNum = 0 ; skillNum < 5 ; skillNum++){
            if(dungeonSkill.isComboSkill(client.data.classType,skillNum) && isComboSkillUseable()){
                skillNum++;
            }
            guiGraphics.blit(WIDGETS, xOffset + (22)* i, yOffset, 24, 23, 22, 22);
            ResourceLocation texture = null;

            texture = dungeonSkill.getSkillTexture(client.data.classType, skillNum);

            drawSkillTexture(guiGraphics, texture, xOffset + (22) * i + 3, yOffset + 3);

            if(isManaRunout(client.data.classType, dungeonSkill.getSkillCategory(skillNum))&&!(skillNum==3&&isUltimateCooltime())){
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.setShaderTexture(0, MANA_RUNOUT_ICON);

                drawSkillTexture(guiGraphics ,MANA_RUNOUT_ICON, xOffset + (22) * i + 3, yOffset + 3);
            }
            if(dungeonSkill.isComboSkill(client.data.classType,skillNum) && !isComboSkillUseable()){
                skillNum++;
            }
            i++;
        }
    }

    private void renderSkillCooltime(GuiGraphics guiGraphics, int xOffset) {
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        int x, y;

        if(client.data.toggleCustomSkillGUIPos){
            x = (screenWidth - (22)* 4) * client.data.SkillCooltimeXpos/1000 + 3;
            y = (screenHeight - 22) * client.data.SkillCooltimeYpos/1000 + 4;
        }
        else
        {
            x = screenWidth / 2 + xOffset + 3;
            y = screenHeight - 22 + 4;
        }

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, BLACK_ICON);

        /*float comboSkillReactivationTime = 0;
        if(client.data.classType == ClassCategory.ASSASSIN) {
            comboSkillReactivationTime = ASSASSIN_SKILL_REACTIVATION_TIME;
        }
        else if(client.data.classType == ClassCategory.DRAGON_WARRIOR) {
            comboSkillReactivationTime = DRAGON_WARRIOR_SKILL_REACTIVATION_TIME;
        }
        else if (this.client.data.classType == ClassCategory.MARTIAL_ARTIST) {
            comboSkillReactivationTime = MARTIAL_ARTIST_SKILL_REACTIVATION_TIME;
        }
        else if (this.client.data.classType == ClassCategory.BATTLE_MAGE){
            comboSkillReactivationTime = BATTLE_MAGE_SKILL_REACTIVATION_TIME;
        }*/

        float lv40SkillCooltime = 0;

        if(client.data.classType == ClassCategory.ASSASSIN) {
            lv40SkillCooltime = BLADE_SPIN_SKILL_COOLTIME;
        }
        else if(client.data.classType == ClassCategory.DRAGON_WARRIOR) {
            lv40SkillCooltime = DRAGON_SMASH_COOLTIME;
        }
        else if (this.client.data.classType == ClassCategory.MARTIAL_ARTIST) {
            lv40SkillCooltime = MARTIAL_DRIVE_COOLTIME;
        }
        else if (this.client.data.classType == ClassCategory.BATTLE_MAGE){
            lv40SkillCooltime = INFERNO_CHAIN_COOLTIME;
        }

        float ultimateCooltime = 10;
        if(client.data.classType == ClassCategory.ASSASSIN) {
            ultimateCooltime = BLADE_DANCE_COOLTIME;
        }
        else if(client.data.classType == ClassCategory.DRAGON_WARRIOR) {
            ultimateCooltime = DRAGON_FURY_COOLTIME;
        }
        else if (this.client.data.classType == ClassCategory.MARTIAL_ARTIST) {
            ultimateCooltime = MULTIPLE_BLOW_COOLTIME;
        }
        else if (this.client.data.classType == ClassCategory.BATTLE_MAGE) {
            ultimateCooltime = ARCANE_DEMOLITION_COOLTIME;
        }
        //if(isComboSkillUseable())
        //    guiGraphics.blit(BLACK_ICON, x + 22 * dungeonSkill.getComboSkill(client.data.classType), y + (int) (SKILL_GUI_SIZE * (1 - leftComboSkillTime / comboSkillReactivationTime)), 0, 0, SKILL_GUI_SIZE, (int) (SKILL_GUI_SIZE * (leftComboSkillTime / comboSkillReactivationTime)));
        guiGraphics.blit(BLACK_ICON, x + 22 * 2, y + (int) (SKILL_GUI_SIZE * (1 - leftLV40SkillTime / lv40SkillCooltime)), 0, 0, SKILL_GUI_SIZE, (int) (SKILL_GUI_SIZE * (leftLV40SkillTime / lv40SkillCooltime)));
        guiGraphics.blit(BLACK_ICON, x + 22 * 3, y + (int) (SKILL_GUI_SIZE * (1 - leftUltimateTime / ultimateCooltime)), 0, 0, SKILL_GUI_SIZE, (int) (SKILL_GUI_SIZE * (leftUltimateTime / ultimateCooltime)));

        PoseStack poseStack = guiGraphics.pose();
        if(isComboSkillUseable()) {
            poseStack.pushPose();
            poseStack.translate(x + 8 + 22 * dungeonSkill.getComboSkill(client.data.classType), y + 4, 0);
            poseStack.scale(1f/1.1f, 1f/1.1f, 1f/1.1f);

            guiGraphics.drawCenteredString(mc.font, Component.literal(String.valueOf((int)(leftComboSkillTime * 10) / 10f)), 0, 0, 0xFFFFFF);

            poseStack.popPose();
        }

        if(isLV40SkillCooltime()){
            poseStack.pushPose();
            poseStack.translate(x + 2 + 22 * 2 + 6, y + 4, 0);
            poseStack.scale(1f/1.1f, 1f/1.1f, 1f/1.1f);

            guiGraphics.drawCenteredString(mc.font, Component.literal(String.valueOf((int) leftLV40SkillTime)), 0, 0, 0xFFFFFF);

            poseStack.popPose();
        }

        if(isUltimateCooltime()) {
            poseStack.pushPose();
            poseStack.translate(x + 2 + 22 * 3 + 6, y + 4, 0);
            poseStack.scale(1f/1.1f, 1f/1.1f, 1f/1.1f);

            guiGraphics.drawCenteredString(mc.font, Component.literal(String.valueOf((int) leftUltimateTime)), 0, 0, 0xFFFFFF);

            poseStack.popPose();
        }
    }

    private void drawSkillTexture(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y) {
        float scaleRatio = SKILL_GUI_SIZE / 256f;

        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(x, y, 0);
        poseStack.scale(scaleRatio, scaleRatio, 1);

        guiGraphics.blit(texture, 0, 0, 0, 0, 256, 256);

        poseStack.popPose();
    }

    public boolean isManaRunout(ClassCategory classtype, SkillCategory skilltype){
        return Mana.current < Mana.getSkillManacost(classtype, skilltype, Mana.dungeon_level);
    }
    public boolean isLV40SkillCooltime() {
        return leftLV40SkillTime > 0;
    }
    public boolean isUltimateCooltime() {
        return leftUltimateTime > 0;
    }
    public boolean isComboSkillUseable(){
        return leftComboSkillTime > 0;
    }
}