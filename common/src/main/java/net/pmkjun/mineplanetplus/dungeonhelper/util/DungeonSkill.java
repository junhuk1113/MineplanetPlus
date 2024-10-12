package net.pmkjun.mineplanetplus.dungeonhelper.util;

import net.minecraft.resources.ResourceLocation;

public class DungeonSkill{
    //용기사
    private static final ResourceLocation DRAGON_DASH_TEXTURE = new ResourceLocation("dungeonhelper", "textures/icon/skill/dragon_dash.png");
    private static final ResourceLocation DRAGON_WHEEL_TEXTURE = new ResourceLocation("dungeonhelper", "textures/icon/skill/dragon_wheel.png");
    private static final ResourceLocation DRAGON_BREATH_TEXTURE = new ResourceLocation("dungeonhelper", "textures/icon/skill/dragon_breath.png");
    private static final ResourceLocation DRAGON_SMASH_TEXTURE = new ResourceLocation("dungeonhelper", "textures/icon/skill/dragon_smash.png");
    private static final ResourceLocation DRAGON_FURY_TEXTURE = new ResourceLocation("dungeonhelper", "textures/icon/skill/dragon_fury.png");
    //어새신
    private static final ResourceLocation BLADE_DASH_TEXTURE = new ResourceLocation("dungeonhelper", "textures/icon/skill/blade_dash.png");
    private static final ResourceLocation THRUST_TEXTURE = new ResourceLocation("dungeonhelper", "textures/icon/skill/thrust.png"); 
    private static final ResourceLocation DAGGER_THROW_TEXTURE = new ResourceLocation("dungeonhelper", "textures/icon/skill/dagger_throw.png");
    private static final ResourceLocation BLADE_SPIN_TEXTURE = new ResourceLocation("dungeonhelper", "textures/icon/skill/blade_spin.png");
    private static final ResourceLocation BLADE_DANCE_TEXTURE = new ResourceLocation("dungeonhelper", "textures/icon/skill/blade_dance.png");
    //무투가
    private static final ResourceLocation AGILE_STRIKE_TEXTURE = new ResourceLocation("dungeonhelper", "textures/icon/skill/agile_strike.png");
    private static final ResourceLocation UPPERCUT_TEXTURE = new ResourceLocation("dungeonhelper", "textures/icon/skill/uppercut.png");
    private static final ResourceLocation DIVING_STRIKE_TEXTURE = new ResourceLocation("dungeonhelper", "textures/icon/skill/diving_strike.png");
    private static final ResourceLocation MARTIAL_DRIVE = new ResourceLocation("dungeonhelper", "textures/icon/skill/martial_drive.png");
    private static final ResourceLocation MULTIPLE_BLOW_TEXTURE = new ResourceLocation("dungeonhelper", "textures/icon/skill/multiple_blow.png");
    //배틀메이지
    private static final ResourceLocation DEATH_SPIN_TEXTURE = new ResourceLocation("dungeonhelper", "textures/icon/skill/death_spin.png");
    private static final ResourceLocation ARCANE_SCISSORS_TEXTURE = new ResourceLocation("dungeonhelper", "textures/icon/skill/arcane_scissors.png");
    private static final ResourceLocation VOID_CHAIN_TEXTURE = new ResourceLocation("dungeonhelper", "textures/icon/skill/void_chain.png");
    private static final ResourceLocation INFERNO_CHAIN_TEXTURE = new ResourceLocation("dungeonhelper", "textures/icon/skill/inferno_chain.png");
    private static final ResourceLocation ARCANE_DEMOLITION_TEXTURE = new ResourceLocation("dungeonhelper", "textures/icon/skill/arcane_demolition.png");
    
    private static final int ASSASSIN_COMBO_SKILL = 0;
    private static final int DRAGON_WARRIOR_COMBO_SKILL = 0;
    private static final int MARTIAL_ARTIST_COMBO_SKILL = 1;
    private static final int BATTLE_MAGE_COMBO_SKILL = 0;

    public ResourceLocation getSkillTexture(ClassCategory classCategory, int i){
        if(classCategory == ClassCategory.ASSASSIN){
            if(i == 0) return BLADE_DASH_TEXTURE;
            else if(i == 1) return THRUST_TEXTURE;
            else if(i == 2) return DAGGER_THROW_TEXTURE;
            else if(i == 3) return BLADE_SPIN_TEXTURE;
            else if(i == 4) return BLADE_DANCE_TEXTURE;
        }
        else if(classCategory == ClassCategory.DRAGON_WARRIOR){
            if(i == 0) return DRAGON_DASH_TEXTURE;
            else if(i == 1) return DRAGON_WHEEL_TEXTURE;
            else if(i == 2) return DRAGON_BREATH_TEXTURE;
            else if(i == 3) return DRAGON_SMASH_TEXTURE;
            else if(i == 4) return DRAGON_FURY_TEXTURE;
        }
        else if(classCategory == ClassCategory.MARTIAL_ARTIST){
            if(i == 0) return AGILE_STRIKE_TEXTURE;
            else if(i == 1) return UPPERCUT_TEXTURE;
            else if(i == 2) return DIVING_STRIKE_TEXTURE;
            else if(i == 3) return MARTIAL_DRIVE;
            else if(i == 4) return MULTIPLE_BLOW_TEXTURE;
        }
        else if(classCategory == ClassCategory.BATTLE_MAGE){
            if(i == 0) return DEATH_SPIN_TEXTURE;
            else if(i == 1) return ARCANE_SCISSORS_TEXTURE;
            else if(i == 2) return VOID_CHAIN_TEXTURE;
            else if(i == 3) return INFERNO_CHAIN_TEXTURE;
            else if(i == 4) return ARCANE_DEMOLITION_TEXTURE;
        }
        return null;
    }
    public int getComboSkill(ClassCategory classCategory){
        if(classCategory == ClassCategory.ASSASSIN){
            return ASSASSIN_COMBO_SKILL;
        }
        else if(classCategory == ClassCategory.DRAGON_WARRIOR){
            return DRAGON_WARRIOR_COMBO_SKILL;
        }
        else if(classCategory == ClassCategory.MARTIAL_ARTIST){
            return MARTIAL_ARTIST_COMBO_SKILL;
        }
        else if(classCategory == ClassCategory.BATTLE_MAGE){
            return BATTLE_MAGE_COMBO_SKILL;
        }
        return 0;
    }

    public boolean isComboSkill(ClassCategory classCategory, int i){
        if(classCategory == ClassCategory.ASSASSIN){
            return ASSASSIN_COMBO_SKILL == i;
        }
        else if(classCategory == ClassCategory.DRAGON_WARRIOR){
            return DRAGON_WARRIOR_COMBO_SKILL == i;
        }
        else if(classCategory == ClassCategory.MARTIAL_ARTIST){
            return MARTIAL_ARTIST_COMBO_SKILL == i;
        }
        else if(classCategory == ClassCategory.BATTLE_MAGE){
            return BATTLE_MAGE_COMBO_SKILL == i;
        }
        return false;
    }

    public SkillCategory getSkillCategory(int i){
        if(i == 0) return SkillCategory.DASH;
        else if(i == 1) return SkillCategory.LV20;
        else if(i == 2) return SkillCategory.LV30;
        else if(i == 3) return SkillCategory.LV40;
        else if(i == 4) return SkillCategory.ULTIMATE;
        return SkillCategory.DASH;
    }

}