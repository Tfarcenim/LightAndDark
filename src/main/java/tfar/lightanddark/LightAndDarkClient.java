package tfar.lightanddark;

import com.mojang.blaze3d.shaders.FogShape;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.ViewportEvent;
import tfar.lightanddark.mixin.RenderChunkRegionMixin;

public class LightAndDarkClient {

    public static void blockColors(RegisterColorHandlersEvent.Block e) {
        e.register((p_92626_, p_92627_, p_92628_, p_92629_) -> p_92627_ != null && p_92628_ != null ? BiomeColors.getAverageFoliageColor(p_92627_, p_92628_) : FoliageColor.getDefaultColor(), Blocks.SPRUCE_LEAVES, Blocks.BIRCH_LEAVES);
    }

    public static void renderFog(ViewportEvent.RenderFog e) {
        Vec3 pos = e.getCamera().getPosition();
        if (e.getCamera().getEntity().level().dimension() == Level.OVERWORLD && !LightAndDark.light(pos)) {
            e.setFarPlaneDistance(e.getFarPlaneDistance()/2);
            e.setCanceled(true);
            e.setNearPlaneDistance(16);
        }
    }

    public static boolean rightDimension(BlockAndTintGetter level) {
        if (level instanceof ClientLevel clientLevel) {
            return clientLevel.dimension() == Level.OVERWORLD;
        } else if (level instanceof RenderChunkRegion renderChunkRegion) {
            ClientLevel clientLevel = (ClientLevel) ((RenderChunkRegionMixin) renderChunkRegion).getLevel();
            return clientLevel.dimension() == Level.OVERWORLD;
        }
        return false;
    }

    public static void fogColor(ViewportEvent.ComputeFogColor e) {
        float r = e.getRed();
        float g = e.getGreen();
        float b = e.getBlue();
        Vec3 pos = e.getCamera().getPosition();
        if (e.getCamera().getEntity().level().dimension() == Level.OVERWORLD && !LightAndDark.light(pos)) {
            float avg = (r + g + b) / 3;
            e.setRed(avg);
            e.setGreen(avg);
            e.setBlue(avg);
        }
    }

    public static Vec3 modifySkyColor(Vec3 original) {
        double avg = (original.x + original.y + original.z) / 3;
        return new Vec3(avg, avg, avg);
    }

    static AmbientParticleSettings ambientParticleSettings = new AmbientParticleSettings(ParticleTypes.WHITE_ASH, 0.118093334F);

    public static void addAshParticles(ClientLevel level, BlockPos pBlockPos) {
        if (!LightAndDark.light(pBlockPos) && level.dimension() == Level.OVERWORLD) {
            if (ambientParticleSettings.canSpawn(level.random)) {
                level.addParticle(ambientParticleSettings.getOptions(), (double) pBlockPos.getX() + level.random.nextDouble(), (double) pBlockPos.getY() + level.random.nextDouble(), (double) pBlockPos.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
