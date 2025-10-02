package net.pmkjun.mineplanetplus.fabric;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.pmkjun.mineplanetplus.MineplanetPlus;
import net.pmkjun.mineplanetplus.fabric.dungeonhelper.DungeonHelper;
import net.pmkjun.mineplanetplus.fabric.fishhelper.FishHelperFabric;
import net.pmkjun.mineplanetplus.fabric.input.KeyMappings;
import net.pmkjun.mineplanetplus.fabric.planetskilltimer.PlanetSkillTimerFabric;
import net.pmkjun.mineplanetplus.fishhelper.util.TotemCMD;
import net.pmkjun.mineplanetplus.serverutility.ServerUtility;
import net.pmkjun.mineplanetplus.serverutility.ServerUtilityClient;
import net.pmkjun.mineplanetplus.serverutility.util.DivingCMD;
import net.pmkjun.mineplanetplus.serverutility.util.FeeCalculator;

public final class MineplanetPlusFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DungeonHelper dungeonhelper = new DungeonHelper();
        FishHelperFabric fishhelper = new FishHelperFabric();
        PlanetSkillTimerFabric skilltimer = new PlanetSkillTimerFabric();

        KeyMappings keyMappings = new KeyMappings();
        keyMappings.register();

        MineplanetPlus.init();
        dungeonhelper.init();
        fishhelper.init();
        skilltimer.init();
        ServerUtility.init();

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("송금수수료").executes(MineplanetPlusFabric::executeSendFee_noArg)
                .then(ClientCommandManager.argument("골드 입력", LongArgumentType.longArg(0))
                    .executes(MineplanetPlusFabric::executeSendFee)
                )
            );
        });
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("판매수수료").executes(MineplanetPlusFabric::executeSellFee_noArg)
                    .then(ClientCommandManager.argument("골드 입력", LongArgumentType.longArg(0))
                            .executes(MineplanetPlusFabric::executeSellFee)
                    )
            );
        });
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("크레딧판매수수료").executes(MineplanetPlusFabric::executeCreditSellFee_noArg)
                    .then(ClientCommandManager.argument("크레딧 입력", IntegerArgumentType.integer(0))
                            .executes(MineplanetPlusFabric::executeCreditSellFee)
                    )
            );
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("토템찾기").executes(MineplanetPlusFabric::loadNearTotem)
            );
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("잠수탐사골드").then(ClientCommandManager.argument("골드 입력", LongArgumentType.longArg(0))
                            .executes(MineplanetPlusFabric::setTargetDivingMoney)
                    ).executes(MineplanetPlusFabric::setTargetDivingMoney_noArg)
            );
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("잠수탐사실링").then(ClientCommandManager.argument("실링 입력", IntegerArgumentType.integer(0))
                            .executes(MineplanetPlusFabric::setTargetDivingShilling)
                    ).executes(MineplanetPlusFabric::setTargetDivingShilling_noArg)
            );
        });
    }
    private static int executeSendFee_noArg(CommandContext<FabricClientCommandSource> context) {
        context.getSource().sendFeedback(FeeCalculator.getSendFeeDescriptionMessage());
        return 1;
    }

    private static int executeSendFee(CommandContext<FabricClientCommandSource> context) {
        long value = LongArgumentType.getLong(context, "골드 입력");
        context.getSource().sendFeedback(FeeCalculator.getSendFeeMessage(value));
        return 1;
    }

    private static int executeSellFee_noArg(CommandContext<FabricClientCommandSource> context){
        context.getSource().sendFeedback(FeeCalculator.getSellFeeDescriptionMessage());
        return 1;
    }

    private static int executeSellFee(CommandContext<FabricClientCommandSource> context) {
        long value2 = LongArgumentType.getLong(context, "골드 입력");
        context.getSource().sendFeedback(FeeCalculator.getSellFeeMessage(value2));
        return 1;
    }

    private static int executeCreditSellFee_noArg(CommandContext<FabricClientCommandSource> context){
        context.getSource().sendFeedback(FeeCalculator.getCreditFeeDescriptionMessage());
        return 1;
    }

    private static int executeCreditSellFee(CommandContext<FabricClientCommandSource> context) {
        int credit = IntegerArgumentType.getInteger(context, "크레딧 입력");
        context.getSource().sendFeedback(FeeCalculator.getCreditFeeMessage(credit));
        return 1;
    }

    private static int loadNearTotem(CommandContext<FabricClientCommandSource> context){
        context.getSource().sendFeedback(Component.literal("↓ 주변에 있는 토템 ↓ ").withStyle(Style.EMPTY.withColor(0x84CA77))
                        .append(Component.literal("(마우스를 올려 토템 스펙 확인)\n").withStyle(Style.EMPTY.withColor(0xCAD1E0)))
                .append(TotemCMD.getComponent()));
        return 1;
    }

    private static int setTargetDivingMoney(CommandContext<FabricClientCommandSource> context){
        long value2 = LongArgumentType.getLong(context, "골드 입력");
        ServerUtilityClient.getInstance().setDivingTargetMoney(value2);
        context.getSource().sendFeedback(Component.literal("골드 목표치 설정 완료"));
        return 1;
    }
    private static int setTargetDivingMoney_noArg(CommandContext<FabricClientCommandSource> context){
        context.getSource().sendFeedback(DivingCMD.getMoneyDescriptionMessage());
        return 1;
    }
    private static int setTargetDivingShilling(CommandContext<FabricClientCommandSource> context){
        int shilling = IntegerArgumentType.getInteger(context, "실링 입력");
        ServerUtilityClient.getInstance().setDivingTargetShilling(shilling);
        context.getSource().sendFeedback(Component.literal("실링 목표치 설정 완료"));
        return 1;
    }
    private static int setTargetDivingShilling_noArg(CommandContext<FabricClientCommandSource> context){
        context.getSource().sendFeedback(DivingCMD.getShillingDescriptionMessage());
        return 1;
    }
}