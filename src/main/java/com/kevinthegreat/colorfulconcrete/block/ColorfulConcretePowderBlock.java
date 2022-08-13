package com.kevinthegreat.colorfulconcrete.block;

import net.minecraft.block.Block;
import net.minecraft.block.ConcretePowderBlock;

public class ColorfulConcretePowderBlock extends ConcretePowderBlock implements ColorfulBlockEntityProvider {
    public ColorfulConcretePowderBlock(Block hardened, Settings settings) {
        super(hardened, settings);
    }
}
