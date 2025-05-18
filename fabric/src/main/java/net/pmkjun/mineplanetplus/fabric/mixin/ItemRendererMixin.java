package net.pmkjun.mineplanetplus.fabric.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.dungeonhelper.util.*;
import net.pmkjun.mineplanetplus.fabric.dungeonhelper.DungeonHelper;
import net.pmkjun.mineplanetplus.fabric.fishhelper.item.FishItems;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.util.ConvertActivateTime;
import net.pmkjun.mineplanetplus.fishhelper.util.ConvertCooldown;
import net.pmkjun.mineplanetplus.fishhelper.util.FishingRod;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Function;

@Mixin(ItemModelResolver.class)
public class ItemRendererMixin {
    @Shadow
    private static void fixupSkullProfile(ItemStack stack) {
    }

    @Shadow
    @Final
    private Function<ResourceLocation, ItemModel> modelGetter;

    private final Minecraft mc = Minecraft.getInstance();
    private final DungeonHelperClient client = DungeonHelperClient.getInstance();
    private ItemStack previousMainhandStack;

    @Inject(method = {"appendItemLayers"}, at = {@At("RETURN")})
    public void appendItemLayers(ItemStackRenderState renderState, ItemStack stack, ItemDisplayContext displayContext, Level level, LivingEntity entity, int seed, CallbackInfo ci) {
        CustomEnchantType cetype;
        RuneofFortuneType rftype;
        Item changed_item;
        List<Component> ItemText;
        String Itemname = null;
        String levelString;
        int levelInt;
        double secondDouble;
        long secondLong;

        ItemText = stack.getTooltipLines(Item.TooltipContext.EMPTY, mc.player, TooltipFlag.NORMAL);
        for (Component text : ItemText) {
            if (Itemname == null)
                Itemname = text.getString();

            if (!Itemname.equals("지속시간 업그레이드") && !Itemname.equals("쿨타임 감소") && !Itemname.contains("토템 리더 |"))
                break;

            if (text.getString().contains("현재 레벨 ➛ ")) {
                levelString = text.getString().replace("현재 레벨 ➛ ", "");
                levelInt = Integer.parseInt(levelString);
                //System.out.println(Itemname +" : "+ levelString);
                if (Itemname.equals("지속시간 업그레이드")) {
                    if (FishHelperClient.getInstance().data.valueTotemActivetime != ConvertActivateTime.asMinute(levelInt)) {
                        FishHelperClient.getInstance().data.valueTotemActivetime = ConvertActivateTime.asMinute(levelInt);
                        FishHelperClient.getInstance().configManage.save();
                    }
                }
                if (Itemname.equals("쿨타임 감소")) {
                    if (FishHelperClient.getInstance().data.valueTotemCooldown != ConvertCooldown.asMinute(levelInt)) {
                        FishHelperClient.getInstance().data.valueTotemCooldown = ConvertCooldown.asMinute(levelInt);
                        FishHelperClient.getInstance().configManage.save();
                    }
                }
            }
            if (Itemname.contains("토템 리더 |") && text.getString().contains("효과|") && !text.getString().contains("다음 레벨 효과")) {
                levelString = text.getString().replace("효과| ", "");
                levelString = levelString.replace(" 초 감소", "");
                secondDouble = Double.parseDouble(levelString);
                secondLong = (long) (secondDouble * 1000);
                if (FishHelperClient.getInstance().data.valueCooldownReduction != secondLong) {
                    FishHelperClient.getInstance().data.valueCooldownReduction = secondLong;
                    FishHelperClient.getInstance().configManage.save();
                }
            }

        }

        ItemStack mainhandStack = mc.player.getMainHandItem();
        if (mainhandStack != previousMainhandStack) {
            previousMainhandStack = mainhandStack;
            //mc.player.sendMessage(Text.literal("MainhandStack 변경됨 : "+mainhandStack.getItem().getTranslationKey()));

            if (mainhandStack.getItem().getDescriptionId().equals("item.minecraft.fishing_rod")) {
                FishingRod.updateSpec(mainhandStack);
            }

            if (this.client.data.toggleAutoClassDetect) {
                ItemText = mainhandStack.getTooltipLines(Item.TooltipContext.EMPTY, mc.player, TooltipFlag.NORMAL);
                for (Component text : ItemText) {
                    if (Itemname == null)
                        Itemname = text.getString();

                    if (text.getString().equals("어새신 전용")) {
                        //mc.player.displayClientMessage(Component.literal("어새신 무기가 감지되었습니다."),false);
                        client.data.classType = ClassCategory.ASSASSIN;
                        client.settings.save();
                    } else if (text.getString().equals("용기사 전용")) {
                        //mc.player.displayClientMessage(Component.literal("용기사 무기가 감지되었습니다."),false);
                        client.data.classType = ClassCategory.DRAGON_WARRIOR;
                        client.settings.save();
                    } else if (text.getString().equals("무투가 전용")) {
                        //mc.player.displayClientMessage(Component.literal("무투가 무기가 감지되었습니다."),false);
                        client.data.classType = ClassCategory.MARTIAL_ARTIST;
                        client.settings.save();
                    } else if (text.getString().equals("배틀메이지 전용")) {
                        //mc.player.displayClientMessage(Component.literal("배틀메이지 무기가 감지되었습니다."),false);
                        client.data.classType = ClassCategory.BATTLE_MAGE;
                        client.settings.save();
                    }

                    //mc.player.displayClientMessage(text, false);

                }
            }
        }

        fixupSkullProfile(stack);
        ResourceLocation resourceLocation = stack.get(DataComponents.ITEM_MODEL);
        if (resourceLocation != null) {
            ItemModel var10000;

            if ((cetype = CEData.getType(stack)) != null) {
                switch (cetype) {
                    case COMMON ->
                            var10000 = modelGetter.apply(ResourceLocation.fromNamespaceAndPath(DungeonHelper.MODID, "common_book"));
                    case UNCOMMON ->
                            var10000 = modelGetter.apply(ResourceLocation.fromNamespaceAndPath(DungeonHelper.MODID, "uncommon_book"));
                    case RARE ->
                            var10000 = modelGetter.apply(ResourceLocation.fromNamespaceAndPath(DungeonHelper.MODID, "rare_book"));
                    case EPIC ->
                            var10000 = modelGetter.apply(ResourceLocation.fromNamespaceAndPath(DungeonHelper.MODID, "epic_book"));
                    case LEGENDARY ->
                            var10000 = modelGetter.apply(ResourceLocation.fromNamespaceAndPath(DungeonHelper.MODID, "legendary_book"));
                    case MYTHIC ->
                            var10000 = modelGetter.apply(ResourceLocation.fromNamespaceAndPath(DungeonHelper.MODID, "mythic_book"));
                    case REMOVED ->
                            var10000 = modelGetter.apply(ResourceLocation.fromNamespaceAndPath(DungeonHelper.MODID, "removed_book"));
                    default -> var10000 = this.modelGetter.apply(resourceLocation);
                }
            } else if ((rftype = RFData.getType(stack)) != null) {
                if (!DungeonHelperClient.getInstance().data.toggleRuneArrowEmpty) {
                    switch (rftype) {
                        case UNCOMMON ->
                            var10000 = modelGetter.apply(ResourceLocation.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_uncommon"));
                        case RARE ->
                                var10000 = modelGetter.apply(ResourceLocation.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_rare"));
                        case EPIC ->
                                var10000 = modelGetter.apply(ResourceLocation.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_epic"));
                        case LEGENDARY ->
                                var10000 = modelGetter.apply(ResourceLocation.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_legendary"));
                        case MYTHIC ->
                                var10000 = modelGetter.apply(ResourceLocation.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_mythic"));
                        default -> var10000 = this.modelGetter.apply(resourceLocation);
                    }
                } else {
                    switch (rftype) {
                        case COMMON ->
                                var10000 = modelGetter.apply(ResourceLocation.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_common_e"));
                        case UNCOMMON ->
                                var10000 = modelGetter.apply(ResourceLocation.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_uncommon_e"));
                        case RARE ->
                                var10000 = modelGetter.apply(ResourceLocation.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_rare_e"));
                        case EPIC ->
                                var10000 = modelGetter.apply(ResourceLocation.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_epic_e"));
                        case LEGENDARY ->
                                var10000 = modelGetter.apply(ResourceLocation.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_legendary_e"));
                        case MYTHIC ->
                                var10000 = modelGetter.apply(ResourceLocation.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_mythic_e"));
                        default -> var10000 = this.modelGetter.apply(resourceLocation);
                    }
                }
            } else if (FishItems.getFishItem(stack) != null) {
                var10000 = modelGetter.apply(new ItemStack(FishItems.getFishItem(stack), stack.getCount()).get(DataComponents.ITEM_MODEL));
            } else {
                return;
            }

            ClientLevel var10005;
            if (level instanceof ClientLevel) {
                ClientLevel clientLevel = (ClientLevel) level;
                var10005 = clientLevel;
            } else {
                var10005 = null;
            }

            var10000.update(renderState, stack, (ItemModelResolver) (Object) this, displayContext, var10005, entity, seed);
        }
    }
}