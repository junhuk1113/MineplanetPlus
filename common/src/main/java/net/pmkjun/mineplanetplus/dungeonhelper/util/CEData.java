package net.pmkjun.mineplanetplus.dungeonhelper.util;

import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.minecraft.world.item.ItemStack;

// CustonEnchantData
public class CEData {
    private static String[] COMMON_ENCHANT_NAMES = {
            "치명타",
            "헤드샷",
            "재련",
            "아이기스"
    };

    private static String[] UNCOMMON_ENCHANT_NAMES = {
            "포식",
            "베놈",
            "불굴",
            "스프링",
            "수중호흡"
    };

    private static String[] RARE_ENCHANT_NAMES = {
            "연격",
            "공복",
            "경화",
            "용상비",
            "인듀어",
            "화로",
            "가속",
            "야광경"
    };

    private static String[] EPIC_ENCHANT_NAMES = {
            "격노",
            "격분",
            "명궁",
            "명사수",
            "흡혈",
            "방화",
            "용장",
            "광맥",
            "바다의 부름",
            "바다의 가호",
            "경공"
    };

    private static String[] LEGENDARY_ENCHANT_NAMES = {
            "수확",
            "검성",
            "야차",
            "헤이스트",
            "금강"
    };

    private static String[] MYTHIC_ENCHANT_NAMES = {
            "굴삭기",
            "구세주",
            "괴력"
    };
    private static String[] REMOVED_ENCHANT_NAMES = {
            "수호자"
    };

   public static CustomEnchantType getType(ItemStack itemStack) {
       String name = itemStack.getDisplayName().getString();
       name = name.substring(1,name.length()-1);

       if(!DungeonHelperClient.getInstance().data.toggleCustomEnchantRender)
           return null;

       if(!itemStack.getItem().toString().equals("paper"))
           return null;

       for(String s : COMMON_ENCHANT_NAMES) {
           if((name.contains(s) && name.contains("I"))||name.equals(s))
               return CustomEnchantType.COMMON;
       }

       for(String s : UNCOMMON_ENCHANT_NAMES) {
           if((name.contains(s) && name.contains("I"))||name.equals(s))
               return CustomEnchantType.UNCOMMON;
       }

       for(String s : RARE_ENCHANT_NAMES) {
           if((name.contains(s) && name.contains("I"))||name.equals(s))
               return CustomEnchantType.RARE;
       }

       for(String s : EPIC_ENCHANT_NAMES) {
           if((name.contains(s) && name.contains("I"))||name.equals(s))
               return CustomEnchantType.EPIC;
       }

       for(String s : LEGENDARY_ENCHANT_NAMES) {
           if((name.contains(s) && name.contains("I"))||name.equals(s))
               return CustomEnchantType.LEGENDARY;
       }

       for(String s : MYTHIC_ENCHANT_NAMES) {
           if((name.contains(s) && name.contains("I"))||name.equals(s))
               return CustomEnchantType.MYTHIC;
       }

       for(String s : REMOVED_ENCHANT_NAMES) {
           if((name.contains(s) && name.contains("I"))||name.equals(s))
               return CustomEnchantType.REMOVED;
       }

       return null;
   }
}
