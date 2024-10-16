package net.pmkjun.mineplanetplus.fabric.planetskilltimer.input;

import com.mojang.blaze3d.platform.InputConstants;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.pmkjun.mineplanetplus.planetskilltimer.config.SkillTimerConfigScreen;
import net.pmkjun.mineplanetplus.planetskilltimer.input.IKeyMappings;

public class KeyMappings implements IKeyMappings {
    public static KeyMapping openSettingScreen = new KeyMapping("planetskilltimer.key.open_settings", InputConstants.KEY_J, "mineplanetplus.key.category");

    public void register() {
        Minecraft mc = Minecraft.getInstance();
        register(openSettingScreen, () -> mc.setScreen(new SkillTimerConfigScreen(mc.screen)));
    }

    private void register(KeyMapping keyMapping, KeyBehavior behavior) {
        keyMapping = KeyBindingHelper.registerKeyBinding(keyMapping);
        KeyMapping finalKeyMapping = keyMapping;
        ClientTickEvents.END_CLIENT_TICK.register(m -> {
            while (finalKeyMapping.consumeClick())
                behavior.action();
        });
    }

    static interface KeyBehavior {
        void action();
    }
}
