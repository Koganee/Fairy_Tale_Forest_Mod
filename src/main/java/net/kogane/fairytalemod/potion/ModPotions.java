package net.kogane.fairytalemod.potion;

import net.kogane.fairytalemod.FairyTaleMod;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(ForgeRegistries.POTIONS, FairyTaleMod.MOD_ID);

    public static final RegistryObject<Potion> CHOCOLATE_ELIXIR = POTIONS.register("chocolate_elixir",
            () -> new Potion(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 2)));

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
