package net.pmkjun.mineplanetplus.serverutility.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.text.DecimalFormat;

public class FeeCalculator {
    public static Component getSendFeeMessage(long money) {
        long fee, sentAmount;
        DecimalFormat df = new DecimalFormat("###,###");

        fee = Math.round((float) money / 10);
        sentAmount = Math.round((float) money / 1.1);

        return Component.literal("지불해야 할 수수료는 ").withColor(0xCAD1E0)
                .append(Component.literal(df.format(fee)).withColor(0xFFE679)
                        .append(Component.literal("\uE1BE").withStyle(ChatFormatting.WHITE))
                        .append(Component.literal("입니다. ").withColor(0xCAD1E0))
                        .append(Component.literal(df.format(money + fee)).withColor(0xFFE679))
                        .append(Component.literal("\uE1BE").withStyle(ChatFormatting.WHITE))
                        .append(Component.literal("이 차감됩니다.\n").withColor(0xCAD1E0))
                        .append(Component.literal(df.format(money)).withColor(0xFFE679))
                        .append(Component.literal("\uE1BE").withStyle(ChatFormatting.WHITE))
                        .append(Component.literal("만 빠지게 하려면 ").withColor(0xCAD1E0))
                        .append(Component.literal(df.format(sentAmount)).withColor(0xFFE679))
                        .append(Component.literal("\uE1BE").withStyle(ChatFormatting.WHITE))
                        .append(Component.literal("을 송금하세요.").withColor(0xCAD1E0)));
    }

    public static Component getSellFeeMessage(long money) {
        long fee, boughtAmount;
        fee = Math.round((float) money / 10);
        boughtAmount = Math.round((float) money / 0.9 );
        DecimalFormat df = new DecimalFormat("###,###");

        return Component.literal("거래소 수수료는 ").withColor(0xCAD1E0)
                .append(Component.literal(df.format(fee)).withColor(0xFFE679)
                        .append(Component.literal("\uE1BE").withStyle(ChatFormatting.WHITE))
                        .append(Component.literal("으로 수령액은 ").withColor(0xCAD1E0))
                        .append(Component.literal(df.format(money - fee)).withColor(0xFFE679))
                        .append(Component.literal("\uE1BE").withStyle(ChatFormatting.WHITE))
                        .append(Component.literal("입니다.\n").withColor(0xCAD1E0))
                        .append(Component.literal(df.format(money)).withColor(0xFFE679))
                        .append(Component.literal("\uE1BE").withStyle(ChatFormatting.WHITE))
                        .append(Component.literal("을 받으려면 ").withColor(0xCAD1E0))
                        .append(Component.literal(df.format(boughtAmount)).withColor(0xFFE679))
                        .append(Component.literal("\uE1BE").withStyle(ChatFormatting.WHITE))
                        .append(Component.literal("에 올리세요.").withColor(0xCAD1E0)));
    }

    public static Component getCreditFeeMessage(int credit) {
        int fee, boughtAmount;
        fee = (int)((float) credit / 10);
        boughtAmount = (int)((float) credit / 0.9 );

        return Component.literal("거래소 수수료는 ").withColor(0xCAD1E0)
                .append(Component.literal("" +fee).withColor(0xFFE679)
                        .append(Component.literal("\uE3B8").withStyle(ChatFormatting.WHITE))
                        .append(Component.literal("으로 수령액은 ").withColor(0xCAD1E0))
                        .append(Component.literal(""+(credit - fee)).withColor(0xFFE679))
                        .append(Component.literal("\uE3B8").withStyle(ChatFormatting.WHITE))
                        .append(Component.literal("입니다.\n").withColor(0xCAD1E0))
                        .append(Component.literal(""+(credit)).withColor(0xFFE679))
                        .append(Component.literal("\uE3B8").withStyle(ChatFormatting.WHITE))
                        .append(Component.literal("을 받으려면 ").withColor(0xCAD1E0))
                        .append(Component.literal(""+boughtAmount)).withColor(0xFFE679))
                        .append(Component.literal("\uE3B8").withStyle(ChatFormatting.WHITE))
                        .append(Component.literal("에 올리세요.").withColor(0xCAD1E0));
    }
}
