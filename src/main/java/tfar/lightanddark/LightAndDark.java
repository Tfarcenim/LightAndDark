package tfar.lightanddark;

import com.mojang.logging.LogUtils;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

//todo:Do not modify BiomeColors or Rubidium/Optifine will fuck your shit up

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
        if (FMLEnvironment.dist.isClient()) {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(LightAndDarkClient::blockColors);
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        GameEvents.setupEvents();
    }


    private void clientSetup(FMLClientSetupEvent e) {
        MinecraftForge.EVENT_BUS.addListener(LightAndDarkClient::fogColor);
        MinecraftForge.EVENT_BUS.addListener(LightAndDarkClient::renderFog);
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
