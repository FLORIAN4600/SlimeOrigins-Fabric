package fr.florian4600.slimeorigins.powers;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import net.minecraft.entity.player.PlayerEntity;

public class FallCancellingPower extends Power {

    public FallCancellingPower(PowerType<?> type, PlayerEntity player) {
        super(type, player);
    }

    @Override
    public String toString() {
        return "FallCancellingPower@"+Integer.toHexString(hashCode())+"{}";
    }
}
