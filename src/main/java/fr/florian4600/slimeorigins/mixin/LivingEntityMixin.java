package fr.florian4600.slimeorigins.mixin;

import fr.florian4600.slimeorigins.entity.attribute.SOEntityAttributes;
import fr.florian4600.slimeorigins.powers.BouncyPower;
import fr.florian4600.slimeorigins.powers.FallCancellingPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow
    @Nullable
    public abstract EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);

    @Shadow protected abstract void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition);

    @Shadow public abstract boolean isAlive();

    @Shadow protected abstract float turnHead(float bodyRotation, float headRotation);

    @Shadow public abstract boolean isFallFlying();

    public LivingEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "createLivingAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", require = 1, allow = 1, at = @At("RETURN"))
    private static void createLivingAttributes(final CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
        final DefaultAttributeContainer.Builder builder = info.getReturnValue();
        builder.add(SOEntityAttributes.ENTITY_SIZE);
    }


    @Inject(method = "handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z", at = @At("HEAD"),cancellable = true)
    public void handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> info) {
        try {
            if(PowerHolderComponent.KEY.get(this).getPowers(FallCancellingPower.class).stream().anyMatch(Power::isActive)) {
                info.setReturnValue(false);
            }
        }catch (Exception ignored) {

        }
    }

    @Inject(method = "travel(Lnet/minecraft/util/math/Vec3d;)V", at = @At("TAIL"))
    public void travel(Vec3d movementInput, CallbackInfo ci) {
        if(this.getVelocity().y < -3.8d && this.getPos().y > 8000d && PowerHolderComponent.KEY.get(this).getPowers(BouncyPower.class).stream().anyMatch(Power::isActive)) {
            Vec3d vel = this.getVelocity();
            this.setVelocity(new Vec3d(vel.x, MathHelper.clamp(vel.y+this.getPos().y*0.025d/vel.y, -16500d, -5.2d), vel.z));
        }
    }

}
