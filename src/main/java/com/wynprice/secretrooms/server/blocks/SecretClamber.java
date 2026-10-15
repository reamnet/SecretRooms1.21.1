package com.wynprice.secretrooms.server.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class SecretClamber extends GhostBlock {
    public static final com.mojang.serialization.MapCodec<SecretClamber> CODEC = simpleCodec(SecretClamber::new);

    @Override
    public com.mojang.serialization.MapCodec<? extends SecretClamber> codec() { return CODEC; }

    public SecretClamber(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader world, BlockPos pos, LivingEntity entity) {
        return true;
    }
}
