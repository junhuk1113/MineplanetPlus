package net.pmkjun.mineplanetplus.neoforge.fishhelper;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperMod;
import net.pmkjun.mineplanetplus.neoforge.fishhelper.input.KeyMappings;
import net.pmkjun.mineplanetplus.neoforge.fishhelper.item.FishItems;

public class PyrofishingHelperForge {
    public PyrofishingHelperForge(IEventBus modEventBus) {
        FishItems.register();
        FishItems.ITEMS.register(modEventBus);

        KeyMappings keyMappings = new KeyMappings();
        keyMappings.register(modEventBus);
        FishHelperMod.init();
    }
}