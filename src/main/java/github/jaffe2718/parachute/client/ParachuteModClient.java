package github.jaffe2718.parachute.client;


import github.jaffe2718.parachute.client.render.ParachuteRenderer;
import github.jaffe2718.parachute.register.ItemRegister;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Environment(EnvType.CLIENT)
public class ParachuteModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        GeoArmorRenderer.registerArmorRenderer(new ParachuteRenderer(), ItemRegister.PARACHUTE);
    }
}
