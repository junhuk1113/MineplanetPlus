package net.pmkjun.mineplanetplus.forge.fishhelper;

import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperMod;
import net.minecraftforge.fml.common.Mod;
import net.pmkjun.mineplanetplus.forge.fishhelper.input.KeyMappings;
import net.pmkjun.mineplanetplus.forge.fishhelper.item.FishItems;
import net.pmkjun.mineplanetplus.fishhelper.gui.screen.ConfigScreen;

@Mod(FishHelperMod.MOD_ID)
public class PyrofishingHelperForge {
    public PyrofishingHelperForge() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        FishItems.register();
        FishItems.ITEMS.register(eventBus);

        KeyMappings keyMappings = new KeyMappings();
        keyMappings.register();
        FishHelperMod.init();
    }

    private void setup(final FMLCommonSetupEvent event){
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((mc, screen) -> new ConfigScreen(screen)));
    }
}