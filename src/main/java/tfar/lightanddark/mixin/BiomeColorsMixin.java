package tfar.lightanddark.mixin;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.lightanddark.LightAndDark;

@Mixin(BiomeColors.class)
public class BiomeColorsMixin {

    @Inject(method = "getAverageColor",at = @At(("HEAD")),cancellable = true)
    private static void changeColor(BlockAndTintGetter pLevel, BlockPos pBlockPos, ColorResolver pColorResolver, CallbackInfoReturnable<Integer> cir) {
        if (!LightAndDark.light(pBlockPos)) {
            cir.setReturnValue(LightAndDark.GRAY);
        }
    }
}
