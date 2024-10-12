package net.pmkjun.mineplanetplus.forge.dungeonhelper.item;

import net.pmkjun.mineplanetplus.forge.dungeonhelper.DungeonHelper;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DungeonItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DungeonHelper.MODID);

    public static final RegistryObject<Item> COMMON_BOOK = ITEMS.register("common_book", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> UNCOMMON_BOOK = ITEMS.register("uncommon_book", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RARE_BOOK = ITEMS.register("rare_book", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EPIC_BOOK = ITEMS.register("epic_book", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LEGENDARY_BOOK = ITEMS.register("legendary_book", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MYTHIC_BOOK = ITEMS.register("mythic_book", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> UNCOMMON_RUNE = ITEMS.register("rune_magic_uncommon", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RARE_RUNE = ITEMS.register("rune_magic_rare", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EPIC_RUNE = ITEMS.register("rune_magic_epic", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LEGENDARY_RUNE = ITEMS.register("rune_magic_legendary", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MYTHIC_RUNE = ITEMS.register("rune_magic_mythic", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> UNCOMMON_RUNE_E = ITEMS.register("rune_magic_uncommon_e", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RARE_RUNE_E = ITEMS.register("rune_magic_rare_e", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EPIC_RUNE_E = ITEMS.register("rune_magic_epic_e", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LEGENDARY_RUNE_E = ITEMS.register("rune_magic_legendary_e", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MYTHIC_RUNE_E = ITEMS.register("rune_magic_mythic_e", () -> new Item(new Item.Properties()));
}
