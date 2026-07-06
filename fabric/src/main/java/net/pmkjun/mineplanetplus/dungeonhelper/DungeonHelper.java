package net.pmkjun.mineplanetplus.dungeonhelper;

import net.pmkjun.mineplanetplus.dungeonhelper.input.KeyMappings;
import net.pmkjun.mineplanetplus.dungeonhelper.item.DungeonItems;

public class DungeonHelper{

    public static final String MODID = "dungeonhelper";

    public DungeonHelperClient client;

    public void init() {
        KeyMappings keyMappings = new KeyMappings();

        client = new DungeonHelperClient(keyMappings);
        client.init();
        System.out.println("DungeonHelperClient init");
        keyMappings.register();
        DungeonItems.register();
    }
}
