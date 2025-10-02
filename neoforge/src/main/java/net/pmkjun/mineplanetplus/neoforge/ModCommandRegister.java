package net.pmkjun.mineplanetplus.neoforge;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
public class ModCommandRegister {
    @SubscribeEvent
    public static void regCommand(RegisterClientCommandsEvent event) {
        new MineplanetplusCommand(event.getDispatcher());
    }
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        FishHelperClient.getInstance().fishCache.tick();
    }
}