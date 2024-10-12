package net.pmkjun.mineplanetplus.dungeonhelper.file;

import net.pmkjun.mineplanetplus.dungeonhelper.util.ClassCategory;
import net.pmkjun.mineplanetplus.dungeonhelper.util.SkillCategory;

public class Mana {
    public static int current = 20;
    public static int max = 20;
    public static int dungeon_level = 1;

    public static int getSkillManacost(ClassCategory classtype, SkillCategory skilltype, int dungeon_level){
        if(classtype == ClassCategory.ASSASSIN){
            if(skilltype == SkillCategory.DASH){
                return (int)Math.ceil(3 + (dungeon_level*0.18));
            }
            if(skilltype == SkillCategory.LV20){
                return (int)Math.ceil(2 + (dungeon_level*0.13));
            }
            if(skilltype == SkillCategory.LV30){
                return (int)Math.ceil(4 + (dungeon_level*0.25));
            }
            if(skilltype == SkillCategory.LV40){
                return (int)Math.ceil(5 + (dungeon_level*0.25));
            }
            if(skilltype == SkillCategory.ULTIMATE){
                return (int)Math.ceil(8 + (dungeon_level*0.48));
            }
        }
        else if(classtype == ClassCategory.DRAGON_WARRIOR){
            if(skilltype == SkillCategory.DASH){
                return (int)Math.ceil(3 + (dungeon_level*0.18));
            }
            if(skilltype == SkillCategory.LV20){
                return (int)Math.ceil(3 + (dungeon_level*0.13));
            }
            if(skilltype == SkillCategory.LV30){
                return (int)Math.ceil(5 + (dungeon_level*0.28));
            }
            if(skilltype == SkillCategory.LV40){
                return (int)Math.ceil(6 + (dungeon_level*0.28));
            }
            if(skilltype == SkillCategory.ULTIMATE){
                return (int)Math.ceil(10 + (dungeon_level*0.48));
            }
        }
        else if(classtype == ClassCategory.MARTIAL_ARTIST){
            if(skilltype == SkillCategory.DASH){
                return (int)Math.ceil(3 + (dungeon_level*0.23));
            }
            if(skilltype == SkillCategory.LV20){
                return (int)Math.ceil(3 + (dungeon_level*0.18));
            }
            if(skilltype == SkillCategory.LV30){
                return (int)Math.ceil(2 + (dungeon_level*0.13));
            }
            if(skilltype == SkillCategory.LV40){
                return (int)Math.ceil(6 + (dungeon_level*0.25));
            }
            if(skilltype == SkillCategory.ULTIMATE){
                return (int)Math.ceil(10 + (dungeon_level*0.48));
            }
        }
        else{ //배틀 메이지
            if(skilltype == SkillCategory.DASH){
                return (int)Math.ceil(3 + (dungeon_level*0.18));
            }
            if(skilltype == SkillCategory.LV20){
                return (int)Math.ceil(3 + (dungeon_level*0.13));
            }
            if(skilltype == SkillCategory.LV30){
                return (int)Math.ceil(5 + (dungeon_level*0.23));
            }
            if(skilltype == SkillCategory.LV40){
                return (int)Math.ceil(6 + (dungeon_level*0.28));
            }
            if(skilltype == SkillCategory.ULTIMATE){
                return (int)Math.ceil(10 + (dungeon_level*0.48));
            }
        }
        return 0;
    }
}