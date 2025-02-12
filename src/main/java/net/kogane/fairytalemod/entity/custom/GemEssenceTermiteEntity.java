package net.kogane.fairytalemod.entity.custom;

import net.kogane.fairytalemod.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class GemEssenceTermiteEntity extends Animal {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public GemEssenceTermiteEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.00));
        this.goalSelector.addGoal(1, new BreakBlockGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0) // Example health value
                .add(Attributes.MOVEMENT_SPEED, 0.25) // Example speed value
                .add(Attributes.FOLLOW_RANGE, 100.0); // Example follow range value
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    protected void updateWalkAnimation(float v) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(v * 6.0F, 1.0F);
        } else {
            f = 0.0F;
        }

        this.walkAnimation.update(f, 0.2F);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.GEM_ESSENCE_TERMITE.get().create(pLevel);
    }

    private class BreakBlockGoal extends Goal {
        private final PathfinderMob entity; // Change LivingEntity to PathfinderMob
        private BlockPos targetBlock;

        public BreakBlockGoal(PathfinderMob entity) { // Change constructor parameter
            this.entity = entity;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.TARGET));
        }

        @Override
        public boolean canUse() {
            targetBlock = findCoalBlockNearby();
            return targetBlock != null;
        }

        @Override
        public boolean canContinueToUse() {
            return this.entity.getNavigation() != null && !this.entity.getNavigation().isDone();
        }

        @Override
        public void start() {
            if (targetBlock != null) {
                this.entity.getNavigation().moveTo(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ(), 1.0);
            }
        }

        @Override
        public void stop() {
            this.entity.getNavigation().stop();
        }

        @Override
        public void tick() {
            if (targetBlock != null) {
                if (this.entity.distanceToSqr(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ()) < 2.0) {
                    BlockState blockState = this.entity.level().getBlockState(targetBlock);
                    if (blockState.getBlock() == Blocks.COAL_ORE) {
                        this.entity.level().destroyBlock(targetBlock, true);
                    }
                }
            }
        }

        private BlockPos findCoalBlockNearby() {
            for (int x = -5; x <= 5; x++) {
                for (int y = -5; y <= 5; y++) {
                    for (int z = -5; z <= 5; z++) {
                        BlockPos pos = new BlockPos(
                                (int) entity.getX() + x,
                                (int) entity.getY() + y,
                                (int) entity.getZ() + z
                        );
                        if (entity.level().getBlockState(pos).getBlock() == Blocks.COAL_ORE) {
                            return pos;
                        }
                    }
                }
            }
            return null;
        }
    }

}
