package fr.florian4600.slimeorigins.util;

import fr.florian4600.slimeorigins.data.serializables.BlockValue;
import fr.florian4600.slimeorigins.data.serializables.SoundSerializable;
import fr.florian4600.slimeorigins.powers.BouncyPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.List;
import java.util.Random;

public class SOMixinUtils {

    public SOMixinUtils() {

    }

    public static void onEntityLand(BlockView world, Entity entity, Block block, CallbackInfo ci) {
        if(entity instanceof LivingEntity livingEntity && !livingEntity.bypassesLandingEffects()) {

            List<BouncyPower> powers;

            try {
                powers = PowerHolderComponent.KEY.get(livingEntity).getPowers(BouncyPower.class).stream().filter(Power::isActive).toList();
            } catch (Exception e) {
                if(livingEntity instanceof PlayerEntity) {
                    e.printStackTrace();
                }
                return;
            }

            if(powers.isEmpty()) return;

            BouncyPower power = powers.get(0);

            Vec3d pos = livingEntity.getPos();
            BlockPos bp = livingEntity.getBlockPos();
            Vec3d velocity = livingEntity.getVelocity();

            double xPlus = (pos.getX() - (double) bp.getX()) < 0 ? -Double.MIN_NORMAL : Double.MIN_NORMAL;
            double zPlus = (pos.getZ() - (double) bp.getZ()) < 0 ? -Double.MIN_NORMAL : Double.MIN_NORMAL;

            if((velocity.y < -power.getMinVelDown() && world.getBlockState(new BlockPos((int) (pos.x-xPlus), (int) (pos.y-1d), (int) (pos.z-zPlus))).isOf(block)) || (velocity.y > power.getMinVelUp() && world.getBlockState(new BlockPos((int) (pos.x-xPlus), (int) (pos.y+ ScaleTypes.HEIGHT.getScaleData(livingEntity).getScale()*ScaleTypes.HITBOX_HEIGHT.getScaleData(livingEntity).getScale()), (int) (pos.z-zPlus))).isOf(block))) {

                bp = new BlockPos((int) (pos.x+xPlus), (int) (pos.y + (velocity.y > power.getMinVelUp() ? 2 : -1)), (int) (pos.z+zPlus));

                if((!world.getBlockState(bp).isFullCube(world, bp) || world.getBlockState(bp).isOf(Blocks.AIR) || world.getBlockState(bp).isOf(Blocks.WATER) || world.getBlockState(bp).isOf(Blocks.LAVA)) && !(velocity.y < -0.42d || velocity.y > 0.42d)) {
                    return;
                }

                Random random = new Random();

                List<BlockValue> multipliers = power.getBounceMultipliers().stream().filter(bv -> bv.block().equals(block)).toList();
                double velMultiplier = -(multipliers.isEmpty() ? power.getDefaultBounce() : multipliers.get(random.nextInt(multipliers.size())).value());

                livingEntity.setVelocity(velocity.x, MathHelper.clamp(velocity.y*velMultiplier, -200000d, 200000), velocity.z);

                SoundSerializable sound = power.getSoundParams();

                List<BlockValue> pitchs = sound.pitchs().stream().filter(bv -> bv.block().equals(block)).toList();
                float pitch = (float) (pitchs.isEmpty() ? sound.defaultPitch() : pitchs.get(random.nextInt(pitchs.size())).value());

                List<BlockValue> volumes = sound.pitchs().stream().filter(bv -> bv.block().equals(block)).toList();
                float volume = (float) (volumes.isEmpty() ? sound.defaultVolume() : volumes.get(random.nextInt(volumes.size())).value());

                BlockSoundGroup blockSoundGroup = BlockSoundGroup.SLIME;
                livingEntity.playSound(blockSoundGroup.getStepSound(), blockSoundGroup.getVolume() * volume, blockSoundGroup.getPitch() + pitch);
                ci.cancel();
                return;
            }
            if(velocity.y > 0.00f && world.getBlockState(livingEntity.getBlockPos().add(0, -1, 0)).isOf(block) || velocity.y < 0.00f && world.getBlockState(new BlockPos((int) pos.x, (int) (pos.y+ScaleTypes.HEIGHT.getScaleData(livingEntity).getScale()), (int) pos.z)).isOf(block)) {
                ci.cancel();
            }
        }
    }

}
