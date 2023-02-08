package github.jaffe2718.parachute.client.model.armor;

import github.jaffe2718.parachute.item.ParachuteItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.GeckoLib;


public class ParachuteModel extends AnimatedGeoModel<ParachuteItem> {

    @Override
    public Identifier getModelResource(ParachuteItem object) {
        return new Identifier(GeckoLib.ModID, "geo/parachute.geo.json");
    }

    @Override
    public Identifier getTextureResource(ParachuteItem object) {
        return new Identifier(GeckoLib.ModID, "textures/models/armor/parachute.png");
    }

    @Override
    public Identifier getAnimationResource(ParachuteItem animatable) {
        return new Identifier(GeckoLib.ModID, "animations/parachute.animation.json");
    }
}
