package net.pmkjun.mineplanetplus.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.world.entity.Display.ItemDisplay;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.dungeonhelper.gui.screen.SkillCooltimeSettingsScreen;
import net.pmkjun.mineplanetplus.dungeonhelper.util.ClassCategory;
import net.pmkjun.mineplanetplus.dungeonhelper.util.TpsTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {
    /*
    //대시 스킬
    private static final int VALID_BLADE_DASH_DISTANCE = 30;
    private static final int VALID_DRAGON_DASH_DISTANCE = 8;
    private static final int VALID_DEATH_SPIN_DISTANCE = 2;

    //LV20 스킬
    private static final int VALID_THRUST_DISTANCE = 5;
    private static final int VALID_DRAGON_WHEEL_DISTANCE = 50;
    private static final int VALID_UPPERCUT_DISTANCE = 5;
    private static final int VALID_ARCANE_SCISSORS_DISTANCE = 21;

    //LV30 스킬
    private static final int VALID_DAGGER_THROW_DISTANCE = 4;
    private static final int VALID_DRAGON_BREATH_DISTANCE = 3;
    private static final int VALID_DIVING_STRIKE_DISTANCE = 50;
    private static final int VALID_ARCANE_SPIN_DISTANCE = 5;

    //LV40 스킬
    private static final int VALID_BLADE_SPIN_DISTANCE = 2;
    private static final int VALID_DRAGON_SMASH_DISTANCE = 10;
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
    private ClientLevel level; //하단 코드 임시조치, 수정필요

    @Inject(method = "handleSetEquipment(Lnet/minecraft/network/protocol/game/ClientboundSetEquipmentPacket;)V", at = {@At("TAIL")})
    private void handleSetEquipmentMixin(ClientboundSetEquipmentPacket clientboundSetEquipmentPacket, CallbackInfo info) {
        Entity entity = this.level.getEntity(clientboundSetEquipmentPacket.getEntity());
        
        if(entity instanceof ArmorStand armorStand) {
            if (mc.player != null) {
                List<Pair<EquipmentSlot, ItemStack>> slots = clientboundSetEquipmentPacket.getSlots();
                for(Pair<EquipmentSlot, ItemStack> slot : slots) {
                    //CompoundTag tag = slot.getSecond().getTag();
                    CustomModelData tag = slot.getSecond().get(DataComponents.CUSTOM_MODEL_DATA);

                    if(tag == null)
                        continue;

                    if(slot.getFirst().getName().equals("mainhand")||slot.getFirst().getName().equals("head")) {
                        int id = tag.getFloat(0).intValue();
                        double distance = armorStand.position().distanceToSqr(mc.player.position());


                        //mc.player.displayClientMessage(Component.literal(tag.toString()), false);
                        //mc.player.displayClientMessage(Component.literal(String.valueOf(distance)), false);


                        if (SkillCooltimeSettingsScreen.DEBUG_MODE) {
                            mc.player.displayClientMessage(Component.literal(tag.toString()), false);
                            mc.player.displayClientMessage(Component.literal(String.valueOf(distance)), false);
                            mc.player.displayClientMessage(Component.literal("currentTime : "+ System.currentTimeMillis()), false);
                        }

                        if(client.data.classType == ClassCategory.ASSASSIN) {
                            if(id == 2899 && distance < VALID_BLADE_DASH_DISTANCE)
                                client.updateLastComboSkillTime();
                            if(id == 2871 && distance < VALID_THRUST_DISTANCE)
                                client.resetLastComboSkillTime();
                            if(id == 2947 && distance < VALID_DAGGER_THROW_DISTANCE)
                                client.updateLastLV30SkillTime();
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
                            if(id == 2336 && distance < VALID_DRAGON_BREATH_DISTANCE)
                                client.updateLastLV30SkillTime();
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
                    //CustomModelData tag = itemDisplay.itemRenderState().itemStack().get(DataComponents.CUSTOM_MODEL_DATA);
                    //Component c = itemDisplay.getCustomName();
                    //int id = tag.getFloat(0).intValue();
                    String model = itemDisplay.itemRenderState().itemStack().get(DataComponents.ITEM_MODEL).toString();
                    double distance = itemDisplay.position().distanceToSqr(mc.player.position());
                    if (SkillCooltimeSettingsScreen.DEBUG_MODE){
                        mc.player.displayClientMessage(Component.literal(itemDisplay.itemRenderState().itemStack().get(DataComponents.ITEM_MODEL).toString()), false);
                        //mc.player.displayClientMessage(itemDisplay.getCustomName(), false);
                        mc.player.displayClientMessage(Component.literal(String.valueOf(distance)), false);
                    }
                    
                    if (client.data.classType == ClassCategory.DRAGON_WARRIOR) {
                        if(model.equals("modelengine:fx_ice_crater_big/body2") && distance < VALID_DRAGON_SMASH_DISTANCE)
                            client.updateLastLV40SkillTime();
                    }

                    else if (client.data.classType == ClassCategory.MARTIAL_ARTIST) {
                        if (model.equals("modelengine:fx_arctic_charge/body") && distance < VALID_MARTIAL_DRIVE_DISTANCE)
                            client.updateLastLV40SkillTime();
                    }
                    else if (client.data.classType == ClassCategory.BATTLE_MAGE) {
                        if (model.equals("modelengine:vfx_gale_slash_1/body") && distance < VALID_ARCANE_SPIN_DISTANCE)
                            client.updateLastLV30SkillTime();
                    }

                    entityids.remove(index);
                }
                catch(NullPointerException e){
                    if (SkillCooltimeSettingsScreen.DEBUG_MODE) {
                        mc.player.displayClientMessage(Component.literal("item display null"), false);
                    }
                }
            }
            
        }
    }

    @Inject(method = "handleAddEntity(Lnet/minecraft/network/protocol/game/ClientboundAddEntityPacket;)V", at = {@At("RETURN")})
    public void handleSetEntityData(ClientboundAddEntityPacket clientboundAddEntityPacket, CallbackInfo info){
        Entity entity = this.level.getEntity(clientboundAddEntityPacket.getId());
        if(entity == null) return;
    
        if(entity instanceof ItemDisplay){
            //System.out.println("item display added");
            try{
                entityids.add(clientboundAddEntityPacket.getId());
            }
            catch(NullPointerException e){
            }
        }
    }
    */
    @Inject(at = @At("TAIL"), method = "handleLogin")
    private void triggerJoinEvent(ClientboundLoginPacket packet, CallbackInfo info) {
        TpsTracker.INSTANCE.onGameJoined();
    }
}