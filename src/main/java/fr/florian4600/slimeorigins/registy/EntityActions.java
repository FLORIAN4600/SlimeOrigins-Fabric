package fr.florian4600.slimeorigins.registy;

import fr.florian4600.slimeorigins.SlimeOrigins;
import fr.florian4600.slimeorigins.actions.FragmentationAction;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.registry.Registry;

public class EntityActions {

    public static final ActionFactory<Entity> FRAGMENTATION = new ActionFactory<>(SlimeOrigins.newId("fragmentation"),
            new SerializableData().add("power", SerializableDataTypes.FLOAT, 2.0f)
                    .add("inverted", SerializableDataTypes.BOOLEAN, false),
            (data, entity) -> {
                if(entity instanceof LivingEntity livingEntity) {
                    FragmentationAction.Fragment(livingEntity, data.getFloat("power"), data.getBoolean("inverted"));
                }
            });

    public static final ActionFactory<Entity> UN_FRAG = new ActionFactory<>(SlimeOrigins.newId("un_frag"), new SerializableData(), (data, entity) -> {
        if(entity instanceof LivingEntity livingEntity) {
            FragmentationAction.UnFragment(livingEntity);
        }
    });

    public static void register() {
        Registry.register(ApoliRegistries.ENTITY_ACTION, FRAGMENTATION.getSerializerId(), FRAGMENTATION);
        Registry.register(ApoliRegistries.ENTITY_ACTION, UN_FRAG.getSerializerId(), UN_FRAG);
    }
}