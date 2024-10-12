package net.pmkjun.mineplanetplus.forge.mixin;

import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.forge.dungeonhelper.item.DungeonItems;
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

        BakedModel bakedmodel;
        CustomEnchantType cetype;
        RuneofFortuneType rftype;
        if (stack.is(Items.TRIDENT)) {
            bakedmodel = this.itemModelShaper.getModelManager().getModel(ItemRenderer.TRIDENT_IN_HAND_MODEL);
        } else if (stack.is(Items.SPYGLASS)) {
            bakedmodel = this.itemModelShaper.getModelManager().getModel(ItemRenderer.SPYGLASS_IN_HAND_MODEL);
        } else if ((cetype = CEData.getType(stack)) != null) {
            // Minecraft.getInstance().player.displayClientMessage(Component.literal("ITEM CODE: " + Item.getId(stack.getItem())), false);
            switch(cetype) {
                case COMMON -> bakedmodel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.COMMON_BOOK.get(), stack.getCount()));
                case UNCOMMON -> bakedmodel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.UNCOMMON_BOOK.get(), stack.getCount()));
                case RARE -> bakedmodel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.RARE_BOOK.get(), stack.getCount()));
                case EPIC -> bakedmodel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.EPIC_BOOK.get(), stack.getCount()));
                case LEGENDARY -> bakedmodel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.LEGENDARY_BOOK.get(), stack.getCount()));
                case MYTHIC -> bakedmodel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.MYTHIC_BOOK.get(), stack.getCount()));
                default -> bakedmodel = this.itemModelShaper.getItemModel(stack);
            }
        } else if((rftype = RFData.getType(stack)) != null) {
            if(!DungeonHelperClient.getInstance().data.toggleRuneArrowEmpty) {
                switch (rftype) {
                    case UNCOMMON -> bakedmodel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.UNCOMMON_RUNE.get(), stack.getCount()));
                    case RARE -> bakedmodel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.RARE_RUNE.get(), stack.getCount()));
                    case EPIC -> bakedmodel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.EPIC_RUNE.get(), stack.getCount()));
                    case LEGENDARY -> bakedmodel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.LEGENDARY_RUNE.get(), stack.getCount()));
                    case MYTHIC -> bakedmodel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.MYTHIC_RUNE.get(), stack.getCount()));
                    default -> bakedmodel = this.itemModelShaper.getItemModel(stack);
                }
            }
            else{
                switch (rftype) {
                    case UNCOMMON -> bakedmodel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.UNCOMMON_RUNE_E.get(), stack.getCount()));
                    case RARE -> bakedmodel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.RARE_RUNE_E.get(), stack.getCount()));
                    case EPIC -> bakedmodel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.EPIC_RUNE_E.get(), stack.getCount()));
                    case LEGENDARY -> bakedmodel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.LEGENDARY_RUNE_E.get(), stack.getCount()));
                    case MYTHIC -> bakedmodel = this.itemModelShaper.getItemModel(new ItemStack(DungeonItems.MYTHIC_RUNE_E.get(), stack.getCount()));
                    default -> bakedmodel = this.itemModelShaper.getItemModel(stack);
                }
            }
        } else {
            bakedmodel = this.itemModelShaper.getItemModel(stack);
        }

        ClientLevel clientlevel = level instanceof ClientLevel ? (ClientLevel)level : null;
        BakedModel bakedmodel1 = bakedmodel.getOverrides().resolve(bakedmodel, stack, clientlevel, entity, seed);

        cir.setReturnValue(bakedmodel1 == null ? this.itemModelShaper.getModelManager().getMissingModel() : bakedmodel1);
    }
}