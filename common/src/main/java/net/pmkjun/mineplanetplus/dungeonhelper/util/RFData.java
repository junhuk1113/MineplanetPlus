package net.pmkjun.mineplanetplus.dungeonhelper.util;

import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class RFData {
    private static final Minecraft mc = Minecraft.getInstance();
    public static RuneofFortuneType getType(ItemStack itemStack){
        String name = itemStack.getDisplayName().getString();
        String linetext;
        name = name.substring(1,name.length()-1);

        List<Component> tooltipLine;

        if(!DungeonHelperClient.getInstance().data.toggleRuneOFFortuneRender)
            return null;

        if(!itemStack.getItem().toString().equals("paper"))
            return null;

        if(!name.contains("행운의 룬"))
            return null;

        tooltipLine=itemStack.getTooltipLines(mc.player, TooltipFlag.NORMAL);

        for(Component line : tooltipLine){
            linetext = line.getString();


            if(linetext.contains("언커먼"))
                return RuneofFortuneType.UNCOMMON;
            if(linetext.contains("커먼"))
                return RuneofFortuneType.COMMON;
            if(linetext.contains("레어"))
                return RuneofFortuneType.RARE;
            if(linetext.contains("에픽"))
                return RuneofFortuneType.EPIC;
            if(linetext.contains("레전더리"))
                return RuneofFortuneType.LEGENDARY;
            if(linetext.contains("신화"))
                return RuneofFortuneType.MYTHIC;
        }
        return null;
    }
}
