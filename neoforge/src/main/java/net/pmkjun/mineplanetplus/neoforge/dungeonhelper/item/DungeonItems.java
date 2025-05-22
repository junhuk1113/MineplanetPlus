package net.pmkjun.mineplanetplus.neoforge.dungeonhelper.item;

import net.neoforged.neoforge.registries.DeferredItem;
import net.pmkjun.mineplanetplus.neoforge.dungeonhelper.DungeonHelper;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DungeonItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DungeonHelper.MODID);

    public static final DeferredItem<Item> COMMON_BOOK = ITEMS.registerItem("common_book",Item::new, new Item.Properties());
    public static final DeferredItem<Item> UNCOMMON_BOOK = ITEMS.registerItem("uncommon_book", Item::new, new Item.Properties());
    public static final DeferredItem<Item> RARE_BOOK = ITEMS.registerItem("rare_book", Item::new, new Item.Properties());
    public static final DeferredItem<Item> EPIC_BOOK = ITEMS.registerItem("epic_book", Item::new, new Item.Properties());
    public static final DeferredItem<Item> LEGENDARY_BOOK = ITEMS.registerItem("legendary_book", Item::new, new Item.Properties());
    public static final DeferredItem<Item> MYTHIC_BOOK = ITEMS.registerItem("mythic_book", Item::new, new Item.Properties());
    public static final DeferredItem<Item> REMOVED_BOOK = ITEMS.registerItem("removed_book", Item::new, new Item.Properties());

    public static final DeferredItem<Item> UNCOMMON_RUNE = ITEMS.registerItem("rune_magic_uncommon", Item::new, new Item.Properties());
    public static final DeferredItem<Item> RARE_RUNE = ITEMS.registerItem("rune_magic_rare", Item::new, new Item.Properties());
    public static final DeferredItem<Item> EPIC_RUNE = ITEMS.registerItem("rune_magic_epic", Item::new, new Item.Properties());
    public static final DeferredItem<Item> LEGENDARY_RUNE = ITEMS.registerItem("rune_magic_legendary", Item::new, new Item.Properties());
    public static final DeferredItem<Item> MYTHIC_RUNE = ITEMS.registerItem("rune_magic_mythic", Item::new, new Item.Properties());
    public static final DeferredItem<Item> COMMON_RUNE_E = ITEMS.registerItem("rune_magic_common_e", Item::new, new Item.Properties());
    public static final DeferredItem<Item> UNCOMMON_RUNE_E = ITEMS.registerItem("rune_magic_uncommon_e", Item::new, new Item.Properties());
    public static final DeferredItem<Item> RARE_RUNE_E = ITEMS.registerItem("rune_magic_rare_e", Item::new, new Item.Properties());
    public static final DeferredItem<Item> EPIC_RUNE_E = ITEMS.registerItem("rune_magic_epic_e", Item::new, new Item.Properties());
    public static final DeferredItem<Item> LEGENDARY_RUNE_E = ITEMS.registerItem("rune_magic_legendary_e", Item::new, new Item.Properties());
    public static final DeferredItem<Item> MYTHIC_RUNE_E = ITEMS.registerItem("rune_magic_mythic_e", Item::new, new Item.Properties());
}