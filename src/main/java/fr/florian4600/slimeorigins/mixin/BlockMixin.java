package fr.florian4600.slimeorigins.mixin;

import fr.florian4600.slimeorigins.util.SOMixinUtils;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock {

    @Shadow protected abstract Block asBlock();

    public BlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "onEntityLand(Lnet/minecraft/world/BlockView;Lnet/minecraft/entity/Entity;)V", at = @At("HEAD"),cancellable = true)
    public void onEntityLand(BlockView world, Entity entity, CallbackInfo ci) {
        SOMixinUtils.onEntityLand(world, entity, this.asBlock(), ci);
    }

}