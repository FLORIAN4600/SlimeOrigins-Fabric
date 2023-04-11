package fr.florian4600.slimeorigins.data.serializables;

import net.minecraft.block.Block;

import java.util.List;

public class SoundSerializable {

    private final List<BlockValue> pitchs;
    private final List<BlockValue> volumes;
    private final float defaultPitch;
    private final float defaultVolume;

    public SoundSerializable(List<BlockValue> pitchs, List<BlockValue> volumes, float defaultPitch, float defaultVolume) {
        this.pitchs = pitchs;
        this.volumes = volumes;
        this.defaultPitch = defaultPitch;
        this.defaultVolume = defaultVolume;
    }

    public List<BlockValue> getPitchs() {
        return this.pitchs;
    }

    public List<BlockValue> getVolumes() {
        return this.volumes;
    }

    public double getDefaultPitch() {
        return this.defaultPitch;
    }

    public double getDefaultVolume() {
        return this.defaultVolume;
    }

    public BlockValue getPitch(Block block) {
        return this.pitchs.stream().filter(blockValue -> block.equals(blockValue.getBlock())).findAny().orElse(new BlockValue(block, this.defaultPitch));
    }

    public BlockValue getVolume(Block block) {
        return this.volumes.stream().filter(blockValue -> block.equals(blockValue.getBlock())).findAny().orElse(new BlockValue(block, this.defaultVolume));
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
