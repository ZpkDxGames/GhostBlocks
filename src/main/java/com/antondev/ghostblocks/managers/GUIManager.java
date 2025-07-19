package com.antondev.ghostblocks.managers;

import com.antondev.ghostblocks.GhostBlocksPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GUIManager {
    private final GhostBlocksPlugin plugin;
    private final Map<String, List<Material>> categories;
    private final Map<Integer, String> categorySlots;
    private final Map<UUID, String> playerCurrentCategory; // Track current category per player
    private final Map<UUID, Integer> playerCurrentPage; // Track current page per player
    private final int BLOCKS_PER_PAGE = 36; // 4 rows * 9 columns (leaving space for navigation)
    
    public GUIManager(GhostBlocksPlugin plugin) {
        this.plugin = plugin;
        this.categories = new HashMap<>();
        this.categorySlots = new HashMap<>();
        this.playerCurrentCategory = new HashMap<>();
        this.playerCurrentPage = new HashMap<>();
        initializeCategories();
    }
    
    private void initializeCategories() {
        // Building blocks category
        List<Material> buildingBlocks = new ArrayList<>();
        buildingBlocks.add(Material.STONE);
        buildingBlocks.add(Material.GRANITE);
        buildingBlocks.add(Material.POLISHED_GRANITE);
        buildingBlocks.add(Material.DIORITE);
        buildingBlocks.add(Material.POLISHED_DIORITE);
        buildingBlocks.add(Material.ANDESITE);
        buildingBlocks.add(Material.POLISHED_ANDESITE);
        buildingBlocks.add(Material.COBBLESTONE);
        buildingBlocks.add(Material.MOSSY_COBBLESTONE);
        buildingBlocks.add(Material.SMOOTH_STONE);
        buildingBlocks.add(Material.STONE_BRICKS);
        buildingBlocks.add(Material.MOSSY_STONE_BRICKS);
        buildingBlocks.add(Material.CRACKED_STONE_BRICKS);
        buildingBlocks.add(Material.CHISELED_STONE_BRICKS);
        buildingBlocks.add(Material.DEEPSLATE);
        buildingBlocks.add(Material.COBBLED_DEEPSLATE);
        buildingBlocks.add(Material.POLISHED_DEEPSLATE);
        buildingBlocks.add(Material.DEEPSLATE_BRICKS);
        buildingBlocks.add(Material.DEEPSLATE_TILES);
        buildingBlocks.add(Material.CHISELED_DEEPSLATE);
        buildingBlocks.add(Material.CRACKED_DEEPSLATE_BRICKS);
        buildingBlocks.add(Material.CRACKED_DEEPSLATE_TILES);
        buildingBlocks.add(Material.SANDSTONE);
        buildingBlocks.add(Material.SMOOTH_SANDSTONE);
        buildingBlocks.add(Material.CHISELED_SANDSTONE);
        buildingBlocks.add(Material.CUT_SANDSTONE);
        buildingBlocks.add(Material.RED_SANDSTONE);
        buildingBlocks.add(Material.SMOOTH_RED_SANDSTONE);
        buildingBlocks.add(Material.CHISELED_RED_SANDSTONE);
        buildingBlocks.add(Material.CUT_RED_SANDSTONE);
        buildingBlocks.add(Material.BRICKS);
        buildingBlocks.add(Material.NETHER_BRICKS);
        buildingBlocks.add(Material.CHISELED_NETHER_BRICKS);
        buildingBlocks.add(Material.CRACKED_NETHER_BRICKS);
        buildingBlocks.add(Material.RED_NETHER_BRICKS);
        buildingBlocks.add(Material.PRISMARINE);
        buildingBlocks.add(Material.PRISMARINE_BRICKS);
        buildingBlocks.add(Material.DARK_PRISMARINE);
        buildingBlocks.add(Material.PURPUR_BLOCK);
        buildingBlocks.add(Material.PURPUR_PILLAR);
        buildingBlocks.add(Material.END_STONE);
        buildingBlocks.add(Material.END_STONE_BRICKS);
        buildingBlocks.add(Material.QUARTZ_BLOCK);
        buildingBlocks.add(Material.SMOOTH_QUARTZ);
        buildingBlocks.add(Material.CHISELED_QUARTZ_BLOCK);
        buildingBlocks.add(Material.QUARTZ_PILLAR);
        buildingBlocks.add(Material.QUARTZ_BRICKS);
        buildingBlocks.add(Material.BLACKSTONE);
        buildingBlocks.add(Material.POLISHED_BLACKSTONE);
        buildingBlocks.add(Material.CHISELED_POLISHED_BLACKSTONE);
        buildingBlocks.add(Material.POLISHED_BLACKSTONE_BRICKS);
        buildingBlocks.add(Material.CRACKED_POLISHED_BLACKSTONE_BRICKS);
        buildingBlocks.add(Material.GILDED_BLACKSTONE);
        buildingBlocks.add(Material.BASALT);
        buildingBlocks.add(Material.SMOOTH_BASALT);
        buildingBlocks.add(Material.POLISHED_BASALT);
        buildingBlocks.add(Material.TUFF);
        buildingBlocks.add(Material.POLISHED_TUFF);
        buildingBlocks.add(Material.TUFF_BRICKS);
        buildingBlocks.add(Material.CHISELED_TUFF);
        buildingBlocks.add(Material.CHISELED_TUFF_BRICKS);
        buildingBlocks.add(Material.CALCITE);
        buildingBlocks.add(Material.DRIPSTONE_BLOCK);
        buildingBlocks.add(Material.COPPER_BLOCK);
        buildingBlocks.add(Material.EXPOSED_COPPER);
        buildingBlocks.add(Material.WEATHERED_COPPER);
        buildingBlocks.add(Material.OXIDIZED_COPPER);
        buildingBlocks.add(Material.WAXED_COPPER_BLOCK);
        buildingBlocks.add(Material.WAXED_EXPOSED_COPPER);
        buildingBlocks.add(Material.WAXED_WEATHERED_COPPER);
        buildingBlocks.add(Material.WAXED_OXIDIZED_COPPER);
        buildingBlocks.add(Material.CUT_COPPER);
        buildingBlocks.add(Material.EXPOSED_CUT_COPPER);
        buildingBlocks.add(Material.WEATHERED_CUT_COPPER);
        buildingBlocks.add(Material.OXIDIZED_CUT_COPPER);
        buildingBlocks.add(Material.WAXED_CUT_COPPER);
        buildingBlocks.add(Material.WAXED_EXPOSED_CUT_COPPER);
        buildingBlocks.add(Material.WAXED_WEATHERED_CUT_COPPER);
        buildingBlocks.add(Material.WAXED_OXIDIZED_CUT_COPPER);
        buildingBlocks.add(Material.CHISELED_COPPER);
        buildingBlocks.add(Material.EXPOSED_CHISELED_COPPER);
        buildingBlocks.add(Material.WEATHERED_CHISELED_COPPER);
        buildingBlocks.add(Material.OXIDIZED_CHISELED_COPPER);
        buildingBlocks.add(Material.WAXED_CHISELED_COPPER);
        buildingBlocks.add(Material.WAXED_EXPOSED_CHISELED_COPPER);
        buildingBlocks.add(Material.WAXED_WEATHERED_CHISELED_COPPER);
        buildingBlocks.add(Material.WAXED_OXIDIZED_CHISELED_COPPER);
        buildingBlocks.add(Material.COPPER_GRATE);
        buildingBlocks.add(Material.EXPOSED_COPPER_GRATE);
        buildingBlocks.add(Material.WEATHERED_COPPER_GRATE);
        buildingBlocks.add(Material.OXIDIZED_COPPER_GRATE);
        buildingBlocks.add(Material.WAXED_COPPER_GRATE);
        buildingBlocks.add(Material.WAXED_EXPOSED_COPPER_GRATE);
        buildingBlocks.add(Material.WAXED_WEATHERED_COPPER_GRATE);
        buildingBlocks.add(Material.WAXED_OXIDIZED_COPPER_GRATE);
        buildingBlocks.add(Material.IRON_BLOCK);
        buildingBlocks.add(Material.GOLD_BLOCK);
        buildingBlocks.add(Material.DIAMOND_BLOCK);
        buildingBlocks.add(Material.EMERALD_BLOCK);
        buildingBlocks.add(Material.NETHERITE_BLOCK);
        buildingBlocks.add(Material.LAPIS_BLOCK);
        buildingBlocks.add(Material.REDSTONE_BLOCK);
        buildingBlocks.add(Material.COAL_BLOCK);
        // Add ores
        buildingBlocks.add(Material.COAL_ORE);
        buildingBlocks.add(Material.DEEPSLATE_COAL_ORE);
        buildingBlocks.add(Material.IRON_ORE);
        buildingBlocks.add(Material.DEEPSLATE_IRON_ORE);
        buildingBlocks.add(Material.COPPER_ORE);
        buildingBlocks.add(Material.DEEPSLATE_COPPER_ORE);
        buildingBlocks.add(Material.GOLD_ORE);
        buildingBlocks.add(Material.DEEPSLATE_GOLD_ORE);
        buildingBlocks.add(Material.NETHER_GOLD_ORE);
        buildingBlocks.add(Material.REDSTONE_ORE);
        buildingBlocks.add(Material.DEEPSLATE_REDSTONE_ORE);
        buildingBlocks.add(Material.EMERALD_ORE);
        buildingBlocks.add(Material.DEEPSLATE_EMERALD_ORE);
        buildingBlocks.add(Material.LAPIS_ORE);
        buildingBlocks.add(Material.DEEPSLATE_LAPIS_ORE);
        buildingBlocks.add(Material.DIAMOND_ORE);
        buildingBlocks.add(Material.DEEPSLATE_DIAMOND_ORE);
        buildingBlocks.add(Material.NETHER_QUARTZ_ORE);
        buildingBlocks.add(Material.ANCIENT_DEBRIS);
        // Add raw ore blocks
        buildingBlocks.add(Material.RAW_IRON_BLOCK);
        buildingBlocks.add(Material.RAW_COPPER_BLOCK);
        buildingBlocks.add(Material.RAW_GOLD_BLOCK);
        // Add additional building materials
        buildingBlocks.add(Material.AMETHYST_BLOCK);
        buildingBlocks.add(Material.BUDDING_AMETHYST);
        buildingBlocks.add(Material.BONE_BLOCK);
        buildingBlocks.add(Material.HAY_BLOCK);
        buildingBlocks.add(Material.DRIED_KELP_BLOCK);
        buildingBlocks.add(Material.HONEYCOMB_BLOCK);
        buildingBlocks.add(Material.PACKED_MUD);
        buildingBlocks.add(Material.MUD_BRICKS);
        buildingBlocks.add(Material.REINFORCED_DEEPSLATE);
        buildingBlocks.add(Material.CHISELED_DEEPSLATE);
        buildingBlocks.add(Material.SMOOTH_STONE_SLAB);
        buildingBlocks.add(Material.COBBLESTONE_SLAB);
        buildingBlocks.add(Material.STONE_BRICK_SLAB);
        buildingBlocks.add(Material.SANDSTONE_SLAB);
        buildingBlocks.add(Material.RED_SANDSTONE_SLAB);
        buildingBlocks.add(Material.QUARTZ_SLAB);
        buildingBlocks.add(Material.BRICK_SLAB);
        buildingBlocks.add(Material.NETHER_BRICK_SLAB);
        buildingBlocks.add(Material.PRISMARINE_SLAB);
        buildingBlocks.add(Material.PRISMARINE_BRICK_SLAB);
        buildingBlocks.add(Material.DARK_PRISMARINE_SLAB);
        buildingBlocks.add(Material.PURPUR_SLAB);
        buildingBlocks.add(Material.END_STONE_BRICK_SLAB);
        buildingBlocks.add(Material.POLISHED_GRANITE_SLAB);
        buildingBlocks.add(Material.POLISHED_DIORITE_SLAB);
        buildingBlocks.add(Material.POLISHED_ANDESITE_SLAB);
        buildingBlocks.add(Material.COBBLED_DEEPSLATE_SLAB);
        buildingBlocks.add(Material.POLISHED_DEEPSLATE_SLAB);
        buildingBlocks.add(Material.DEEPSLATE_BRICK_SLAB);
        buildingBlocks.add(Material.DEEPSLATE_TILE_SLAB);
        buildingBlocks.add(Material.BLACKSTONE_SLAB);
        buildingBlocks.add(Material.POLISHED_BLACKSTONE_SLAB);
        buildingBlocks.add(Material.POLISHED_BLACKSTONE_BRICK_SLAB);
        buildingBlocks.add(Material.CUT_COPPER_SLAB);
        buildingBlocks.add(Material.EXPOSED_CUT_COPPER_SLAB);
        buildingBlocks.add(Material.WEATHERED_CUT_COPPER_SLAB);
        buildingBlocks.add(Material.OXIDIZED_CUT_COPPER_SLAB);
        buildingBlocks.add(Material.WAXED_CUT_COPPER_SLAB);
        buildingBlocks.add(Material.WAXED_EXPOSED_CUT_COPPER_SLAB);
        buildingBlocks.add(Material.WAXED_WEATHERED_CUT_COPPER_SLAB);
        buildingBlocks.add(Material.WAXED_OXIDIZED_CUT_COPPER_SLAB);
        buildingBlocks.add(Material.TUFF_SLAB);
        buildingBlocks.add(Material.POLISHED_TUFF_SLAB);
        buildingBlocks.add(Material.TUFF_BRICK_SLAB);
        buildingBlocks.add(Material.CUT_SANDSTONE_SLAB);
        buildingBlocks.add(Material.CUT_RED_SANDSTONE_SLAB);
        buildingBlocks.add(Material.SMOOTH_RED_SANDSTONE_SLAB);
        buildingBlocks.add(Material.SMOOTH_QUARTZ_SLAB);
        buildingBlocks.add(Material.SMOOTH_SANDSTONE_SLAB);
        buildingBlocks.add(Material.RED_NETHER_BRICK_SLAB);
        buildingBlocks.add(Material.MOSSY_STONE_BRICK_SLAB);
        buildingBlocks.add(Material.MOSSY_COBBLESTONE_SLAB);
        buildingBlocks.add(Material.GRANITE_SLAB);
        buildingBlocks.add(Material.DIORITE_SLAB);
        buildingBlocks.add(Material.ANDESITE_SLAB);
        buildingBlocks.add(Material.STONE_SLAB);
        buildingBlocks.add(Material.MUD_BRICK_SLAB);
        
        // Add stone/brick stairs variants
        buildingBlocks.add(Material.COBBLESTONE_STAIRS);
        buildingBlocks.add(Material.STONE_STAIRS);
        buildingBlocks.add(Material.STONE_BRICK_STAIRS);
        buildingBlocks.add(Material.MOSSY_STONE_BRICK_STAIRS);
        buildingBlocks.add(Material.MOSSY_COBBLESTONE_STAIRS);
        buildingBlocks.add(Material.GRANITE_STAIRS);
        buildingBlocks.add(Material.POLISHED_GRANITE_STAIRS);
        buildingBlocks.add(Material.DIORITE_STAIRS);
        buildingBlocks.add(Material.POLISHED_DIORITE_STAIRS);
        buildingBlocks.add(Material.ANDESITE_STAIRS);
        buildingBlocks.add(Material.POLISHED_ANDESITE_STAIRS);
        buildingBlocks.add(Material.BRICK_STAIRS);
        buildingBlocks.add(Material.SANDSTONE_STAIRS);
        buildingBlocks.add(Material.SMOOTH_SANDSTONE_STAIRS);
        buildingBlocks.add(Material.RED_SANDSTONE_STAIRS);
        buildingBlocks.add(Material.SMOOTH_RED_SANDSTONE_STAIRS);
        buildingBlocks.add(Material.QUARTZ_STAIRS);
        buildingBlocks.add(Material.SMOOTH_QUARTZ_STAIRS);
        buildingBlocks.add(Material.NETHER_BRICK_STAIRS);
        buildingBlocks.add(Material.RED_NETHER_BRICK_STAIRS);
        buildingBlocks.add(Material.BLACKSTONE_STAIRS);
        buildingBlocks.add(Material.POLISHED_BLACKSTONE_STAIRS);
        buildingBlocks.add(Material.POLISHED_BLACKSTONE_BRICK_STAIRS);
        buildingBlocks.add(Material.CUT_COPPER_STAIRS);
        buildingBlocks.add(Material.EXPOSED_CUT_COPPER_STAIRS);
        buildingBlocks.add(Material.WEATHERED_CUT_COPPER_STAIRS);
        buildingBlocks.add(Material.OXIDIZED_CUT_COPPER_STAIRS);
        buildingBlocks.add(Material.WAXED_CUT_COPPER_STAIRS);
        buildingBlocks.add(Material.WAXED_EXPOSED_CUT_COPPER_STAIRS);
        buildingBlocks.add(Material.WAXED_WEATHERED_CUT_COPPER_STAIRS);
        buildingBlocks.add(Material.WAXED_OXIDIZED_CUT_COPPER_STAIRS);
        buildingBlocks.add(Material.PURPUR_STAIRS);
        buildingBlocks.add(Material.PRISMARINE_STAIRS);
        buildingBlocks.add(Material.PRISMARINE_BRICK_STAIRS);
        buildingBlocks.add(Material.DARK_PRISMARINE_STAIRS);
        buildingBlocks.add(Material.END_STONE_BRICK_STAIRS);
        buildingBlocks.add(Material.TUFF_STAIRS);
        buildingBlocks.add(Material.POLISHED_TUFF_STAIRS);
        buildingBlocks.add(Material.TUFF_BRICK_STAIRS);
        buildingBlocks.add(Material.MUD_BRICK_STAIRS);
        // Note: DEEPSLATE_STAIRS doesn't exist - removed
        buildingBlocks.add(Material.COBBLED_DEEPSLATE_STAIRS);
        buildingBlocks.add(Material.POLISHED_DEEPSLATE_STAIRS);
        buildingBlocks.add(Material.DEEPSLATE_BRICK_STAIRS);
        buildingBlocks.add(Material.DEEPSLATE_TILE_STAIRS);
        categories.put("Building", buildingBlocks);
        
        // Nature blocks category
        List<Material> natureBlocks = new ArrayList<>();
        natureBlocks.add(Material.DIRT);
        natureBlocks.add(Material.COARSE_DIRT);
        natureBlocks.add(Material.PODZOL);
        natureBlocks.add(Material.MYCELIUM);
        natureBlocks.add(Material.GRASS_BLOCK);
        natureBlocks.add(Material.FARMLAND);
        natureBlocks.add(Material.DIRT_PATH);
        natureBlocks.add(Material.SAND);
        natureBlocks.add(Material.RED_SAND);
        natureBlocks.add(Material.GRAVEL);
        natureBlocks.add(Material.CLAY);
        natureBlocks.add(Material.TERRACOTTA);
        natureBlocks.add(Material.WHITE_TERRACOTTA);
        natureBlocks.add(Material.ORANGE_TERRACOTTA);
        natureBlocks.add(Material.MAGENTA_TERRACOTTA);
        natureBlocks.add(Material.LIGHT_BLUE_TERRACOTTA);
        natureBlocks.add(Material.YELLOW_TERRACOTTA);
        natureBlocks.add(Material.LIME_TERRACOTTA);
        natureBlocks.add(Material.PINK_TERRACOTTA);
        natureBlocks.add(Material.GRAY_TERRACOTTA);
        natureBlocks.add(Material.LIGHT_GRAY_TERRACOTTA);
        natureBlocks.add(Material.CYAN_TERRACOTTA);
        natureBlocks.add(Material.PURPLE_TERRACOTTA);
        natureBlocks.add(Material.BLUE_TERRACOTTA);
        natureBlocks.add(Material.BROWN_TERRACOTTA);
        natureBlocks.add(Material.GREEN_TERRACOTTA);
        natureBlocks.add(Material.RED_TERRACOTTA);
        natureBlocks.add(Material.BLACK_TERRACOTTA);
        natureBlocks.add(Material.WHITE_GLAZED_TERRACOTTA);
        natureBlocks.add(Material.ORANGE_GLAZED_TERRACOTTA);
        natureBlocks.add(Material.MAGENTA_GLAZED_TERRACOTTA);
        natureBlocks.add(Material.LIGHT_BLUE_GLAZED_TERRACOTTA);
        natureBlocks.add(Material.YELLOW_GLAZED_TERRACOTTA);
        natureBlocks.add(Material.LIME_GLAZED_TERRACOTTA);
        natureBlocks.add(Material.PINK_GLAZED_TERRACOTTA);
        natureBlocks.add(Material.GRAY_GLAZED_TERRACOTTA);
        natureBlocks.add(Material.LIGHT_GRAY_GLAZED_TERRACOTTA);
        natureBlocks.add(Material.CYAN_GLAZED_TERRACOTTA);
        natureBlocks.add(Material.PURPLE_GLAZED_TERRACOTTA);
        natureBlocks.add(Material.BLUE_GLAZED_TERRACOTTA);
        natureBlocks.add(Material.BROWN_GLAZED_TERRACOTTA);
        natureBlocks.add(Material.GREEN_GLAZED_TERRACOTTA);
        natureBlocks.add(Material.RED_GLAZED_TERRACOTTA);
        natureBlocks.add(Material.BLACK_GLAZED_TERRACOTTA);
        natureBlocks.add(Material.OAK_LOG);
        natureBlocks.add(Material.OAK_WOOD);
        natureBlocks.add(Material.STRIPPED_OAK_LOG);
        natureBlocks.add(Material.STRIPPED_OAK_WOOD);
        natureBlocks.add(Material.OAK_PLANKS);
        natureBlocks.add(Material.SPRUCE_LOG);
        natureBlocks.add(Material.SPRUCE_WOOD);
        natureBlocks.add(Material.STRIPPED_SPRUCE_LOG);
        natureBlocks.add(Material.STRIPPED_SPRUCE_WOOD);
        natureBlocks.add(Material.SPRUCE_PLANKS);
        natureBlocks.add(Material.BIRCH_LOG);
        natureBlocks.add(Material.BIRCH_WOOD);
        natureBlocks.add(Material.STRIPPED_BIRCH_LOG);
        natureBlocks.add(Material.STRIPPED_BIRCH_WOOD);
        natureBlocks.add(Material.BIRCH_PLANKS);
        natureBlocks.add(Material.JUNGLE_LOG);
        natureBlocks.add(Material.JUNGLE_WOOD);
        natureBlocks.add(Material.STRIPPED_JUNGLE_LOG);
        natureBlocks.add(Material.STRIPPED_JUNGLE_WOOD);
        natureBlocks.add(Material.JUNGLE_PLANKS);
        natureBlocks.add(Material.ACACIA_LOG);
        natureBlocks.add(Material.ACACIA_WOOD);
        natureBlocks.add(Material.STRIPPED_ACACIA_LOG);
        natureBlocks.add(Material.STRIPPED_ACACIA_WOOD);
        natureBlocks.add(Material.ACACIA_PLANKS);
        natureBlocks.add(Material.DARK_OAK_LOG);
        natureBlocks.add(Material.DARK_OAK_WOOD);
        natureBlocks.add(Material.STRIPPED_DARK_OAK_LOG);
        natureBlocks.add(Material.STRIPPED_DARK_OAK_WOOD);
        natureBlocks.add(Material.DARK_OAK_PLANKS);
        natureBlocks.add(Material.MANGROVE_LOG);
        natureBlocks.add(Material.MANGROVE_WOOD);
        natureBlocks.add(Material.STRIPPED_MANGROVE_LOG);
        natureBlocks.add(Material.STRIPPED_MANGROVE_WOOD);
        natureBlocks.add(Material.MANGROVE_PLANKS);
        natureBlocks.add(Material.CHERRY_LOG);
        natureBlocks.add(Material.CHERRY_WOOD);
        natureBlocks.add(Material.STRIPPED_CHERRY_LOG);
        natureBlocks.add(Material.STRIPPED_CHERRY_WOOD);
        natureBlocks.add(Material.CHERRY_PLANKS);
        natureBlocks.add(Material.BAMBOO_BLOCK);
        natureBlocks.add(Material.STRIPPED_BAMBOO_BLOCK);
        natureBlocks.add(Material.BAMBOO_PLANKS);
        natureBlocks.add(Material.BAMBOO_MOSAIC);
        // Add slabs for all wood types
        natureBlocks.add(Material.OAK_SLAB);
        natureBlocks.add(Material.SPRUCE_SLAB);
        natureBlocks.add(Material.BIRCH_SLAB);
        natureBlocks.add(Material.JUNGLE_SLAB);
        natureBlocks.add(Material.ACACIA_SLAB);
        natureBlocks.add(Material.DARK_OAK_SLAB);
        natureBlocks.add(Material.MANGROVE_SLAB);
        natureBlocks.add(Material.CHERRY_SLAB);
        natureBlocks.add(Material.BAMBOO_SLAB);
        natureBlocks.add(Material.BAMBOO_MOSAIC_SLAB);
        natureBlocks.add(Material.CRIMSON_SLAB);
        natureBlocks.add(Material.WARPED_SLAB);
        // Add stairs for all wood types
        natureBlocks.add(Material.OAK_STAIRS);
        natureBlocks.add(Material.SPRUCE_STAIRS);
        natureBlocks.add(Material.BIRCH_STAIRS);
        natureBlocks.add(Material.JUNGLE_STAIRS);
        natureBlocks.add(Material.ACACIA_STAIRS);
        natureBlocks.add(Material.DARK_OAK_STAIRS);
        natureBlocks.add(Material.MANGROVE_STAIRS);
        natureBlocks.add(Material.CHERRY_STAIRS);
        natureBlocks.add(Material.BAMBOO_STAIRS);
        natureBlocks.add(Material.BAMBOO_MOSAIC_STAIRS);
        natureBlocks.add(Material.CRIMSON_STAIRS);
        natureBlocks.add(Material.WARPED_STAIRS);
        // Add more nature blocks
        natureBlocks.add(Material.ROOTED_DIRT);
        natureBlocks.add(Material.MUD);
        natureBlocks.add(Material.MUDDY_MANGROVE_ROOTS);
        natureBlocks.add(Material.MANGROVE_ROOTS);
        natureBlocks.add(Material.MOSS_BLOCK);
        natureBlocks.add(Material.MOSS_CARPET);
        natureBlocks.add(Material.AZALEA_LEAVES);
        natureBlocks.add(Material.FLOWERING_AZALEA_LEAVES);
        natureBlocks.add(Material.OAK_LEAVES);
        natureBlocks.add(Material.SPRUCE_LEAVES);
        natureBlocks.add(Material.BIRCH_LEAVES);
        natureBlocks.add(Material.JUNGLE_LEAVES);
        natureBlocks.add(Material.ACACIA_LEAVES);
        natureBlocks.add(Material.DARK_OAK_LEAVES);
        natureBlocks.add(Material.MANGROVE_LEAVES);
        natureBlocks.add(Material.CHERRY_LEAVES);
        // Add mushroom blocks
        natureBlocks.add(Material.BROWN_MUSHROOM_BLOCK);
        natureBlocks.add(Material.RED_MUSHROOM_BLOCK);
        natureBlocks.add(Material.MUSHROOM_STEM);
        // Add ice blocks
        natureBlocks.add(Material.ICE);
        natureBlocks.add(Material.PACKED_ICE);
        natureBlocks.add(Material.BLUE_ICE);
        natureBlocks.add(Material.FROSTED_ICE);
        // Add snow blocks
        natureBlocks.add(Material.SNOW_BLOCK);
        natureBlocks.add(Material.POWDER_SNOW);
        // Add plant blocks - removed individual plant items (sugar_cane, bamboo, kelp, wheat)
        natureBlocks.add(Material.CACTUS);
        natureBlocks.add(Material.MELON);
        natureBlocks.add(Material.PUMPKIN);
        natureBlocks.add(Material.CARVED_PUMPKIN);
        natureBlocks.add(Material.JACK_O_LANTERN);
        // Add crop blocks - removed individual crop items
        natureBlocks.add(Material.HAY_BLOCK);
        natureBlocks.add(Material.CARROTS);
        natureBlocks.add(Material.POTATOES);
        natureBlocks.add(Material.BEETROOTS);
        // Add coral blocks
        natureBlocks.add(Material.TUBE_CORAL_BLOCK);
        natureBlocks.add(Material.BRAIN_CORAL_BLOCK);
        natureBlocks.add(Material.BUBBLE_CORAL_BLOCK);
        natureBlocks.add(Material.FIRE_CORAL_BLOCK);
        natureBlocks.add(Material.HORN_CORAL_BLOCK);
        natureBlocks.add(Material.DEAD_TUBE_CORAL_BLOCK);
        natureBlocks.add(Material.DEAD_BRAIN_CORAL_BLOCK);
        natureBlocks.add(Material.DEAD_BUBBLE_CORAL_BLOCK);
        natureBlocks.add(Material.DEAD_FIRE_CORAL_BLOCK);
        natureBlocks.add(Material.DEAD_HORN_CORAL_BLOCK);
        // Add sponge blocks
        natureBlocks.add(Material.SPONGE);
        natureBlocks.add(Material.WET_SPONGE);
        categories.put("Nature", natureBlocks);
        
        // Redstone category - keeping only full blocks and functional redstone components
        List<Material> redstoneBlocks = new ArrayList<>();
        redstoneBlocks.add(Material.REDSTONE_BLOCK);
        // Removed redstone torch - it's a light source component, not a building block
        redstoneBlocks.add(Material.LEVER);
        // Removed all buttons and pressure plates per request
        redstoneBlocks.add(Material.TRIPWIRE_HOOK);
        redstoneBlocks.add(Material.DISPENSER);
        redstoneBlocks.add(Material.DROPPER);
        redstoneBlocks.add(Material.HOPPER);
        redstoneBlocks.add(Material.CHEST);
        redstoneBlocks.add(Material.TRAPPED_CHEST);
        redstoneBlocks.add(Material.FURNACE);
        redstoneBlocks.add(Material.BLAST_FURNACE);
        redstoneBlocks.add(Material.SMOKER);
        redstoneBlocks.add(Material.OBSERVER);
        redstoneBlocks.add(Material.PISTON);
        redstoneBlocks.add(Material.STICKY_PISTON);
        redstoneBlocks.add(Material.SLIME_BLOCK);
        redstoneBlocks.add(Material.HONEY_BLOCK);
        redstoneBlocks.add(Material.TNT);
        redstoneBlocks.add(Material.NOTE_BLOCK);
        redstoneBlocks.add(Material.JUKEBOX);
        redstoneBlocks.add(Material.REDSTONE_LAMP);
        redstoneBlocks.add(Material.DAYLIGHT_DETECTOR);
        // Removed comparator and repeater - they're complex redstone components, not building blocks
        redstoneBlocks.add(Material.LECTERN);
        redstoneBlocks.add(Material.TARGET);
        redstoneBlocks.add(Material.LIGHTNING_ROD);
        redstoneBlocks.add(Material.COPPER_BULB);
        redstoneBlocks.add(Material.EXPOSED_COPPER_BULB);
        redstoneBlocks.add(Material.WEATHERED_COPPER_BULB);
        redstoneBlocks.add(Material.OXIDIZED_COPPER_BULB);
        redstoneBlocks.add(Material.WAXED_COPPER_BULB);
        redstoneBlocks.add(Material.WAXED_EXPOSED_COPPER_BULB);
        redstoneBlocks.add(Material.WAXED_WEATHERED_COPPER_BULB);
        redstoneBlocks.add(Material.WAXED_OXIDIZED_COPPER_BULB);
        redstoneBlocks.add(Material.CRAFTER);
        redstoneBlocks.add(Material.CALIBRATED_SCULK_SENSOR);
        redstoneBlocks.add(Material.SCULK_SENSOR);
        redstoneBlocks.add(Material.SCULK_SHRIEKER);
        redstoneBlocks.add(Material.SCULK);
        redstoneBlocks.add(Material.SCULK_CATALYST);
        redstoneBlocks.add(Material.SCULK_VEIN);
        // Removed administrative blocks: COMMAND_BLOCK, STRUCTURE_BLOCK, JIGSAW, BARRIER, LIGHT, STRUCTURE_VOID per request
        // Add functional blocks
        redstoneBlocks.add(Material.BREWING_STAND);
        redstoneBlocks.add(Material.CAULDRON);
        redstoneBlocks.add(Material.WATER_CAULDRON);
        redstoneBlocks.add(Material.LAVA_CAULDRON);
        redstoneBlocks.add(Material.POWDER_SNOW_CAULDRON);
        redstoneBlocks.add(Material.COMPOSTER);
        redstoneBlocks.add(Material.BARREL);
        redstoneBlocks.add(Material.CAMPFIRE);
        redstoneBlocks.add(Material.SOUL_CAMPFIRE);
        redstoneBlocks.add(Material.STONECUTTER);
        redstoneBlocks.add(Material.CARTOGRAPHY_TABLE);
        redstoneBlocks.add(Material.FLETCHING_TABLE);
        redstoneBlocks.add(Material.SMITHING_TABLE);
        redstoneBlocks.add(Material.LOOM);
        redstoneBlocks.add(Material.GRINDSTONE);
        redstoneBlocks.add(Material.ENCHANTING_TABLE);
        redstoneBlocks.add(Material.BOOKSHELF);
        redstoneBlocks.add(Material.CHISELED_BOOKSHELF);
        redstoneBlocks.add(Material.ANVIL);
        redstoneBlocks.add(Material.CHIPPED_ANVIL);
        redstoneBlocks.add(Material.DAMAGED_ANVIL);
        redstoneBlocks.add(Material.BEACON);
        redstoneBlocks.add(Material.CONDUIT);
        // Removed doors and trapdoors - not typical redstone building blocks
        // Keep fence gates as they are redstone-powered
        redstoneBlocks.add(Material.OAK_FENCE_GATE);
        redstoneBlocks.add(Material.SPRUCE_FENCE_GATE);
        redstoneBlocks.add(Material.BIRCH_FENCE_GATE);
        redstoneBlocks.add(Material.JUNGLE_FENCE_GATE);
        redstoneBlocks.add(Material.ACACIA_FENCE_GATE);
        redstoneBlocks.add(Material.DARK_OAK_FENCE_GATE);
        redstoneBlocks.add(Material.MANGROVE_FENCE_GATE);
        redstoneBlocks.add(Material.CHERRY_FENCE_GATE);
        redstoneBlocks.add(Material.BAMBOO_FENCE_GATE);
        redstoneBlocks.add(Material.CRIMSON_FENCE_GATE);
        redstoneBlocks.add(Material.WARPED_FENCE_GATE);
        // Add powered rails and tracks
        redstoneBlocks.add(Material.RAIL);
        redstoneBlocks.add(Material.POWERED_RAIL);
        redstoneBlocks.add(Material.DETECTOR_RAIL);
        redstoneBlocks.add(Material.ACTIVATOR_RAIL);
        // Add more mechanical blocks
        redstoneBlocks.add(Material.POWERED_RAIL);
        redstoneBlocks.add(Material.DETECTOR_RAIL);
        redstoneBlocks.add(Material.ACTIVATOR_RAIL);
        // Removed all minecart variants - they're entities, not building blocks
        categories.put("Redstone", redstoneBlocks);
        
        // Decoration category
        List<Material> decorationBlocks = new ArrayList<>();
        decorationBlocks.add(Material.WHITE_WOOL);
        decorationBlocks.add(Material.ORANGE_WOOL);
        decorationBlocks.add(Material.MAGENTA_WOOL);
        decorationBlocks.add(Material.LIGHT_BLUE_WOOL);
        decorationBlocks.add(Material.YELLOW_WOOL);
        decorationBlocks.add(Material.LIME_WOOL);
        decorationBlocks.add(Material.PINK_WOOL);
        decorationBlocks.add(Material.GRAY_WOOL);
        decorationBlocks.add(Material.LIGHT_GRAY_WOOL);
        decorationBlocks.add(Material.CYAN_WOOL);
        decorationBlocks.add(Material.PURPLE_WOOL);
        decorationBlocks.add(Material.BLUE_WOOL);
        decorationBlocks.add(Material.BROWN_WOOL);
        decorationBlocks.add(Material.GREEN_WOOL);
        decorationBlocks.add(Material.RED_WOOL);
        decorationBlocks.add(Material.BLACK_WOOL);
        decorationBlocks.add(Material.WHITE_CARPET);
        decorationBlocks.add(Material.ORANGE_CARPET);
        decorationBlocks.add(Material.MAGENTA_CARPET);
        decorationBlocks.add(Material.LIGHT_BLUE_CARPET);
        decorationBlocks.add(Material.YELLOW_CARPET);
        decorationBlocks.add(Material.LIME_CARPET);
        decorationBlocks.add(Material.PINK_CARPET);
        decorationBlocks.add(Material.GRAY_CARPET);
        decorationBlocks.add(Material.LIGHT_GRAY_CARPET);
        decorationBlocks.add(Material.CYAN_CARPET);
        decorationBlocks.add(Material.PURPLE_CARPET);
        decorationBlocks.add(Material.BLUE_CARPET);
        decorationBlocks.add(Material.BROWN_CARPET);
        decorationBlocks.add(Material.GREEN_CARPET);
        decorationBlocks.add(Material.RED_CARPET);
        decorationBlocks.add(Material.BLACK_CARPET);
        decorationBlocks.add(Material.WHITE_CONCRETE);
        decorationBlocks.add(Material.ORANGE_CONCRETE);
        decorationBlocks.add(Material.MAGENTA_CONCRETE);
        decorationBlocks.add(Material.LIGHT_BLUE_CONCRETE);
        decorationBlocks.add(Material.YELLOW_CONCRETE);
        decorationBlocks.add(Material.LIME_CONCRETE);
        decorationBlocks.add(Material.PINK_CONCRETE);
        decorationBlocks.add(Material.GRAY_CONCRETE);
        decorationBlocks.add(Material.LIGHT_GRAY_CONCRETE);
        decorationBlocks.add(Material.CYAN_CONCRETE);
        decorationBlocks.add(Material.PURPLE_CONCRETE);
        decorationBlocks.add(Material.BLUE_CONCRETE);
        decorationBlocks.add(Material.BROWN_CONCRETE);
        decorationBlocks.add(Material.GREEN_CONCRETE);
        decorationBlocks.add(Material.RED_CONCRETE);
        decorationBlocks.add(Material.BLACK_CONCRETE);
        decorationBlocks.add(Material.WHITE_CONCRETE_POWDER);
        decorationBlocks.add(Material.ORANGE_CONCRETE_POWDER);
        decorationBlocks.add(Material.MAGENTA_CONCRETE_POWDER);
        decorationBlocks.add(Material.LIGHT_BLUE_CONCRETE_POWDER);
        decorationBlocks.add(Material.YELLOW_CONCRETE_POWDER);
        decorationBlocks.add(Material.LIME_CONCRETE_POWDER);
        decorationBlocks.add(Material.PINK_CONCRETE_POWDER);
        decorationBlocks.add(Material.GRAY_CONCRETE_POWDER);
        decorationBlocks.add(Material.LIGHT_GRAY_CONCRETE_POWDER);
        decorationBlocks.add(Material.CYAN_CONCRETE_POWDER);
        decorationBlocks.add(Material.PURPLE_CONCRETE_POWDER);
        decorationBlocks.add(Material.BLUE_CONCRETE_POWDER);
        decorationBlocks.add(Material.BROWN_CONCRETE_POWDER);
        decorationBlocks.add(Material.GREEN_CONCRETE_POWDER);
        decorationBlocks.add(Material.RED_CONCRETE_POWDER);
        decorationBlocks.add(Material.BLACK_CONCRETE_POWDER);
        decorationBlocks.add(Material.WHITE_STAINED_GLASS);
        decorationBlocks.add(Material.ORANGE_STAINED_GLASS);
        decorationBlocks.add(Material.MAGENTA_STAINED_GLASS);
        decorationBlocks.add(Material.LIGHT_BLUE_STAINED_GLASS);
        decorationBlocks.add(Material.YELLOW_STAINED_GLASS);
        decorationBlocks.add(Material.LIME_STAINED_GLASS);
        decorationBlocks.add(Material.PINK_STAINED_GLASS);
        decorationBlocks.add(Material.GRAY_STAINED_GLASS);
        decorationBlocks.add(Material.LIGHT_GRAY_STAINED_GLASS);
        decorationBlocks.add(Material.CYAN_STAINED_GLASS);
        decorationBlocks.add(Material.PURPLE_STAINED_GLASS);
        decorationBlocks.add(Material.BLUE_STAINED_GLASS);
        decorationBlocks.add(Material.BROWN_STAINED_GLASS);
        decorationBlocks.add(Material.GREEN_STAINED_GLASS);
        decorationBlocks.add(Material.RED_STAINED_GLASS);
        decorationBlocks.add(Material.BLACK_STAINED_GLASS);
        decorationBlocks.add(Material.GLASS);
        decorationBlocks.add(Material.TINTED_GLASS);
        decorationBlocks.add(Material.GLOWSTONE);
        decorationBlocks.add(Material.SEA_LANTERN);
        decorationBlocks.add(Material.JACK_O_LANTERN);
        decorationBlocks.add(Material.MAGMA_BLOCK);
        decorationBlocks.add(Material.SOUL_SAND);
        decorationBlocks.add(Material.SOUL_SOIL);
        decorationBlocks.add(Material.NETHERRACK);
        decorationBlocks.add(Material.NETHER_WART_BLOCK);
        decorationBlocks.add(Material.WARPED_WART_BLOCK);
        decorationBlocks.add(Material.CRIMSON_NYLIUM);
        decorationBlocks.add(Material.WARPED_NYLIUM);
        decorationBlocks.add(Material.CRIMSON_STEM);
        decorationBlocks.add(Material.WARPED_STEM);
        decorationBlocks.add(Material.CRIMSON_HYPHAE);
        decorationBlocks.add(Material.WARPED_HYPHAE);
        decorationBlocks.add(Material.STRIPPED_CRIMSON_STEM);
        decorationBlocks.add(Material.STRIPPED_WARPED_STEM);
        decorationBlocks.add(Material.STRIPPED_CRIMSON_HYPHAE);
        decorationBlocks.add(Material.STRIPPED_WARPED_HYPHAE);
        decorationBlocks.add(Material.CRIMSON_PLANKS);
        decorationBlocks.add(Material.WARPED_PLANKS);
        decorationBlocks.add(Material.SHROOMLIGHT);
        // Removed vines - they're growing plant blocks, not solid building blocks
        decorationBlocks.add(Material.CRYING_OBSIDIAN);
        decorationBlocks.add(Material.OBSIDIAN);
        decorationBlocks.add(Material.ANCIENT_DEBRIS);
        decorationBlocks.add(Material.RESPAWN_ANCHOR);
        decorationBlocks.add(Material.LODESTONE);
        decorationBlocks.add(Material.CHISELED_BOOKSHELF);
        decorationBlocks.add(Material.BOOKSHELF);
        decorationBlocks.add(Material.ENCHANTING_TABLE);
        decorationBlocks.add(Material.ANVIL);
        decorationBlocks.add(Material.CHIPPED_ANVIL);
        decorationBlocks.add(Material.DAMAGED_ANVIL);
        decorationBlocks.add(Material.BEACON);
        decorationBlocks.add(Material.CONDUIT);
        decorationBlocks.add(Material.DRAGON_EGG);
        decorationBlocks.add(Material.END_PORTAL_FRAME);
        decorationBlocks.add(Material.SPAWNER);
        decorationBlocks.add(Material.CHEST);
        decorationBlocks.add(Material.ENDER_CHEST);
        decorationBlocks.add(Material.BARREL);
        decorationBlocks.add(Material.SHULKER_BOX);
        decorationBlocks.add(Material.WHITE_SHULKER_BOX);
        decorationBlocks.add(Material.ORANGE_SHULKER_BOX);
        decorationBlocks.add(Material.MAGENTA_SHULKER_BOX);
        decorationBlocks.add(Material.LIGHT_BLUE_SHULKER_BOX);
        decorationBlocks.add(Material.YELLOW_SHULKER_BOX);
        decorationBlocks.add(Material.LIME_SHULKER_BOX);
        decorationBlocks.add(Material.PINK_SHULKER_BOX);
        decorationBlocks.add(Material.GRAY_SHULKER_BOX);
        decorationBlocks.add(Material.LIGHT_GRAY_SHULKER_BOX);
        decorationBlocks.add(Material.CYAN_SHULKER_BOX);
        decorationBlocks.add(Material.PURPLE_SHULKER_BOX);
        decorationBlocks.add(Material.BLUE_SHULKER_BOX);
        decorationBlocks.add(Material.BROWN_SHULKER_BOX);
        decorationBlocks.add(Material.GREEN_SHULKER_BOX);
        decorationBlocks.add(Material.RED_SHULKER_BOX);
        decorationBlocks.add(Material.BLACK_SHULKER_BOX);
        decorationBlocks.add(Material.CRAFTING_TABLE);
        decorationBlocks.add(Material.CARTOGRAPHY_TABLE);
        decorationBlocks.add(Material.FLETCHING_TABLE);
        decorationBlocks.add(Material.SMITHING_TABLE);
        decorationBlocks.add(Material.STONECUTTER);
        decorationBlocks.add(Material.LOOM);
        decorationBlocks.add(Material.GRINDSTONE);
        decorationBlocks.add(Material.BELL);
        decorationBlocks.add(Material.LANTERN);
        decorationBlocks.add(Material.SOUL_LANTERN);
        decorationBlocks.add(Material.CAMPFIRE);
        decorationBlocks.add(Material.SOUL_CAMPFIRE);
        // Removed torches - they're light sources, not decorative building blocks
        decorationBlocks.add(Material.END_ROD);
        decorationBlocks.add(Material.CHAIN);
        decorationBlocks.add(Material.IRON_BARS);
        decorationBlocks.add(Material.GLASS_PANE);
        decorationBlocks.add(Material.WHITE_STAINED_GLASS_PANE);
        decorationBlocks.add(Material.ORANGE_STAINED_GLASS_PANE);
        decorationBlocks.add(Material.MAGENTA_STAINED_GLASS_PANE);
        decorationBlocks.add(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        decorationBlocks.add(Material.YELLOW_STAINED_GLASS_PANE);
        decorationBlocks.add(Material.LIME_STAINED_GLASS_PANE);
        decorationBlocks.add(Material.PINK_STAINED_GLASS_PANE);
        decorationBlocks.add(Material.GRAY_STAINED_GLASS_PANE);
        decorationBlocks.add(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        decorationBlocks.add(Material.CYAN_STAINED_GLASS_PANE);
        decorationBlocks.add(Material.PURPLE_STAINED_GLASS_PANE);
        decorationBlocks.add(Material.BLUE_STAINED_GLASS_PANE);
        decorationBlocks.add(Material.BROWN_STAINED_GLASS_PANE);
        decorationBlocks.add(Material.GREEN_STAINED_GLASS_PANE);
        decorationBlocks.add(Material.RED_STAINED_GLASS_PANE);
        decorationBlocks.add(Material.BLACK_STAINED_GLASS_PANE);
        // Removed all banners - they display patterns which can't be set for ghost blocks
        // Removed all beds - they're furniture items, not building blocks
        // Add fences and walls
        decorationBlocks.add(Material.OAK_FENCE);
        decorationBlocks.add(Material.SPRUCE_FENCE);
        decorationBlocks.add(Material.BIRCH_FENCE);
        decorationBlocks.add(Material.JUNGLE_FENCE);
        decorationBlocks.add(Material.ACACIA_FENCE);
        decorationBlocks.add(Material.DARK_OAK_FENCE);
        decorationBlocks.add(Material.MANGROVE_FENCE);
        decorationBlocks.add(Material.CHERRY_FENCE);
        decorationBlocks.add(Material.BAMBOO_FENCE);
        decorationBlocks.add(Material.CRIMSON_FENCE);
        decorationBlocks.add(Material.WARPED_FENCE);
        decorationBlocks.add(Material.NETHER_BRICK_FENCE);
        decorationBlocks.add(Material.COBBLESTONE_WALL);
        decorationBlocks.add(Material.MOSSY_COBBLESTONE_WALL);
        decorationBlocks.add(Material.STONE_BRICK_WALL);
        decorationBlocks.add(Material.MOSSY_STONE_BRICK_WALL);
        decorationBlocks.add(Material.GRANITE_WALL);
        decorationBlocks.add(Material.DIORITE_WALL);
        decorationBlocks.add(Material.ANDESITE_WALL);
        decorationBlocks.add(Material.SANDSTONE_WALL);
        decorationBlocks.add(Material.RED_SANDSTONE_WALL);
        decorationBlocks.add(Material.BRICK_WALL);
        decorationBlocks.add(Material.PRISMARINE_WALL);
        decorationBlocks.add(Material.RED_NETHER_BRICK_WALL);
        decorationBlocks.add(Material.NETHER_BRICK_WALL);
        decorationBlocks.add(Material.END_STONE_BRICK_WALL);
        decorationBlocks.add(Material.DEEPSLATE_BRICK_WALL);
        decorationBlocks.add(Material.DEEPSLATE_TILE_WALL);
        decorationBlocks.add(Material.COBBLED_DEEPSLATE_WALL);
        decorationBlocks.add(Material.POLISHED_DEEPSLATE_WALL);
        decorationBlocks.add(Material.BLACKSTONE_WALL);
        decorationBlocks.add(Material.POLISHED_BLACKSTONE_WALL);
        decorationBlocks.add(Material.POLISHED_BLACKSTONE_BRICK_WALL);
        decorationBlocks.add(Material.TUFF_WALL);
        decorationBlocks.add(Material.POLISHED_TUFF_WALL);
        decorationBlocks.add(Material.TUFF_BRICK_WALL);
        decorationBlocks.add(Material.MUD_BRICK_WALL);
        // Removed all sign variants - they display text which can't be set for ghost blocks
        // Add flower pots and plants
        decorationBlocks.add(Material.FLOWER_POT);
        decorationBlocks.add(Material.POTTED_OAK_SAPLING);
        decorationBlocks.add(Material.POTTED_SPRUCE_SAPLING);
        decorationBlocks.add(Material.POTTED_BIRCH_SAPLING);
        decorationBlocks.add(Material.POTTED_JUNGLE_SAPLING);
        decorationBlocks.add(Material.POTTED_ACACIA_SAPLING);
        decorationBlocks.add(Material.POTTED_DARK_OAK_SAPLING);
        decorationBlocks.add(Material.POTTED_MANGROVE_PROPAGULE);
        decorationBlocks.add(Material.POTTED_CHERRY_SAPLING);
        decorationBlocks.add(Material.POTTED_BAMBOO);
        decorationBlocks.add(Material.POTTED_FERN);
        decorationBlocks.add(Material.POTTED_DANDELION);
        decorationBlocks.add(Material.POTTED_POPPY);
        decorationBlocks.add(Material.POTTED_BLUE_ORCHID);
        decorationBlocks.add(Material.POTTED_ALLIUM);
        decorationBlocks.add(Material.POTTED_AZURE_BLUET);
        decorationBlocks.add(Material.POTTED_RED_TULIP);
        decorationBlocks.add(Material.POTTED_ORANGE_TULIP);
        decorationBlocks.add(Material.POTTED_WHITE_TULIP);
        decorationBlocks.add(Material.POTTED_PINK_TULIP);
        decorationBlocks.add(Material.POTTED_OXEYE_DAISY);
        decorationBlocks.add(Material.POTTED_CORNFLOWER);
        decorationBlocks.add(Material.POTTED_LILY_OF_THE_VALLEY);
        decorationBlocks.add(Material.POTTED_WITHER_ROSE);
        decorationBlocks.add(Material.POTTED_RED_MUSHROOM);
        decorationBlocks.add(Material.POTTED_BROWN_MUSHROOM);
        decorationBlocks.add(Material.POTTED_DEAD_BUSH);
        decorationBlocks.add(Material.POTTED_CACTUS);
        decorationBlocks.add(Material.POTTED_CRIMSON_FUNGUS);
        decorationBlocks.add(Material.POTTED_WARPED_FUNGUS);
        decorationBlocks.add(Material.POTTED_CRIMSON_ROOTS);
        decorationBlocks.add(Material.POTTED_WARPED_ROOTS);
        decorationBlocks.add(Material.POTTED_AZALEA_BUSH);
        decorationBlocks.add(Material.POTTED_FLOWERING_AZALEA_BUSH);
        // Removed heads, skulls, armor stand, item frames, painting, decorated pot - they're entities/interactive items
        decorationBlocks.add(Material.SUSPICIOUS_SAND);
        decorationBlocks.add(Material.SUSPICIOUS_GRAVEL);
        categories.put("Decoration", decorationBlocks);
        
        // Validate and clean all categories to remove invalid materials
        validateAndCleanCategories();
        
        // Setup category slots
        categorySlots.put(10, "Building");
        categorySlots.put(12, "Nature");
        categorySlots.put(14, "Redstone");
        categorySlots.put(16, "Decoration");
    }
    
    private void validateAndCleanCategories() {
        for (Map.Entry<String, List<Material>> entry : categories.entrySet()) {
            String categoryName = entry.getKey();
            List<Material> materials = entry.getValue();
            List<Material> validMaterials = new ArrayList<>();
            
            for (Material material : materials) {
                try {
                    // Test if we can create an ItemStack with this material
                    new ItemStack(material);
                    validMaterials.add(material);
                } catch (Exception e) {
                    System.out.println("Warning: Removed invalid material " + material.name() + 
                                     " from category " + categoryName + " - " + e.getMessage());
                }
            }
            
            entry.setValue(validMaterials);
        }
    }
    
    public void openMainGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, ChatColor.DARK_GREEN + "Ghost Blocks - Categories");
        
        // Add category items
        for (Map.Entry<Integer, String> entry : categorySlots.entrySet()) {
            int slot = entry.getKey();
            String category = entry.getValue();
            
            ItemStack item = getCategoryItem(category);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + category);
            
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Click to view " + category.toLowerCase() + " blocks");
            lore.add(ChatColor.GRAY + "Available blocks: " + categories.get(category).size());
            meta.setLore(lore);
            
            item.setItemMeta(meta);
            gui.setItem(slot, item);
        }
        
        // Add info item
        ItemStack info = new ItemStack(Material.BOOK);
        ItemMeta infoMeta = info.getItemMeta();
        infoMeta.setDisplayName(ChatColor.YELLOW + "How to use Ghost Blocks");
        List<String> infoLore = new ArrayList<>();
        infoLore.add(ChatColor.GRAY + "1. Select a category above");
        infoLore.add(ChatColor.GRAY + "2. Choose a block to get");
        infoLore.add(ChatColor.GRAY + "3. Place the block to create a ghost");
        infoLore.add(ChatColor.GRAY + "4. Use stick to remove ghost blocks");
        infoMeta.setLore(infoLore);
        info.setItemMeta(infoMeta);
        gui.setItem(22, info);
        
        player.openInventory(gui);
    }
    
    public void openCategoryGUI(Player player, String category) {
        openCategoryGUI(player, category, 0);
    }
    
    public void openCategoryGUI(Player player, String category, int page) {
        List<Material> blocks = categories.get(category);
        if (blocks == null || blocks.isEmpty()) {
            player.sendMessage(ChatColor.RED + "Category not found or empty!");
            return;
        }
        
        // Store player's current navigation state
        playerCurrentCategory.put(player.getUniqueId(), category);
        playerCurrentPage.put(player.getUniqueId(), page);
        
        // Calculate pagination
        int totalBlocks = blocks.size();
        int totalPages = (int) Math.ceil((double) totalBlocks / BLOCKS_PER_PAGE);
        int startIndex = page * BLOCKS_PER_PAGE;
        int endIndex = Math.min(startIndex + BLOCKS_PER_PAGE, totalBlocks);
        
        String title = ChatColor.DARK_GREEN + "Ghost Blocks - " + category + " (Page " + (page + 1) + "/" + totalPages + ")";
        Inventory gui = Bukkit.createInventory(null, 54, title);
        
        // Add blocks for current page
        int slot = 0;
        for (int i = startIndex; i < endIndex && slot < BLOCKS_PER_PAGE; i++) {
            Material material = blocks.get(i);
            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + formatName(material.name()));
            
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Click to get this block");
            lore.add(ChatColor.GRAY + "Place it to create a ghost block");
            meta.setLore(lore);
            
            item.setItemMeta(meta);
            gui.setItem(slot, item);
            slot++;
        }
        
        // Add navigation buttons
        addNavigationButtons(gui, page, totalPages, category);
        
        player.openInventory(gui);
    }
    
    private void addNavigationButtons(Inventory gui, int currentPage, int totalPages, String category) {
        // Back to categories button
        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatColor.RED + "← Back to Categories");
        List<String> backLore = new ArrayList<>();
        backLore.add(ChatColor.GRAY + "Return to main menu");
        backMeta.setLore(backLore);
        back.setItemMeta(backMeta);
        gui.setItem(45, back);
        
        // Previous page button
        if (currentPage > 0) {
            ItemStack prevPage = new ItemStack(Material.SPECTRAL_ARROW);
            ItemMeta prevMeta = prevPage.getItemMeta();
            prevMeta.setDisplayName(ChatColor.YELLOW + "← Previous Page");
            List<String> prevLore = new ArrayList<>();
            prevLore.add(ChatColor.GRAY + "Go to page " + currentPage);
            prevMeta.setLore(prevLore);
            prevPage.setItemMeta(prevMeta);
            gui.setItem(48, prevPage);
        }
        
        // Page info
        ItemStack pageInfo = new ItemStack(Material.BOOK);
        ItemMeta pageInfoMeta = pageInfo.getItemMeta();
        pageInfoMeta.setDisplayName(ChatColor.AQUA + "Page " + (currentPage + 1) + " of " + totalPages);
        List<String> pageInfoLore = new ArrayList<>();
        pageInfoLore.add(ChatColor.GRAY + "Category: " + category);
        pageInfoLore.add(ChatColor.GRAY + "Total blocks: " + categories.get(category).size());
        pageInfoLore.add(ChatColor.GRAY + "Blocks per page: " + BLOCKS_PER_PAGE);
        pageInfoMeta.setLore(pageInfoLore);
        pageInfo.setItemMeta(pageInfoMeta);
        gui.setItem(49, pageInfo);
        
        // Next page button
        if (currentPage < totalPages - 1) {
            ItemStack nextPage = new ItemStack(Material.SPECTRAL_ARROW);
            ItemMeta nextMeta = nextPage.getItemMeta();
            nextMeta.setDisplayName(ChatColor.YELLOW + "Next Page →");
            List<String> nextLore = new ArrayList<>();
            nextLore.add(ChatColor.GRAY + "Go to page " + (currentPage + 2));
            nextMeta.setLore(nextLore);
            nextPage.setItemMeta(nextMeta);
            gui.setItem(50, nextPage);
        }
        
        // Ghost Block Remover tool
        ItemStack remover = new ItemStack(Material.STICK);
        ItemMeta removerMeta = remover.getItemMeta();
        removerMeta.setDisplayName(ChatColor.RED + "Ghost Block Remover");
        List<String> removerLore = new ArrayList<>();
        removerLore.add(ChatColor.GRAY + "Right-click to remove ghost blocks");
        removerLore.add(ChatColor.GRAY + "Point at a ghost block and right-click");
        removerLore.add(ChatColor.YELLOW + "Click here to get the tool!");
        removerMeta.setLore(removerLore);
        remover.setItemMeta(removerMeta);
        gui.setItem(53, remover); // Top-right corner
    }
    
    private ItemStack getCategoryItem(String category) {
        switch (category) {
            case "Building":
                return new ItemStack(Material.BRICKS);
            case "Nature":
                return new ItemStack(Material.GRASS_BLOCK);
            case "Redstone":
                return new ItemStack(Material.REDSTONE_BLOCK);
            case "Decoration":
                return new ItemStack(Material.PAINTING);
            default:
                return new ItemStack(Material.STONE);
        }
    }
    
    private String formatName(String name) {
        String[] words = name.toLowerCase().replace('_', ' ').split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                result.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    result.append(word.substring(1));
                }
                result.append(" ");
            }
        }
        return result.toString().trim();
    }
    
    // Add missing methods that are expected by other classes
    public void openGUI(Player player) {
        openMainGUI(player);
    }
    
    public boolean isGhostBlockGUI(String title) {
        return title.contains("Ghost Blocks");
    }
    
    public boolean isGhostBlockRemover(ItemStack item) {
        if (item == null || item.getType() != Material.STICK) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName()) {
            return false;
        }
        return meta.getDisplayName().contains("Ghost Block Remover");
    }
    
    public boolean isCategoryButton(ItemStack item) {
        if (item == null || item.getItemMeta() == null) {
            return false;
        }
        String displayName = item.getItemMeta().getDisplayName();
        if (displayName == null) {
            return false;
        }
        
        // Strip color codes and check if the display name matches any of our categories
        String strippedName = ChatColor.stripColor(displayName);
        for (String category : categories.keySet()) {
            if (strippedName.equals(category)) {
                return true;
            }
        }
        return false;
    }
    
    public String getCategoryFromButton(ItemStack item) {
        if (item == null || item.getItemMeta() == null) {
            return null;
        }
        String displayName = item.getItemMeta().getDisplayName();
        if (displayName == null) {
            return null;
        }
        
        // Strip color codes and check each category to find a match
        String strippedName = ChatColor.stripColor(displayName);
        for (String category : categories.keySet()) {
            if (strippedName.equals(category)) {
                return category;
            }
        }
        
        return null;
    }
    
    public boolean isNavigationButton(ItemStack item) {
        if (item == null || item.getItemMeta() == null) return false;
        
        Material type = item.getType();
        String displayName = item.getItemMeta().getDisplayName();
        
        if (displayName == null) return false;
        
        // Strip color codes for comparison
        String strippedName = ChatColor.stripColor(displayName);
        
        return (type == Material.ARROW || type == Material.SPECTRAL_ARROW || type == Material.BOOK) &&
               (strippedName.contains("Back to Categories") || 
                strippedName.contains("Previous Page") || 
                strippedName.contains("Next Page") ||
                strippedName.contains("Page "));
    }
    
    public void handleNavigation(Player player, ItemStack item) {
        if (item == null || item.getItemMeta() == null) return;
        
        String displayName = item.getItemMeta().getDisplayName();
        if (displayName == null) return;
        
        // Strip color codes for comparison
        String strippedName = ChatColor.stripColor(displayName);
        UUID playerId = player.getUniqueId();
        
        if (strippedName.contains("Back to Categories")) {
            // Clear player's navigation state
            playerCurrentCategory.remove(playerId);
            playerCurrentPage.remove(playerId);
            openMainGUI(player);
        } else if (strippedName.contains("Previous Page")) {
            String category = playerCurrentCategory.get(playerId);
            Integer currentPage = playerCurrentPage.get(playerId);
            
            // If state is missing, try to parse from the GUI title as fallback
            if (category == null || currentPage == null) {
                parseStateFromGUI(player);
                category = playerCurrentCategory.get(playerId);
                currentPage = playerCurrentPage.get(playerId);
            }
            
            if (category != null && currentPage != null && currentPage > 0) {
                openCategoryGUI(player, category, currentPage - 1);
            }
        } else if (strippedName.contains("Next Page")) {
            String category = playerCurrentCategory.get(playerId);
            Integer currentPage = playerCurrentPage.get(playerId);
            
            // If state is missing, try to parse from the GUI title as fallback
            if (category == null || currentPage == null) {
                parseStateFromGUI(player);
                category = playerCurrentCategory.get(playerId);
                currentPage = playerCurrentPage.get(playerId);
            }
            
            if (category != null && currentPage != null) {
                List<Material> blocks = categories.get(category);
                if (blocks != null) {
                    int totalPages = (int) Math.ceil((double) blocks.size() / BLOCKS_PER_PAGE);
                    if (currentPage < totalPages - 1) {
                        openCategoryGUI(player, category, currentPage + 1);
                    }
                }
            }
        }
    }
    
    // Helper method to parse category and page from GUI title as fallback
    private void parseStateFromGUI(Player player) {
        try {
            String title = ChatColor.stripColor(player.getOpenInventory().getTitle());
            if (title.contains("Ghost Blocks - ") && title.contains("(Page ")) {
                // Example: "Ghost Blocks - Redstone (Page 2/3)"
                String[] parts = title.split(" - ");
                if (parts.length >= 2) {
                    String categoryAndPage = parts[1]; // "Redstone (Page 2/3)"
                    String category = categoryAndPage.split(" \\(Page ")[0]; // "Redstone"
                    String pageInfo = categoryAndPage.split("\\(Page ")[1]; // "2/3)"
                    int currentPage = Integer.parseInt(pageInfo.split("/")[0]) - 1; // Convert to 0-based index
                    
                    playerCurrentCategory.put(player.getUniqueId(), category);
                    playerCurrentPage.put(player.getUniqueId(), currentPage);
                }
            }
        } catch (Exception e) {
            // Failed to parse state from GUI title - silently continue
        }
    }
    
    public Map<String, List<Material>> getCategories() {
        return categories;
    }
    
    public ItemStack createGhostBlockItem(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.AQUA + "Ghost " + formatName(material.name()));
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Place this block to create a ghost");
            lore.add(ChatColor.GRAY + "Ghost blocks have no hitbox!");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }
    
    public boolean isGhostBlockItem(ItemStack item) {
        if (item == null || item.getItemMeta() == null) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasDisplayName()) {
            return false;
        }
        return meta.getDisplayName().contains("Ghost");
    }
    
    public String getCategoryFromSlot(int slot) {
        return categorySlots.get(slot);
    }
    
    public boolean isValidBlock(Material material) {
        return categories.values().stream().anyMatch(list -> list.contains(material));
    }
    
    public boolean isBackButton(Material material) {
        return material == Material.ARROW;
    }
    
    // Cleanup method to remove player data when they disconnect or close GUI
    public void cleanupPlayerData(Player player) {
        UUID playerId = player.getUniqueId();
        playerCurrentCategory.remove(playerId);
        playerCurrentPage.remove(playerId);
    }
    
    // Debug method to check if categories are properly initialized
    public void printCategoryInfo() {
        System.out.println("=== GhostBlocks Category Debug Info ===");
        for (Map.Entry<String, List<Material>> entry : categories.entrySet()) {
            System.out.println("Category: " + entry.getKey() + " - Blocks: " + entry.getValue().size());
        }
        System.out.println("Category slots mapping:");
        for (Map.Entry<Integer, String> entry : categorySlots.entrySet()) {
            System.out.println("Slot " + entry.getKey() + " -> " + entry.getValue());
        }
        System.out.println("=======================================");
    }
}
