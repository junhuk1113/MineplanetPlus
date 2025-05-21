package net.pmkjun.mineplanetplus.neoforge.dungeonhelper.item;

import net.neoforged.neoforge.registries.DeferredItem;
import net.pmkjun.mineplanetplus.neoforge.dungeonhelper.DungeonHelper;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DungeonItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DungeonHelper.MODID);

    public static final DeferredItem<Item> COMMON_BOOK = ITEMS.register("common_book", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> UNCOMMON_BOOK = ITEMS.register("uncommon_book", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RARE_BOOK = ITEMS.register("rare_book", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> EPIC_BOOK = ITEMS.register("epic_book", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LEGENDARY_BOOK = ITEMS.register("legendary_book", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MYTHIC_BOOK = ITEMS.register("mythic_book", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> REMOVED_BOOK = ITEMS.register("removed_book", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> UNCOMMON_RUNE = ITEMS.register("rune_magic_uncommon", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RARE_RUNE = ITEMS.register("rune_magic_rare", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> EPIC_RUNE = ITEMS.register("rune_magic_epic", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LEGENDARY_RUNE = ITEMS.register("rune_magic_legendary", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MYTHIC_RUNE = ITEMS.register("rune_magic_mythic", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> COMMON_RUNE_E = ITEMS.register("rune_magic_common_e", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> UNCOMMON_RUNE_E = ITEMS.register("rune_magic_uncommon_e", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RARE_RUNE_E = ITEMS.register("rune_magic_rare_e", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> EPIC_RUNE_E = ITEMS.register("rune_magic_epic_e", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LEGENDARY_RUNE_E = ITEMS.register("rune_magic_legendary_e", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MYTHIC_RUNE_E = ITEMS.register("rune_magic_mythic_e", () -> new Item(new Item.Properties()));
}