package net.pmkjun.mineplanetplus.input;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.pmkjun.mineplanetplus.gui.SettingsScreen;

public class KeyMappings implements IKeyMappings {

    // 26.1: KeyMapping Category가 레코드로 분리되었습니다. 다른 하위 모듈에서도 사용할 수 있도록 static으로 선언합니다.
    public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("mineplanetplus", "key.category"));

    public static KeyMapping openSettingScreen = new KeyMapping(
            "mineplanetplus.key.open_settings",
            InputConstants.KEY_M,
            CATEGORY);

    @Override
    public void register() {
        Minecraft mc = Minecraft.getInstance();

        register(openSettingScreen, () -> mc.setScreen(new SettingsScreen()));
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

    public interface KeyBehavior {
        void action();
    }
}