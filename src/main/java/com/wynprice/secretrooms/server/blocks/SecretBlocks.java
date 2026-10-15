package com.wynprice.secretrooms.server.blocks;

import com.wynprice.secretrooms.SecretRooms6;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;

import java.util.function.Supplier;

public class SecretBlocks {

    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(Registries.BLOCK, SecretRooms6.MODID);

    public static final Supplier<Block> GHOST_BLOCK = REGISTRY.register("ghost_block", () -> new GhostBlock(Block.Properties.of().mapColor(MapColor.STONE).noOcclusion()));
    public static final Supplier<Block> SECRET_STAIRS = REGISTRY.register("secret_stairs", () -> new SecretStairs(Block.Properties.of().mapColor(MapColor.STONE).noOcclusion()));
    public static final Supplier<Block> SECRET_LEVER = REGISTRY.register("secret_lever", () -> new SecretLever(Block.Properties.of().mapColor(MapColor.STONE).noOcclusion()));
    public static final Supplier<Block> SECRET_REDSTONE = REGISTRY.register("secret_redstone", () -> new SecretRedstone(Block.Properties.of().mapColor(MapColor.STONE).noOcclusion()));
    public static final Supplier<Block> ONE_WAY_GLASS = REGISTRY.register("one_way_glass", () -> new OneWayGlass(Block.Properties.of().mapColor(MapColor.STONE).noOcclusion()));
    public static final Supplier<Block> SECRET_WOODEN_BUTTON = REGISTRY.register("secret_wooden_button", () -> new SecretButton(Block.Properties.of().mapColor(MapColor.STONE).noOcclusion(), true));
    public static final Supplier<Block> SECRET_STONE_BUTTON = REGISTRY.register("secret_stone_button", () -> new SecretButton(Block.Properties.of().mapColor(MapColor.STONE).noOcclusion(), false));
    public static final Supplier<Block> TORCH_LEVER = REGISTRY.register("torch_lever", () -> new TorchLever(Block.Properties.of().mapColor(MapColor.WOOD).lightLevel((blockstate) -> 14).noOcclusion()));
    public static final Supplier<Block> WALL_TORCH_LEVER = REGISTRY.register("wall_torch_lever", () -> new WallTorchLever(Block.Properties.of().mapColor(MapColor.WOOD).lightLevel((blockstate) -> 14).noOcclusion()));
    public static final Supplier<Block> SECRET_PRESSURE_PLATE = REGISTRY.register("secret_pressure_plate", () -> new SecretPressurePlate(Block.Properties.of().mapColor(MapColor.STONE).noOcclusion(), false));
    public static final Supplier<Block> SECRET_PLAYER_PRESSURE_PLATE = REGISTRY.register("secret_player_pressure_plate", () -> new SecretPressurePlate(Block.Properties.of().mapColor(MapColor.STONE).noOcclusion(), true));
    public static final Supplier<Block> SECRET_DOOR = REGISTRY.register("secret_door", () -> new SecretDoor(Block.Properties.of().mapColor(MapColor.STONE).noOcclusion()));
    public static final Supplier<Block> SECRET_IRON_DOOR = REGISTRY.register("secret_iron_door", () -> new SecretDoor(Block.Properties.of().mapColor(MapColor.METAL).noOcclusion(), true));
    public static final Supplier<Block> SECRET_CHEST = REGISTRY.register("secret_chest", () -> new SecretChest(Block.Properties.of().mapColor(MapColor.STONE).noOcclusion()));
    public static final Supplier<Block> SECRET_TRAPDOOR = REGISTRY.register("secret_trapdoor", () -> new SecretTrapdoor(Block.Properties.of().mapColor(MapColor.STONE).noOcclusion()));
    public static final Supplier<Block> SECRET_IRON_TRAPDOOR = REGISTRY.register("secret_iron_trapdoor", () -> new SecretTrapdoor(Block.Properties.of().mapColor(MapColor.METAL).noOcclusion(), true));
    public static final Supplier<Block> SECRET_TRAPPED_CHEST = REGISTRY.register("secret_trapped_chest", () -> new SecretTrappedChest(Block.Properties.of().mapColor(MapColor.STONE).noOcclusion()));
    public static final Supplier<Block> SECRET_GATE = REGISTRY.register("secret_gate", () -> new SecretGateBlock(Block.Properties.of().mapColor(MapColor.STONE).noOcclusion()));
    public static final Supplier<Block> SECRET_DUMMY_BLOCK = REGISTRY.register("secret_dummy_block", () -> new SecretBaseBlock(Block.Properties.of().mapColor(MapColor.STONE).noOcclusion().noLootTable()));
    public static final Supplier<Block> SECRET_DAYLIGHT_DETECTOR = REGISTRY.register("secret_daylight_detector", () -> new SecretDaylightDetector(Block.Properties.of().mapColor(MapColor.STONE).noOcclusion()));
    public static final Supplier<Block> SECRET_OBSERVER = REGISTRY.register("secret_observer", () -> new SecretObserver(Block.Properties.of().mapColor(MapColor.STONE).noOcclusion()));
    public static final Supplier<Block> SECRET_CLAMBER = REGISTRY.register("secret_clamber", () -> new SecretClamber(Block.Properties.of().mapColor(MapColor.STONE).noOcclusion()));

}
