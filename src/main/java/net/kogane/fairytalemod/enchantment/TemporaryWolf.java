package net.kogane.fairytalemod.enchantment;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;

public class TemporaryWolf extends Wolf {
    private int ticksAlive = 0;
    private final int despawnTime = 100; // 100 ticks = 5 seconds

    public TemporaryWolf(EntityType<? extends Wolf> entityType, ServerLevel level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        ticksAlive++;

        // Despawn the wolf after 100 ticks
        if (ticksAlive >= despawnTime) {
            this.discard(); // Remove the wolf from the world
        }
    }
}

