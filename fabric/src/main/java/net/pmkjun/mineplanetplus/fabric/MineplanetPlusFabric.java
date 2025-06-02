package net.pmkjun.mineplanetplus.fabric;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
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
            dispatcher.register(ClientCommandManager.literal("송금수수료")
                .then(ClientCommandManager.argument("value", IntegerArgumentType.integer(0))
                    .executes(MineplanetPlusFabric::executeCommandWithArg)
                )
            );
        });
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("판매수수료")
                    .then(ClientCommandManager.argument("value2", IntegerArgumentType.integer(0))
                            .executes(MineplanetPlusFabric::executeBuyFee)
                    )
            );
        });
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("크레딧판매수수료")
                    .then(ClientCommandManager.argument("credit", IntegerArgumentType.integer(0))
                            .executes(MineplanetPlusFabric::executeCreditBuyFee)
                    )
            );
        });
    }
    private static int executeCommandWithArg(CommandContext<FabricClientCommandSource> context) {
        int value = IntegerArgumentType.getInteger(context, "value");
        context.getSource().sendFeedback(FeeCalculator.getSendFeeMessage(value));
        return 1;
    }
    private static int executeBuyFee(CommandContext<FabricClientCommandSource> context) {
        int value2 = IntegerArgumentType.getInteger(context, "value2");
        context.getSource().sendFeedback(FeeCalculator.getBuyFeeMessage(value2));
        return 1;
    }
    private static int executeCreditBuyFee(CommandContext<FabricClientCommandSource> context) {
        int credit = IntegerArgumentType.getInteger(context, "credit");
        context.getSource().sendFeedback(FeeCalculator.getCreditFeeMessage(credit));
        return 1;
    }
}