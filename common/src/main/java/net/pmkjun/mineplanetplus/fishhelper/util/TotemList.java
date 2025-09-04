package net.pmkjun.mineplanetplus.fishhelper.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.file.TotemData;

import java.util.ArrayList;

public class TotemList {
    public static Component getComponent() {
        ArrayList<TotemData> totemDataList;
        totemDataList = FishHelperClient.getInstance().getRemoteTotemDataList();
        if(totemDataList == null || totemDataList.isEmpty()) {
            return Component.literal("현재 근처에 토템이 없습니다!").withColor(0xCAD1E0);
        }
        Timer timer = new Timer();
        MutableComponent component = Component.literal("");
        String username;

        for (TotemData totemData : totemDataList) {
            username = totemData.username;
            /*HoverEvent event = new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    Component.literal("지속시간 : "+totemData.valueTotemActiveTime+"분\n" +
                            "쿨타임 : " + totemData.valueTotemCooldown +"분\n"+
                    "범위 : "+totemData.valueTotemRange+"블럭"));*/

            HoverEvent event = new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    Component.literal("토템 정보\n").withStyle(Style.EMPTY.withColor(0x84CA77))
                            .append(Component.literal("지속시간 : ").withStyle(Style.EMPTY.withColor(0x84CA77)))
                            .append(Component.literal(totemData.valueTotemActiveTime+"").withColor(0xFFE679))
                            .append(Component.literal("분\n").withColor(0xCAD1E0))
                            .append(Component.literal("쿨타임 : ").withColor(0xFFE679))
                            .append(Component.literal(totemData.valueTotemCooldown+"").withColor(0xFFE679))
                            .append(Component.literal("분\n").withColor(0xCAD1E0))
                            .append(Component.literal("범위 : ").withStyle(ChatFormatting.RED))
                            .append(Component.literal(totemData.valueTotemRange+"").withColor(0x4CBDFF))
                            .append(Component.literal("블록").withColor(0xCAD1E0))
            );

            component = component.append(Component.literal(username).withStyle(ChatFormatting.WHITE)
                    .append(Component.literal("님의 토템 : ").withStyle(ChatFormatting.WHITE)));
            timer.updateTime();
            if (totemData.isActive()) {
                int second = totemData.valueTotemActiveTime * 60 - (int) timer.getDifference(totemData.lastTotemtime);
                int minute = second / 60;
                second -= minute * 60;

                component = component.append(Component.literal("남은 시간 -> ").withStyle(ChatFormatting.RED)
                        .append(Component.literal(String.format("%02d:%02d", minute, second)).withColor(0xFFE679))).withStyle(Style.EMPTY.withHoverEvent(event));

            } else {
                int second = totemData.valueTotemCooldown * 60 - (int) timer.getDifference(totemData.lastTotemCooldownTime);
                int minute = second / 60;
                second -= minute * 60;
                component = component.append(Component.literal("재사용 대기시간 -> ").withColor(0x4CBDFF)
                        .append(Component.literal(String.format("%02d:%02d", minute, second)).withColor(0xFFE679))).withStyle(Style.EMPTY.withHoverEvent(event));
            }
            component = component.append(Component.literal("\n"));
        }

        return component;
    }
}
