package net.pmkjun.mineplanetplus;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelper;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperFabric;
import net.pmkjun.mineplanetplus.input.KeyMappings;
import net.pmkjun.mineplanetplus.planetskilltimer.PlanetSkillTimerFabric;
import net.pmkjun.mineplanetplus.serverutility.ServerUtility;
import net.pmkjun.mineplanetplus.serverutility.util.FeeCalculator;

public class MineplanetPlusFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        //DungeonHelper dungeonhelper = new DungeonHelper();
        FishHelperFabric fishhelper = new FishHelperFabric();
        PlanetSkillTimerFabric skilltimer = new PlanetSkillTimerFabric();

        KeyMappings keyMappings = new KeyMappings();
        keyMappings.register();

        MineplanetPlus.init();
        //dungeonhelper.init();
        fishhelper.init();
        skilltimer.init();
        ServerUtility.init();

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommands.literal("송금수수료")
                    .then(ClientCommands.argument("골드 입력", LongArgumentType.longArg(0))
                            .executes(MineplanetPlusFabric::executeCommandWithArg)
                    )
            );
        });
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommands.literal("판매수수료")
                    .then(ClientCommands.argument("골드 입력", LongArgumentType.longArg(0))
                            .executes(MineplanetPlusFabric::executeSellFee)
                    )
            );
        });
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommands.literal("크레딧판매수수료")
                    .then(ClientCommands.argument("크레딧 입력", IntegerArgumentType.integer(0))
                            .executes(MineplanetPlusFabric::executeCreditSellFee)
                    )
            );
        });
    }
    private static int executeCommandWithArg(CommandContext<FabricClientCommandSource> context) {
        long value = LongArgumentType.getLong(context, "골드 입력");
        context.getSource().sendFeedback(FeeCalculator.getSendFeeMessage(value));
        return 1;
    }
    private static int executeSellFee(CommandContext<FabricClientCommandSource> context) {
        long value2 = LongArgumentType.getLong(context, "골드 입력");
        context.getSource().sendFeedback(FeeCalculator.getSellFeeMessage(value2));
        return 1;
    }
    private static int executeCreditSellFee(CommandContext<FabricClientCommandSource> context) {
        int credit = IntegerArgumentType.getInteger(context, "크레딧 입력");
        context.getSource().sendFeedback(FeeCalculator.getCreditFeeMessage(credit));
        return 1;
    }
}
