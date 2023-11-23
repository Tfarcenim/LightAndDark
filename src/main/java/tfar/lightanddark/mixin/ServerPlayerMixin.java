package tfar.lightanddark.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.lightanddark.LightAndDark;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends LivingEntity {
	protected ServerPlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	@Shadow public abstract ServerLevel serverLevel();

	@Inject(at = @At("HEAD"), method = "isPvpAllowed", cancellable = true)
	private void init(CallbackInfoReturnable<Boolean> cir) {
		if (LightAndDark.enabled(serverLevel()) && level().dimension() == Level.OVERWORLD) {
			cir.setReturnValue(!LightAndDark.light(position()));
		}
	}
}
