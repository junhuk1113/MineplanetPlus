package net.pmkjun.mineplanetplus.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.pmkjun.mineplanetplus.dungeonhelper.util.TpsTracker;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;

@Mixin(Connection.class)
public class ConnectionMixin {
    @Inject(method = "genericsFtw", at = @At("HEAD"), cancellable = true)
    private static <T extends PacketListener> void onHandlePacket(Packet<T> packet, PacketListener packetListener, CallbackInfo ci) {
        TpsTracker.INSTANCE.onHandlePacket(packet, packetListener);
    }
}