package fr.florian4600.slimeorigins.data.serializables;

import net.minecraft.block.Block;

public class BlockValue {

    private final Block block;
    private final double value;

    public BlockValue(Block block, double value) {
        this.block = block;
        this.value = value;
    }

    public Block getBlock() {
        return this.block;
    }

    public double getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "BlockValue{" + "block=" + block + ", val=" + value + "D}";
    }
}
