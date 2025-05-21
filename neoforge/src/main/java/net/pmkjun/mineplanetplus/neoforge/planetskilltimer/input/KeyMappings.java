package net.pmkjun.mineplanetplus.neoforge.planetskilltimer.input;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.Lazy;
import net.pmkjun.mineplanetplus.dungeonhelper.gui.screen.DungeonHelperSettingsScreen;
import net.pmkjun.mineplanetplus.planetskilltimer.config.SkillTimerConfigScreen;
import net.pmkjun.mineplanetplus.planetskilltimer.input.IKeyMappings;
//new KeyMapping("planetskilltimer.key.open_settings", InputConstants.KEY_J, "planetskilltimer.key.open_settings");
public class KeyMappings implements IKeyMappings {
    public static final Lazy<KeyMapping> SKILLTIMER_MAPPING = Lazy.of(() -> new KeyMapping("planetskilltimer.key.open_settings", InputConstants.KEY_J, "planetskilltimer.key.open_settings"));

    @Override
    public void register() {
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.addListener(KeyMappings::registerBindings);
    }

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(SKILLTIMER_MAPPING.get());
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        while (SKILLTIMER_MAPPING.get().consumeClick()) {
            mc.setScreen(new SkillTimerConfigScreen(mc.screen));
        }
    }
}
