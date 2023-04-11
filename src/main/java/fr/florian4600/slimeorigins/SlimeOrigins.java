package fr.florian4600.slimeorigins;


import fr.florian4600.slimeorigins.entity.attribute.SOEntityAttributes;
import fr.florian4600.slimeorigins.registy.SOEntityActions;
import fr.florian4600.slimeorigins.registy.SOPowers;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;

public class SlimeOrigins implements ModInitializer {

    public static final String MOD_ID = "slimeorigins";
    public static final Logger LOGGER = LogManager.getLogger(SlimeOrigins.MOD_ID);

    @Override
    public void onInitialize() {
        SOEntityAttributes.register();
        SOEntityActions.register();
        SOPowers.register();
    }

    public static void log(Level level, String message) {
        LOGGER.log(level, message);
    }

    public static void log(Level level, Marker marker, String message) {
        LOGGER.log(level, marker, message);
    }

    public static Identifier newId(String id) {
        return new Identifier(MOD_ID, id);
    }

}
