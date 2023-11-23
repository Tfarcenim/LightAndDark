package tfar.lightanddark.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.lightanddark.LightAndDark;
import tfar.lightanddark.LightAndDarkClient;

import java.util.function.Supplier;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin extends Level{

    protected ClientLevelMixin(WritableLevelData pLevelData, ResourceKey<Level> pDimension, RegistryAccess pRegistryAccess, Holder<DimensionType> pDimensionTypeRegistration, Supplier<ProfilerFiller> pProfiler, boolean pIsClientSide, boolean pIsDebug, long pBiomeZoomSeed, int pMaxChainedNeighborUpdates) {
        super(pLevelData, pDimension, pRegistryAccess, pDimensionTypeRegistration, pProfiler, pIsClientSide, pIsDebug, pBiomeZoomSeed, pMaxChainedNeighborUpdates);
    }

    @Inject(method = "getSkyColor",at = @At("RETURN"),cancellable = true)
    private void modifyColor(Vec3 pPos, float pPartialTick, CallbackInfoReturnable<Vec3> cir) {
        if (this.dimension() == Level.OVERWORLD && pPos.x < 0) {
            cir.setReturnValue(LightAndDarkClient.modifySkyColor(cir.getReturnValue()));
        }
    }

    @Inject(method = "getBlockTint",at = @At("HEAD"),cancellable = true)
    private void modifyTints(BlockPos pBlockPos, ColorResolver pColorResolver, CallbackInfoReturnable<Integer> cir) {
        if (this.dimension() == Level.OVERWORLD && !LightAndDark.light(pBlockPos)) {
            cir.setReturnValue(LightAndDark.GRAY);
        }
    }

    @Inject(method = "doAnimateTick",at = @At(value = "INVOKE",target = "Lnet/minecraft/core/Holder;value()Ljava/lang/Object;"))
    private void addAshParticles(int pPosX, int pPosY, int pPosZ, int pRange, RandomSource pRandom, Block pBlock, BlockPos.MutableBlockPos pBlockPos, CallbackInfo ci) {
        LightAndDarkClient.addAshParticles((ClientLevel) (Object)this,pBlockPos);
    }
}
