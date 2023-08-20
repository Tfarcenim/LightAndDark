package tfar.lightanddark;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import tfar.lightanddark.mixin.BiomeColorsMixin;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LightAndDark.MODID)
public class LightAndDark {
    public static final String MODID = "lightanddark";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

   // public static final GameRules.Key<GameRules.BooleanValue> RULE_LIGHTANDDARK = GameRules.register(MODID, GameRules.Category.UPDATES, GameRules.BooleanValue.create(false));


    public LightAndDark() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        GameEvents.setupEvents();
    }

    private void clientSetup(FMLClientSetupEvent e) {
    }

    public static boolean enabled(ServerLevel level) {
        return true;//level.getGameRules().getBoolean(LightAndDark.RULE_LIGHTANDDARK);
    }

    public static boolean light(Vec3 pos) {
        return pos.x > 0;
    }

    public static boolean light(Vec3i pos) {
        return pos.getX() > 0;
    }

    public static final int GRAY = 0x777777;
}
