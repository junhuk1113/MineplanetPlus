package net.pmkjun.mineplanetplus.fishhelper.util;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;

public class FishingRod {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final FishHelperClient client = FishHelperClient.getInstance();

    public static void updateSpec(ItemStack stack){
        List<Component> ItemText;//Solar Rage, Precision Cutting
        String tooltipString;
        int SolarRagelevel = 0, PrecisionCuttinglevel = 0;

        ItemText = stack.getTooltipLines(mc.player, TooltipFlag.NORMAL);
        //mc.player.sendMessage(Text.literal("낚시대 감지"))    ;
        for(Component text : ItemText){
            //mc.player.sendMessage(text);
            tooltipString = text.getString();
            if(tooltipString.contains("Solar Rage")){
                tooltipString = tooltipString.replace("Solar Rage ", "");
                SolarRagelevel = RomanNum.toInt(tooltipString);
            }
            else if(tooltipString.contains("Precision Cutting")){
                tooltipString = tooltipString.replace("Precision Cutting ", "");
                PrecisionCuttinglevel = RomanNum.toInt(tooltipString);
            }
        }
        if(SolarRagelevel != client.data.valueSolarRage)
        {
            //mc.player.sendMessage(Text.literal("Solar Rage Lv : "+client.data.valueSolarRage+" >> "+Augmentlevel));
            client.data.valueSolarRage = SolarRagelevel;
            client.configManage.save();
        }
        if(PrecisionCuttinglevel != client.data.valuePrecisionCutting)
        {
            //mc.player.sendMessage(Text.literal("Solar Rage Lv : "+client.data.valueSolarRage+" >> "+Augmentlevel));
            client.data.valuePrecisionCutting = PrecisionCuttinglevel;
            client.configManage.save();
        }
    }
}