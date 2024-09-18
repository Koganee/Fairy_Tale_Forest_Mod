package net.kogane.fairytalemod.enchantment;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class WolfsFangEnchantment extends Enchantment {
    protected WolfsFangEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        if(!pAttacker.level().isClientSide()) {
            ServerLevel level = ((ServerLevel) pAttacker.level());
            BlockPos position = pTarget.blockPosition();

            if(pLevel == 1) {
                int random = (int) (Math.random() * 10) + 1;
                if (pAttacker instanceof Player player && random == 5) {
                    // Spawn the wolf
                    TemporaryWolf spawnedWolf = new TemporaryWolf(EntityType.WOLF, level);
                    spawnedWolf.moveTo(position.getX(), position.getY(), position.getZ(), pAttacker.getYRot(), pAttacker.getXRot());
                    level.addFreshEntity(spawnedWolf);

                    // Tame the wolf to the player
                    spawnedWolf.tame(player);  // This sets the player as the wolf's owner
                    spawnedWolf.setOwnerUUID(player.getUUID());  // Set the owner's UUID explicitly
                }
            }
        }

        super.doPostAttack(pAttacker, pTarget, pLevel);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}