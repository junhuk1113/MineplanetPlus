package net.pmkjun.mineplanetplus.fabric.input;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.pmkjun.mineplanetplus.SettingsScreen;
import net.pmkjun.mineplanetplus.dungeonhelper.input.IKeyMappings;

public class KeyMappings implements IKeyMappings {

    public static KeyMapping openSettingScreen = new KeyMapping(
            "mineplanetplus.key.open_settings",
            InputConstants.KEY_M,
            "mineplanetplus.key.category");


    @Override
    public void register() {
        Minecraft mc = Minecraft.getInstance();

        register(openSettingScreen, () -> mc.setScreen(new SettingsScreen()));
    }

    private void register(KeyMapping keyMapping, KeyBehavior behavior) {
        keyMapping = KeyBindingHelper.registerKeyBinding(keyMapping);

        KeyMapping finalKeyMapping = keyMapping;
        ClientTickEvents.END_CLIENT_TICK.register(m -> {
            while(finalKeyMapping.consumeClick()) {
                behavior.action();
            }
        });
    }

    interface KeyBehavior {
        void action();
    }
}
