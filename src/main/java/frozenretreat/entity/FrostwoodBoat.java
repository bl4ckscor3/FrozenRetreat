package frozenretreat.entity;

import frozenretreat.registration.FRItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class FrostwoodBoat extends Boat {
	public FrostwoodBoat(EntityType<? extends Boat> type, Level level) {
		super(type, level);
	}

	public FrostwoodBoat(Level level, double x, double y, double z) {
		super(level, x, y, z);
	}

	@Override
	public Item getDropItem() {
		return FRItems.FROSTWOOD_BOAT.get();
	}

	@Override
	public float getGroundFriction() {
		return super.getGroundFriction() + 0.1F;
	}
}
