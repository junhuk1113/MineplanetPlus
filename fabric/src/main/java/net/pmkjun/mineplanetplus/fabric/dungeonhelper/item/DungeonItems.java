package net.pmkjun.mineplanetplus.fabric.dungeonhelper.item;

import net.pmkjun.mineplanetplus.fabric.dungeonhelper.DungeonHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class DungeonItems {
    public static final Item COMMON_BOOK = new Item(new Item.Properties());
    public static final Item UNCOMMON_BOOK = new Item(new Item.Properties());
    public static final Item RARE_BOOK = new Item(new Item.Properties());
    public static final Item EPIC_BOOK = new Item(new Item.Properties());
    public static final Item LEGENDARY_BOOK = new Item(new Item.Properties());
    public static final Item MYTHIC_BOOK = new Item(new Item.Properties());
    public static final Item REMOVED_BOOK = new Item(new Item.Properties());
    public static final Item COMMON_RUNE_E = new Item(new Item.Properties());
    public static final Item UNCOMMON_RUNE = new Item(new Item.Properties());
    public static final Item RARE_RUNE = new Item(new Item.Properties());
    public static final Item EPIC_RUNE = new Item(new Item.Properties());
    public static final Item LEGENDARY_RUNE = new Item(new Item.Properties());
    public static final Item MYTHIC_RUNE = new Item(new Item.Properties());
    public static final Item UNCOMMON_RUNE_E = new Item(new Item.Properties());
    public static final Item RARE_RUNE_E = new Item(new Item.Properties());
    public static final Item EPIC_RUNE_E = new Item(new Item.Properties());
    public static final Item LEGENDARY_RUNE_E = new Item(new Item.Properties());
    public static final Item MYTHIC_RUNE_E = new Item(new Item.Properties());

    public static void register() {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DungeonHelper.MODID, "common_book"), COMMON_BOOK);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DungeonHelper.MODID, "uncommon_book"), UNCOMMON_BOOK);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DungeonHelper.MODID, "rare_book"), RARE_BOOK);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DungeonHelper.MODID, "epic_book"), EPIC_BOOK);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DungeonHelper.MODID, "legendary_book"), LEGENDARY_BOOK);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DungeonHelper.MODID, "mythic_book"), MYTHIC_BOOK);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DungeonHelper.MODID, "removed_book"), REMOVED_BOOK);

        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DungeonHelper.MODID, "rune_magic_uncommon"), UNCOMMON_RUNE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DungeonHelper.MODID, "rune_magic_rare"), RARE_RUNE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DungeonHelper.MODID, "rune_magic_epic"), EPIC_RUNE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DungeonHelper.MODID, "rune_magic_legendary"), LEGENDARY_RUNE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DungeonHelper.MODID, "rune_magic_mythic"), MYTHIC_RUNE);

        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DungeonHelper.MODID, "rune_magic_common_e"), COMMON_RUNE_E);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DungeonHelper.MODID, "rune_magic_uncommon_e"), UNCOMMON_RUNE_E);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DungeonHelper.MODID, "rune_magic_rare_e"), RARE_RUNE_E);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DungeonHelper.MODID, "rune_magic_epic_e"), EPIC_RUNE_E);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DungeonHelper.MODID, "rune_magic_legendary_e"), LEGENDARY_RUNE_E);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(DungeonHelper.MODID, "rune_magic_mythic_e"), MYTHIC_RUNE_E);
    }
}