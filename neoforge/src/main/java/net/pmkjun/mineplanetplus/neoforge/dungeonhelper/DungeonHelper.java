package net.pmkjun.mineplanetplus.neoforge.dungeonhelper;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import com.mojang.logging.LogUtils;
import net.pmkjun.mineplanetplus.neoforge.dungeonhelper.input.KeyMappings;
import net.pmkjun.mineplanetplus.neoforge.dungeonhelper.item.DungeonItems;
import org.slf4j.Logger;


public class DungeonHelper {
    public static final String MODID = "dungeonhelper";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DungeonHelperClient client;

    public DungeonHelper(IEventBus modEventBus){
        DungeonItems.ITEMS.register(modEventBus);

        KeyMappings keyMapping = new KeyMappings();
        keyMapping.register(modEventBus);

        client = new DungeonHelperClient(keyMapping);
        client.init();
    }
}
