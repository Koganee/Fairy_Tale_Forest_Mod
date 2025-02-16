package net.kogane.fairytalemod.entity;

import net.kogane.fairytalemod.FairyTaleMod;
import net.kogane.fairytalemod.entity.custom.FancyPigEntity;
import net.kogane.fairytalemod.entity.custom.GemEssenceFairyEntity;
import net.kogane.fairytalemod.entity.custom.GemEssenceKingEntity;
import net.kogane.fairytalemod.entity.custom.GemEssenceTermiteEntity;
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
                    .sized(0.6F, 1.95F).build("fancy_pig"));
    public static final RegistryObject<EntityType<GemEssenceFairyEntity>> GEM_ESSENCE_FAIRY =
            ENTITY_TYPES.register("gem_essence_fairy", () -> EntityType.Builder.of(GemEssenceFairyEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F).build("gem_essence_fairy"));
    public static final RegistryObject<EntityType<GemEssenceTermiteEntity>> GEM_ESSENCE_TERMITE =
            ENTITY_TYPES.register("gem_essence_termite", () -> EntityType.Builder.of(GemEssenceTermiteEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 0.95F).build("gem_essence_termite"));
    public static final RegistryObject<EntityType<GemEssenceKingEntity>> GEM_ESSENCE_KING =
            ENTITY_TYPES.register("gem_essence_king", () -> EntityType.Builder.of(GemEssenceKingEntity::new, MobCategory.MONSTER)
                    .sized(2.5F, 2.5F).build("gem_essence_king"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}