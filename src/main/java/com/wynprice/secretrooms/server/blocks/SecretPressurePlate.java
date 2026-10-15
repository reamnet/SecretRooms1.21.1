package com.wynprice.secretrooms.server.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;

import java.util.function.Predicate;

public class SecretPressurePlate extends AbstractSecretPressurePlateBase {
    public static final com.mojang.serialization.MapCodec<SecretPressurePlate> CODEC = com.mojang.serialization.codecs.RecordCodecBuilder.mapCodec(instance -> instance.group(
        com.mojang.serialization.Codec.BOOL.optionalFieldOf("onlyPlayers", false).forGetter(block -> block.onlyPlayers), propertiesCodec()
    ).apply(instance, (flag, properties) -> new SecretPressurePlate(properties, flag)));

    @Override
    public com.mojang.serialization.MapCodec<? extends SecretPressurePlate> codec() { return CODEC; }


    private static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    private final boolean onlyPlayers;
    private final Predicate<Entity> entityPredicate;
    public SecretPressurePlate(Properties properties, boolean onlyPlayers) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, false));
        this.onlyPlayers = onlyPlayers;
        this.entityPredicate = entity -> !onlyPlayers || entity instanceof net.minecraft.world.entity.player.Player;
    }

    @Override
    protected void playClickOnSound(LevelAccessor worldIn, BlockPos pos) {
        worldIn.playSound(null, pos, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON, SoundSource.BLOCKS, 0.3F, 0.8F);
    }

    @Override
    protected void playClickOffSound(LevelAccessor worldIn, BlockPos pos) {
        worldIn.playSound(null, pos, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundSource.BLOCKS, 0.3F, 0.7F);
    }

    @Override
    protected int computeRedstoneStrength(Level worldIn, BlockPos pos) {
        return worldIn.getEntitiesOfClass(Entity.class, new AABB(pos).expandTowards(0, 0.25, 0), this.entityPredicate).isEmpty() ? 0 : 15;
    }

    @Override
    protected int getRedstoneStrength(BlockState state) {
        return state.getValue(POWERED) ? 15 : 0;
    }

    @Override
    protected BlockState setRedstoneStrength(BlockState state, int strength) {
        return state.setValue(POWERED, strength > 0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(POWERED);
    }
}
