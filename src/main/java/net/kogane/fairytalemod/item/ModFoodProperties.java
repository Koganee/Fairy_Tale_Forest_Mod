package net.kogane.fairytalemod.item;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties CANDYCANE = new FoodProperties.Builder().nutrition(5).saturationMod(0.25f)
            .effect(() -> new MobEffectInstance(MobEffects.HEAL, 10), 0.1f).build();
    public static final FoodProperties CHOCOLATE_ITEM = new FoodProperties.Builder().nutrition(1).saturationMod(0.25f)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20), 1.0f).build();
}
