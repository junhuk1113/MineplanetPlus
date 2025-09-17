package net.pmkjun.mineplanetplus.neoforge.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.dungeonhelper.util.*;
import net.pmkjun.mineplanetplus.fishhelper.ApiRequestManager;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.util.ConvertActivateTime;
import net.pmkjun.mineplanetplus.fishhelper.util.ConvertCooldown;
import net.pmkjun.mineplanetplus.fishhelper.util.FishingRod;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.pmkjun.mineplanetplus.fishhelper.util.ShareMode;
import net.pmkjun.mineplanetplus.neoforge.fishhelper.item.FishItems;
import net.pmkjun.mineplanetplus.serverutility.ServerUtilityClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.pmkjun.mineplanetplus.neoforge.dungeonhelper.item.DungeonItems;

import java.util.List;
import java.util.NoSuchElementException;
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
    private final ServerUtilityClient serverutility = ServerUtilityClient.getInstance();
    private ItemStack previousMainhandStack;

    @Inject(method = {"appendItemLayers"}, at = {@At("RETURN")})
    public void appendItemLayers(ItemStackRenderState renderState, ItemStack stack, ItemDisplayContext displayContext, Level level, LivingEntity entity, int seed, CallbackInfo ci) {
        if (serverutility.isHereMineplanet()) {
            CustomEnchantType cetype;
            RuneofFortuneType rftype;
            Item changed_item;
            List<Component> ItemText;
            String Itemname = null;
            String levelString, rangeString;
            int levelInt, rangeInt;
            double secondDouble;
            long secondLong;

            ItemText = stack.getTooltipLines(Item.TooltipContext.EMPTY, mc.player, TooltipFlag.NORMAL);
            try {
                boolean isActivate;
                if (ItemText.getFirst().getString().contains("신비한 옹달샘")) {
                    if (ItemText.getLast().getString().contains("활성화"))
                        isActivate = true;
                    else
                        isActivate = false;

                    if (isActivate != fishhelper.data.isMythicalWaterActive) {
                        fishhelper.data.isMythicalWaterActive = isActivate;
                        fishhelper.configManage.save();
                        //mc.player.displayClientMessage(Component.literal("신비한 옹달샘 : " + isActivate), false);
                        if(fishhelper.data.toggleShareTotemData == ShareMode.ON)
                            ApiRequestManager.updateTotemSkill();
                    }
                } else if (ItemText.getFirst().getString().contains("숙련된 낚시꾼")) {
                    if (ItemText.getLast().getString().contains("활성화"))
                        isActivate = true;
                    else
                        isActivate = false;

                    if (isActivate != fishhelper.data.isExpBoosterActive) {
                        fishhelper.data.isExpBoosterActive = isActivate;
                        fishhelper.configManage.save();
                        //mc.player.displayClientMessage(Component.literal("숙련된 낚시꾼 : " + isActivate), false);
                        if(fishhelper.data.toggleShareTotemData == ShareMode.ON)
                            ApiRequestManager.updateTotemSkill();
                    }
                } else if (ItemText.getFirst().getString().contains("오버 핫스팟")) {
                    if (ItemText.getLast().getString().contains("활성화"))
                        isActivate = true;
                    else
                        isActivate = false;

                    if (isActivate != fishhelper.data.isOverHotspotActive) {
                        fishhelper.data.isOverHotspotActive = isActivate;
                        fishhelper.configManage.save();
                        //mc.player.displayClientMessage(Component.literal("오버 핫스팟 : " + isActivate), false);
                        if(fishhelper.data.toggleShareTotemData == ShareMode.ON)
                            ApiRequestManager.updateTotemSkill();
                    }
                } else if (ItemText.getFirst().getString().contains("트레져 헌터")) {
                    if (ItemText.getLast().getString().contains("활성화"))
                        isActivate = true;
                    else
                        isActivate = false;

                    if (isActivate != fishhelper.data.isTreasureHunterActive) {
                        fishhelper.data.isTreasureHunterActive = isActivate;
                        fishhelper.configManage.save();
                        //mc.player.displayClientMessage(Component.literal("트레져 헌터 : " + isActivate), false);
                        if(fishhelper.data.toggleShareTotemData == ShareMode.ON)
                            ApiRequestManager.updateTotemSkill();
                    }
                } else if (ItemText.getFirst().getString().contains("엔트로피 호더")) {
                    if (ItemText.getLast().getString().contains("활성화"))
                        isActivate = true;
                    else
                        isActivate = false;

                    if (isActivate != fishhelper.data.isEntropyHoarder) {
                        fishhelper.data.isEntropyHoarder = isActivate;
                        fishhelper.configManage.save();
                        //mc.player.displayClientMessage(Component.literal("엔트로피 호더 : " + isActivate), false);
                        if(fishhelper.data.toggleShareTotemData == ShareMode.ON)
                            ApiRequestManager.updateTotemSkill();
                    }
                }
            }
            catch (NoSuchElementException e){
                //mc.player.displayClientMessage(Component.literal("그런 요소는 읎단다 예외 발생!"),false);
            }
            for (Component text : ItemText) {
                if (Itemname == null)
                    Itemname = text.getString();

                if (!Itemname.equals("지속시간 업그레이드") && !Itemname.equals("쿨타임 감소")
                        && !Itemname.contains("토템 리더 |") && !Itemname.equals("범위 업그레이드")
                        && !Itemname.contains("콤보 캐쳐 |") && !Itemname.contains("패시브 슬롯 확장")
                        && !Itemname.contains("부족의 외침 |"))
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
                if (text.getString().contains("현재 범위 ➛ ")){
                    if (Itemname.equals("범위 업그레이드")){
                        rangeString = text.getString().replace("현재 범위 ➛ ", "");
                        rangeString = rangeString.replace("블록", "");

                        rangeInt = Integer.parseInt(rangeString);
                        fishhelper.setTotemRange(rangeInt);
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

                if (Itemname.contains("콤보 캐쳐 |")) {
                    if(text.getString().contains("최대 콤보|")) {
                        levelString = text.getString().replace("최대 콤보| ", "");
                        levelString = levelString.replace(".0", "");
                        levelInt = Integer.parseInt(levelString);
                        //System.out.println(levelString);
                        fishhelper.comboCatcher.setMaxComboCount(levelInt);
                    }
                }

                if(Itemname.contains("부족의 외침 |")){
                    if(text.getString().contains("효과| ")){
                        levelString = text.getString().replace("효과| ", "").replace("% 공유","");
                        if(!levelString.equals(fishhelper.data.shareTotemPercentage)){
                            fishhelper.data.shareTotemPercentage = levelString;
                            fishhelper.configManage.save();
                            //System.out.println("부족의 외침 효과 \""+fishhelper.data.shareTotemPercentage+"\"");
                        }
                    }
                }

                if(Itemname.contains("패시브 슬롯 확장")){
                    if(text.getString().contains("사용중인 패시브 슬롯|")){
                        String slotString;
                        slotString = text.getString().replace("사용중인 패시브 슬롯| (","");
                        slotString = slotString.replace(") 슬롯","");
                        if(!slotString.equals(fishhelper.data.totemSlotString)){
                            fishhelper.data.totemSlotString = slotString;
                            fishhelper.configManage.save();
                            //mc.player.displayClientMessage(Component.literal("토템 슬롯 데이터 저장됨 : " + slotString),false);
                        }
                    }

                }

            }

            ItemStack mainhandStack = mc.player.getMainHandItem();
            if (mainhandStack != previousMainhandStack) {
                previousMainhandStack = mainhandStack;
                //mc.player.sendMessage(Text.literal("MainhandStack 변경됨 : "+mainhandStack.getItem().getTranslationKey()));

                if (mainhandStack.getItem().getDescriptionId().equals("item.minecraft.fishing_rod")) {
                    FishingRod.updateSpec(mainhandStack);
                    fishhelper.setHoldingFishingRod(true);
                }
                else{
                    fishhelper.setHoldingFishingRod(false);
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
                        //System.out.println("커인 북 : null pointer exception");
                    }
                    switch (cetype) {
                        case COMMON ->
                                var10000 = modelGetter.apply(new ItemStack(DungeonItems.COMMON_BOOK.asItem(), stack.getCount()).get(DataComponents.ITEM_MODEL));
                        case UNCOMMON ->
                                var10000 = modelGetter.apply(new ItemStack(DungeonItems.UNCOMMON_BOOK.asItem(), stack.getCount()).get(DataComponents.ITEM_MODEL));
                        case RARE ->
                                var10000 = modelGetter.apply(new ItemStack(DungeonItems.RARE_BOOK.asItem(), stack.getCount()).get(DataComponents.ITEM_MODEL));
                        case EPIC ->
                                var10000 = modelGetter.apply(new ItemStack(DungeonItems.EPIC_BOOK.asItem(), stack.getCount()).get(DataComponents.ITEM_MODEL));
                        case LEGENDARY ->
                                var10000 = modelGetter.apply(new ItemStack(DungeonItems.LEGENDARY_BOOK.asItem(), stack.getCount()).get(DataComponents.ITEM_MODEL));
                        case MYTHIC ->
                                var10000 = modelGetter.apply(new ItemStack(DungeonItems.MYTHIC_BOOK.asItem(), stack.getCount()).get(DataComponents.ITEM_MODEL));
                        case REMOVED ->
                                var10000 = modelGetter.apply(new ItemStack(DungeonItems.REMOVED_BOOK.asItem(), stack.getCount()).get(DataComponents.ITEM_MODEL));
                        default -> var10000 = this.modelGetter.apply(resourceLocation);
                    }
                } else if ((rftype = RFData.getType(stack)) != null) {
                    try {
                        float customModelData = stack.get(DataComponents.CUSTOM_MODEL_DATA).getFloat(0);
                        if (customModelData == 2658.0f || customModelData == 2293.0f) return; //만료된 아이템은 렌더하지 않음
                    } catch (NullPointerException e) {
                        //System.out.println("행운의 룬 : null pointer exception");
                    }
                    if (!DungeonHelperClient.getInstance().data.toggleRuneArrowEmpty) {
                        switch (rftype) {
                            case UNCOMMON ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.UNCOMMON_RUNE.asItem(), stack.getCount()).get(DataComponents.ITEM_MODEL));
                            case RARE ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.RARE_RUNE.asItem(), stack.getCount()).get(DataComponents.ITEM_MODEL));
                            case EPIC ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.EPIC_RUNE.asItem(), stack.getCount()).get(DataComponents.ITEM_MODEL));
                            case LEGENDARY ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.LEGENDARY_RUNE.asItem(), stack.getCount()).get(DataComponents.ITEM_MODEL));
                            case MYTHIC ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.MYTHIC_RUNE.asItem(), stack.getCount()).get(DataComponents.ITEM_MODEL));
                            default -> var10000 = this.modelGetter.apply(resourceLocation);
                        }
                    } else {
                        switch (rftype) {
                            case COMMON ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.COMMON_RUNE_E.asItem(), stack.getCount()).get(DataComponents.ITEM_MODEL));
                            case UNCOMMON ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.UNCOMMON_RUNE_E.asItem(), stack.getCount()).get(DataComponents.ITEM_MODEL));
                            case RARE ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.RARE_RUNE_E.asItem(), stack.getCount()).get(DataComponents.ITEM_MODEL));
                            case EPIC ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.EPIC_RUNE_E.asItem(), stack.getCount()).get(DataComponents.ITEM_MODEL));
                            case LEGENDARY ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.LEGENDARY_RUNE_E.asItem(), stack.getCount()).get(DataComponents.ITEM_MODEL));
                            case MYTHIC ->
                                    var10000 = modelGetter.apply(new ItemStack(DungeonItems.MYTHIC_RUNE_E.asItem(), stack.getCount()).get(DataComponents.ITEM_MODEL));
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