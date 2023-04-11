package fr.florian4600.slimeorigins.registy;

import fr.florian4600.slimeorigins.SlimeOrigins;
import fr.florian4600.slimeorigins.data.SOSerializableDataTypes;
import fr.florian4600.slimeorigins.data.serializables.SoundSerializable;
import fr.florian4600.slimeorigins.powers.BouncyPower;
import fr.florian4600.slimeorigins.powers.FallCancellingPower;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.registry.Registry;

import java.util.List;

public class Powers {
    public static final PowerFactory<Power> BOUNCINESS;
    public static final PowerFactory<Power> NO_FD;

    public Powers() {

    }

    public static void register() {
        Registry.register(ApoliRegistries.POWER_FACTORY, BOUNCINESS.getSerializerId(), BOUNCINESS);
        Registry.register(ApoliRegistries.POWER_FACTORY, NO_FD.getSerializerId(), NO_FD);
    }

    static {
        BOUNCINESS = new PowerFactory<>(SlimeOrigins.newId("bounciness"),
                new SerializableData()
                        .add("bounce_multipliers", SOSerializableDataTypes.BLOCK_VALUES, List.of())
                        .add("default_bounce", SerializableDataTypes.DOUBLE, 0.70d)
                        .add("bounce_sound", SOSerializableDataTypes.SOUND, new SoundSerializable(List.of(), List.of(), 0.0f, 1.0f))
                        .add("min_velocity_down", SerializableDataTypes.FLOAT, null)
                        .add("min_velocity_up", SerializableDataTypes.FLOAT, null)
                        .add("min_velocity", SerializableDataTypes.FLOAT, 0.35f),
                data -> (type, entity) -> new BouncyPower(type, entity,
                        data.get("bounce_multipliers"),
                        data.get("bounce_sound"),
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
