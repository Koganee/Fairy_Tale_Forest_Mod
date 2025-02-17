package net.kogane.fairytalemod.entity.custom;

import net.kogane.fairytalemod.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class GemEssenceKingEntity extends PathfinderMob {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public GemEssenceKingEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 4.00f));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.00));
        this.goalSelector.addGoal(1, new JumpCrushGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return 4.0D;
            }
        });
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.ATTACK_DAMAGE, 8.5)
                .add(Attributes.KNOCKBACK_RESISTANCE, 2.5);
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
        // Ensure the mob targets the player correctly
        if (this.getTarget() == null || !(this.getTarget() instanceof Player)) {
            this.setTarget(this.level().getNearestPlayer(this, 10.0D));  // Ensure it targets the player
        }
    }


    @Override
    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }


    private class JumpCrushGoal extends Goal {
        private final PathfinderMob entity;
        private Player targetPlayer;

        public JumpCrushGoal(PathfinderMob entity) {
            this.entity = entity;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            // Find the nearest player within 10 blocks
            this.targetPlayer = this.entity.level().getNearestPlayer(this.entity, 10.0D);
            return this.targetPlayer != null;
        }

        @Override
        public void start() {
            if (targetPlayer != null) {
                // Make the entity look at the player
                this.entity.getLookControl().setLookAt(targetPlayer, 30.0F, 30.0F);
            }
        }

        @Override
        public void tick() {
            if (targetPlayer != null) {
                double distance = this.entity.distanceTo(targetPlayer);

                // Move towards the player
                this.entity.getNavigation().moveTo(targetPlayer, 1.6D);

                // If close enough, jump and "crush" the player
                if (distance < 4.5D) { // Adjust attack range as needed
                    this.entity.setDeltaMovement(0, 0.9, 0); // Simulate a jump
                    this.entity.doHurtTarget(targetPlayer); // Attack the player
                }
            }
        }
    }
}



