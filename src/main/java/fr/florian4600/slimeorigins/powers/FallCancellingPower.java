package fr.florian4600.slimeorigins.powers;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;

public class FallCancellingPower extends Power {

    public FallCancellingPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }

    @Override
    public String toString() {
        return "FallCancellingPower@"+Integer.toHexString(hashCode())+"{}";
    }
}
