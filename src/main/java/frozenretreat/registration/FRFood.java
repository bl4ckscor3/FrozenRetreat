package frozenretreat.registration;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class FRFood {
	public static final FoodProperties WINTERBERRIES = new FoodProperties.Builder()
			.nutrition(2)
			.saturationMod(0.1F)
			.effect(() -> new MobEffectInstance(MobEffects.CONFUSION), 0.1F)
			.effect(() -> new MobEffectInstance(MobEffects.POISON), 0.1F)
			.build();
}