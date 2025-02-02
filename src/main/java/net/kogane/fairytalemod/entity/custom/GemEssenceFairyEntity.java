package net.kogane.fairytalemod.entity.custom;

import net.kogane.fairytalemod.entity.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class GemEssenceFairyEntity extends Animal {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    private final TamableAnimal tamable;
    private final RangedAttackMob rangedAttackMob;

    public GemEssenceFairyEntity(EntityType<? extends Animal> pEntityType, Level pLevel, TamableAnimal tamable, RangedAttackMob rangedAttackMob) {
        super(pEntityType, pLevel);
        this.tamable = tamable;
        this.rangedAttackMob = rangedAttackMob;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.00));

        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 4.00f));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(0, new FollowOwnerGoal(tamable, 2, 0, 100, true));
        this.goalSelector.addGoal(1, new RangedAttackGoal(rangedAttackMob, 1.5, 3, 10));
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
        return ModEntities.FANCY_PIG.get().create(pLevel);
    }
}
