package net.pmkjun.mineplanetplus.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.multiplayer.chat.GuiMessageTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperMod;
import net.pmkjun.mineplanetplus.fishhelper.item.FishItemList;
import net.pmkjun.mineplanetplus.planetskilltimer.PlanetSkillTimerClient;
import net.pmkjun.mineplanetplus.planetskilltimer.file.Skill;
import net.pmkjun.mineplanetplus.serverutility.ServerUtilityClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.DecimalFormat;
import java.util.List;

@Mixin(ChatComponent.class)
public abstract class ChatMixin {
    private static final int COMMON = 0;
    private static final int UNCOMMON = 1;
    private static final int RARE = 2;
    private static final int EPIC = 3;
    private static final int LEGENDARY = 4;
    private static final int MYTHIC = 5;

    private static final Minecraft mc = Minecraft.getInstance();
    private final FishHelperClient fishhelper = FishHelperClient.getInstance();
    private final PlanetSkillTimerClient skilltimer = PlanetSkillTimerClient.getInstance();
    private final ServerUtilityClient serverutility = ServerUtilityClient.getInstance();

    @Inject(at = @At("RETURN"), method = "addPlayerMessage")
    private void addPlayerMessageMixin(Component message, MessageSignature signature, GuiMessageTag tag, CallbackInfo ci) {
        processChatMessage(message);
    }

    @Inject(at = @At("RETURN"), method = "addServerSystemMessage")
    private void addServerMessageMixin(Component message, CallbackInfo ci) {
        processChatMessage(message);
    }

    private void processChatMessage(Component message) {
        if (serverutility.isHereMineplanet()) {
            String playerString;

            //피시헬퍼
            if (fishhelper.data.toggleChattinglog)
                FishHelperMod.LOGGER.info(message.getString());

            if ((message.getString().contains("\uE2F8 ") && (message.getString().contains("을(를) 낚았습니다.") || message.getString().contains("You caught a"))) ||
                    (message.getString().contains("\uE2F8 ") && message.getString().contains("로 변환되었습니다."))) {
                FISH:
                {
                    for (String fishName : FishItemList.MYTHIC_FISH_LIST) {
                        if (message.getString().contains(fishName)) {
                            this.fishhelper.data.fish_Count[MYTHIC]++;
                            this.fishhelper.configManage.save();
                            break FISH;
                        }
                    }
                    for (String fishName : FishItemList.LEGENDARY_FISH_LIST) {
                        if (message.getString().contains(fishName)) {
                            this.fishhelper.data.fish_Count[LEGENDARY]++;
                            this.fishhelper.configManage.save();
                            break FISH;
                        }
                    }
                    for (String fishName : FishItemList.EPIC_FISH_LIST) {
                        if (message.getString().contains(fishName)) {
                            this.fishhelper.data.fish_Count[EPIC]++;
                            this.fishhelper.configManage.save();
                            break FISH;
                        }
                    }
                    for (String fishName : FishItemList.RARE_FISH_LIST) {
                        if (message.getString().contains(fishName)) {
                            this.fishhelper.data.fish_Count[RARE]++;
                            this.fishhelper.configManage.save();
                            break FISH;
                        }
                    }
                    for (String fishName : FishItemList.UNCOMMON_FISH_LIST) {
                        if (message.getString().contains(fishName)) {
                            this.fishhelper.data.fish_Count[UNCOMMON]++;
                            this.fishhelper.configManage.save();
                            break FISH;
                        }
                    }
                    for (String fishName : FishItemList.COMMMON_FISH_LIST) {
                        if (message.getString().contains(fishName)) {
                            this.fishhelper.data.fish_Count[COMMON]++;
                            this.fishhelper.configManage.save();
                        }
                    }
                }
            }
            if (message.getString().contains("\uE2F8 You do not have enough fish for this delivery.")){
                this.fishhelper.deliveryQuests.pushQuestData();
            }
            else if(message.getString().contains("\uE2F8 해당 배달 주문을 삭제하였습니다.")){
                this.fishhelper.deliveryQuests.popQuestData();
            }

            //스킬타이머
            if (message.getString().contains(" 발동되었습니다!") && !message.getString().contains("|")) {
                for (int i = 0; i < Skill.list.length; i++) {
                    if (message.getString().contains(Skill.list[i])) {
                        skilltimer.updateLastSkilltime(i);
                    }
                }
            }

            //확성기타이머
            try {
                playerString = mc.gui.getTabList().getNameForDisplay(mc.getConnection().getPlayerInfo(mc.player.getUUID())).toFlatList().getLast().getString();
            } catch (NullPointerException e) {
                System.out.println("MineplanetPlus : player info null!");
                return;
            }

            if (message.getString().contains(" " + playerString)) {
                try {
                    serverutility.updateLastUsedMegaphonetime();
                } catch (NullPointerException e) {
                    // 26.1: displayClientMessage(msg, false) ➔ sendSystemMessage(msg) 로 변경
                    mc.player.sendSystemMessage(Component.literal("확성기 타이머 : NullPointerException!"));
                }
            }

            if (serverutility.data.toggleFeeCalcalator) {
                List<Component> messageList = message.toFlatList();
                long money, fee;
                DecimalFormat df = new DecimalFormat("###,###");
                for (int index = 0; index < messageList.size(); index++) {
                    if (messageList.get(index).getString().equals("\uE1BE") && messageList.get(index + 1).getString().equals("을 송금하였습니다.") && messageList.get(index - 2).getString().equals("님에게 ")) {
                        try {
                            money = Long.parseLong(messageList.get(index - 1).getString().replaceAll(",", ""));
                            fee = Math.round((float) money / 10);

                            // 26.1: displayClientMessage(msg, false) ➔ sendSystemMessage(msg) 로 변경
                            mc.player.sendSystemMessage(Component.literal("지불하신 수수료는 ").withColor(0xCAD1E0)
                                    .append(Component.literal(df.format(fee)).withColor(0xFFE679)
                                            .append(Component.literal("\uE1BE").withStyle(ChatFormatting.WHITE))
                                            .append(Component.literal("입니다. ").withColor(0xCAD1E0))
                                            .append(Component.literal(df.format(money + fee)).withColor(0xFFE679))
                                            .append(Component.literal("\uE1BE").withStyle(ChatFormatting.WHITE))
                                            .append(Component.literal("이 차감되었습니다.").withColor(0xCAD1E0))));
                        } catch (NumberFormatException e) {
                            System.out.println("MineplanetPlus : NumberFormatException!");
                        }
                    }

                    if (messageList.get(index).getString().equals("\uE1BE") && messageList.get(index + 1).getString().equals("에 구매하였습니다.") && messageList.get(index - 4).getString().equals("님의 당신의 ")) {
                        try {
                            money = Long.parseLong(messageList.get(index - 1).getString().replaceAll(",", ""));
                            fee = Math.round((float) money / 10);

                            // 26.1: displayClientMessage(msg, false) ➔ sendSystemMessage(msg) 로 변경
                            mc.player.sendSystemMessage(Component.literal("거래소 수수료 ").withColor(0xCAD1E0)
                                    .append(Component.literal(df.format(fee)).withColor(0xFFE679)
                                            .append(Component.literal("\uE1BE").withStyle(ChatFormatting.WHITE))
                                            .append(Component.literal("이 차감되어 총 ").withColor(0xCAD1E0))
                                            .append(Component.literal(df.format(money - fee)).withColor(0xFFE679))
                                            .append(Component.literal("\uE1BE").withStyle(ChatFormatting.WHITE))
                                            .append(Component.literal("이 지급되었습니다.").withColor(0xCAD1E0))));
                        } catch (NumberFormatException e) {
                            System.out.println("MineplanetPlus : NumberFormatException!");
                        }
                    }

                    if (messageList.get(index).getString().equals("\uE3B8") && messageList.get(index + 1).getString().equals("에 구매하였습니다.") && messageList.get(index - 4).getString().equals("님의 당신의 ")) {
                        try {
                            money = Integer.parseInt(messageList.get(index - 1).getString().replaceAll(",", ""));
                            fee = (int) ((float) money / 10);

                            // 26.1: displayClientMessage(msg, false) ➔ sendSystemMessage(msg) 로 변경
                            mc.player.sendSystemMessage(Component.literal("거래소 수수료 ").withColor(0xCAD1E0)
                                    .append(Component.literal("" + fee).withColor(0xFFE679)
                                            .append(Component.literal("\uE3B8").withStyle(ChatFormatting.WHITE))
                                            .append(Component.literal("이 차감되어 총 ").withColor(0xCAD1E0))
                                            .append(Component.literal("" + (money - fee)).withColor(0xFFE679))
                                            .append(Component.literal("\uE3B8").withStyle(ChatFormatting.WHITE))
                                            .append(Component.literal("이 지급되었습니다.").withColor(0xCAD1E0))));
                        } catch (NumberFormatException e) {
                            System.out.println("MineplanetPlus : NumberFormatException!");
                        }
                    }
                }
            }
        }
    }
}