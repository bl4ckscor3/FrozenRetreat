package frozenretreat.item;

import java.util.List;

import frozenretreat.entity.FrostwoodBoat;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FrostwoodBoatItem extends Item {
	public FrostwoodBoatItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		HitResult result = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);

		if (result.getType() == HitResult.Type.MISS) {
			return InteractionResultHolder.pass(stack);
		} else {
			Vec3 vec3 = player.getViewVector(1.0F);
			List<Entity> entities = level.getEntities(player, player.getBoundingBox().expandTowards(vec3.scale(5.0D)).inflate(1.0D), EntitySelector.NO_SPECTATORS.and(Entity::isPickable));

			if (!entities.isEmpty()) {
				Vec3 eyePosition = player.getEyePosition();

				for(Entity entity : entities) {
					AABB boundingBox = entity.getBoundingBox().inflate(entity.getPickRadius());

					if (boundingBox.contains(eyePosition)) {
						return InteractionResultHolder.pass(stack);
					}
				}
			}

			if (result.getType() == HitResult.Type.BLOCK) {
				Boat boat = new FrostwoodBoat(level, result.getLocation().x, result.getLocation().y, result.getLocation().z);

				boat.setYRot(player.getYRot());

				if (!level.noCollision(boat, boat.getBoundingBox())) {
					return InteractionResultHolder.fail(stack);
				} else {
					if (!level.isClientSide) {
						level.addFreshEntity(boat);
						level.gameEvent(player, GameEvent.ENTITY_PLACE, new BlockPos(result.getLocation()));

						if (!player.getAbilities().instabuild) {
							stack.shrink(1);
						}
					}

					player.awardStat(Stats.ITEM_USED.get(this));
					return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
				}
			} else {
				return InteractionResultHolder.pass(stack);
			}
		}
	}
}
