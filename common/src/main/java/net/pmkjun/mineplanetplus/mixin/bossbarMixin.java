package net.pmkjun.mineplanetplus.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.dungeonhelper.file.Mana;
import net.pmkjun.mineplanetplus.dungeonhelper.util.ClassCategory;
import net.pmkjun.mineplanetplus.dungeonhelper.util.DefaultSkillUI;
import net.pmkjun.mineplanetplus.serverutility.ServerUtilityClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(BossEvent.class)
public class bossbarMixin {
    Minecraft mc = Minecraft.getInstance();
    DungeonHelperClient client = DungeonHelperClient.getInstance();
    ServerUtilityClient serverUtilityClient = ServerUtilityClient.getInstance();

    @Shadow
    protected Component name;

    @Inject(method = "setName(Lnet/minecraft/network/chat/Component;)V",at = {@At("RETURN")},cancellable = true)
    private void BossBarOverlayMixin(Component bossbarComponent, CallbackInfo cir){
        //System.out.println(bossbarComponent.getString());
        String[] mana;
        String dungeon_level;
        Component text;
        boolean dungeonExp = false;
        boolean isSlot1Updated = false, isSlot3Updated = false, isSlot4Updated = false, isSlot5Updated = false, isHUD = false;

        List<Component> actionbarTextList = bossbarComponent.toFlatList();
        mc.player.displayClientMessage(Component.literal(bossbarComponent.getStyle().getFont().toString()), false);
        //mc.player.displayClientMessage(Component.literal("보스바 감지"), false);
        for (Component component : actionbarTextList) {
            text = component;

            if(text.getStyle().getFont().getNamespace().equals("mythichud")){
                isHUD = true;
            }

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

            if(text.getStyle().getFont().getPath().equals("layout/hud_dungeon/fonts/skill/slot1_cooldown_text")){
                client.updateLeftComboSkillTime(Float.parseFloat(text.getString()));
                isSlot1Updated = true;
            }
            if(text.getStyle().getFont().getPath().equals("layout/hud_dungeon/fonts/skill/slot3_cooldown_text")){
                if(client.data.classType == ClassCategory.MARTIAL_ARTIST) client.updateLeftLV40SkillTime(Float.parseFloat(text.getString()));
                else client.updateLeftLV30SkillTime(Float.parseFloat(text.getString()));
                isSlot3Updated = true;
            }
            if(text.getStyle().getFont().getPath().equals("layout/hud_dungeon/fonts/skill/slot4_cooldown_text")){
                if(client.data.classType == ClassCategory.MARTIAL_ARTIST) {client.updateLeftComboSkillTime(Float.parseFloat(text.getString()));
                mc.player.displayClientMessage(Component.literal(text.getString()), false);
                }
                else client.updateLeftLV40SkillTime(Float.parseFloat(text.getString()));
                isSlot4Updated = true;
            }
            if(text.getStyle().getFont().getPath().equals("layout/hud_dungeon/fonts/skill/slot5_cooldown_text")){
                client.updateLeftUltimateTime(Float.parseFloat(text.getString()));
                isSlot5Updated = true;
            }
        }
        if(!isSlot1Updated && isHUD){
            client.updateLeftComboSkillTime(0f);
        }
        if(!isSlot3Updated && isHUD){
            if(client.data.classType == ClassCategory.MARTIAL_ARTIST) client.updateLeftLV40SkillTime(0f);
            else client.updateLeftLV30SkillTime(0f);
        }
        if(!isSlot4Updated && isHUD){
            if(client.data.classType == ClassCategory.MARTIAL_ARTIST) client.updateLeftComboSkillTime(0f);
            else client.updateLeftLV40SkillTime(0f);
        }
        if(!isSlot5Updated && isHUD){
            client.updateLeftUltimateTime(0f);
        }

        client.ishereDungeon = dungeonExp;

        boolean dungeonhudHide = (client.data.toggleDefaultSkillUI == DefaultSkillUI.AUTO && client.data.toggleSkillCooltime || client.data.toggleDefaultSkillUI == DefaultSkillUI.OFF);
        if(!serverUtilityClient.data.toggleCurrencyDisplay || (dungeonhudHide && client.ishereDungeon)) {
            List<Component> modifiedList = new ArrayList<>();
            boolean skipNext = false;
            //System.out.println(bossbarComponent.toString());
            for (Component component : actionbarTextList) {

                if(skipNext){
                    skipNext = false;
                }
                else if (component.getStyle().getFont().getPath().equals("layout/status/textures") && !serverUtilityClient.data.toggleCurrencyDisplay) {
                    // 원하는 새로운 컴포넌트로 교체
                    Component newComponent = Component.literal("\uE051").setStyle(Style.EMPTY.withFont(ResourceLocation.fromNamespaceAndPath("mythichud", "spaces"))); // 기존 스타일 유지
                    modifiedList.add(newComponent);
                }
                else if (component.getStyle().getFont().getPath().equals("layout/status/fonts/status/credit") && !serverUtilityClient.data.toggleCurrencyDisplay) {
                    if(!component.getString().equals(" ")) {
                        skipNext = true; // 다음 컴포넌트를 건너뛰기 위한 플래그
                    }
                }
                else if (component.getStyle().getFont().getPath().equals("layout/status/fonts/status/money") && !serverUtilityClient.data.toggleCurrencyDisplay) {
                    if(!component.getString().equals(" ")) {
                        skipNext = true; // 다음 컴포넌트를 건너뛰기 위한 플래그
                        /*if(money != Integer.parseInt(component.getString().replaceAll(",", ""))) {
                            mc.player.displayClientMessage(Component.literal("골드 변화 감지 : " + (Integer.parseInt(component.getString().replaceAll(",", ""))-money)), false);

                            money = Integer.parseInt(component.getString().replaceAll(",", ""));
                        }*/
                    }
                }
                else if (component.getStyle().getFont().getPath().equals("layout/status/fonts/status/coin") && !serverUtilityClient.data.toggleCurrencyDisplay) {
                    if(!component.getString().equals(" ")) {
                        skipNext = true; // 다음 컴포넌트를 건너뛰기 위한 플래그
                    }
                }
                else if (component.getStyle().getFont().getPath().equals("layout/hud_dungeon/textures") && client.skillUIChars.getSkillChars().contains(component.getString()) && dungeonhudHide) {
                    skipNext = true; // 다음 컴포넌트를 건너뛰기 위한 플래그
                }
                else if (component.getStyle().getFont().getPath().contains("layout/hud_dungeon/fonts/skill") && dungeonhudHide) {
                    modifiedList.removeLast();
                    skipNext = true;
                }
                else{
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