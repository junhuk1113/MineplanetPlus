package net.pmkjun.mineplanetplus.mixin;

import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.dungeonhelper.gui.screen.SkillCooltimeSettingsScreen;
import net.pmkjun.mineplanetplus.dungeonhelper.util.ClassCategory;
import net.pmkjun.mineplanetplus.dungeonhelper.util.TpsTracker;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.world.entity.Display.ItemDisplay;

import java.util.ArrayList;
import java.util.List;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {
    //대시 스킬
    private static final int VALID_BLADE_DASH_DISTANCE = 30;
    private static final int VALID_DRAGON_DASH_DISTANCE = 5;
    private static final int VALID_DEATH_SPIN_DISTANCE = 2;

    //LV20 스킬
    private static final int VALID_THRUST_DISTANCE = 5;
    private static final int VALID_DRAGON_WHEEL_DISTANCE = 50;
    private static final int VALID_UPPERCUT_DISTANCE = 5;
    private static final int VALID_ARCANE_SCISSORS_DISTANCE = 21;

    //LV30 스킬
    private static final int VALID_DIVING_STRIKE_DISTANCE = 50;

    //LV40 스킬
    private static final int VALID_BLADE_SPIN_DISTANCE = 2;
    private static final int VALID_DRAGON_SMASH_DISTANCE = 25;
    private static final int VALID_MARTIAL_DRIVE_DISTANCE = 16;
    private static final int VALID_INFERNO_CHAIN_DISTANCE = 28;

    //궁극기
    private static final int VALID_BLADE_DANCE_DISTANCE = 5;
    private static final int VALID_DRAGON_FURY_DISTANCE = 5;
    private static final int VALID_MULTIPLE_BLOW_DISTANCE = 40;
    private static final int VALID_ARCANE_DEMOLITION_DISTANCE = 83;

    private static final Minecraft mc = Minecraft.getInstance();
    private static final DungeonHelperClient client = DungeonHelperClient.getInstance();
    private ArrayList<Integer> entityids = new ArrayList<>();

    @Shadow
    private ClientLevel level;

    @Inject(method = "handleSetEquipment(Lnet/minecraft/network/protocol/game/ClientboundSetEquipmentPacket;)V", at = {@At("TAIL")})
    private void handleSetEquipmentMixin(ClientboundSetEquipmentPacket clientboundSetEquipmentPacket, CallbackInfo info) {
        Entity entity = this.level.getEntity(clientboundSetEquipmentPacket.getEntity());
        
        if(entity instanceof ArmorStand armorStand) {
            if (mc.player != null) {
                List<Pair<EquipmentSlot, ItemStack>> slots = clientboundSetEquipmentPacket.getSlots();
                for(Pair<EquipmentSlot, ItemStack> slot : slots) {
                    CompoundTag tag = slot.getSecond().getTag();

                    if(tag == null)
                        continue;

                    if(slot.getFirst().getName().equals("mainhand")||slot.getFirst().getName().equals("head")) {
                        int id = tag.getInt("CustomModelData");
                        double distance = armorStand.position().distanceToSqr(mc.player.position());

                        /*
                        mc.player.displayClientMessage(Component.literal(tag.toString()), false);
                        mc.player.displayClientMessage(Component.literal(String.valueOf(distance)), false);
                        */

                        if (SkillCooltimeSettingsScreen.DEBUG_MODE) {
                            mc.player.displayClientMessage((Component)Component.literal(tag.toString()), false);
                            mc.player.displayClientMessage((Component)Component.literal(String.valueOf(distance)), false);
                            mc.player.displayClientMessage(Component.literal("currentTime : "+String.valueOf(System.currentTimeMillis())), false);
                        }

                        if(client.data.classType == ClassCategory.ASSASSIN) {
                            if(id == 2899 && distance < VALID_BLADE_DASH_DISTANCE)
                                client.updateLastComboSkillTime();
                            if(id == 2871 && distance < VALID_THRUST_DISTANCE)
                                client.resetLastComboSkillTime();
                            if(id == 2883 && distance < VALID_BLADE_SPIN_DISTANCE)
                                client.updateLastLV40SkillTime();
                            if(id == 2948 && distance < 2)
                                client.updateLastLV40SkillTime(1f);
                            if(id == 2884 && distance < VALID_BLADE_DANCE_DISTANCE)
                                client.updateLastUltimateTime();
                        }
                        else if (client.data.classType == ClassCategory.DRAGON_WARRIOR) {
                            if(id == 2325 && distance < VALID_DRAGON_DASH_DISTANCE)
                                client.updateLastComboSkillTime();
                            if(id == 2347 && distance < VALID_DRAGON_WHEEL_DISTANCE)
                                client.resetLastComboSkillTime();
                            if(id == 2334 && distance < VALID_DRAGON_FURY_DISTANCE)
                                client.updateLastUltimateTime();
                        }

                        else if (client.data.classType == ClassCategory.MARTIAL_ARTIST) {
                            if (id == 2455 && distance < VALID_UPPERCUT_DISTANCE)
                                client.updateLastComboSkillTime();
                            if (id == 2408 && distance < VALID_DIVING_STRIKE_DISTANCE)
                                client.resetLastComboSkillTime();
                            if (id == 2442 && distance < VALID_MULTIPLE_BLOW_DISTANCE)
                                client.updateLastUltimateTime();
                        }
                        else if(client.data.classType == ClassCategory.BATTLE_MAGE){
                            if (id == 2857 && distance < VALID_DEATH_SPIN_DISTANCE)
                                client.updateLastComboSkillTime();
                            if (id == 2849 && distance < VALID_ARCANE_SCISSORS_DISTANCE)
                                client.resetLastComboSkillTime();
                            if (id == 2960 && distance < VALID_INFERNO_CHAIN_DISTANCE)
                                client.updateLastLV40SkillTime();
                            if (id == 2686 && distance < VALID_ARCANE_DEMOLITION_DISTANCE)
                                client.updateLastUltimateTime();    
                        }
                        // else 2329 - dragon piercing
                        // else 2109 - assassin_dash_1
                    }
                }
            }
        }
    }
    @Inject(method = "handleSetEntityData(Lnet/minecraft/network/protocol/game/ClientboundSetEntityDataPacket;)V", at = {@At("TAIL")})
    public void handleSetEntityData(ClientboundSetEntityDataPacket clientboundSetEntityDataPacket, CallbackInfo info){
        int index;
        if(entityids.contains(clientboundSetEntityDataPacket.id()))
        {
            index = entityids.indexOf(clientboundSetEntityDataPacket.id());
            Entity entity = this.level.getEntity(clientboundSetEntityDataPacket.id());
            if(entity instanceof ItemDisplay itemDisplay){
                try{
                    CompoundTag tag = itemDisplay.itemRenderState().itemStack().getTag();
                    int id = tag.getInt("CustomModelData");
                    double distance = itemDisplay.position().distanceToSqr(mc.player.position());
                    if (SkillCooltimeSettingsScreen.DEBUG_MODE){
                        mc.player.displayClientMessage(Component.literal("itemdisplay : " + tag), false);
                        mc.player.displayClientMessage((Component)Component.literal(String.valueOf(distance)), false);
                    }
                    
                    else if (client.data.classType == ClassCategory.DRAGON_WARRIOR) {
                        if(id == 3617 && distance < VALID_DRAGON_SMASH_DISTANCE)
                            client.updateLastLV40SkillTime();
                    }

                    else if (client.data.classType == ClassCategory.MARTIAL_ARTIST) {
                        if (id == 3633 && distance < VALID_MARTIAL_DRIVE_DISTANCE)
                            client.updateLastLV40SkillTime();
                    }

                    entityids.remove(index);
                }
                catch(NullPointerException e){
                }
            }
            
        }
    }
    
    @Inject(method = "handleAddEntity(Lnet/minecraft/network/protocol/game/ClientboundAddEntityPacket;)V", at = {@At("RETURN")})
    public void handleSetEntityData(ClientboundAddEntityPacket clientboundAddEntityPacket, CallbackInfo info){
        Entity entity = this.level.getEntity(clientboundAddEntityPacket.getId());
        if(entity == null) return;
    
        if(entity instanceof ItemDisplay){
            try{
                entityids.add(clientboundAddEntityPacket.getId());
            }
            catch(NullPointerException e){
            }
        }
    }
    
    @Inject(at = @At("TAIL"), method = "handleLogin")
    private void triggerJoinEvent(ClientboundLoginPacket packet, CallbackInfo info) {
        TpsTracker.INSTANCE.onGameJoined();
    }
    @Shadow
    @Final
    private Connection connection;

    @Shadow
    private ServerData serverData;

    @Inject(method = "handleResourcePack", at = @At("HEAD"), cancellable = true)
    private void onResourcePackSend(ClientboundResourcePackPacket packet, CallbackInfo ci) {
        //String hash = packet.getHash();
        boolean required = packet.isRequired();  // 서버에서 리소스팩을 필수로 요구하는지 확인
        //boolean ishashMatch = (hash == client.data.hash);
        /*if(!ishashMatch&&client.data.hash != null){ // 해시가 일치하지 않는 경우
            client.data.hash = hash;
            client.configManage.save();
            this.serverData.setResourcePackPolicy(ServerInfo.ResourcePackPolicy.ENABLED);
            mc.getResourcePackManager().disable("mineplanet.kr");
        }*/
        // 서버에서 리소스팩을 필수로 요구하고, 클라이언트에서 리소스팩 정책이 DISABLED인 경우에만 처리
        if (this.serverData != null && this.serverData.getResourcePackStatus() == ServerData.ServerPackStatus.DISABLED && required) {
            //client.data.hash = hash;
            //client.configManage.save();
            // 리소스팩 거부했더라도 ACCEPTED 신호를 보냄
            this.sendResourcePackStatus(ServerboundResourcePackPacket.Action.ACCEPTED);

            // 리소스팩을 다운로드하지 않고 SUCCESSFULLY_LOADED 신호를 전송
            // 별도의 스레드 생성
            new Thread(() -> {
            try {
                Thread.sleep(1000); // 1000ms 대기
            } catch (InterruptedException e) {
                System.err.println("스레드가 중단되었습니다: " + e.getMessage());
            }

            // 리소스팩을 다운로드하지 않고 SUCCESSFULLY_LOADED 신호를 전송
                this.sendResourcePackStatus(ServerboundResourcePackPacket.Action.SUCCESSFULLY_LOADED);
            }).start();

            // 기존 리소스팩 처리 로직을 취소 (리소스팩을 실제로 적용하지 않음)     
            ci.cancel();
        }
    }
    private void sendResourcePackStatus(ServerboundResourcePackPacket.Action action) {
        // 서버에 리소스팩 상태 신호 전송
        this.connection.send(new ServerboundResourcePackPacket(action));
    }
}