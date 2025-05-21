package net.pmkjun.mineplanetplus.neoforge.fishhelper.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperMod;
import net.pmkjun.mineplanetplus.fishhelper.item.FishItemList;

import java.util.Arrays;

public class FishItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems("pyrofishinghelper");
    public static final DeferredItem<?>[] COMMON_FISH = new DeferredItem<?>[FishItemList.COMMMON_FISH_LIST.length];
    public static final DeferredItem<?>[] UNCOMMON_FISH = new DeferredItem<?>[FishItemList.UNCOMMON_FISH_LIST.length];
    public static final DeferredItem<?>[] RARE_FISH = new DeferredItem<?>[FishItemList.RARE_FISH_LIST.length];
    public static final DeferredItem<?>[] EPIC_FISH = new DeferredItem<?>[FishItemList.EPIC_FISH_LIST.length];
    public static final DeferredItem<?>[] LEGENDARY_FISH = new DeferredItem<?>[FishItemList.LEGENDARY_FISH_LIST.length];
    public static final DeferredItem<?>[] MYTHIC_FISH = new DeferredItem<?>[FishItemList.MYTHIC_FISH_LIST.length];

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