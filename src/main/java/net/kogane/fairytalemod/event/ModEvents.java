package net.kogane.fairytalemod.event;

import net.kogane.fairytalemod.FairyTaleMod;
import net.kogane.fairytalemod.block.ModBlocks;
import net.kogane.fairytalemod.entity.ModEntities;
import net.kogane.fairytalemod.entity.custom.FancyPigEntity;
import net.kogane.fairytalemod.entity.custom.GemEssenceFairyEntity;
import net.kogane.fairytalemod.entity.custom.GemEssenceTermiteEntity;
import net.kogane.fairytalemod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Mod.EventBusSubscriber(modid = FairyTaleMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        ItemStack mainHandItem = player.getMainHandItem();

        // Check if the item in the player's main hand is the specific item
        if (mainHandItem.getItem() == ModItems.SWEET_BOOSTED_BLADE.get()) {
            player.addEffect(new MobEffectInstance(new MobEffectInstance(MobEffects.MOVEMENT_SPEED)));
        }

        Level level = player.level();
        BlockPos playerPos = player.blockPosition();

        if (!level.isClientSide) { // Ensure this runs only on the server
            AABB boundingBox = new AABB(
                    playerPos.getX() - 50, playerPos.getY() - 50, playerPos.getZ() - 50, // Min corner
                    playerPos.getX() + 50, playerPos.getY() + 50, playerPos.getZ() + 50  // Max corner
            );

            // Get all item entities within the bounding box (50 block radius around the player)
            for (ItemEntity itemEntity : level.getEntitiesOfClass(ItemEntity.class, boundingBox)) {
                if (itemEntity.isAlive()) {
                    BlockPos pos = itemEntity.blockPosition();
                    BlockState state = level.getBlockState(pos);

                    // Check if the item is inside your custom liquid block
                    if (state.getBlock() == ModBlocks.GEM_ESSENCE_BLOCK.get()) {
                        smeltItem(level, itemEntity);
                    }
                    if (state.getBlock() == ModBlocks.GEM_ESSENCE_BLOCK.get() && itemEntity.getItem().getItem() == Items.WATER_BUCKET) {
                        player.level().rainLevel = 50.0f;
                    }
                    if (state.getBlock() == ModBlocks.GEM_ESSENCE_BLOCK.get() && itemEntity.getItem().getItem() == ModItems.FAIRY_GEM.get()) {
                        level.addParticle(ParticleTypes.BUBBLE, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), 0, 0, 0);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onItemUse(PlayerInteractEvent.RightClickBlock event) {
        LivingEntity entity = event.getEntity();
        Player player = (Player) entity;
        Level level = event.getLevel();
        ItemStack itemStack = event.getItemStack();

        if (itemStack.getItem() == ModItems.SWEET_BOOSTED_BLADE.get()) {
            player.addEffect(new MobEffectInstance(new MobEffectInstance(MobEffects.JUMP, 20, 5)));
        }
        if (itemStack.getItem() == Items.WATER_BUCKET && event.getLevel().getBlockState(event.getPos()).getBlock() == ModBlocks.GEM_ESSENCE_BLOCK.get()) {
            itemStack.shrink(1);
            player.addItem(new ItemStack(ModItems.GEM_ESSENCE_BUCKET.get()));
        }
    }

    @SubscribeEvent
    public static void onItemUseEmpty(PlayerInteractEvent.RightClickItem event) {
        LivingEntity entity = event.getEntity();
        Player player = (Player) entity;
        Level level = event.getLevel();
        Item item = event.getItemStack().getItem();

        if (item == ModItems.GEM_ESSENCE_EXTRACT_BOTTLE.get()) {
            event.getItemStack().shrink(1);

            for (int i = 0; i < player.getInventory().items.size(); i++) {
                ItemStack stack = player.getInventory().items.get(i);

                if (stack.getItem() == Items.RAW_IRON) {
                    int stackSize = stack.getCount();
                    stack.shrink(stackSize);

                    ItemStack ingot = new ItemStack(Items.IRON_INGOT, stackSize);
                    if (!player.getInventory().add(ingot)) {
                        player.drop(ingot, false); // Drop if inventory is full
                    }
                }
                if (stack.getItem() == Items.RAW_COPPER) {
                    int stackSize = stack.getCount();
                    stack.shrink(stackSize);

                    ItemStack ingot = new ItemStack(Items.COPPER_INGOT, stackSize);
                    if (!player.getInventory().add(ingot)) {
                        player.drop(ingot, false); // Drop if inventory is full
                    }
                }
                if (stack.getItem() == Items.RAW_GOLD) {
                    int stackSize = stack.getCount();
                    stack.shrink(stackSize);

                    ItemStack ingot = new ItemStack(Items.GOLD_INGOT, stackSize);
                    if (!player.getInventory().add(ingot)) {
                        player.drop(ingot, false); // Drop if inventory is full
                    }
                }
            }

            player.addItem(new ItemStack(Items.GLASS_BOTTLE));
        }
    }

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.FANCY_PIG.get(), FancyPigEntity.createAttributes().build());
        event.put(ModEntities.GEM_ESSENCE_FAIRY.get(), GemEssenceFairyEntity.createAttributes().build());
        event.put(ModEntities.GEM_ESSENCE_TERMITE.get(), GemEssenceTermiteEntity.createAttributes().build());
    }

    private static void smeltItem(Level level, ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();

        // Define smelting logic
        Map<Item, Item> smeltingMap = new HashMap<>();
        smeltingMap.put(Items.RAW_IRON, Items.IRON_INGOT);
        smeltingMap.put(Items.RAW_GOLD, Items.GOLD_INGOT);
        smeltingMap.put(Items.RAW_COPPER, Items.COPPER_INGOT);

        if (smeltingMap.containsKey(stack.getItem())) {
            ItemStack smeltedStack = new ItemStack(smeltingMap.get(stack.getItem()), stack.getCount());

            // Replace with smelted item
            itemEntity.setItem(smeltedStack);

            // Optional: Play sound & particles
            level.playSound(null, itemEntity.blockPosition(), SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.addParticle(ParticleTypes.FLAME, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), 0, 0.1, 0);
        }
    }
}

