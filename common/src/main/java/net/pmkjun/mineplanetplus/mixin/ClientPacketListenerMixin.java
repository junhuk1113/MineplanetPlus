package net.pmkjun.mineplanetplus.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.pmkjun.mineplanetplus.serverutility.ServerUtilityClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {
    @Inject(at = @At("TAIL"), method = "handleLogin")
    private void triggerJoinEvent(ClientboundLoginPacket packet, CallbackInfo info) {
        Minecraft mc = Minecraft.getInstance();
        ServerUtilityClient serverUtilityClient = ServerUtilityClient.getInstance();
        String ip;
        try{
            ip = mc.getCurrentServer().ip;
            //mc.player.displayClientMessage(Component.literal("정상처리되었습니다. 현재 서버 주소는 \""+ ip + "\" 입니다"),false);
            serverUtilityClient.updateCurrentIP(ip);
        }
        catch (NullPointerException e){
            serverUtilityClient.updateCurrentIP("null");
        }
    }
}