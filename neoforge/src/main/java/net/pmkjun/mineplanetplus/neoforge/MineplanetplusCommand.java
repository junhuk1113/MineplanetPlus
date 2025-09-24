package net.pmkjun.mineplanetplus.neoforge;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.pmkjun.mineplanetplus.fishhelper.util.TotemCMD;
import net.pmkjun.mineplanetplus.serverutility.ServerUtilityClient;
import net.pmkjun.mineplanetplus.serverutility.util.DivingCMD;
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

        dispatcher.register(LiteralArgumentBuilder.<CommandSourceStack>literal("토템찾기").executes(MineplanetplusCommand::loadNearTotem));

        dispatcher.register(LiteralArgumentBuilder.<CommandSourceStack>literal("잠수탐사골드").executes(MineplanetplusCommand::setTargetDivingMoney_noArg)
                .then(Commands.argument("골드 입력", LongArgumentType.longArg(0))
                        .executes(MineplanetplusCommand::setTargetDivingMoney)));

        dispatcher.register(LiteralArgumentBuilder.<CommandSourceStack>literal("잠수탐사실링").executes(MineplanetplusCommand::setTargetDivingShilling_noArg)
                .then(Commands.argument("실링 입력", IntegerArgumentType.integer(0))
                        .executes(MineplanetplusCommand::setTargetDivingShilling)));
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

    private static int loadNearTotem(CommandContext<CommandSourceStack> context){
        context.getSource().sendSystemMessage(Component.literal("↓ 주변에 있는 토템 ↓ ").withStyle(Style.EMPTY.withColor(0x84CA77))
                .append(Component.literal("(마우스를 올려 토템 스펙 확인)\n").withStyle(Style.EMPTY.withColor(0xCAD1E0)))
                .append(TotemCMD.getComponent()));
        return 1;
    }

    private static int setTargetDivingMoney(CommandContext<CommandSourceStack> context){
        long value2 = LongArgumentType.getLong(context, "골드 입력");
        ServerUtilityClient.getInstance().setDivingTargetMoney(value2);
        context.getSource().sendSystemMessage(Component.literal("골드 목표치 설정 완료"));
        return 1;
    }
    private static int setTargetDivingMoney_noArg(CommandContext<CommandSourceStack> context){
        context.getSource().sendSystemMessage(DivingCMD.getMoneyDescriptionMessage());
        return 1;
    }
    private static int setTargetDivingShilling(CommandContext<CommandSourceStack> context){
        int shilling = IntegerArgumentType.getInteger(context, "실링 입력");
        ServerUtilityClient.getInstance().setDivingTargetShilling(shilling);
        context.getSource().sendSystemMessage(Component.literal("실링 목표치 설정 완료"));
        return 1;
    }
    private static int setTargetDivingShilling_noArg(CommandContext<CommandSourceStack> context){
        context.getSource().sendSystemMessage(DivingCMD.getShillingDescriptionMessage());
        return 1;
    }
}
