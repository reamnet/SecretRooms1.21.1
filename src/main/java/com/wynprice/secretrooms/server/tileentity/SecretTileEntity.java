package com.wynprice.secretrooms.server.tileentity;

import com.wynprice.secretrooms.client.SecretModelData;
import com.wynprice.secretrooms.server.blocks.SecretBaseBlock;
import com.wynprice.secretrooms.server.data.SecretData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SecretTileEntity extends BlockEntity {

    private final SecretData data = new SecretData(this);

    public SecretTileEntity(BlockPos p_155115_, BlockState p_155116_) {
        super(SecretTileEntities.SECRET_TILE_ENTITY.get(), p_155115_, p_155116_);
    }

    public SecretTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.put("secret_data", this.data.writeNBT(new CompoundTag()));
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
        this.data.readNBT(nbt.getCompound("secret_data"));
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }


    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        this.loadWithComponents(tag, registries);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider registries) {
        this.handleUpdateTag(pkt.getTag(), registries);
    }

    @Nonnull
    @Override
    public ModelData getModelData() {
        if(this.isRemoved() || this.level == null) {
            return super.getModelData();
        }
        ModelData.Builder builder = ModelData.builder()
                .with(SecretModelData.SRM_BLOCKSTATE, this.data.getBlockState());
        BlockState state = this.level.getBlockState(this.worldPosition);
        if(state.getBlock() instanceof SecretBaseBlock) {
            ((SecretBaseBlock) state.getBlock()).applyExtraModelData(this.level, this.worldPosition, state, builder);
        }
        return builder.build();
    }

    public SecretData getData() {
        return this.data;
    }
}
