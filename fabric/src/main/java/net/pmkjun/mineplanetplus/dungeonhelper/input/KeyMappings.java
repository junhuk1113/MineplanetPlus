package net.pmkjun.mineplanetplus.dungeonhelper.input;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.input.IKeyMappings;

public class KeyMappings implements IKeyMappings {

    // 26.1: 메인에 선언해둔 CATEGORY를 불러와 사용합니다.
    public static KeyMapping openSettingScreen = new KeyMapping(
            "key.dungeonhelper.open_dungeonhelper_settings",
            -1,
            net.pmkjun.mineplanetplus.input.KeyMappings.CATEGORY);

    @Override
    public void register() {
        Minecraft mc = Minecraft.getInstance();
        DungeonHelperClient client = DungeonHelperClient.getInstance();

        register(openSettingScreen, () -> mc.setScreen(client.getSettingsScreen()));
    }

    private void register(KeyMapping keyMapping, KeyBehavior behavior) {
        keyMapping = KeyMappingHelper.registerKeyMapping(keyMapping);

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