package net.pmkjun.mineplanetplus.dungeonhelper.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;

public class RFData {
    private static final Minecraft mc = Minecraft.getInstance();
    public static RuneofFortuneType getType(ItemStack itemStack){
        String name = itemStack.getDisplayName().getString();
        name = name.substring(1,name.length()-1);

        if(!DungeonHelperClient.getInstance().data.toggleRuneOFFortuneRender)
            return null;

        if(!itemStack.getItem().toString().equals("minecraft:paper"))
            return null;

        if(!name.contains("행운의 룬"))
            return null;

        if(name.contains("언커먼 행운의 룬"))
            return RuneofFortuneType.UNCOMMON;
        if(name.contains("커먼 행운의 룬"))
            return RuneofFortuneType.COMMON;
        if(name.contains("레어 행운의 룬"))
            return RuneofFortuneType.RARE;
        if(name.contains("에픽 행운의 룬"))
            return RuneofFortuneType.EPIC;
        if(name.contains("레전더리 행운의 룬"))
            return RuneofFortuneType.LEGENDARY;
        if(name.contains("신화 행운의 룬"))
            return RuneofFortuneType.MYTHIC;

        return null;
    }
}
