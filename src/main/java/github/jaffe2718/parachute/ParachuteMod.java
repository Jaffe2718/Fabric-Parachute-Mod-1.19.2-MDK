package github.jaffe2718.parachute;

import github.jaffe2718.parachute.register.ItemRegister;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.option.KeyBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.lwjgl.glfw.GLFW;

public class ParachuteMod implements ModInitializer {

    public static final String ModID = "parachute"; // This is just so we can refer to our ModID easier.

    public static final Logger LOGGER = LoggerFactory.getLogger(ModID);

    @Override
    public void onInitialize() {
        ItemRegister.register();
        LOGGER.info("Hello Fabric!");
    }
}
