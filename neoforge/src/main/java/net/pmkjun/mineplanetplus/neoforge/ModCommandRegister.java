package net.pmkjun.mineplanetplus.neoforge;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

@EventBusSubscriber
public class ModCommandRegister {
    @SubscribeEvent
    public static void regCommand(RegisterClientCommandsEvent event) {
        new MineplanetplusCommand(event.getDispatcher());
    }
}