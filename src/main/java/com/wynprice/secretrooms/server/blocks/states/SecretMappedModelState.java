package com.wynprice.secretrooms.server.blocks.states;

import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SecretMappedModelState extends SecretBaseState {
    public SecretMappedModelState(Block block, Reference2ObjectArrayMap<Property<?>, Comparable<?>> propertiesToValueMap, MapCodec<BlockState> codec) {
        super(block, propertiesToValueMap, codec);
    }

    @Override
    public VoxelShape getFaceOcclusionShape(BlockGetter worldIn, BlockPos p, Direction directionIn) {
        return Shapes.getFaceShape(this.getOcclusionShape(worldIn, p), directionIn);
    }
    
}
