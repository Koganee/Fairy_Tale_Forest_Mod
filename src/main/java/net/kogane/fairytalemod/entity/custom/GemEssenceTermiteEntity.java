package net.kogane.fairytalemod.entity.custom;

import net.kogane.fairytalemod.entity.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class GemEssenceTermiteEntity extends Animal{
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;


    public GemEssenceTermiteEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.00));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0) // Example health value
                .add(Attributes.MOVEMENT_SPEED, 0.05) // Example speed value
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
    public @Nullable AgeableMob getBreedOffspring (ServerLevel pLevel, AgeableMob pOtherParent){
        return ModEntities.GEM_ESSENCE_TERMITE.get().create(pLevel);
    }
}
