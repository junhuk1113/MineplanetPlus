package net.pmkjun.mineplanetplus.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(FishingHook.class)
public abstract class FishingMixin {
    //private static final Logger LOGGER = LogManager.getLogger("FishingMixin");
    FishHelperClient client = FishHelperClient.getInstance();

    @Shadow
    private boolean biting;


    @Shadow @Nullable public abstract Player getPlayerOwner();

    @Shadow public abstract boolean canChangeDimensions();

    @Inject(method = "onClientRemoval", at = @At("RETURN"))
    private void onRemovedMixin(CallbackInfo ci) {
        String bobberOwner;
        try{
            bobberOwner = getPlayerOwner().getName().getString();
        }
        catch (NullPointerException e){
            //System.out.println("null1!");
            return;
        }

        //LOGGER.info("Fishing bobber entity removed."+caughtFish+" "+bobberOwner);

        if(biting && bobberOwner.equals(FishHelperClient.getInstance().getUsername()) && client.data.isTotemCooldown){
            //LOGGER.info("fish caught!"+FishHelperClient.getInstance().getUsername());
            client.data.lastTotemCooldownTime -= client.data.valueCooldownReduction;
            client.configManage.save();
        }
    }
}