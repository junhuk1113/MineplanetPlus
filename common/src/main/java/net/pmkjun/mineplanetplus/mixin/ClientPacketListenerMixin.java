package net.pmkjun.mineplanetplus.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.dungeonhelper.util.ClassCategory;
import net.pmkjun.mineplanetplus.dungeonhelper.util.TpsTracker;
import net.pmkjun.mineplanetplus.serverutility.ServerUtilityClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.NoSuchElementException;

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

        TpsTracker.INSTANCE.onGameJoined();
    }

    @Inject(method = "handleSoundEvent(Lnet/minecraft/network/protocol/game/ClientboundSoundPacket;)V", at = {@At("HEAD")}, cancellable = true)
    public void handleSoundEvent(ClientboundSoundPacket packet, CallbackInfo ci) {
        try{
            //mc.player.displayClientMessage(Component.literal("sound event : " + packet.getSound().unwrapKey().get().location().getPath()),false);
            if(packet.getSound().unwrapKey().get().location().getPath().contains("entity.player.attack")) {
                if(shouldMuteAttackSound())
                    ci.cancel();
            }
        }
        catch(NoSuchElementException e){}
    }

    @Unique
    private boolean shouldMuteAttackSound(){
        Minecraft mc = Minecraft.getInstance();
        DungeonHelperClient dungeonhelper = DungeonHelperClient.getInstance();
        List<Component> ItemText;
        ItemStack mainhandStack;
        boolean isHoldingDungeonWeapon = false;

        try{
            mainhandStack = mc.player.getMainHandItem();
        }
        catch(NullPointerException e){return false;}


        ItemText = mainhandStack.getTooltipLines(Item.TooltipContext.EMPTY, mc.player, TooltipFlag.NORMAL);
        for (Component text : ItemText) {
            if (text.getString().equals("어새신 전용") || text.getString().equals("용기사 전용") ||
                    text.getString().equals("무투가 전용") || text.getString().equals("배틀메이지 전용")) {
                isHoldingDungeonWeapon = true;
                break;
            }

        }


        return !dungeonhelper.data.toggleDefaultWeaponSound && isHoldingDungeonWeapon && dungeonhelper.ishereDungeon;
    }
}