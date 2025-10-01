package net.pmkjun.mineplanetplus.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class McMixin {

    @Inject(method = "startUseItem", at = @At(value = "HEAD"))
    private void startUseItem(CallbackInfo ci){
        Minecraft mc = Minecraft.getInstance();
        FishHelperClient client = FishHelperClient.getInstance();
        //mc.player.displayClientMessage(Component.literal("우클릭 감지!"),false);

        Level level = mc.level;
        HitResult hit = mc.hitResult;

        if (hit != null && hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hit;

            if (level != null && level.getBlockState(blockHit.getBlockPos()).getBlock() == Blocks.OBSERVER) {

                if (mc.player != null && mc.player.getMainHandItem().getItem() == Items.FISHING_ROD) {
                    //mc.player.displayClientMessage(Component.literal("관측기 블록을 우클릭했습니다!"),false);
                    //mc.player.displayClientMessage(Component.literal(blockHit.getBlockPos().toShortString()),false);

                    client.setTotempos(blockHit.getBlockPos().getX(), blockHit.getBlockPos().getZ());
                }

                // ci.cancel();
            }
        }
    }
}
