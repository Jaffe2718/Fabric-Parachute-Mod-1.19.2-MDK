package github.jaffe2718.parachute.register;

import github.jaffe2718.parachute.ParachuteMod;
import github.jaffe2718.parachute.item.ParachuteItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegister {
    public static final Item PARACHUTE = new ParachuteItem(new Item.Settings().maxDamage(100).group(ItemGroup.COMBAT));

    public static void register() {
        Registry.register(Registry.ITEM, new Identifier(ParachuteMod.ModID, "parachute"), PARACHUTE);
    }
}
