package net.pmkjun.mineplanetplus.neoforge.input;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.Lazy;
import net.pmkjun.mineplanetplus.input.IKeyMappings;
import net.pmkjun.mineplanetplus.gui.SettingsScreen;

public class KeyMappings implements IKeyMappings {

    public static final Lazy<KeyMapping> MINEPLANETPLUS_MAPPING = Lazy.of(() -> new KeyMapping(
            "mineplanetplus.key.open_settings",
            InputConstants.KEY_M,
            "mineplanetplus.key.category"));
    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(MINEPLANETPLUS_MAPPING.get());
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        while (MINEPLANETPLUS_MAPPING.get().consumeClick()) {
            mc.setScreen(new SettingsScreen());
        }
    }

    public void register(IEventBus modEventBus) {
        modEventBus.addListener(KeyMappings::registerBindings);
        NeoForge.EVENT_BUS.addListener(this::onClientTick);
    }

    public void register(){

    }
}
