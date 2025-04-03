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

            //mc.player.displayClientMessage(Component.literal(text.getStyle().getFont().getPath()), false);
            if (text.getStyle().getFont().getPath().equals("layout/hud_dungeon/fonts/mana/text")) {
                //mc.player.displayClientMessage(Component.literal("현재 마나 : " + text.getString()),false);
                mana = text.getString().split("/");
                try{
                    Mana.current = Integer.parseInt(mana[0].trim());
                    Mana.max = Integer.parseInt(mana[1].trim());
                }
                catch(NumberFormatException ignored){
                    //mc.player.displayClientMessage(Component.literal("변환오류!"),false);
                }
            }
            if (text.getStyle().getFont().getPath().equals("layout/hud_dungeon/fonts/exp_dungeon/level")){
                dungeonExp = true;
                dungeon_level = text.getString();
                //mc.player.displayClientMessage(Component.literal("던전 레벨 : " + text.getString()),false);
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