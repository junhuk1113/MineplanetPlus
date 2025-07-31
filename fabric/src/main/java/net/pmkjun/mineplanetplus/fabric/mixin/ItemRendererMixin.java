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
import net.pmkjun.mineplanetplus.fabric.dungeonhelper.item.DungeonItems;
import net.pmkjun.mineplanetplus.fabric.fishhelper.item.FishItems;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.util.ConvertActivateTime;
import net.pmkjun.mineplanetplus.fishhelper.util.ConvertCooldown;
import net.pmkjun.mineplanetplus.fishhelper.util.FishingRod;
import net.pmkjun.mineplanetplus.serverutility.ServerUtilityClient;
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
    private final FishHelperClient fishhelper = FishHelperClient.getInstance();
    private ItemStack previousMainhandStack;

    @Inject(method = {"appendItemLayers"}, at = {@At("RETURN")})
    public void appendItemLayers(ItemStackRenderState renderState, ItemStack stack, ItemDisplayContext displayContext, Level level, LivingEntity entity, int seed, CallbackInfo ci) {
        ServerUtilityClient serverutility = ServerUtilityClient.getInstance();

        if (serverutility.isHereMineplanet()) {

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
                ItemModel extraItemModel = null;

                if ((cetype = CEData.getType(stack)) != null) {
                    try {
                        float customModelData = stack.get(DataComponents.CUSTOM_MODEL_DATA).getFloat(0);
                        if (customModelData == 2513.0f) return; //칭호 렌더링 하지 않음
                        if (customModelData >= 2370 && customModelData <= 2375) return; //만료된 커인 북 렌더링 하지 않음
                    } catch (NullPointerException e) {
                    }
                    switch (cetype) {
                        case COMMON ->
                                var10000 = modelGetter.apply(new ItemStack(DungeonItems.COMMON_BOOK, stack.getCount()).get(DataComponents.ITEM_MODEL));
                        case UNCOMMON ->
                                var10000 = modelGetter.apply(new ItemStack(DungeonItems.UNCOMMON_BOOK, stack.getCount()).get(DataComponents.ITEM_MODEL));
                        case RARE ->
                                var10000 = modelGetter.apply(new ItemStack(DungeonItems.RARE_BOOK, stack.getCount()).get(DataComponents.ITEM_MODEL));
                        case EPIC ->
                                var10000 = modelGetter.apply(new ItemStack(DungeonItems.EPIC_BOOK, stack.getCount()).get(DataComponents.ITEM_MODEL));
                        case LEGENDARY ->
                                var10000 = modelGetter.apply(new ItemStack(DungeonItems.LEGENDARY_BOOK, stack.getCount()).get(DataComponents.ITEM_MODEL));
                        case MYTHIC ->
                                var10000 = modelGetter.apply(new ItemStack(DungeonItems.MYTHIC_BOOK, stack.getCount()).get(DataComponents.ITEM_MODEL));
                        case REMOVED ->
                                var10000 = modelGetter.apply(new ItemStack(DungeonItems.REMOVED_BOOK, stack.getCount()).get(DataComponents.ITEM_MODEL));
                        default -> var10000 = this.modelGetter.apply(resourceLocation);
                    }
                } else if ((rftype = RFData.getType(stack)) != null) {
                    try {
                        float customModelData = stack.get(DataComponents.CUSTOM_MODEL_DATA).getFloat(0);
                        if (customModelData == 2658.0f || customModelData == 2293.0f) return; //만료된 아이템은 렌더하지 않음
                    } catch (NullPointerException e) {
                    }

                    if (!DungeonHelperClient.getInstance().data.toggleRuneArrowEmpty) {
                        switch (rftype) {
                            case UNCOMMON ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.UNCOMMON_RUNE, stack.getCount()).get(DataComponents.ITEM_MODEL));
                            case RARE ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.RARE_RUNE, stack.getCount()).get(DataComponents.ITEM_MODEL));
                            case EPIC ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.EPIC_RUNE, stack.getCount()).get(DataComponents.ITEM_MODEL));
                            case LEGENDARY ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.LEGENDARY_RUNE, stack.getCount()).get(DataComponents.ITEM_MODEL));
                            case MYTHIC ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.MYTHIC_RUNE, stack.getCount()).get(DataComponents.ITEM_MODEL));
                            default -> var10000 = this.modelGetter.apply(resourceLocation);
                        }
                    } else {
                        switch (rftype) {
                            case COMMON ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.COMMON_RUNE_E, stack.getCount()).get(DataComponents.ITEM_MODEL));
                            case UNCOMMON ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.UNCOMMON_RUNE_E, stack.getCount()).get(DataComponents.ITEM_MODEL));
                            case RARE ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.RARE_RUNE_E, stack.getCount()).get(DataComponents.ITEM_MODEL));
                            case EPIC ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.EPIC_RUNE_E, stack.getCount()).get(DataComponents.ITEM_MODEL));
                            case LEGENDARY ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.LEGENDARY_RUNE_E, stack.getCount()).get(DataComponents.ITEM_MODEL));
                            case MYTHIC ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.MYTHIC_RUNE_E, stack.getCount()).get(DataComponents.ITEM_MODEL));
                            default -> var10000 = this.modelGetter.apply(resourceLocation);
                        }
                    }
                } else if (FishItems.getFishItem(stack) != null) {
                    var10000 = modelGetter.apply(new ItemStack(FishItems.getFishItem(stack), stack.getCount()).get(DataComponents.ITEM_MODEL));
                    if(FishItems.getQuestItem(stack, fishhelper) != null){
                        extraItemModel = modelGetter.apply(new ItemStack(FishItems.getQuestItem(stack, fishhelper), stack.getCount()).get(DataComponents.ITEM_MODEL));
                    }
                }

                else if(fishhelper.deliveryQuests.findMatchQuestTooltip(stack.getTooltipLines(Item.TooltipContext.EMPTY, mc.player, TooltipFlag.NORMAL))!=-1){
                    var10000 = modelGetter.apply(new ItemStack(FishItems.getQuestItem(), stack.getCount()).get(DataComponents.ITEM_MODEL));
                }

                else {
                    return;
                }

                ClientLevel var10005;
                if (level instanceof ClientLevel clientLevel) {
                    var10005 = clientLevel;
                } else {
                    var10005 = null;
                }

                var10000.update(renderState, stack, (ItemModelResolver) (Object) this, displayContext, var10005, entity, seed);
                if(extraItemModel != null) {
                    extraItemModel.update(renderState, stack, (ItemModelResolver) (Object) this, displayContext, var10005, entity, seed);
                }
            }
        }
    }
}