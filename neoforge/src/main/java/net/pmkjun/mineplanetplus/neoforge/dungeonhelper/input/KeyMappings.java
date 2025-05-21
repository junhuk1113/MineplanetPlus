package net.pmkjun.mineplanetplus.neoforge.dungeonhelper.input;

import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.Lazy;
import net.pmkjun.mineplanetplus.dungeonhelper.gui.screen.DungeonHelperSettingsScreen;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.pmkjun.mineplanetplus.dungeonhelper.input.IKeyMappings;

public class KeyMappings implements IKeyMappings {

    public static final Lazy<KeyMapping> DUNGEONHELPER_MAPPING = Lazy.of(() -> new KeyMapping(
            "key.dungeonhelper.open_dungeonhelper_settings",
            -1,
            "key.dungeonhelper.category"));
    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(DUNGEONHELPER_MAPPING.get());
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        while (DUNGEONHELPER_MAPPING.get().consumeClick()) {
            mc.setScreen(new DungeonHelperSettingsScreen());
        }
    }

    @Override
    public void register() {
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.addListener(KeyMappings::registerBindings);
    }
}
