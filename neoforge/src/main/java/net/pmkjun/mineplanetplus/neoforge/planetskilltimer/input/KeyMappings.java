package net.pmkjun.mineplanetplus.neoforge.planetskilltimer.input;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.Lazy;
import net.pmkjun.mineplanetplus.planetskilltimer.config.SkillTimerConfigScreen;
import net.pmkjun.mineplanetplus.planetskilltimer.input.IKeyMappings;

public class KeyMappings implements IKeyMappings {
    public static final Lazy<KeyMapping> SKILLTIMER_MAPPING = Lazy.of(() -> new KeyMapping("planetskilltimer.key.open_settings", -1, "mineplanetplus.key.category"));

    public void register(IEventBus modEventBus) {
        modEventBus.addListener(KeyMappings::registerBindings);
        NeoForge.EVENT_BUS.addListener(this::onClientTick);
    }

    @Override
    public void register() {
    }

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(SKILLTIMER_MAPPING.get());
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        while (SKILLTIMER_MAPPING.get().consumeClick()) {
            mc.setScreen(new SkillTimerConfigScreen(mc.screen));
        }
    }
}