package fr.florian4600.slimeorigins.entity.attribute;

import fr.florian4600.slimeorigins.SlimeOrigins;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.registry.Registry;

public class SOEntityAttributes {

    public static final EntityAttribute ENTITY_SIZE = new ClampedEntityAttribute("attribute.name.generic." + SlimeOrigins.MOD_ID + ".entity_size", 1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).setTracked(true);

    public static void register() {
        Registry.register(Registry.ATTRIBUTE, SlimeOrigins.newId("entity_size"), ENTITY_SIZE);
    }

}
