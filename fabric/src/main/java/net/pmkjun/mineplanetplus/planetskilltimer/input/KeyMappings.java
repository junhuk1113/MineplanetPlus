package net.pmkjun.mineplanetplus.planetskilltimer.input;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.pmkjun.mineplanetplus.planetskilltimer.config.SkillTimerConfigScreen;

public class KeyMappings implements IKeyMappings {

    // 26.1: 공용 CATEGORY 객체 적용
    public static KeyMapping openSettingScreen = new KeyMapping(
            "planetskilltimer.key.open_settings",
            -1,
            net.pmkjun.mineplanetplus.input.KeyMappings.CATEGORY);

    public void register() {
        Minecraft mc = Minecraft.getInstance();
        register(openSettingScreen, () -> mc.setScreen(new SkillTimerConfigScreen(mc.screen)));
    }

    private void register(KeyMapping keyMapping, KeyBehavior behavior) {
        keyMapping = KeyMappingHelper.registerKeyMapping(keyMapping);
        KeyMapping finalKeyMapping = keyMapping;
        ClientTickEvents.END_CLIENT_TICK.register(m -> {
            while (finalKeyMapping.consumeClick())
                behavior.action();
        });
    }

    interface KeyBehavior {
        void action();
    }
}