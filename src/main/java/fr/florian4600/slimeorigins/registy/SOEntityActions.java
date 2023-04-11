package fr.florian4600.slimeorigins.registy;

import fr.florian4600.slimeorigins.SlimeOrigins;
import fr.florian4600.slimeorigins.actions.FragmentationAction;
import io.github.apace100.origins.power.factory.action.ActionFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.registry.Registry;

public class SOEntityActions {

    public static final ActionFactory<Entity> FRAGMENTATION = new ActionFactory<>(SlimeOrigins.newId("fragmentation"),
            new SerializableData().add("power", SerializableDataType.FLOAT, 2.0f)
                    .add("inverted", SerializableDataType.BOOLEAN, false),
            (data, entity) -> {
                if(entity instanceof LivingEntity) {
                    FragmentationAction.Fragment((LivingEntity) entity, data.getFloat("power"), data.getBoolean("inverted"));
                }
            });

    public static final ActionFactory<Entity> UN_FRAG = new ActionFactory<>(SlimeOrigins.newId("un_frag"), new SerializableData(), (data, entity) -> {
        if(entity instanceof LivingEntity) {
            FragmentationAction.UnFragment((LivingEntity) entity);
        }
    });

    public static void register() {
        Registry.register(ModRegistries.ENTITY_ACTION, FRAGMENTATION.getSerializerId(), FRAGMENTATION);
        Registry.register(ModRegistries.ENTITY_ACTION, UN_FRAG.getSerializerId(), UN_FRAG);
    }
}