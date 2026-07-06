package net.pmkjun.mineplanetplus.dungeonhelper.item;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier; // ResourceLocation ➔ Identifier 변경
import net.minecraft.world.item.Item;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelper;

public class DungeonItems{
    public static final ResourceKey<Item> COMMON_BOOK_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DungeonHelper.MODID, "common_book"));
    public static final ResourceKey<Item> UNCOMMON_BOOK_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DungeonHelper.MODID, "uncommon_book"));
    public static final ResourceKey<Item> RARE_BOOK_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DungeonHelper.MODID, "rare_book"));
    public static final ResourceKey<Item> EPIC_BOOK_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DungeonHelper.MODID, "epic_book"));
    public static final ResourceKey<Item> LEGENDARY_BOOK_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DungeonHelper.MODID, "legendary_book"));
    public static final ResourceKey<Item> MYTHIC_BOOK_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DungeonHelper.MODID, "mythic_book"));
    public static final ResourceKey<Item> REMOVED_BOOK_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DungeonHelper.MODID, "removed_book"));

    public static final ResourceKey<Item> UNCOMMON_RUNE_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_uncommon"));
    public static final ResourceKey<Item> RARE_RUNE_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_rare"));
    public static final ResourceKey<Item> EPIC_RUNE_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_epic"));
    public static final ResourceKey<Item> LEGENDARY_RUNE_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_legendary"));
    public static final ResourceKey<Item> MYTHIC_RUNE_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_mythic"));
    public static final ResourceKey<Item> COMMON_RUNE_E_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_common_e"));
    public static final ResourceKey<Item> UNCOMMON_RUNE_E_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_uncommon_e"));
    public static final ResourceKey<Item> RARE_RUNE_E_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_rare_e"));
    public static final ResourceKey<Item> EPIC_RUNE_E_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_epic_e"));
    public static final ResourceKey<Item> LEGENDARY_RUNE_E_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_legendary_e"));
    public static final ResourceKey<Item> MYTHIC_RUNE_E_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DungeonHelper.MODID, "rune_magic_mythic_e"));

    public static final Item COMMON_BOOK = registerItem(COMMON_BOOK_KEY, new Item(new Item.Properties().useItemDescriptionPrefix().setId(COMMON_BOOK_KEY)));
    public static final Item UNCOMMON_BOOK = registerItem(UNCOMMON_BOOK_KEY, new Item(new Item.Properties().useItemDescriptionPrefix().setId(UNCOMMON_BOOK_KEY)));
    public static final Item RARE_BOOK = registerItem(RARE_BOOK_KEY, new Item(new Item.Properties().useItemDescriptionPrefix().setId(RARE_BOOK_KEY)));
    public static final Item EPIC_BOOK = registerItem(EPIC_BOOK_KEY, new Item(new Item.Properties().useItemDescriptionPrefix().setId(EPIC_BOOK_KEY)));
    public static final Item LEGENDARY_BOOK = registerItem(LEGENDARY_BOOK_KEY, new Item(new Item.Properties().useItemDescriptionPrefix().setId(LEGENDARY_BOOK_KEY)));
    public static final Item MYTHIC_BOOK = registerItem(MYTHIC_BOOK_KEY, new Item(new Item.Properties().useItemDescriptionPrefix().setId(MYTHIC_BOOK_KEY)));
    public static final Item REMOVED_BOOK = registerItem(REMOVED_BOOK_KEY, new Item(new Item.Properties().useItemDescriptionPrefix().setId(REMOVED_BOOK_KEY)));

    public static final Item UNCOMMON_RUNE = registerItem(UNCOMMON_RUNE_KEY, new Item(new Item.Properties().useItemDescriptionPrefix().setId(UNCOMMON_RUNE_KEY)));
    public static final Item RARE_RUNE = registerItem(RARE_RUNE_KEY, new Item(new Item.Properties().useItemDescriptionPrefix().setId(RARE_RUNE_KEY)));
    public static final Item EPIC_RUNE = registerItem(EPIC_RUNE_KEY, new Item(new Item.Properties().useItemDescriptionPrefix().setId(EPIC_RUNE_KEY)));
    public static final Item LEGENDARY_RUNE = registerItem(LEGENDARY_RUNE_KEY, new Item(new Item.Properties().useItemDescriptionPrefix().setId(LEGENDARY_RUNE_KEY)));
    public static final Item MYTHIC_RUNE = registerItem(MYTHIC_RUNE_KEY, new Item(new Item.Properties().useItemDescriptionPrefix().setId(MYTHIC_RUNE_KEY)));
    public static final Item COMMON_RUNE_E = registerItem(COMMON_RUNE_E_KEY, new Item(new Item.Properties().useItemDescriptionPrefix().setId(COMMON_RUNE_E_KEY)));
    public static final Item UNCOMMON_RUNE_E = registerItem(UNCOMMON_RUNE_E_KEY, new Item(new Item.Properties().useItemDescriptionPrefix().setId(UNCOMMON_RUNE_E_KEY)));
    public static final Item RARE_RUNE_E = registerItem(RARE_RUNE_E_KEY, new Item(new Item.Properties().useItemDescriptionPrefix().setId(RARE_RUNE_E_KEY)));
    public static final Item EPIC_RUNE_E = registerItem(EPIC_RUNE_E_KEY, new Item(new Item.Properties().useItemDescriptionPrefix().setId(EPIC_RUNE_E_KEY)));
    public static final Item LEGENDARY_RUNE_E = registerItem(LEGENDARY_RUNE_E_KEY, new Item(new Item.Properties().useItemDescriptionPrefix().setId(LEGENDARY_RUNE_E_KEY)));
    public static final Item MYTHIC_RUNE_E = registerItem(MYTHIC_RUNE_E_KEY, new Item(new Item.Properties().useItemDescriptionPrefix().setId(MYTHIC_RUNE_E_KEY)));

    private static Item registerItem(ResourceKey<Item> key, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    public static void register(){
    }
}