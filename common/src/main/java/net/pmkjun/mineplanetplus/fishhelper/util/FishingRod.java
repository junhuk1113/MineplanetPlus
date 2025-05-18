package net.pmkjun.mineplanetplus.fishhelper.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;

import java.util.List;

public class FishingRod {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final FishHelperClient client = FishHelperClient.getInstance();

    public static void updateSpec(ItemStack stack){
        List<Component> ItemText;//Solar Rage, Precision Cutting
        String tooltipString;
        int SolarRagelevel = 0, PrecisionCuttinglevel = 0;

        ItemText = stack.getTooltipLines(Item.TooltipContext.EMPTY, mc.player, TooltipFlag.NORMAL);
        //mc.player.sendMessage(Text.literal("낚시대 감지"))    ;
        for(Component text : ItemText){
            //mc.player.displayClientMessage(text, false);
            tooltipString = text.getString();
            if(tooltipString.contains("태양열")){
                tooltipString = tooltipString.replace("태양열 ", "");
                SolarRagelevel = RomanNum.toInt(tooltipString);
                //mc.player.displayClientMessage(Component.literal("태양열 : " + SolarRagelevel), false);
            }
            else if(tooltipString.contains("정밀 절단")){
                tooltipString = tooltipString.replace("정밀 절단 ", "");
                PrecisionCuttinglevel = RomanNum.toInt(tooltipString);
                //mc.player.displayClientMessage(Component.literal("정밀 절단 : " + PrecisionCuttinglevel), false);
            }
        }
        if(SolarRagelevel != client.data.valueSolarRage)
        {
            //mc.player.displayClientMessage(Component.literal("Solar Rage Lv : "+client.data.valueSolarRage),false);
            client.data.valueSolarRage = SolarRagelevel;
            client.configManage.save();
        }
        if(PrecisionCuttinglevel != client.data.valuePrecisionCutting)
        {
            //mc.player.displayClientMessage(Component.literal("정밀 절단 Lv : "+client.data.valuePrecisionCutting),false);
            client.data.valuePrecisionCutting = PrecisionCuttinglevel;
            client.configManage.save();
        }
    }
}