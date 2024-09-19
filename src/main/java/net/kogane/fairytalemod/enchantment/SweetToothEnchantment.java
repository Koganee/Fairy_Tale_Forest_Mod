package net.kogane.fairytalemod.enchantment;

import net.kogane.fairytalemod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SweetToothEnchantment extends Enchantment {
    protected SweetToothEnchantment(Enchantment.Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel)
    {
        if(!pAttacker.level().isClientSide()) {
            ServerLevel level = ((ServerLevel) pAttacker.level());
            BlockPos position = pTarget.blockPosition();

            if(pAttacker.getOffhandItem().getItem() == ModItems.CANDYCANE.get())
            {
                float baseDamage = pAttacker.getMainHandItem().getDamageValue();
                float extraDamage = baseDamage + (pLevel * 10.0F);

                pTarget.hurt(pAttacker.damageSources().magic(), extraDamage);
                pAttacker.getOffhandItem().shrink(1);
            }
        }

        super.doPostAttack(pAttacker, pTarget, pLevel);
    }

}
