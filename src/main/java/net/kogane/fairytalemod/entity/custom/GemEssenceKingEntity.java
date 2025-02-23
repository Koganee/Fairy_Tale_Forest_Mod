package net.kogane.fairytalemod.entity.custom;

import net.kogane.fairytalemod.entity.ModEntities;
import net.kogane.fairytalemod.item.ModItems;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.Vec3;


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
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.00));
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
                .add(Attributes.MAX_HEALTH, 500.0)
                .add(Attributes.MOVEMENT_SPEED, 0.45)
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.ATTACK_DAMAGE, 6.5)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.25);
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

        // Ensure the mob always has a target
        Player nearestPlayer = this.level().getNearestPlayer(this, 10.0D);
        if (nearestPlayer != null) {
            this.setTarget(nearestPlayer);
        }

        this.fallDistance = 0.0F;
    }


    @Override
    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);  // Calls the default death behavior

        // Drop a diamond block when the entity dies
        if (!this.level().isClientSide) {
            this.spawnAtLocation(ModItems.GEM_ESSENCE_CORE.get());  // Drop the diamond block
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity entity = source.getDirectEntity();
        if (entity instanceof Projectile) {
            this.playSound(SoundEvents.DECORATED_POT_BREAK, 1.0F, 1.0F);
            return false;
        } else return super.hurt(source, amount);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        this.level().broadcastEntityEvent(this, (byte) 4);
        this.playSound(SoundEvents.EVOKER_FANGS_ATTACK, 1.0F, 1.0F);
        return super.doHurtTarget(entity);
    }

    private class JumpCrushGoal extends Goal {
        private final PathfinderMob entity;
        private Player targetPlayer;
        private int cooldownTicks = 0; // Tracks cooldown between attacks (ticks)
        private boolean isJumping = false;

        public JumpCrushGoal(PathfinderMob entity) {
            this.entity = entity;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (cooldownTicks > 0) {
                cooldownTicks--; // Reduce cooldown each tick
                return false; // Cannot attack yet
            }

            // Find the nearest player within 10 blocks
            this.targetPlayer = this.entity.level().getNearestPlayer(this.entity, 10.0D);
            return this.targetPlayer != null;
        }

        @Override
        public void start() {
            if (targetPlayer != null) {
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
                if (distance < 4.5D) {
                    isJumping = true; // Mark as jumping
                    // Calculate jump direction
                    Vec3 direction = new Vec3(
                            targetPlayer.getX() - this.entity.getX(),
                            0,
                            targetPlayer.getZ() - this.entity.getZ()
                    ).normalize();

                    // Jump forward towards the player
                    this.entity.setDeltaMovement(direction.x * 0.5, 0.9, direction.z * 0.5);

                    // Attack after jumping
                    this.entity.doHurtTarget(targetPlayer);

                    // Play jump sound
                    this.entity.playSound(SoundEvents.SLIME_JUMP, 1.0F, 1.0F);

                    // Set cooldown (100 ticks = 5 seconds)
                    cooldownTicks = 80;
                }

                // Detect landing and trigger explosion
                if (isJumping && this.entity.onGround()) {
                    isJumping = false;
                    this.entity.level().explode(this.entity, this.entity.getX(), this.entity.getY(), this.entity.getZ(), 2.0f, Level.ExplosionInteraction.BLOCK);
                }
            }
        }
    }
}



