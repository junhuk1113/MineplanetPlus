package net.pmkjun.mineplanetplus.neoforge.fishhelper.input;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.Lazy;
import net.pmkjun.mineplanetplus.dungeonhelper.gui.screen.DungeonHelperSettingsScreen;
import net.pmkjun.mineplanetplus.fishhelper.gui.screen.FishHelperConfigScreen;
import net.pmkjun.mineplanetplus.fishhelper.input.IKeyMappings;

public class KeyMappings implements IKeyMappings {
    public static final Lazy<KeyMapping> FISHHELPER_MAPPING = Lazy.of(Lazy.of(() -> new KeyMapping(
            "fishhelper.key.open_settings",
            -1,
            "mineplanetplus.key.category")));


    @Override
    public void register() {
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.addListener(KeyMappings::registerKeyBindings);
    }

    @SubscribeEvent
    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        event.register(FISHHELPER_MAPPING.get());
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        while (FISHHELPER_MAPPING.get().consumeClick()) {
            mc.setScreen(new DungeonHelperSettingsScreen());
        }
    }
}