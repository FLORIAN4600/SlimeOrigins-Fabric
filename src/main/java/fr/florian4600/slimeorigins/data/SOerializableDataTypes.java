package fr.florian4600.slimeorigins.data;

import com.google.common.collect.Lists;
import fr.florian4600.slimeorigins.data.serializables.BlockValue;
import fr.florian4600.slimeorigins.data.serializables.SoundSerializable;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import net.minecraft.block.Block;

import java.util.List;

public class SOerializableDataTypes {

    public static final SerializableDataType<BlockValue> BLOCK_VALUE;
    public static final SerializableDataType<List<BlockValue>> BLOCK_VALUES;
    public static final SerializableDataType<SoundSerializable> SOUND;

    public SOerializableDataTypes() {

    }

    static {
        BLOCK_VALUE = SerializableDataType.compound(BlockValue.class,
                new SerializableData()
                        .add("id", SerializableDataType.BLOCK)
                        .add("value", SerializableDataType.DOUBLE),
                instance -> new BlockValue((Block) instance.get("id"), instance.getDouble("value")),
                (data, inst) -> {
                    SerializableData.Instance dataInst = data.new Instance();
                    dataInst.set("id", inst.getBlock());
                    dataInst.set("value", inst.getValue());
                    return dataInst;
                });

        BLOCK_VALUES = SerializableDataType.list(BLOCK_VALUE);

        SOUND = SerializableDataType.compound(SoundSerializable.class,
                new SerializableData()
                        .add("block_pitches", SOerializableDataTypes.BLOCK_VALUES, Lists.newArrayList())
                        .add("block_volumes", SOerializableDataTypes.BLOCK_VALUES, Lists.newArrayList())
                        .add("default_pitch", SerializableDataType.FLOAT, 0.0f)
                        .add("default_volume", SerializableDataType.FLOAT, 1.0f),
                instance -> new SoundSerializable((List<BlockValue>) instance.get("block_pitches"), (List<BlockValue>) instance.get("block_volumes"), instance.getFloat("default_pitch"), instance.getFloat("default_volume")),
                (data, inst) -> {
                    SerializableData.Instance dataInst = data.new Instance();
                    dataInst.set("block_pitches", inst.getPitchs());
                    dataInst.set("block_volumes", inst.getVolumes());
                    dataInst.set("default_pitch", inst.getDefaultPitch());
                    dataInst.set("default_volume", inst.getDefaultVolume());
                    return dataInst;
                });
    }

}
