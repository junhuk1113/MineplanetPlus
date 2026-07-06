package net.pmkjun.mineplanetplus.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription; // 26.1: FontDescription Import 추가
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
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
    //DungeonHelperClient client = DungeonHelperClient.getInstance();
    ServerUtilityClient serverUtilityClient = ServerUtilityClient.getInstance();

    @Shadow
    protected Component name;

    @Inject(method = "setName(Lnet/minecraft/network/chat/Component;)V", at = {@At("RETURN")}, cancellable = true)
    private void BossBarOverlayMixin(Component bossbarComponent, CallbackInfo cir) {
        if(serverUtilityClient.isHereMineplanet()) {
            String[] mana;
            String dungeon_level;
            Component text;
            boolean dungeonExp = false;
            boolean isSlot1Updated = false, isSlot3Updated = false, isSlot4Updated = false, isSlot5Updated = false, isHUD = false;
            boolean isSlot1Manarunout = false, isSlot3Manarunout = false, isSlot4Manarunout = false, isSlot5Manarunout = false;

            List<Component> actionbarTextList = bossbarComponent.toFlatList();

            /*for (Component component : actionbarTextList) {
                text = component;
                FontDescription fontDesc = text.getStyle().getFont();

                // 26.1: 폰트가 일반 리소스 경로(Resource)를 사용하는 경우인지 체크하고 Identifier를 가져옵니다.
                if (fontDesc instanceof FontDescription.Resource fontRes) {
                    Identifier fontId = fontRes.id(); // FontDescription.Resource 내부의 Identifier 추출
                    String fontNamespace = fontId.getNamespace();
                    String fontPath = fontId.getPath();

                    if (fontNamespace.equals("mythichud")) {
                        isHUD = true;
                    }

                    if (fontPath.equals("layout/hud_dungeon/fonts/mana/text")) {
                        mana = text.getString().split("/");
                        try {
                            Mana.current = Integer.parseInt(mana[0].trim());
                            Mana.max = Integer.parseInt(mana[1].trim());
                        } catch (NumberFormatException ignored) {
                        }
                    }
                    if (fontPath.equals("layout/hud_dungeon/fonts/exp_dungeon/level")) {
                        dungeonExp = true;
                        dungeon_level = text.getString();
                        try {
                            Mana.dungeon_level = Integer.parseInt(dungeon_level);
                        } catch (NumberFormatException ignored) {
                        }
                    }

                    if (fontPath.equals("layout/hud_dungeon/fonts/skill/slot1_cooldown_text")) {
                        client.updateLeftComboSkillTime(Float.parseFloat(text.getString()));
                        isSlot1Updated = true;
                    } else if (fontPath.equals("layout/hud_dungeon/fonts/skill/slot3_cooldown_text")) {
                        if (client.data.classType == ClassCategory.MARTIAL_ARTIST)
                            client.updateLeftLV40SkillTime(Float.parseFloat(text.getString()));
                        else client.updateLeftLV30SkillTime(Float.parseFloat(text.getString()));
                        isSlot3Updated = true;
                    } else if (fontPath.equals("layout/hud_dungeon/fonts/skill/slot4_cooldown_text")) {
                        if (client.data.classType == ClassCategory.MARTIAL_ARTIST)
                            client.updateLeftComboSkillTime(Float.parseFloat(text.getString()));
                        else client.updateLeftLV40SkillTime(Float.parseFloat(text.getString()));
                        isSlot4Updated = true;
                    } else if (fontPath.equals("layout/hud_dungeon/fonts/skill/slot5_cooldown_text")) {
                        client.updateLeftUltimateTime(Float.parseFloat(text.getString()));
                        isSlot5Updated = true;
                    } else if (fontPath.equals("layout/status/fonts/status/money") && !text.getString().equals(" ")) {
                        serverUtilityClient.money = text;
                    } else if (fontPath.equals("layout/status/fonts/status/coin") && !text.getString().equals(" ")) {
                        serverUtilityClient.coin = text;
                    } else if (fontPath.equals("layout/status/fonts/status/credit") && !text.getString().equals(" ")) {
                        serverUtilityClient.credit = text;
                    }
                    else if (fontPath.equals("layout/hud_dungeon/textures") && text.getString().equals("\uE05E")) {
                        isSlot1Manarunout = true;
                    } else if (fontPath.equals("layout/hud_dungeon/textures") && text.getString().equals("\uE046")) {
                        isSlot3Manarunout = true;
                    } else if (fontPath.equals("layout/hud_dungeon/textures") && text.getString().equals("\uE02E")) {
                        isSlot4Manarunout = true;
                    } else if (fontPath.equals("layout/hud_dungeon/textures") && text.getString().equals("\uE016")) {
                        isSlot5Manarunout = true;
                    }
                }
            }

            if (!isSlot1Updated && isHUD) {
                if (!(client.data.classType == ClassCategory.MARTIAL_ARTIST))
                    client.updateLeftComboSkillTime(0f);
            }
            if (!isSlot3Updated && isHUD) {
                if (client.data.classType == ClassCategory.MARTIAL_ARTIST) client.updateLeftLV40SkillTime(0f);
                else client.updateLeftLV30SkillTime(0f);
            }
            if (!isSlot4Updated && isHUD) {
                if (client.data.classType == ClassCategory.MARTIAL_ARTIST) client.updateLeftComboSkillTime(0f);
                else client.updateLeftLV40SkillTime(0f);
            }
            if (!isSlot5Updated && isHUD) {
                client.updateLeftUltimateTime(0f);
            }

            this.client.isSlot1Manarunout  = isSlot1Manarunout;
            this.client.isSlot3Manarunout  = isSlot3Manarunout;
            this.client.isSlot4Manarunout  = isSlot4Manarunout;
            this.client.isSlot5Manarunout  = isSlot5Manarunout;

            client.ishereDungeon = dungeonExp;
            boolean currencyDisplayHide = !(serverUtilityClient.data.toggleCurrencyDisplay && !serverUtilityClient.data.toggleCurrencyDisplayCustompos);
            boolean dungeonhudHide = (client.data.toggleDefaultSkillUI == DefaultSkillUI.AUTO && client.data.toggleSkillCooltime || client.data.toggleDefaultSkillUI == DefaultSkillUI.OFF);

            if (currencyDisplayHide || (dungeonhudHide && client.ishereDungeon)) {
                List<Component> modifiedList = new ArrayList<>();
                boolean skipNext = false;

                for (Component component : actionbarTextList) {
                    if (skipNext) {
                        skipNext = false;
                        continue;
                    }

                    FontDescription fontDesc = component.getStyle().getFont();

                    // 폰트 체크
                    if (fontDesc instanceof FontDescription.Resource fontRes) {
                        String path = fontRes.id().getPath();

                        if (path.equals("layout/status/textures") && currencyDisplayHide) {
                            // 26.1: FontDescription.Resource 객체로 감싸서 전달
                            Component newComponent = Component.literal("\uE051").setStyle(Style.EMPTY.withFont(
                                    new FontDescription.Resource(Identifier.fromNamespaceAndPath("mythichud", "spaces"))
                            ));
                            modifiedList.add(newComponent);
                        } else if (path.equals("layout/status/fonts/status/credit") && currencyDisplayHide) {
                            if (!component.getString().equals(" ")) {
                                skipNext = true;
                            }
                        } else if (path.equals("layout/status/fonts/status/money") && currencyDisplayHide) {
                            if (!component.getString().equals(" ")) {
                                skipNext = true;
                            }
                        } else if (path.equals("layout/status/fonts/status/coin") && currencyDisplayHide) {
                            if (!component.getString().equals(" ")) {
                                skipNext = true;
                            }
                        } else if (path.equals("layout/hud_dungeon/textures") && client.skillUIChars.getSkillChars().contains(component.getString()) && dungeonhudHide) {
                            skipNext = true;
                        } else if (path.contains("layout/hud_dungeon/fonts/skill") && dungeonhudHide) {
                            if (!modifiedList.isEmpty()) {
                                modifiedList.removeLast(); // 안전을 위해 비어있지 않을 때만 삭제
                            }
                            skipNext = true;
                        } else {
                            modifiedList.add(component);
                        }
                    } else {
                        // Resource 타입이 아닐 경우 그대로 추가
                        modifiedList.add(component);
                    }
                }

                Component mergedComponent = Component.empty();
                for (Component component : modifiedList) {
                    mergedComponent = mergedComponent.copy().append(component);
                }
                bossbarComponent.copy().append(mergedComponent).setStyle(bossbarComponent.getStyle());
                name = mergedComponent;
            }*/
        }
    }
}