package net.pmkjun.mineplanetplus.fabric.fishhelper;

import net.pmkjun.mineplanetplus.fishhelper.FishHelperMod;
import net.pmkjun.mineplanetplus.fabric.fishhelper.input.KeyMappings;
import net.pmkjun.mineplanetplus.fabric.fishhelper.item.FishItems;

public class FishHelperFabric{
    public void init() {
        FishHelperMod.init();
        FishItems.register();
        KeyMappings keyMappings = new KeyMappings();
        keyMappings.register();
    }
}