package github.jaffe2718.parachute.client.render;

import github.jaffe2718.parachute.client.model.armor.ParachuteModel;
import github.jaffe2718.parachute.item.ParachuteItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class ParachuteRenderer extends GeoArmorRenderer<ParachuteItem>{

    public ParachuteRenderer() {
        super(new ParachuteModel());
        this.headBone = null;
        this.bodyBone = "Body";
        this.leftArmBone = null;
        this.rightArmBone = null;
        this.leftLegBone = null;
        this.rightLegBone = null;
        this.leftBootBone = null;
       this.rightBootBone = null;
    }
}
