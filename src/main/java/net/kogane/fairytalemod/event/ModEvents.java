package net.kogane.fairytalemod.event;

import net.kogane.fairytalemod.FairyTaleMod;
import net.kogane.fairytalemod.block.ModBlocks;
import net.kogane.fairytalemod.entity.ModEntities;
import net.kogane.fairytalemod.entity.custom.FancyPigEntity;
import net.kogane.fairytalemod.entity.custom.GemEssenceFairyEntity;
import net.kogane.fairytalemod.entity.custom.GemEssenceTermiteEntity;
import net.kogane.fairytalemod.item.ModItems;
import net.kogane.fairytalemod.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
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
        Level level = player.level();

        // Check if the item in the player's main hand is the specific item
        if (mainHandItem.getItem() == ModItems.SWEET_BOOSTED_BLADE.get()) {
            player.addEffect(new MobEffectInstance(new MobEffectInstance(MobEffects.MOVEMENT_SPEED)));
        }

        boolean touchingWall = isPlayerAgainstWall(level, player);

        if (touchingWall && player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.GEM_ESSENCE_SYMBIOTE.get()) {
            // Apply upward motion to simulate climbing
            player.setDeltaMovement(player.getDeltaMovement().x, 0.2, player.getDeltaMovement().z);
        }

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

                        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
                            spawnFoundParticles(serverLevel, pos);
                        }
                    }
                }
            }
        }

        if(player.fallDistance > 5.0f) {
            player.level().explode(player, playerPos.getX(), playerPos.getY(), playerPos.getZ(), 1.5f, Level.ExplosionInteraction.NONE);
            if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
                spawnFoundParticles(serverLevel, playerPos);
            }
        }
    }

    private static void spawnFoundParticles(ServerLevel level, BlockPos positionClicked) {
        for (int i = 0; i < 20; i++) {
            level.sendParticles(ModParticles.GEM_ESSENCE_BUBBLE_PARTICLES.get(),
                    positionClicked.getX() + 0.5d, positionClicked.getY() + 1, positionClicked.getZ() + 0.5d, 1,
                    Math.cos(i * 18) * 0.15d, 0.15d, Math.sin(i * 18) * 0.15d, 0.1);
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
        if (itemStack.getItem() == Items.BUCKET && event.getLevel().getBlockState(event.getPos()).getBlock() == ModBlocks.GEM_ESSENCE_BLOCK.get()) {
            itemStack.shrink(1);
            player.addItem(new ItemStack(ModItems.GEM_ESSENCE_BUCKET.get()));
        }
    }

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickEmpty event) {
        Player player = event.getEntity();
        Level level = player.level();

        double reachDistance = 30.0; // Increase if you want longer range
        HitResult result = player.pick(reachDistance, 0.0F, false);

        if (result.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) result;
            BlockPos blockPos = blockHit.getBlockPos();
            Vec3 targetPos = new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5);

            // Start dragging the player towards the target position
            if(player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.GEM_ESSENCE_SYMBIOTE.get())
            {

                dragPlayer(player, targetPos);

                if (level.isClientSide) {
                    renderStickBeam(level, player, targetPos);
                }
            }
        }
    }

    // Function to smoothly drag the player
    private static void dragPlayer(Player player, Vec3 targetPos) {
        Vec3 playerPos = player.position();
        Vec3 direction = targetPos.subtract(playerPos).normalize(); // Direction vector
        double speed = 1.5; // Adjust this to change drag speed

        // Apply movement
        player.setDeltaMovement(direction.scale(speed));
    }
    private static void renderStickBeam(Level level, Player player, Vec3 targetPos) {
        if (level.isClientSide) { // Make sure it runs only on the client
            Vec3 start = player.getEyePosition(); // Start from player's eyes
            Vec3 direction = targetPos.subtract(start).normalize(); // Direction of the stick
            double distance = start.distanceTo(targetPos);
            int steps = (int) (distance * 5); // More steps = smoother line

            for (int i = 0; i < steps; i++) {
                Vec3 stepPos = start.add(direction.scale(i * 0.2)); // Position along the "stick"
                level.addParticle(ParticleTypes.CRIT, stepPos.x, stepPos.y, stepPos.z, 0, 0, 0);
            }
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

    private static boolean isPlayerAgainstWall(Level level, Player player) {
        BlockPos playerPos = player.blockPosition();
        return level.getBlockState(playerPos.north()).isSolid() ||
                level.getBlockState(playerPos.south()).isSolid() ||
                level.getBlockState(playerPos.east()).isSolid() ||
                level.getBlockState(playerPos.west()).isSolid();
    }
}

