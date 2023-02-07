package github.jaffe2718.parachute.item;

import github.jaffe2718.parachute.register.ItemRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class ParachuteItem extends ArmorItem implements IAnimatable {

    private final EquipmentSlot slot = EquipmentSlot.CHEST;
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public static void setActiveTick(ItemStack stack, int activeTick) {
        if (stack.isOf(ItemRegister.PARACHUTE) && activeTick >= 0) {
            stack.getOrCreateNbt().putInt("Active", activeTick);
        }
    }

    public static int getActiveTick(ItemStack stack) {
        return stack.getNbt() != null ? stack.getNbt().getInt("Active") : 0;
    }

    public ParachuteItem(Settings settings) {
        super(ArmorMaterials.LEATHER, EquipmentSlot.CHEST, settings);
    }

    public EquipmentSlot getSlotType() {
        return this.slot;
    }

//    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
//        ItemStack paraStack = user.getStackInHand(hand);
//        ItemStack woreStack = user.getEquippedStack(this.slot);
//        if (woreStack.isEmpty()) {
//            user.equipStack(this.slot, paraStack.copy());
//            if (!world.isClient()) {
//                user.incrementStat(Stats.USED.getOrCreateStat(this));
//            }
//
//            paraStack.setCount(0);
//            return TypedActionResult.success(paraStack, world.isClient());
//        } else {
//            return TypedActionResult.fail(paraStack);
//        }
//    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (entity instanceof PlayerEntity user && user.getEquippedStack(this.slot).isOf(this) && stack.isOf(this)) {  // 玩家佩戴伞包时
            if (stack.getDamage() >= stack.getMaxDamage()) {  // 没有耐久了
                user.getInventory().remove(
                        paraStack -> paraStack.isOf(this) && paraStack.getDamage() >= paraStack.getMaxDamage(),
                        1,
                        user.getInventory()
                );
            }
            if (user.getVelocity().y < -0.20F) {
                user.setVelocity(user.getVelocity().x, -0.18F, user.getVelocity().z);
            }
            if (user.fallDistance >= 2.5F) {      // 屏蔽摔落伤害
                user.fallDistance = 0.1F;
            } else if (user.fallDistance == 0 && getActiveTick(stack) > 0) {
                setActiveTick(stack,getActiveTick(stack)-1);
                if (getActiveTick(stack)==0 && !user.isCreative()) {    // 刚用完，消耗耐久
                    stack.damage(1, Random.create() , null);
                }
            }
        }
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        LivingEntity user = event.getExtraDataOfType(LivingEntity.class).get(0);
        AnimationBuilder builder = new AnimationBuilder();
        ItemStack paraStuck = user.getEquippedStack(EquipmentSlot.CHEST);
        if (user instanceof ArmorStandEntity) event.getController().setAnimation(builder.addAnimation("animation.parachute.open"));
        else if (user instanceof PlayerEntity) {
            if (user.fallDistance >= 2.5F) {           // 坠落开伞
                event.getController().setAnimation(builder.addAnimation("animation.parachute.open"));
                setActiveTick(paraStuck, 10);
            } else if (user.fallDistance==0 && getActiveTick(paraStuck) == 10) {
                event.getController().setAnimation(builder.addAnimation("animation.parachute.close"));
            } else if (user.fallDistance==0 && getActiveTick(paraStuck)==0) {
                event.getController().setAnimation(builder.addAnimation("animation.parachute.stay"));
            }
        }
        return PlayState.CONTINUE;
    }

    @Override               // implements IAnimatable
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this,
                "controller", 1, this::predicate));
    }

    @Override              // implements IAnimatable
    public AnimationFactory getFactory() {
        return factory;
    }
}
