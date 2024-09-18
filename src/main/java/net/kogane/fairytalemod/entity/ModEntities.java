package net.kogane.fairytalemod.entity;

import net.kogane.fairytalemod.FairyTaleMod;
import net.kogane.fairytalemod.entity.custom.FancyPigEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FairyTaleMod.MOD_ID);

    public static final RegistryObject<EntityType<FancyPigEntity>> FANCY_PIG =
            ENTITY_TYPES.register("fancy_pig", () -> EntityType.Builder.of(FancyPigEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F) .build("fancy_pig"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}

