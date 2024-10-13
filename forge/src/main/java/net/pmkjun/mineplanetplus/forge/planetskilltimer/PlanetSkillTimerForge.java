package net.pmkjun.mineplanetplus.forge.planetskilltimer;

import net.pmkjun.mineplanetplus.planetskilltimer.PlanetSkillTimer;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.pmkjun.mineplanetplus.forge.planetskilltimer.input.KeyMappings;
import net.pmkjun.mineplanetplus.planetskilltimer.config.SkillTimerConfigScreen;

public class PlanetSkillTimerForge {
    public PlanetSkillTimerForge() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);

        KeyMappings keyMappings = new KeyMappings();
        keyMappings.register();

        PlanetSkillTimer.init();
    }
    private void setup(final FMLCommonSetupEvent event){
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((mc, screen) -> new SkillTimerConfigScreen(screen)));
    }
}
