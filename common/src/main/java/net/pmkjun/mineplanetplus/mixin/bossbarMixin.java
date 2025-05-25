package net.pmkjun.mineplanetplus.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.dungeonhelper.file.Mana;
import net.pmkjun.mineplanetplus.megaphonetimer.MegaphoneTimerClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mixin(BossEvent.class)
public class bossbarMixin {
    Minecraft mc = Minecraft.getInstance();
    DungeonHelperClient client = DungeonHelperClient.getInstance();
    MegaphoneTimerClient megaphoneTimerClient = MegaphoneTimerClient.getInstance();

    @Shadow
    protected Component name;

    @Inject(method = "setName(Lnet/minecraft/network/chat/Component;)V",at = {@At("RETURN")},cancellable = true)
    private void BossBarOverlayMixin(Component bossbarComponent, CallbackInfo cir){
        //System.out.println(bossbarComponent.getString());
        String[] mana;
        String dungeon_level;
        Component text;
        boolean dungeonExp = false;
        List<Component> actionbarTextList = bossbarComponent.toFlatList();
        //List<Component> actionbarTextList = bossbarComponent.getSiblings();
        //mc.player.displayClientMessage(Component.literal(bossbarComponent), false);
        //mc.player.displayClientMessage(Component.literal("보스바 감지"), false);
        for (Component component : actionbarTextList) {
            text = component;

            //mc.player.displayClientMessage(text, false);
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

        if(megaphoneTimerClient.data.toggleHudRemover) {
            List<Component> modifiedList = new ArrayList<>();
            for (Component component : actionbarTextList) {
                if (component.getStyle().getFont().getPath().equals("layout/top_survival/textures")) {
                    // 원하는 새로운 컴포넌트로 교체
                    Component newComponent = Component.literal("\uE05B").setStyle(Style.EMPTY.withFont(ResourceLocation.fromNamespaceAndPath("mythichud", "spaces"))); // 기존 스타일 유지
                    modifiedList.add(newComponent);
                } else {
                    modifiedList.add(component);
                }
            }


            Component mergedComponent = Component.empty();
            for (Component component : modifiedList) {
                mergedComponent = mergedComponent.copy().append(component);
            }
            bossbarComponent.copy().append(mergedComponent).setStyle(bossbarComponent.getStyle());
            name = mergedComponent;

        }



    }
}