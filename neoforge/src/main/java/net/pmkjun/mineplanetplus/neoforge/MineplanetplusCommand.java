package net.pmkjun.mineplanetplus.neoforge;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.pmkjun.mineplanetplus.serverutility.util.FeeCalculator;

public class MineplanetplusCommand {
    public MineplanetplusCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(LiteralArgumentBuilder.<CommandSourceStack>literal("송금수수료").executes(MineplanetplusCommand::executeSendFee_noArg)
                        .then(Commands.argument("골드 입력", LongArgumentType.longArg(0))
                                .executes(MineplanetplusCommand::executeSendFee)));

        dispatcher.register(LiteralArgumentBuilder.<CommandSourceStack>literal("판매수수료").executes(MineplanetplusCommand::executeSellFee_noArg)
                .then(Commands.argument("골드 입력", LongArgumentType.longArg(0))
                        .executes(MineplanetplusCommand::executeSellFee)));

        dispatcher.register(LiteralArgumentBuilder.<CommandSourceStack>literal("크레딧판매수수료").executes(MineplanetplusCommand::executeCreditSellFee_noArg)
                .then(Commands.argument("크레딧 입력", IntegerArgumentType.integer(0))
                        .executes(MineplanetplusCommand::executeCreditSellFee)));
    }

    private static int executeSendFee_noArg(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSystemMessage(FeeCalculator.getSendFeeDescriptionMessage());
        return 1;
    }

    private static int executeSendFee(CommandContext<CommandSourceStack> objectCommandContext) {
        objectCommandContext.getSource().sendSystemMessage(FeeCalculator.getSendFeeMessage(LongArgumentType.getLong(objectCommandContext, "골드 입력")));
        return 1;
    }

    private static int executeSellFee_noArg(CommandContext<CommandSourceStack> context){
        context.getSource().sendSystemMessage(FeeCalculator.getSellFeeDescriptionMessage());
        return 1;
    }

    private static int executeSellFee(CommandContext<CommandSourceStack> objectCommandContext) {
        objectCommandContext.getSource().sendSystemMessage(FeeCalculator.getSellFeeMessage(LongArgumentType.getLong(objectCommandContext, "골드 입력")));
        return 1;
    }

    private static int executeCreditSellFee_noArg(CommandContext<CommandSourceStack> context){
        context.getSource().sendSystemMessage(FeeCalculator.getCreditFeeDescriptionMessage());
        return 1;
    }

    private static int executeCreditSellFee(CommandContext<CommandSourceStack> objectCommandContext) {
        objectCommandContext.getSource().sendSystemMessage(FeeCalculator.getCreditFeeMessage(IntegerArgumentType.getInteger(objectCommandContext, "크레딧 입력")));
        return 1;
    }
}
