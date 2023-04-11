package fr.florian4600.slimeorigins.data.serializables;

import net.minecraft.block.Block;

import java.util.List;

public record SoundSerializable(List<BlockValue> pitchs, List<BlockValue> volumes, float defaultPitch,
                                float defaultVolume) {

    public BlockValue getPitch(Block block) {
        return this.pitchs.stream().filter(blockValue -> block.equals(blockValue.block())).findAny().orElse(new BlockValue(block, this.defaultPitch));
    }

    public BlockValue getVolume(Block block) {
        return this.volumes.stream().filter(blockValue -> block.equals(blockValue.block())).findAny().orElse(new BlockValue(block, this.defaultVolume));
    }

    @Override
    public String toString() {
        return "SoundSerializable{" +
                "Pitchs=" + pitchs +
                ", Volumes=" + volumes +
                ", pitch=" + defaultPitch + "F" +
                ", volume=" + defaultVolume + "F" +
                '}';
    }
}
