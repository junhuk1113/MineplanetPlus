package net.pmkjun.mineplanetplus.fabric.fishhelper;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.pmkjun.mineplanetplus.fabric.fishhelper.input.KeyMappings;
import net.pmkjun.mineplanetplus.fabric.fishhelper.item.FishItems;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperMod;

public class FishHelperFabric{
    public void init() {
        FishHelperMod.init();
        FishItems.register();
        KeyMappings keyMappings = new KeyMappings();
        keyMappings.register();

        registerClientTickEvents();
    }

    private void registerClientTickEvents() {
        // 클라이언트의 매 틱이 끝날 때마다 코드가 실행됩니다.
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            FishHelperClient.getInstance().fishCache.tick();
        });
    }
}