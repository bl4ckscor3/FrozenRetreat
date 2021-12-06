package frozenretreat.renderer;

import com.mojang.datafixers.util.Pair;

import frozenretreat.FrozenRetreat;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

public class FrozenBoatRenderer extends BoatRenderer {
	private final BoatModel model;

	public FrozenBoatRenderer(EntityRendererProvider.Context ctx) {
		super(ctx);
		model = new BoatModel(ctx.bakeLayer(ModelLayers.createBoatModelName(Boat.Type.OAK))); //TODO: different model for our boat
	}

	@Override
	public Pair<ResourceLocation, BoatModel> getModelWithLocation(Boat boat) {
		return Pair.of(new ResourceLocation(FrozenRetreat.MODID, "textures/entity/boat/frostwood.png"), model);
	}
}
