package net.pmkjun.mineplanetplus.fishhelper;

import net.pmkjun.mineplanetplus.fishhelper.input.KeyMappings;
import net.pmkjun.mineplanetplus.fishhelper.item.FishItems;

public class FishHelperFabric{
    public void init() {
        FishHelperMod.init();
        FishItems.register();
        KeyMappings keyMappings = new KeyMappings();
        keyMappings.register();
    }
}