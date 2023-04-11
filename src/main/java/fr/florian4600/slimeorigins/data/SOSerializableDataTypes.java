package fr.florian4600.slimeorigins.data;

import fr.florian4600.slimeorigins.data.serializables.BlockValue;
import fr.florian4600.slimeorigins.data.serializables.SoundSerializable;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;

import java.util.List;

public class SOSerializableDataTypes {

    public static final SerializableDataType<BlockValue> BLOCK_VALUE;
    public static final SerializableDataType<List<BlockValue>> BLOCK_VALUES;
    public static final SerializableDataType<SoundSerializable> SOUND;

    public SOSerializableDataTypes() {

    }

    static {
        BLOCK_VALUE = SerializableDataType.compound(BlockValue.class,
                new SerializableData()
                        .add("id", SerializableDataTypes.BLOCK)
                        .add("value", SerializableDataTypes.DOUBLE),
                instance -> new BlockValue(instance.get("id"), instance.getDouble("value")),
                (data, inst) -> {
                    SerializableData.Instance dataInst = data.new Instance();
                    dataInst.set("id", inst.block());
                    dataInst.set("value", inst.value());
                    return dataInst;
                });

        BLOCK_VALUES = SerializableDataType.list(BLOCK_VALUE);

        SOUND = SerializableDataType.compound(SoundSerializable.class,
                new SerializableData()
                        .add("block_pitches", SOSerializableDataTypes.BLOCK_VALUES, List.of())
                        .add("block_volumes", SOSerializableDataTypes.BLOCK_VALUES, List.of())
                        .add("default_pitch", SerializableDataTypes.FLOAT, 0.0f)
                        .add("default_volume", SerializableDataTypes.FLOAT, 1.0f),
                instance -> new SoundSerializable(
                        instance.get("block_pitches"),
                        instance.get("block_volumes"),
                        instance.getFloat("default_pitch"),
                        instance.getFloat("default_volume")),
                (data, inst) -> {
                    SerializableData.Instance dataInst = data.new Instance();
                    dataInst.set("block_pitches", inst.pitchs());
                    dataInst.set("block_volumes", inst.volumes());
                    dataInst.set("default_pitch", inst.defaultPitch());
                    dataInst.set("default_volume", inst.defaultVolume());
                    return dataInst;
                });
    }

}
