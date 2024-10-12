package net.pmkjun.mineplanetplus.fabric.mixin;

import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.fabric.dungeonhelper.item.DungeonItems;
import net.pmkjun.mineplanetplus.dungeonhelper.util.CEData;
import net.pmkjun.mineplanetplus.dungeonhelper.util.CustomEnchantType;
import net.pmkjun.mineplanetplus.dungeonhelper.util.RFData;
import net.pmkjun.mineplanetplus.dungeonhelper.util.RuneofFortuneType;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Shadow
    @Final
    private ItemModelShaper itemModelShaper;

    @Inject(method = {"getModel(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;I)Lnet/minecraft/client/resources/model/BakedModel;"}, at = {@At("HEAD")}, cancellable = true)
    public void getModelMixin(ItemStack stack, Level level, LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir) {
        cir.cancel();

        BakedModel bakedModel;
        CustomEnchantType cetype;
        RuneofFortuneType rftype;
        if (stack.is(Items.TRIDENT)) {
            bakedModel = this.itemModelShaper.getModelManager().getModel(ItemRenderer.TRIDENT_IN_HAND_MODEL);
        } else if (stack.is(Items.SPYGLASS)) {
            bakedModel = this.itemModelShaper.getModelManager().getModel(ItemRenderer.SPYGLASS_IN_HAND_MODEL);
        } else if ((cetype = CEData.getType(stack)) != null) {
            switch(cetype) {
                case COMMON -> bakedModel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.COMMON_BOOK, stack.getCount()));
                case UNCOMMON -> bakedModel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.UNCOMMON_BOOK, stack.getCount()));
                case RARE -> bakedModel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.RARE_BOOK, stack.getCount()));
                case EPIC -> bakedModel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.EPIC_BOOK, stack.getCount()));
                case LEGENDARY -> bakedModel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.LEGENDARY_BOOK, stack.getCount()));
                case MYTHIC -> bakedModel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.MYTHIC_BOOK, stack.getCount()));
                case REMOVED -> bakedModel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.REMOVED_BOOK, stack.getCount()));
                default -> bakedModel = this.itemModelShaper.getItemModel(stack);
            }
        } else if((rftype = RFData.getType(stack)) != null) {
            if(!DungeonHelperClient.getInstance().data.toggleRuneArrowEmpty) {
                switch (rftype) {
                    case UNCOMMON -> bakedModel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.UNCOMMON_RUNE, stack.getCount()));
                    case RARE -> bakedModel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.RARE_RUNE, stack.getCount()));
                    case EPIC -> bakedModel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.EPIC_RUNE, stack.getCount()));
                    case LEGENDARY -> bakedModel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.LEGENDARY_RUNE, stack.getCount()));
                    case MYTHIC -> bakedModel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.MYTHIC_RUNE, stack.getCount()));
                    default -> bakedModel = this.itemModelShaper.getItemModel(stack);
                }
            }
            else{
                switch (rftype) {
                    case COMMON -> bakedModel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.COMMON_RUNE_E, stack.getCount()));
                    case UNCOMMON -> bakedModel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.UNCOMMON_RUNE_E, stack.getCount()));
                    case RARE -> bakedModel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.RARE_RUNE_E, stack.getCount()));
                    case EPIC -> bakedModel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.EPIC_RUNE_E, stack.getCount()));
                    case LEGENDARY -> bakedModel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.LEGENDARY_RUNE_E, stack.getCount()));
                    case MYTHIC -> bakedModel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.MYTHIC_RUNE_E, stack.getCount()));
                    default -> bakedModel = this.itemModelShaper.getItemModel(stack);
                }
            }
        } else {
            bakedModel = this.itemModelShaper.getItemModel(stack);
        }

        ClientLevel clientWorld = level instanceof ClientLevel ? (ClientLevel)level : null;
        BakedModel bakedModel2 = bakedModel.getOverrides().resolve(bakedModel, stack, clientWorld, entity, seed);
        cir.setReturnValue(bakedModel2 == null ? this.itemModelShaper.getModelManager().getMissingModel() : bakedModel2);
    }
}
