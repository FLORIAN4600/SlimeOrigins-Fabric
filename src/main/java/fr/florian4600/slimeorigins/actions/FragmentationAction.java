package fr.florian4600.slimeorigins.actions;

import fr.florian4600.slimeorigins.entity.attribute.SOEntityAttributes;
import fr.florian4600.slimeorigins.util.SOUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import virtuoel.pehkui.api.ScaleTypes;

public class FragmentationAction {

    public static final EntityAttribute ENTITY_SIZE;

    public static void Fragment(LivingEntity entity, float divide_by, boolean inverted) {

        if(entity.getEntityWorld().isClient) return;

        if(divide_by <= 0) divide_by = Float.MIN_NORMAL;

        int flooredSize = (int) Math.floor(entity.getAttributeValue(ENTITY_SIZE));

        if (flooredSize == 0) {
            flooredSize = (int) Math.floor(entity.getAttributeValue(ENTITY_SIZE));
        }

        double health = SOUtils.getAndApplyModifier(entity, EntityAttributes.GENERIC_MAX_HEALTH, divide_by, 0.75d, inverted);
        double speed = SOUtils.getAndApplyModifier(entity, EntityAttributes.GENERIC_MOVEMENT_SPEED, divide_by, 0.56d, !inverted);
        double size = SOUtils.getAndApplyModifier(entity, ENTITY_SIZE, divide_by, 1d, inverted);


        EntityAttributeModifier sizeModifier = new EntityAttributeModifier(
                String.format("FragmentationSize%d", flooredSize),
                size,
                EntityAttributeModifier.Operation.ADDITION
        );

        entity.getAttributeInstance(ENTITY_SIZE).addPersistentModifier(sizeModifier);


        EntityAttributeModifier healthModifier = new EntityAttributeModifier(
                String.format("FragmentationHealthDummy%d", flooredSize),
                health,
                EntityAttributeModifier.Operation.ADDITION
        );

        entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addPersistentModifier(healthModifier);


        EntityAttributeModifier speedModifier = new EntityAttributeModifier(
                String.format("FragmentationSpeedDummy%d", flooredSize),
                speed,
                EntityAttributeModifier.Operation.ADDITION
        );

        entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addPersistentModifier(speedModifier);

        entity.setHealth(entity.getMaxHealth());
        entity.clearStatusEffects();
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 15, 4));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 10, 3));

        ScaleTypes.HEIGHT.getScaleData(entity).setScale(inverted ? ScaleTypes.HEIGHT.getScaleData(entity).getScale()*divide_by : ScaleTypes.HEIGHT.getScaleData(entity).getScale()/divide_by);
        ScaleTypes.WIDTH.getScaleData(entity).setScale(inverted ? ScaleTypes.WIDTH.getScaleData(entity).getScale()*divide_by : ScaleTypes.WIDTH.getScaleData(entity).getScale()/divide_by);
        ScaleTypes.HITBOX_HEIGHT.getScaleData(entity).setScale(1.015f);
        ScaleTypes.HITBOX_WIDTH.getScaleData(entity).setScale(1.015f);
        SOUtils.applyScaleMultiplier(ScaleTypes.REACH.getScaleData(entity), divide_by, 0.59f, inverted);
        SOUtils.applyScaleMultiplier(ScaleTypes.ATTACK_SPEED.getScaleData(entity), divide_by, 0.71f, !inverted);
        SOUtils.applyScaleMultiplier(ScaleTypes.ATTACK.getScaleData(entity), divide_by, 0.65f, inverted);
        SOUtils.applyScaleMultiplier(ScaleTypes.KNOCKBACK.getScaleData(entity), divide_by, 0.61f, inverted);
        SOUtils.applyScaleMultiplier(ScaleTypes.JUMP_HEIGHT.getScaleData(entity), divide_by, 0.62f, inverted);
    }


    public static void UnFragment(LivingEntity entity) {
        if(entity.getEntityWorld().isClient) return;
        EntityAttributeInstance instance = entity.getAttributeInstance(SOEntityAttributes.ENTITY_SIZE);
        if (instance.getValue() != instance.getBaseValue()) {

            float size = (float) entity.getAttributeInstance(ENTITY_SIZE).getValue();

            if(size == 0) {
                size = (float) entity.getAttributeInstance(ENTITY_SIZE).getValue();
            }

            entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getModifiers().stream().filter(x -> x.getName().contains("FragmentationHealthDummy")).forEach(x -> entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).removeModifier(x));

            entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).getModifiers().stream().filter(x -> x.getName().contains("FragmentationSpeedDummy")).forEach(x -> entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).removeModifier(x));

            long pow = entity.getAttributeInstance(SOEntityAttributes.ENTITY_SIZE).getModifiers().stream().filter(x -> x.getName().contains("FragmentationSize")).count();

            entity.getAttributeInstance(SOEntityAttributes.ENTITY_SIZE).getModifiers().stream().filter(x -> x.getName().contains("FragmentationSize")).forEach(x -> entity.getAttributeInstance(SOEntityAttributes.ENTITY_SIZE).removeModifier(x));

            SOUtils.revertScaleMultiplier(ScaleTypes.HEIGHT.getScaleData(entity), size, 1f, pow, false);
            SOUtils.revertScaleMultiplier(ScaleTypes.WIDTH.getScaleData(entity), size, 1f, pow, false);
            ScaleTypes.HITBOX_HEIGHT.getScaleData(entity).setScale(1.000f);
            ScaleTypes.HITBOX_WIDTH.getScaleData(entity).setScale(1.000f);
            SOUtils.revertScaleMultiplier(ScaleTypes.REACH.getScaleData(entity), size, 0.59f, pow, false);
            SOUtils.revertScaleMultiplier(ScaleTypes.ATTACK_SPEED.getScaleData(entity), size, 0.71f, pow, true);
            SOUtils.revertScaleMultiplier(ScaleTypes.ATTACK.getScaleData(entity), size, 0.65f, pow, false);
            SOUtils.revertScaleMultiplier(ScaleTypes.KNOCKBACK.getScaleData(entity), size, 0.61f, pow, false);
            SOUtils.revertScaleMultiplier(ScaleTypes.JUMP_HEIGHT.getScaleData(entity), size, 0.62f, pow, false);

            entity.setHealth(entity.getMaxHealth());
            entity.clearStatusEffects();
        }
    }


    static {
        ENTITY_SIZE = SOEntityAttributes.ENTITY_SIZE;
    }
}