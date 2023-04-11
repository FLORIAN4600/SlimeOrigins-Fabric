package fr.florian4600.slimeorigins.registy;

import com.google.common.collect.Lists;
import fr.florian4600.slimeorigins.SlimeOrigins;
import fr.florian4600.slimeorigins.data.SOerializableDataTypes;
import fr.florian4600.slimeorigins.data.serializables.BlockValue;
import fr.florian4600.slimeorigins.data.serializables.SoundSerializable;
import fr.florian4600.slimeorigins.powers.BouncyPower;
import fr.florian4600.slimeorigins.powers.FallCancellingPower;
import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class SOPowers {
    public static final PowerFactory<Power> BOUNCINESS;
    public static final PowerFactory<Power> NO_FD;

    public SOPowers() {

    }

    public static void register() {
        Registry.register(ModRegistries.POWER_FACTORY, BOUNCINESS.getSerializerId(), BOUNCINESS);
        Registry.register(ModRegistries.POWER_FACTORY, NO_FD.getSerializerId(), NO_FD);
    }

    static {
        BOUNCINESS = new PowerFactory<>(SlimeOrigins.newId("bounciness"),
                new SerializableData()
                        .add("bounce_multipliers", SOerializableDataTypes.BLOCK_VALUES, Lists.newArrayList())
                        .add("default_bounce", SerializableDataType.DOUBLE, 0.70d)
                        .add("bounce_sound", SOerializableDataTypes.SOUND, new SoundSerializable(Lists.newArrayList(), Lists.newArrayList(), 0.0f, 1.0f))
                        .add("min_velocity_down", SerializableDataType.FLOAT, null)
                        .add("min_velocity_up", SerializableDataType.FLOAT, null)
                        .add("min_velocity", SerializableDataType.FLOAT, 0.35f),
                data -> (type, player) -> new BouncyPower(type, player,
                        (List<BlockValue>) data.get("bounce_multipliers"),
                        (SoundSerializable) data.get("bounce_sound"),
                        data.getDouble("default_bounce"),
                        data.isPresent("min_velocity_down") ? data.getFloat("min_velocity_down") : data.getFloat("min_velocity"),
                        data.isPresent("min_velocity_up") ? data.getFloat("min_velocity_up") : data.getFloat("min_velocity")
                )).allowCondition();

        NO_FD = new PowerFactory<Power>(SlimeOrigins.newId("no_fall_damage"),
                new SerializableData(),
                data -> FallCancellingPower::new
                ).allowCondition();
    }
}
