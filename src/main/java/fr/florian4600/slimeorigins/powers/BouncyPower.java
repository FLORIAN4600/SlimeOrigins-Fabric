package fr.florian4600.slimeorigins.powers;

import fr.florian4600.slimeorigins.data.serializables.BlockValue;
import fr.florian4600.slimeorigins.data.serializables.SoundSerializable;
import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public class BouncyPower extends Power {

    private final List<BlockValue> bounceMultipliers;
    private final SoundSerializable soundParams;
    private final double defaultBounce;
    private final float minVelDown;
    private final float minVelUp;

    public BouncyPower(PowerType<?> type, PlayerEntity player, List<BlockValue> bounceMultipliers, SoundSerializable soundParams, double defaultBounce, float minVelDown, float minVelUp) {
        super(type, player);
        this.bounceMultipliers = bounceMultipliers;
        this.soundParams = soundParams;
        this.defaultBounce = defaultBounce;
        this.minVelDown = minVelDown;
        this.minVelUp = minVelUp;
    }

    public List<BlockValue> getBounceMultipliers() {
        return bounceMultipliers;
    }

    public double getDefaultBounce() {
        return defaultBounce;
    }

    public SoundSerializable getSoundParams() {
        return soundParams;
    }

    public float getMinVelDown() {
        return minVelDown;
    }

    public float getMinVelUp() {
        return minVelUp;
    }

    @Override
    public String toString() {
        return "BouncyPower@"+Integer.toHexString(hashCode())+" {" +
                "player=" + player +
                ", Bounces=" + bounceMultipliers +
                ", sound=" + soundParams +
                ", bounce=" + defaultBounce +
                ", minDown=" + minVelDown +
                "F, minUp=" + minVelUp + "F}";
    }
}
