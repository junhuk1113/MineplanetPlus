package net.pmkjun.mineplanetplus.forge.fishhelper.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperMod;
import net.pmkjun.mineplanetplus.fishhelper.item.FishItemList;

import java.util.Arrays;

public class FishItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FishHelperMod.MOD_ID);
    public static final RegistryObject<?>[] COMMON_FISH = new RegistryObject<?>[FishItemList.COMMMON_FISH_LIST.length];
    public static final RegistryObject<?>[] UNCOMMON_FISH = new RegistryObject<?>[FishItemList.UNCOMMON_FISH_LIST.length];
    public static final RegistryObject<?>[] RARE_FISH = new RegistryObject<?>[FishItemList.RARE_FISH_LIST.length];
    public static final RegistryObject<?>[] EPIC_FISH = new RegistryObject<?>[FishItemList.EPIC_FISH_LIST.length];
    public static final RegistryObject<?>[] LEGENDARY_FISH = new RegistryObject<?>[FishItemList.LEGENDARY_FISH_LIST.length];
    public static final RegistryObject<?>[] MYTHIC_FISH = new RegistryObject<?>[FishItemList.MYTHIC_FISH_LIST.length];

    public static void register(){
        int i;
        for(i = 0; i<FishItemList.COMMMON_FISH_LIST.length; i++){
            COMMON_FISH[i] = ITEMS.register("common_"+i, () -> new Item(new Item.Properties()));
        }
        for(i = 0; i<FishItemList.UNCOMMON_FISH_LIST.length; i++){
            UNCOMMON_FISH[i] = ITEMS.register("uncommon_"+i, () -> new Item(new Item.Properties()));
        }
        for(i = 0; i<FishItemList.RARE_FISH_LIST.length; i++){
            RARE_FISH[i] = ITEMS.register("rare_"+i, () -> new Item(new Item.Properties()));
        }
        for(i = 0; i<FishItemList.EPIC_FISH_LIST.length; i++){
            EPIC_FISH[i] = ITEMS.register("epic_"+i, () -> new Item(new Item.Properties()));
        }
        for(i = 0; i<FishItemList.LEGENDARY_FISH_LIST.length; i++){
            LEGENDARY_FISH[i] = ITEMS.register("legendary_"+i, () -> new Item(new Item.Properties()));
        }
        for(i = 0; i<FishItemList.MYTHIC_FISH_LIST.length; i++){
            MYTHIC_FISH[i] = ITEMS.register("mythic_"+i, () -> new Item(new Item.Properties()));
        }

    }
    public static Item getFishItem(ItemStack itemStack){
        String name = itemStack.getHoverName().getString();
        int index;

        if(!(itemStack.getItem().toString().equals("cod"))) return null;
        if(!FishHelperClient.getInstance().data.toggleCustomTexture) return null;

        index = Arrays.stream(FishItemList.COMMMON_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return (Item)COMMON_FISH[index].get();

        index = Arrays.stream(FishItemList.UNCOMMON_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return (Item)UNCOMMON_FISH[index].get();

        index = Arrays.stream(FishItemList.RARE_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return (Item)RARE_FISH[index].get();

        index = Arrays.stream(FishItemList.EPIC_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return (Item)EPIC_FISH[index].get();

        index = Arrays.stream(FishItemList.LEGENDARY_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return (Item)LEGENDARY_FISH[index].get();

        index = Arrays.stream(FishItemList.MYTHIC_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return (Item)MYTHIC_FISH[index].get();

        return null;
    }
}