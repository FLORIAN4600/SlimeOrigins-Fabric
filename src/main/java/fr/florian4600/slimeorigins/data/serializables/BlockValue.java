package fr.florian4600.slimeorigins.data.serializables;

import net.minecraft.block.Block;

public record BlockValue(Block block, double value) {

    @Override
    public String toString() {
        return "BlockValue{" + "block=" + block + ", val=" + value + "D}";
    }
}
