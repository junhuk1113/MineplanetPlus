package net.pmkjun.mineplanetplus.fabric;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.network.chat.Component;
import net.pmkjun.mineplanetplus.MineplanetPlus;
import net.pmkjun.mineplanetplus.fabric.dungeonhelper.DungeonHelper;
import net.pmkjun.mineplanetplus.fabric.fishhelper.FishHelperFabric;
import net.pmkjun.mineplanetplus.fabric.input.KeyMappings;
import net.pmkjun.mineplanetplus.fabric.planetskilltimer.PlanetSkillTimerFabric;
import net.pmkjun.mineplanetplus.serverutility.ServerUtility;
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
    }
    private static int executeSendFee_noArg(CommandContext<FabricClientCommandSource> context) {
        context.getSource().sendFeedback(Component.literal("송금 수수료를 미리 계산해보세요!\n사용법 : /송금수수료 [송금할 금액]\n(오류 발생 시 모드 개발자 PMKJun에게 문의 주세요)").withColor(0xCAD1E0));
        return 1;
    }

    private static int executeSendFee(CommandContext<FabricClientCommandSource> context) {
        long value = LongArgumentType.getLong(context, "골드 입력");
        context.getSource().sendFeedback(FeeCalculator.getSendFeeMessage(value));
        return 1;
    }

    private static int executeSellFee_noArg(CommandContext<FabricClientCommandSource> context){
        context.getSource().sendFeedback(Component.literal("거래소에서 아이템을 판매할 때 차감되는 수수료를 미리 계산해보세요!\n사용법 : /판매수수료 [판매금액]\n(오류 발생 시 모드 개발자 PMKJun에게 문의 주세요)").withColor(0xCAD1E0));
        return 1;
    }

    private static int executeSellFee(CommandContext<FabricClientCommandSource> context) {
        long value2 = LongArgumentType.getLong(context, "골드 입력");
        context.getSource().sendFeedback(FeeCalculator.getSellFeeMessage(value2));
        return 1;
    }

    private static int executeCreditSellFee_noArg(CommandContext<FabricClientCommandSource> context){
        context.getSource().sendFeedback(Component.literal("거래소에서 아이템을 판매할 때 차감되는 수수료를 미리 계산해보세요!\n사용법 : /크레딧판매수수료 [판매금액]\n(오류 발생 시 모드 개발자 PMKJun에게 문의 주세요)").withColor(0xCAD1E0));
        return 1;
    }

    private static int executeCreditSellFee(CommandContext<FabricClientCommandSource> context) {
        int credit = IntegerArgumentType.getInteger(context, "크레딧 입력");
        context.getSource().sendFeedback(FeeCalculator.getCreditFeeMessage(credit));
        return 1;
    }
}