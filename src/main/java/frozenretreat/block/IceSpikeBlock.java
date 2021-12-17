package frozenretreat.block;

import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class IceSpikeBlock extends Block implements Fallable, SimpleWaterloggedBlock {
	public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
	public static final EnumProperty<DripstoneThickness> THICKNESS = BlockStateProperties.DRIPSTONE_THICKNESS;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final VoxelShape TIP_MERGE_SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
	private static final VoxelShape TIP_SHAPE_UP = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 11.0D, 11.0D);
	private static final VoxelShape TIP_SHAPE_DOWN = Block.box(5.0D, 5.0D, 5.0D, 11.0D, 16.0D, 11.0D);
	private static final VoxelShape FRUSTUM_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
	private static final VoxelShape MIDDLE_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
	private static final VoxelShape BASE_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

	public IceSpikeBlock(BlockBehaviour.Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(TIP_DIRECTION, Direction.UP).setValue(THICKNESS, DripstoneThickness.TIP).setValue(WATERLOGGED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(TIP_DIRECTION, THICKNESS, WATERLOGGED);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if(!level.isClientSide)
		{
			System.out.println("---");
			System.out.println(canMelt(level, pos, state));
			System.out.println(canTipGrow(state, (ServerLevel)level, pos));
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return isValidIceSpikePlacement(level, pos, state.getValue(TIP_DIRECTION));
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState otherState, LevelAccessor level, BlockPos pos, BlockPos otherPos) {
		if (state.getValue(WATERLOGGED))
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

		if (direction != Direction.UP && direction != Direction.DOWN)
			return state;
		else {
			Direction tipDirection = state.getValue(TIP_DIRECTION);

			if (tipDirection == Direction.DOWN && level.getBlockTicks().hasScheduledTick(pos, this))
				return state;
			else if (direction == tipDirection.getOpposite() && !canSurvive(state, level, pos)) {
				if (tipDirection == Direction.DOWN)
					scheduleStalactiteFallTicks(state, level, pos);
				else
					level.scheduleTick(pos, this, 1);

				return state;
			}
			else {
				boolean isMergedTip = state.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
				DripstoneThickness thickness = calculateThickness(level, pos, tipDirection, isMergedTip);

				return state.setValue(THICKNESS, thickness);
			}
		}
	}

	@Override
	public void onProjectileHit(Level level, BlockState state, BlockHitResult hitResult, Projectile projectile) {
		BlockPos pos = hitResult.getBlockPos();

		if (!level.isClientSide && projectile.mayInteract(level, pos) && projectile instanceof ThrownTrident && projectile.getDeltaMovement().length() > 0.6D)
			level.destroyBlock(pos, true);
	}

	@Override
	public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
		if (state.getValue(TIP_DIRECTION) == Direction.UP && state.getValue(THICKNESS) == DripstoneThickness.TIP)
			entity.causeFallDamage(fallDistance + 2.0F, 2.0F, DamageSource.STALAGMITE);
		else
			super.fallOn(level, state, pos, entity, fallDistance);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
		if (canDrip(level, pos, state) && canMelt(level, pos, state)) {
			float f = random.nextFloat();

			if (!(f > 0.12F))
				getFluidAboveStalactite(level, pos, state).filter(fluid -> f < 0.02F).ifPresent(fluid -> spawnDripParticle(level, pos, state));
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
		if (isStalagmite(state) && !canSurvive(state, level, pos))
			level.destroyBlock(pos, true);
		else
			spawnFallingStalactite(state, level, pos);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
		if (level.getBiome(pos).getBiomeCategory() == BiomeCategory.ICY) {
			if (isStalactiteStartPos(state, level, pos))
				growStalactiteOrStalagmiteIfPossible(state, level, pos, random);
		}
		else if(canMelt(level, pos, state)) {
			Direction moveDirection = Direction.UP;

			if (!isStalactite(state)) {
				spawnDripParticle(level, pos, state);
				moveDirection = Direction.DOWN;
			}

			BlockPos tipPos = findTip(state, level, pos, 7, true);

			if (tipPos != null) {
				MutableBlockPos currentPos = tipPos.mutable();
				BlockState stateToCheck = level.getBlockState(currentPos);
				DripstoneThickness thickness = stateToCheck.getValue(THICKNESS);

				//remove tip
				level.removeBlock(currentPos, false);

				//adapt other spike
				if (thickness == DripstoneThickness.TIP_MERGE) {
					currentPos.move(moveDirection.getOpposite());
					stateToCheck = level.getBlockState(currentPos);
					thickness = stateToCheck.getValue(THICKNESS);

					if (thickness == DripstoneThickness.TIP_MERGE)
						level.removeBlock(currentPos, false);
					else if (thickness == DripstoneThickness.FRUSTUM)
						createIceSpike(level, currentPos, moveDirection, DripstoneThickness.TIP);
					else if (thickness == DripstoneThickness.MIDDLE)
						createIceSpike(level, currentPos, moveDirection, DripstoneThickness.FRUSTUM);
					else if (thickness == DripstoneThickness.BASE)
						createIceSpike(level, currentPos, moveDirection, DripstoneThickness.MIDDLE);

					currentPos.move(moveDirection);
				}

				currentPos.move(moveDirection);
				thickness = level.getBlockState(currentPos).getValue(THICKNESS);

				//shrink next block
				if (thickness == DripstoneThickness.FRUSTUM)
					createIceSpike(level, currentPos, moveDirection.getOpposite(), DripstoneThickness.TIP);
				else if (thickness == DripstoneThickness.MIDDLE)
					createIceSpike(level, currentPos, moveDirection.getOpposite(), DripstoneThickness.FRUSTUM);
				else if (thickness == DripstoneThickness.BASE)
					createIceSpike(level, currentPos, moveDirection.getOpposite(), DripstoneThickness.MIDDLE);
			}
		}
	}

	@Override
	public PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.DESTROY;
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		LevelAccessor level = ctx.getLevel();
		BlockPos pos = ctx.getClickedPos();
		Direction verticalDir = ctx.getNearestLookingVerticalDirection().getOpposite();
		Direction tipDirection = calculateTipDirection(level, pos, verticalDir);

		if (tipDirection == null)
			return null;
		else {
			boolean secondaryUseNotActive = !ctx.isSecondaryUseActive();
			DripstoneThickness thickness = calculateThickness(level, pos, tipDirection, secondaryUseNotActive);

			return thickness == null ? null : defaultBlockState().setValue(TIP_DIRECTION, tipDirection).setValue(THICKNESS, thickness).setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
		}
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
		return Shapes.empty();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
		DripstoneThickness thickness = state.getValue(THICKNESS);
		VoxelShape shape;

		if (thickness == DripstoneThickness.TIP_MERGE)
			shape = TIP_MERGE_SHAPE;
		else if (thickness == DripstoneThickness.TIP) {
			if (state.getValue(TIP_DIRECTION) == Direction.DOWN)
				shape = TIP_SHAPE_DOWN;
			else
				shape = TIP_SHAPE_UP;

		}
		else if (thickness == DripstoneThickness.FRUSTUM)
			shape = FRUSTUM_SHAPE;
		else if (thickness == DripstoneThickness.MIDDLE)
			shape = MIDDLE_SHAPE;
		else
			shape = BASE_SHAPE;

		Vec3 vec3 = state.getOffset(level, pos);
		return shape.move(vec3.x, 0.0D, vec3.z);
	}

	@Override
	public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
		return false;
	}

	@Override
	public BlockBehaviour.OffsetType getOffsetType() {
		return BlockBehaviour.OffsetType.XZ;
	}

	@Override
	public float getMaxHorizontalOffset() {
		return 0.125F;
	}

	@Override
	public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity fallingBlockEntity) {
		if (!fallingBlockEntity.isSilent())
			level.levelEvent(LevelEvent.SOUND_POINTED_DRIPSTONE_LAND, pos, 0);
	}

	@Override
	public DamageSource getFallDamageSource() {
		return DamageSource.FALLING_STALACTITE;
	}

	@Override
	public Predicate<Entity> getHurtsEntitySelector() {
		return EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.LIVING_ENTITY_STILL_ALIVE);
	}

	public void scheduleStalactiteFallTicks(BlockState state, LevelAccessor level, BlockPos pos) {
		BlockPos tipPos = findTip(state, level, pos, Integer.MAX_VALUE, true);

		if (tipPos != null) {
			BlockPos.MutableBlockPos mutableTipPos = tipPos.mutable();
			BlockState stateToCheck;

			mutableTipPos.move(Direction.DOWN);
			stateToCheck = level.getBlockState(mutableTipPos);

			if (stateToCheck.getCollisionShape(level, mutableTipPos, CollisionContext.empty()).max(Direction.Axis.Y) >= 1.0D || stateToCheck.is(Blocks.POWDER_SNOW)) {
				level.destroyBlock(tipPos, true);
				mutableTipPos.move(Direction.UP);
			}

			mutableTipPos.move(Direction.UP);

			while(isStalactite(level.getBlockState(mutableTipPos))) {
				level.scheduleTick(mutableTipPos, this, 2);
				mutableTipPos.move(Direction.UP);
			}
		}
	}

	public int getStalactiteSizeFromTip(ServerLevel level, BlockPos pos, int maxCheckDistance) {
		int i = 1;
		BlockPos.MutableBlockPos posAbove = pos.mutable().move(Direction.UP);

		while(i < maxCheckDistance && isStalactite(level.getBlockState(posAbove))) {
			++i;
			posAbove.move(Direction.UP);
		}

		return i;
	}

	public void spawnFallingStalactite(BlockState state, ServerLevel level, BlockPos pos) {
		Vec3 vec3 = Vec3.atBottomCenterOf(pos);
		FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(level, vec3.x, vec3.y, vec3.z, state);

		if (isTip(state, true))
			fallingBlockEntity.setHurtsEntities(getStalactiteSizeFromTip(level, pos, 6), 40);

		level.addFreshEntity(fallingBlockEntity);
	}

	public void growStalactiteOrStalagmiteIfPossible(BlockState state, ServerLevel level, BlockPos pos, Random random) {
		BlockState stateAbove = level.getBlockState(pos.above(1));
		BlockState state2Above = level.getBlockState(pos.above(2));

		if (canGrow(level, pos, stateAbove, state2Above)) {
			BlockPos tipPos = findTip(state, level, pos, 7, false);

			if (tipPos != null) {
				BlockState tipState = level.getBlockState(tipPos);

				if (canDrip(level, tipPos, tipState) && canTipGrow(tipState, level, tipPos))
					grow(level, tipPos, Direction.DOWN);
			}
		}
	}

	public void growStalagmiteBelow(ServerLevel level, BlockPos pos) {
		BlockPos.MutableBlockPos mutablePos = pos.mutable();

		for(int i = 0; i < 10; ++i) {
			BlockState stateToCheck;

			mutablePos.move(Direction.DOWN);
			stateToCheck = level.getBlockState(mutablePos);

			if (!stateToCheck.getFluidState().isEmpty())
				return;

			if (isUnmergedTipWithDirection(stateToCheck, Direction.UP) && canTipGrow(stateToCheck, level, mutablePos)) {
				grow(level, mutablePos, Direction.UP);
				return;
			}

			if (isValidIceSpikePlacement(level, mutablePos, Direction.UP) && !level.isWaterAt(mutablePos.below())) {
				grow(level, mutablePos.below(), Direction.UP);
				return;
			}
		}
	}

	public void grow(ServerLevel level, BlockPos pos, Direction direction) {
		BlockPos relativePos = pos.relative(direction);
		BlockState relativeState = level.getBlockState(relativePos);

		if (isUnmergedTipWithDirection(relativeState, direction.getOpposite()))
			createMergedTips(relativeState, level, relativePos);
		else if (relativeState.isAir() || relativeState.is(Blocks.WATER))
			createIceSpike(level, relativePos, direction, DripstoneThickness.TIP);
	}

	public void createIceSpike(LevelAccessor level, BlockPos pos, Direction direction, DripstoneThickness thickness) {
		level.setBlock(pos, defaultBlockState().setValue(TIP_DIRECTION, direction).setValue(THICKNESS, thickness).setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER), 3);
	}

	public void createMergedTips(BlockState state, LevelAccessor level, BlockPos pos) {
		BlockPos lowerPos;
		BlockPos upperPos;

		if (state.getValue(TIP_DIRECTION) == Direction.UP) {
			upperPos = pos;
			lowerPos = pos.above();
		}
		else {
			lowerPos = pos;
			upperPos = pos.below();
		}

		createIceSpike(level, lowerPos, Direction.DOWN, DripstoneThickness.TIP_MERGE);
		createIceSpike(level, upperPos, Direction.UP, DripstoneThickness.TIP_MERGE);
	}

	public void spawnDripParticle(Level level, BlockPos pos, BlockState state) {
		Vec3 vec3 = state.getOffset(level, pos);
		double d1 = pos.getX() + 0.5D + vec3.x;
		double d2 = pos.getY() + 1 - 0.6875F - 0.0625D;
		double d3 = pos.getZ() + 0.5D + vec3.z;
		Fluid fluid = getDripFluid(level, pos);
		ParticleOptions particleoptions = fluid.is(FluidTags.LAVA) ? ParticleTypes.DRIPPING_DRIPSTONE_LAVA : ParticleTypes.DRIPPING_DRIPSTONE_WATER;

		level.addParticle(particleoptions, d1, d2, d3, 0.0D, 0.0D, 0.0D);
	}

	public BlockPos findTip(BlockState state, LevelAccessor level, BlockPos pos, int maxCheckDistance, boolean canBeMerged) {
		if (isTip(state, canBeMerged))
			return pos;
		else {
			Direction direction = state.getValue(TIP_DIRECTION);
			Predicate<BlockState> predicate = stateToCheck -> stateToCheck.is(this) && stateToCheck.getValue(TIP_DIRECTION) == direction;

			return findBlockVertical(level, pos, direction.getAxisDirection(), predicate, stateToCheck -> isTip(stateToCheck, canBeMerged), maxCheckDistance).orElse(null);
		}
	}

	public Direction calculateTipDirection(LevelReader level, BlockPos pos, Direction direction) {
		Direction tipDirection;

		if (isValidIceSpikePlacement(level, pos, direction))
			tipDirection = direction;
		else {
			if (!isValidIceSpikePlacement(level, pos, direction.getOpposite()))
				return null;


			tipDirection = direction.getOpposite();
		}

		return tipDirection;
	}

	public DripstoneThickness calculateThickness(LevelReader level, BlockPos pos, Direction direction, boolean p_154096_) {
		Direction oppositeDirection = direction.getOpposite();
		BlockState state = level.getBlockState(pos.relative(direction));

		if (isIceSpikeWithDirection(state, oppositeDirection))
			return !p_154096_ && state.getValue(THICKNESS) != DripstoneThickness.TIP_MERGE ? DripstoneThickness.TIP : DripstoneThickness.TIP_MERGE;
		else if (!isIceSpikeWithDirection(state, direction))
			return DripstoneThickness.TIP;
		else {
			DripstoneThickness thickness = state.getValue(THICKNESS);

			if (thickness != DripstoneThickness.TIP && thickness != DripstoneThickness.TIP_MERGE) {
				BlockState oppositeState = level.getBlockState(pos.relative(oppositeDirection));

				return !isIceSpikeWithDirection(oppositeState, direction) ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
			}
			else
				return DripstoneThickness.FRUSTUM;
		}
	}

	public boolean canDrip(Level level, BlockPos pos, BlockState state) {
		return isStalactite(state) && state.getValue(THICKNESS) == DripstoneThickness.TIP && !state.getValue(WATERLOGGED);
	}

	public boolean canMelt(Level level, BlockPos pos, BlockState state) {
		if (level.getBiome(pos).getBiomeCategory() == BiomeCategory.ICY)
			return false;

		AxisDirection direction = isStalactite(state) ? AxisDirection.POSITIVE : AxisDirection.NEGATIVE;
		Direction tipDirection = state.getValue(TIP_DIRECTION);
		Predicate<BlockState> predicate = stateToCheck -> stateToCheck.is(this) && stateToCheck.getValue(TIP_DIRECTION) == tipDirection;

		//allow ice spikes to not melt in warm biomes if there is an ice block placed above/below it
		return !findBlockVertical(level, pos, direction, predicate, stateToCheck -> stateToCheck.is(BlockTags.ICE), 11).isPresent();
	}

	public boolean canTipGrow(BlockState state, ServerLevel level, BlockPos pos) {
		Direction tipDirection = state.getValue(TIP_DIRECTION);
		BlockPos posNextToTip = pos.relative(tipDirection);
		BlockState stateNextToTip = level.getBlockState(posNextToTip);

		if (!stateNextToTip.getFluidState().isEmpty())
			return false;
		else
			return stateNextToTip.isAir() ? true : isUnmergedTipWithDirection(stateNextToTip, tipDirection.getOpposite());
	}

	public Optional<BlockPos> findRootBlock(Level level, BlockPos pos, BlockState state, int maxCheckDistance) {
		Direction tipDirection = state.getValue(TIP_DIRECTION);
		Predicate<BlockState> predicate = stateToCheck -> stateToCheck.is(this) && stateToCheck.getValue(TIP_DIRECTION) == tipDirection;

		return findBlockVertical(level, pos, tipDirection.getOpposite().getAxisDirection(), predicate, stateToCheck -> !stateToCheck.is(this), maxCheckDistance);
	}

	public boolean isValidIceSpikePlacement(LevelReader level, BlockPos state, Direction direction) {
		BlockPos oppositePos = state.relative(direction.getOpposite());
		BlockState oppositeState = level.getBlockState(oppositePos);

		return oppositeState.isFaceSturdy(level, oppositePos, direction) || isIceSpikeWithDirection(oppositeState, direction);
	}

	public boolean isTip(BlockState state, boolean canBeMerged) {
		if (!state.is(this)) {
			return false;
		} else {
			DripstoneThickness thickness = state.getValue(THICKNESS);
			return thickness == DripstoneThickness.TIP || canBeMerged && thickness == DripstoneThickness.TIP_MERGE;
		}
	}

	public boolean isUnmergedTipWithDirection(BlockState state, Direction direction) {
		return isTip(state, false) && state.getValue(TIP_DIRECTION) == direction;
	}

	public boolean isStalactite(BlockState state) {
		return isIceSpikeWithDirection(state, Direction.DOWN);
	}

	public boolean isStalagmite(BlockState state) {
		return isIceSpikeWithDirection(state, Direction.UP);
	}

	public boolean isStalactiteStartPos(BlockState state, LevelReader level, BlockPos pos) {
		return isStalactite(state) && !level.getBlockState(pos.above()).is(this);
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType pathComputationType) {
		return false;
	}

	public boolean isIceSpikeWithDirection(BlockState state, Direction direction) {
		return state.is(this) && state.getValue(TIP_DIRECTION) == direction;
	}

	public Optional<Fluid> getFluidAboveStalactite(Level level, BlockPos pos, BlockState state) {
		return !isStalactite(state) ? Optional.empty() : findRootBlock(level, pos, state, 11).map(posToCheck -> level.getFluidState(posToCheck.above()).getType());
	}

	public boolean canGrow(Level level, BlockPos pos, BlockState stateAbove, BlockState state2Above) {
		return (stateAbove.is(BlockTags.ICE) || (state2Above.is(Blocks.WATER) && state2Above.getFluidState().isSource())) && level.getBiome(pos).getBiomeCategory() == BiomeCategory.ICY;
	}

	public Fluid getDripFluid(Level level, BlockPos pos) {
		return level.getBiome(pos).getBiomeCategory() == BiomeCategory.ICY ? Fluids.EMPTY : Fluids.WATER;
	}

	public Optional<BlockPos> findBlockVertical(LevelAccessor level, BlockPos pos, Direction.AxisDirection axisDirection, Predicate<BlockState> statePredicate1, Predicate<BlockState> statePredicate2, int maxCheckDistance) {
		Direction direction = Direction.get(axisDirection, Direction.Axis.Y);
		BlockPos.MutableBlockPos mutablePos = pos.mutable();

		for(int i = 1; i < maxCheckDistance; ++i) {
			BlockState stateToCheck;

			mutablePos.move(direction);
			stateToCheck = level.getBlockState(mutablePos);

			if (statePredicate2.test(stateToCheck))
				return Optional.of(mutablePos.immutable());

			if (level.isOutsideBuildHeight(mutablePos.getY()) || !statePredicate1.test(stateToCheck))
				return Optional.empty();
		}

		return Optional.empty();
	}
}