package net.pmkjun.mineplanetplus.fabric.dungeonhelper;

import com.mojang.logging.LogUtils;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.fabric.dungeonhelper.input.KeyMappings;
import net.pmkjun.mineplanetplus.fabric.dungeonhelper.item.DungeonItems;
import net.pmkjun.mineplanetplus.fabric.dungeonhelper.sound.SoundManager;
import org.slf4j.Logger;

public class DungeonHelper{

    public static final String MODID = "dungeonhelper";

    public DungeonHelperClient client;

    public void init() {
        SoundManager soundManager = new SoundManager();
        KeyMappings keyMappings = new KeyMappings();

        client = new DungeonHelperClient(soundManager, keyMappings);
        client.init();
        System.out.println("DungeonHelperClient init");
        soundManager.register();
        keyMappings.register();
        DungeonItems.register();
    }
}
