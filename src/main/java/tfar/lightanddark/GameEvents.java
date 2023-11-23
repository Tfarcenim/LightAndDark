package tfar.lightanddark;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.Event;

public class GameEvents {

    public static void setupEvents() {
        MinecraftForge.EVENT_BUS.addListener(GameEvents::canSpawn);
    }

    public static void canSpawn(MobSpawnEvent.SpawnPlacementCheck e) {
        ServerLevelAccessor serverLevelAccessor = e.getLevel();
        if (LightAndDark.enabled(null)) {
            if (serverLevelAccessor instanceof ServerLevel serverLevel) {
                if (LightAndDark.light(e.getPos()) && serverLevel.dimension() == Level.OVERWORLD && e.getEntityType().getCategory() == MobCategory.MONSTER) {
                    //no hostile mobs
                    e.setResult(Event.Result.DENY);
                }
            } else if (serverLevelAccessor instanceof WorldGenRegion worldGenRegion) {
                ServerLevel serverLevel = worldGenRegion.getLevel();
                if (LightAndDark.light(e.getPos()) && serverLevel.dimension() == Level.OVERWORLD && e.getEntityType().getCategory() == MobCategory.MONSTER) {
                    //no hostile mobs
                    e.setResult(Event.Result.DENY);
                }
            }
        }
    }
}
