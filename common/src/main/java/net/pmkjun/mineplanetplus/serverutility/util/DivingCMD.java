package net.pmkjun.mineplanetplus.serverutility.util;

import net.minecraft.network.chat.Component;

public class DivingCMD {

    public static Component getMoneyDescriptionMessage() {
        return Component.literal("잠수탐사 카운터에서 골드 목표치를 설정해보세요!\n사용법 : ").withColor(0xCAD1E0)
                .append(Component.literal("/잠수탐사골드 [골드]\n").withColor(0xFFE679)).
                append(Component.literal("(오류 발생 시 모드 개발자 PMKJun에게 문의 주세요)").withColor(0xCAD1E0));
    }
    public static Component getShillingDescriptionMessage() {
        return Component.literal("잠수탐사 카운터에서 실링 목표치를 설정해보세요!\n사용법 : ").withColor(0xCAD1E0)
                .append(Component.literal("/잠수탐사실링 [실링]\n").withColor(0xFFE679)).
                append(Component.literal("(오류 발생 시 모드 개발자 PMKJun에게 문의 주세요)").withColor(0xCAD1E0));
    }
}
