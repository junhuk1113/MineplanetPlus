package net.pmkjun.mineplanetplus.fishhelper.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.file.TotemData;

import java.util.ArrayList;

public class TotemList {
    public static Component getComponent() {
        ArrayList<TotemData> totemDataList;
        totemDataList = FishHelperClient.getInstance().getRemoteTotemDataList();
        if(totemDataList == null || totemDataList.isEmpty()) {
            return Component.literal("현재 근처에 토템이 없습니다!");
        }
        Timer timer = new Timer();
        MutableComponent component = Component.literal("");
        String username;

        for (TotemData totemData : totemDataList) {
            username = totemData.username;

            component = component.append(Component.literal(username + "님의 토템 : "));
            timer.updateTime();
            if (totemData.isActive()) {
                int second = totemData.valueTotemActiveTime * 60 - (int) timer.getDifference(totemData.lastTotemtime);
                int minute = second / 60;
                second -= minute * 60;
                component = component.append(Component.literal("남은 시간 -> " + String.format("%02d:%02d", minute, second)));
            } else {
                int second = totemData.valueTotemCooldown * 60 - (int) timer.getDifference(totemData.lastTotemCooldownTime);
                int minute = second / 60;
                second -= minute * 60;
                component = component.append(Component.literal("재사용 대기시간 -> " + String.format("%02d:%02d", minute, second)));
            }
            component = component.append(Component.literal("\n"));
        }

        return component;
    }
}
