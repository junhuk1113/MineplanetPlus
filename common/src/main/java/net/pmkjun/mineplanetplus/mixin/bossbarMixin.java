package net.pmkjun.mineplanetplus.mixin;

import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.dungeonhelper.file.Mana;
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;

@Mixin(BossEvent.class)
public class bossbarMixin {
    DungeonHelperClient client = DungeonHelperClient.getInstance();

    @Inject(method = "Lnet/minecraft/world/BossEvent;setName(Lnet/minecraft/network/chat/Component;)V",at = {@At("RETURN")})
    private void BossBarOverlayMixin(Component bossbarComponent, CallbackInfo cir){
        //System.out.println(bossbarComponent.getString());
        String[] mana;
        String dungeon_level;
        Component text;
        boolean dungeonExp = false;

        List<Component> actionbarTextList = bossbarComponent.toFlatList();

        for (Component component : actionbarTextList) {
            text = component;
            if (text.getStyle().getFont().getPath().equals("layout/hud_dungeon/fonts/mana/manatext")) {
                //System.out.println("현재 마나 : " + text.getString());
                mana = text.getString().split("/");
                try{
                    Mana.current = Integer.parseInt(mana[0]);
                    Mana.max = Integer.parseInt(mana[1]);
                }
                catch(NumberFormatException ignored){
                }
            }
            if (text.getStyle().getFont().getPath().equals("layout/hud_dungeon/fonts/exp/leveltext")){
                dungeonExp = true;
                dungeon_level = text.getString();
                //System.out.println("던전 레벨 : " + text.getString());
                try{
                    Mana.dungeon_level = Integer.parseInt(dungeon_level);
                }
                catch(NumberFormatException ignored){
                }
            }
        }
        client.ishereDungeon = dungeonExp;
    }
}