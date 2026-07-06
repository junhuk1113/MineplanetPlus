package net.pmkjun.mineplanetplus.fishhelper.item;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier; // ResourceLocation ➔ Identifier로 변경
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;

import java.util.Arrays;

public class FishItems {
    public static final ResourceKey<Item>[] COMMON_FISH_KEY = new ResourceKey[FishItemList.COMMMON_FISH_LIST.length];

    public static final Item[] COMMON_FISH = new Item[FishItemList.COMMMON_FISH_LIST.length];
    public static final Item[] UNCOMMON_FISH = new Item[FishItemList.UNCOMMON_FISH_LIST.length];
    public static final Item[] RARE_FISH = new Item[FishItemList.RARE_FISH_LIST.length];
    public static final Item[] EPIC_FISH = new Item[FishItemList.EPIC_FISH_LIST.length];
    public static final Item[] LEGENDARY_FISH = new Item[FishItemList.LEGENDARY_FISH_LIST.length];
    public static final Item[] MYTHIC_FISH = new Item[FishItemList.MYTHIC_FISH_LIST.length];

    // ResourceLocation.fromNamespaceAndPath -> Identifier.fromNamespaceAndPath 로 변경
    public static final ResourceKey<Item> QUEST_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("pyrofishinghelper", "quest"));
    public static final ResourceKey<Item> QUEST_NEED_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("pyrofishinghelper", "quest_need"));

    public static final Item QUEST = registerItem(QUEST_KEY, new Item(new Item.Properties().setId(QUEST_KEY)));
    public static final Item QUEST_NEED = registerItem(QUEST_NEED_KEY, new Item(new Item.Properties().setId(QUEST_NEED_KEY)));

    private static Item registerItem(ResourceKey<Item> key, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    public static void register() {
        int i;
        for (i = 0; i < COMMON_FISH.length; i++) {
            COMMON_FISH_KEY[i] = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("pyrofishinghelper", "common_" + i));
            COMMON_FISH[i] = new Item(new Item.Properties().setId(COMMON_FISH_KEY[i]));
            registerItem(COMMON_FISH_KEY[i], COMMON_FISH[i]);
        }
        for (i = 0; i < UNCOMMON_FISH.length; i++) {
            ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("pyrofishinghelper", "uncommon_" + i));
            UNCOMMON_FISH[i] = new Item(new Item.Properties().setId(key));
            registerItem(key, UNCOMMON_FISH[i]);
        }
        for (i = 0; i < RARE_FISH.length; i++) {
            ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("pyrofishinghelper", "rare_" + i));
            RARE_FISH[i] = new Item(new Item.Properties().setId(key));
            registerItem(key, RARE_FISH[i]);
        }
        for (i = 0; i < EPIC_FISH.length; i++) {
            ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("pyrofishinghelper", "epic_" + i));
            EPIC_FISH[i] = new Item(new Item.Properties().setId(key));
            registerItem(key, EPIC_FISH[i]);
        }

        for (i = 0; i < LEGENDARY_FISH.length; i++) {
            ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("pyrofishinghelper", "legendary_" + i));
            LEGENDARY_FISH[i] = new Item(new Item.Properties().setId(key));
            registerItem(key, LEGENDARY_FISH[i]);
        }
        for (i = 0; i < MYTHIC_FISH.length; i++) {
            ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("pyrofishinghelper", "mythic_" + i));
            MYTHIC_FISH[i] = new Item(new Item.Properties().setId(key));
            registerItem(key, MYTHIC_FISH[i]);
        }
    }

    public static Item getFishItem(ItemStack itemStack) {
        String name = itemStack.getHoverName().getString();
        int index;

        if(!(itemStack.getItem().toString().equals("minecraft:cod"))) return null;
        if(!FishHelperClient.getInstance().data.toggleCustomTexture) return null;

        index = Arrays.stream(FishItemList.COMMMON_FISH_LIST).toList().indexOf(name);
        if(index != -1) return COMMON_FISH[index];

        index = Arrays.stream(FishItemList.UNCOMMON_FISH_LIST).toList().indexOf(name);
        if(index != -1) return UNCOMMON_FISH[index];

        index = Arrays.stream(FishItemList.RARE_FISH_LIST).toList().indexOf(name);
        if(index != -1) return RARE_FISH[index];

        index = Arrays.stream(FishItemList.EPIC_FISH_LIST).toList().indexOf(name);
        if(index != -1) return EPIC_FISH[index];

        index = Arrays.stream(FishItemList.LEGENDARY_FISH_LIST).toList().indexOf(name);
        if(index != -1) return LEGENDARY_FISH[index];

        index = Arrays.stream(FishItemList.MYTHIC_FISH_LIST).toList().indexOf(name);
        if(index != -1) return MYTHIC_FISH[index];

        return null;
    }

    public static Item getQuestItem(ItemStack itemStack, FishHelperClient client) {
        String name = itemStack.getHoverName().getString();

        if(!(itemStack.getItem().toString().equals("minecraft:cod"))) return null;
        if(!FishHelperClient.getInstance().data.toggleCustomTexture) return null;

        if (client.deliveryQuests.isFishExistQuestData(name)) {
            if(client.deliveryQuests.hasEnoughFishToDeliver(name))
                return QUEST.asItem();
            else
                return QUEST_NEED.asItem();
        }

        return null;
    }

    public static Item getQuestItem() {
        return QUEST;
    }
}