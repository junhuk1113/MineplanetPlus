package net.pmkjun.mineplanetplus.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.dungeonhelper.util.TpsTracker;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.item.FishItemList;
import net.pmkjun.mineplanetplus.serverutility.ServerUtilityClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {
    private ArrayList<Integer> entityids = new ArrayList<>();

    @Shadow
    private ClientLevel level;

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

    @Inject(method = "handleContainerSetSlot",at = {@At("HEAD")})
    public void handleContainerSetSlot(ClientboundContainerSetSlotPacket packet, CallbackInfo ci){
        Minecraft mc = Minecraft.getInstance();
        FishHelperClient fishhelper = FishHelperClient.getInstance();

        if(packet.getContainerId() == 0 && mc.isSameThread()) {//netty io 실행 시 실행하지 않음
            ItemStack itemStack = packet.getItem();
            try {
                int current_count = mc.player.containerMenu.getSlot(packet.getSlot()).getItem().getCount();

                //System.out.println("Container Set Slot : " + itemStack.getHoverName().getString() + "(" + current_count + ">>" + itemStack.getCount() + ")");
                if (FishItemList.getFishType(itemStack.getHoverName())!=null && itemStack.getComponents().has(DataComponents.CUSTOM_MODEL_DATA)) {
                    if (itemStack.getCount() != current_count){
                        //mc.player.displayClientMessage(Component.literal("물고기 감지 : " + itemStack.getHoverName().getString() + "(" + current_count + ">>" + itemStack.getCount() + ")"), false);
                        fishhelper.fishCache.catchFish(itemStack.getHoverName().getString(), current_count, itemStack.getCount());
                    }


                }
            } catch (Exception ignored) {}
        }
    }

    @Inject(method = "handleSetEntityData(Lnet/minecraft/network/protocol/game/ClientboundSetEntityDataPacket;)V", at = {@At("TAIL")})
    public void handleSetEntityData(ClientboundSetEntityDataPacket clientboundSetEntityDataPacket, CallbackInfo info){
        Minecraft mc = Minecraft.getInstance();
        FishHelperClient fishhelper = FishHelperClient.getInstance();

        int index;
        if(entityids.contains(clientboundSetEntityDataPacket.id()))
        {
            index = entityids.indexOf(clientboundSetEntityDataPacket.id());
            Entity entity = this.level.getEntity(clientboundSetEntityDataPacket.id());
            if(entity instanceof ItemEntity itemEntity){
                try{
                    ItemStack itemStack = itemEntity.getItem();
                    //mc.player.displayClientMessage(Component.literal("item entity added : "+ itemEntity.getItem().getHoverName().getString() + "("+itemEntity.position().distanceToSqr(mc.player.position())+")"),false);
                    //System.out.println("item entity added : "+ itemEntity.getItem().getHoverName().getString() + "("+itemEntity.position().distanceToSqr(mc.player.position())+")");
                    if (FishItemList.getFishType(itemStack.getHoverName())!=null && itemStack.getComponents().has(DataComponents.CUSTOM_MODEL_DATA)) {
                        System.out.println("item entity added : "+ itemEntity.getItem().getHoverName().getString() + "("+itemEntity.position().distanceToSqr(mc.player.position())+")");
                        if(itemEntity.position().distanceToSqr(mc.player.position())<1.6) {
                            //mc.player.displayClientMessage(Component.literal("물고기 드랍 감지 : " + itemStack.getHoverName().getString() + "(" + itemStack.getCount() + ")"), false);
                            fishhelper.fishCache.catchFish(itemStack.getHoverName().getString(), itemStack.getCount());
                        }

                    }
                    entityids.remove(index);
                }
                catch(NullPointerException e){
                    //System.out.println("null!");
                    entityids.remove(index);
                }
            }

        }
    }

    @Inject(method = "handleAddEntity(Lnet/minecraft/network/protocol/game/ClientboundAddEntityPacket;)V", at = {@At("RETURN")})
    public void handleAddEntity(ClientboundAddEntityPacket clientboundAddEntityPacket, CallbackInfo info){
        Entity entity = this.level.getEntity(clientboundAddEntityPacket.getId());
        if(entity == null) return;

        if(entity instanceof ItemEntity){
            //System.out.println("item entity added");
            try{
                entityids.add(clientboundAddEntityPacket.getId());
            }
            catch(NullPointerException e){
            }
        }
    }
}