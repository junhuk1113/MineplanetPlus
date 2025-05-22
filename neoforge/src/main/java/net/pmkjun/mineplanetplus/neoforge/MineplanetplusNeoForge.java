package net.pmkjun.mineplanetplus.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.pmkjun.mineplanetplus.MineplanetPlus;
import net.neoforged.fml.common.Mod;
import net.pmkjun.mineplanetplus.gui.SettingsScreen;
import net.pmkjun.mineplanetplus.megaphonetimer.MegaphoneTimer;
import net.pmkjun.mineplanetplus.neoforge.dungeonhelper.DungeonHelper;
import net.pmkjun.mineplanetplus.neoforge.fishhelper.PyrofishingHelperForge;
import net.pmkjun.mineplanetplus.neoforge.input.KeyMappings;
import net.pmkjun.mineplanetplus.neoforge.planetskilltimer.PlanetSkillTimerForge;

@Mod(MineplanetPlus.MOD_ID)
public final class MineplanetplusNeoForge {
    public MineplanetplusNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        // Run our common setup.
        KeyMappings keyMappings = new KeyMappings();
        keyMappings.register(modEventBus);

        DungeonHelper dungeonhelper = new DungeonHelper(modEventBus);
        PyrofishingHelperForge fishhelper = new PyrofishingHelperForge(modEventBus);
        PlanetSkillTimerForge skilltimer = new PlanetSkillTimerForge(modEventBus);
        MegaphoneTimer.init();

        MineplanetPlus.init();
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, (mc, screen) -> new SettingsScreen(screen));
    }
}
