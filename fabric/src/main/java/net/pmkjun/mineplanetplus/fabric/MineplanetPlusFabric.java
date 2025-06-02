package net.pmkjun.mineplanetplus.fabric;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.mixin.command.CommandManagerMixin;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.pmkjun.mineplanetplus.MineplanetPlus;
import net.pmkjun.mineplanetplus.fabric.dungeonhelper.DungeonHelper;
import net.pmkjun.mineplanetplus.fabric.fishhelper.FishHelperFabric;
import net.pmkjun.mineplanetplus.fabric.input.KeyMappings;
import net.pmkjun.mineplanetplus.fabric.planetskilltimer.PlanetSkillTimerFabric;
import net.pmkjun.mineplanetplus.megaphonetimer.MegaphoneTimer;

public final class MineplanetPlusFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DungeonHelper dungeonhelper = new DungeonHelper();
        FishHelperFabric fishhelper = new FishHelperFabric();
        PlanetSkillTimerFabric skilltimer = new PlanetSkillTimerFabric();

        KeyMappings keyMappings = new KeyMappings();
        keyMappings.register();

        MineplanetPlus.init();
        dungeonhelper.init();
        fishhelper.init();
        skilltimer.init();
        MegaphoneTimer.init();

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("송금수수료").then(ClientCommandManager.argument("value", StringArgumentType.string())).executes(context -> {
                context.getSource().sendFeedback(Component.literal("송금수수료 명령어가 실행되었습니다. 입력된 값 : " + StringArgumentType.getString(context, "value")));
                return 1;
            }));
        });
    }
}
