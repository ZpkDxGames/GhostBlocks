package com.antondev.ghostblocks.managers;

import com.antondev.ghostblocks.GhostBlocksPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GUIManager {
    private final GhostBlocksPlugin plugin;
    private final Map<String, List<Material>> categories;
    private final Map<UUID, String> playerCategory;
    private final Map<UUID, Integer> playerPage;
    private final int itemsPerPage = 45; // 5 rows for items, 1 row for navigation

    public GUIManager(GhostBlocksPlugin plugin) {
        this.plugin = plugin;
        this.categories = new HashMap<>();
        this.playerCategory = new HashMap<>();
        this.playerPage = new HashMap<>();
        initializeCategories();
    }

    private void initializeCategories() {
        // Building Blocks (full blocks only)
        categories.put("Building", Arrays.asList(
            Material.STONE,
            Material.COBBLESTONE,
            Material.STONE_BRICKS,
            Material.MOSSY_STONE_BRICKS,
            Material.CRACKED_STONE_BRICKS,
            Material.GRANITE,
            Material.POLISHED_GRANITE,
            Material.DIORITE,
            Material.POLISHED_DIORITE,
            Material.ANDESITE,
            Material.POLISHED_ANDESITE,
            Material.DEEPSLATE,
            Material.COBBLED_DEEPSLATE,
            Material.POLISHED_DEEPSLATE,
            Material.DEEPSLATE_BRICKS,
            Material.CRACKED_DEEPSLATE_BRICKS,
            Material.DEEPSLATE_TILES,
            Material.CRACKED_DEEPSLATE_TILES,
            Material.SANDSTONE,
            Material.SMOOTH_SANDSTONE,
            Material.CUT_SANDSTONE,
            Material.RED_SANDSTONE,
            Material.SMOOTH_RED_SANDSTONE,
            Material.CUT_RED_SANDSTONE,
            Material.BRICKS,
            Material.QUARTZ_BLOCK,
            Material.SMOOTH_QUARTZ,
            Material.QUARTZ_BRICKS,
            Material.PRISMARINE,
            Material.PRISMARINE_BRICKS,
            Material.DARK_PRISMARINE,
            Material.BLACKSTONE,
            Material.POLISHED_BLACKSTONE,
            Material.POLISHED_BLACKSTONE_BRICKS,
            Material.CRACKED_POLISHED_BLACKSTONE_BRICKS,
            Material.GILDED_BLACKSTONE,
            Material.CHISELED_POLISHED_BLACKSTONE
        ));

        // Nature Blocks (full blocks only)
        categories.put("Nature", Arrays.asList(
            Material.OAK_LOG,
            Material.SPRUCE_LOG,
            Material.BIRCH_LOG,
            Material.JUNGLE_LOG,
            Material.ACACIA_LOG,
            Material.DARK_OAK_LOG,
            Material.MANGROVE_LOG,
            Material.CHERRY_LOG,
            Material.BAMBOO_BLOCK,
            Material.STRIPPED_OAK_LOG,
            Material.STRIPPED_SPRUCE_LOG,
            Material.STRIPPED_BIRCH_LOG,
            Material.STRIPPED_JUNGLE_LOG,
            Material.STRIPPED_ACACIA_LOG,
            Material.STRIPPED_DARK_OAK_LOG,
            Material.STRIPPED_MANGROVE_LOG,
            Material.STRIPPED_CHERRY_LOG,
            Material.STRIPPED_BAMBOO_BLOCK,
            Material.OAK_WOOD,
            Material.SPRUCE_WOOD,
            Material.BIRCH_WOOD,
            Material.JUNGLE_WOOD,
            Material.ACACIA_WOOD,
            Material.DARK_OAK_WOOD,
            Material.MANGROVE_WOOD,
            Material.CHERRY_WOOD,
            Material.BAMBOO_MOSAIC,
            Material.DIRT,
            Material.COARSE_DIRT,
            Material.PODZOL,
            Material.MYCELIUM,
            Material.GRASS_BLOCK,
            Material.MOSS_BLOCK,
            Material.MUD,
            Material.PACKED_MUD,
            Material.CLAY,
            Material.TERRACOTTA,
            Material.SNOW_BLOCK,
            Material.ICE,
            Material.PACKED_ICE,
            Material.BLUE_ICE,
            Material.CALCITE,
            Material.TUFF,
            Material.DRIPSTONE_BLOCK
        ));

        // Redstone Blocks (full blocks only)
        categories.put("Redstone", Arrays.asList(
            Material.REDSTONE_BLOCK,
            Material.OBSERVER,
            Material.DISPENSER,
            Material.DROPPER,
            Material.PISTON,
            Material.STICKY_PISTON,
            Material.TNT,
            Material.NOTE_BLOCK,
            Material.JUKEBOX,
            Material.REDSTONE_LAMP,
            Material.TARGET,
            Material.COPPER_BLOCK,
            Material.EXPOSED_COPPER,
            Material.WEATHERED_COPPER,
            Material.OXIDIZED_COPPER,
            Material.WAXED_COPPER_BLOCK,
            Material.WAXED_EXPOSED_COPPER,
            Material.WAXED_WEATHERED_COPPER,
            Material.WAXED_OXIDIZED_COPPER,
            Material.CUT_COPPER,
            Material.EXPOSED_CUT_COPPER,
            Material.WEATHERED_CUT_COPPER,
            Material.OXIDIZED_CUT_COPPER,
            Material.WAXED_CUT_COPPER,
            Material.WAXED_EXPOSED_CUT_COPPER,
            Material.WAXED_WEATHERED_CUT_COPPER,
            Material.WAXED_OXIDIZED_CUT_COPPER,
            Material.LIGHTNING_ROD,
            Material.COMPOSTER,
            Material.BARREL,
            Material.SMOKER,
            Material.BLAST_FURNACE,
            Material.FURNACE,
            Material.CRAFTING_TABLE,
            Material.CARTOGRAPHY_TABLE,
            Material.FLETCHING_TABLE,
            Material.SMITHING_TABLE,
            Material.STONECUTTER,
            Material.LOOM,
            Material.GRINDSTONE
        ));

        // Decoration Blocks (full blocks only)
        categories.put("Decoration", Arrays.asList(
            Material.WHITE_CONCRETE,
            Material.ORANGE_CONCRETE,
            Material.MAGENTA_CONCRETE,
            Material.LIGHT_BLUE_CONCRETE,
            Material.YELLOW_CONCRETE,
            Material.LIME_CONCRETE,
            Material.PINK_CONCRETE,
            Material.GRAY_CONCRETE,
            Material.LIGHT_GRAY_CONCRETE,
            Material.CYAN_CONCRETE,
            Material.PURPLE_CONCRETE,
            Material.BLUE_CONCRETE,
            Material.BROWN_CONCRETE,
            Material.GREEN_CONCRETE,
            Material.RED_CONCRETE,
            Material.BLACK_CONCRETE,
            Material.WHITE_CONCRETE_POWDER,
            Material.ORANGE_CONCRETE_POWDER,
            Material.MAGENTA_CONCRETE_POWDER,
            Material.LIGHT_BLUE_CONCRETE_POWDER,
            Material.YELLOW_CONCRETE_POWDER,
            Material.LIME_CONCRETE_POWDER,
            Material.PINK_CONCRETE_POWDER,
            Material.GRAY_CONCRETE_POWDER,
            Material.LIGHT_GRAY_CONCRETE_POWDER,
            Material.CYAN_CONCRETE_POWDER,
            Material.PURPLE_CONCRETE_POWDER,
            Material.BLUE_CONCRETE_POWDER,
            Material.BROWN_CONCRETE_POWDER,
            Material.GREEN_CONCRETE_POWDER,
            Material.RED_CONCRETE_POWDER,
            Material.BLACK_CONCRETE_POWDER,
            Material.WHITE_TERRACOTTA,
            Material.ORANGE_TERRACOTTA,
            Material.MAGENTA_TERRACOTTA,
            Material.LIGHT_BLUE_TERRACOTTA,
            Material.YELLOW_TERRACOTTA,
            Material.LIME_TERRACOTTA,
            Material.PINK_TERRACOTTA,
            Material.GRAY_TERRACOTTA,
            Material.LIGHT_GRAY_TERRACOTTA,
            Material.CYAN_TERRACOTTA,
            Material.PURPLE_TERRACOTTA,
            Material.BLUE_TERRACOTTA,
            Material.BROWN_TERRACOTTA,
            Material.GREEN_TERRACOTTA,
            Material.RED_TERRACOTTA,
            Material.BLACK_TERRACOTTA,
            Material.WHITE_GLAZED_TERRACOTTA,
            Material.ORANGE_GLAZED_TERRACOTTA,
            Material.MAGENTA_GLAZED_TERRACOTTA,
            Material.LIGHT_BLUE_GLAZED_TERRACOTTA,
            Material.YELLOW_GLAZED_TERRACOTTA,
            Material.LIME_GLAZED_TERRACOTTA,
            Material.PINK_GLAZED_TERRACOTTA,
            Material.GRAY_GLAZED_TERRACOTTA,
            Material.LIGHT_GRAY_GLAZED_TERRACOTTA,
            Material.CYAN_GLAZED_TERRACOTTA,
            Material.PURPLE_GLAZED_TERRACOTTA,
            Material.BLUE_GLAZED_TERRACOTTA,
            Material.BROWN_GLAZED_TERRACOTTA,
            Material.GREEN_GLAZED_TERRACOTTA,
            Material.RED_GLAZED_TERRACOTTA,
            Material.BLACK_GLAZED_TERRACOTTA,
            Material.NETHERRACK,
            Material.NETHER_BRICKS,
            Material.CRACKED_NETHER_BRICKS,
            Material.CHISELED_NETHER_BRICKS,
            Material.RED_NETHER_BRICKS,
            Material.NETHER_WART_BLOCK,
            Material.WARPED_WART_BLOCK,
            Material.SHROOMLIGHT,
            Material.END_STONE,
            Material.END_STONE_BRICKS,
            Material.PURPUR_BLOCK,
            Material.PURPUR_PILLAR,
            Material.OBSIDIAN,
            Material.CRYING_OBSIDIAN,
            Material.RESPAWN_ANCHOR,
            Material.ANCIENT_DEBRIS,
            Material.NETHERITE_BLOCK,
            Material.LODESTONE,
            Material.BONE_BLOCK,
            Material.COAL_BLOCK,
            Material.IRON_BLOCK,
            Material.GOLD_BLOCK,
            Material.DIAMOND_BLOCK,
            Material.EMERALD_BLOCK,
            Material.LAPIS_BLOCK,
            Material.REDSTONE_BLOCK,
            Material.AMETHYST_BLOCK,
            Material.BUDDING_AMETHYST,
            Material.SCULK,
            Material.SCULK_CATALYST,
            Material.SCULK_SHRIEKER,
            Material.REINFORCED_DEEPSLATE
        ));
    }

    public void openGUI(Player player) {
        // Default to Building category, page 0
        String category = playerCategory.getOrDefault(player.getUniqueId(), "Building");
        int page = playerPage.getOrDefault(player.getUniqueId(), 0);
        openCategoryGUI(player, category, page);
    }

    public void openCategoryGUI(Player player, String category, int page) {
        playerCategory.put(player.getUniqueId(), category);
        playerPage.put(player.getUniqueId(), page);
        
        List<Material> categoryBlocks = categories.get(category);
        if (categoryBlocks == null) {
            category = "Building";
            categoryBlocks = categories.get("Building");
            page = 0;
        }
        
        int totalPages = (int) Math.ceil((double) categoryBlocks.size() / itemsPerPage);
        if (page >= totalPages) page = totalPages - 1;
        if (page < 0) page = 0;
        
        playerPage.put(player.getUniqueId(), page);
        
        // Create inventory with category and page info in title
        String title = ChatColor.DARK_GRAY + "Ghost Blocks - " + ChatColor.GOLD + category + 
                      ChatColor.DARK_GRAY + " (Page " + (page + 1) + "/" + totalPages + ")";
        Inventory gui = Bukkit.createInventory(null, 54, title);

        // Add category blocks for current page
        int startIndex = page * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, categoryBlocks.size());
        
        int slot = 0;
        for (int i = startIndex; i < endIndex; i++) {
            Material material = categoryBlocks.get(i);
            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + formatMaterialName(material));
            meta.setLore(Arrays.asList(
                ChatColor.GRAY + "Click to get this ghost block",
                ChatColor.YELLOW + "Place it to create a ghost block"
            ));
            item.setItemMeta(meta);
            gui.setItem(slot, item);
            slot++;
        }

        // Add navigation items (last row)
        // Category buttons
        gui.setItem(45, createCategoryButton("Building", category));
        gui.setItem(46, createCategoryButton("Nature", category));
        gui.setItem(47, createCategoryButton("Redstone", category));
        gui.setItem(48, createCategoryButton("Decoration", category));
        
        // Page navigation
        if (page > 0) {
            gui.setItem(50, createNavigationButton("Previous Page", Material.ARROW, "Go to page " + page));
        }
        if (page < totalPages - 1) {
            gui.setItem(51, createNavigationButton("Next Page", Material.ARROW, "Go to page " + (page + 2)));
        }
        
        // Ghost Block Remover
        gui.setItem(53, createRemoverTool());

        player.openInventory(gui);
    }

    private ItemStack createCategoryButton(String categoryName, String currentCategory) {
        Material material;
        switch (categoryName) {
            case "Building":
                material = Material.STONE_BRICKS;
                break;
            case "Nature":
                material = Material.OAK_LOG;
                break;
            case "Redstone":
                material = Material.REDSTONE_BLOCK;
                break;
            case "Decoration":
                material = Material.WHITE_CONCRETE;
                break;
            default:
                material = Material.STONE;
        }
        
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        
        if (categoryName.equals(currentCategory)) {
            meta.setDisplayName(ChatColor.GOLD + "» " + categoryName + " Blocks «");
            meta.setLore(Arrays.asList(ChatColor.GREEN + "Currently viewing this category"));
        } else {
            meta.setDisplayName(ChatColor.YELLOW + categoryName + " Blocks");
            meta.setLore(Arrays.asList(ChatColor.GRAY + "Click to switch to " + categoryName.toLowerCase() + " blocks"));
        }
        
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createNavigationButton(String name, Material material, String description) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + name);
        meta.setLore(Arrays.asList(ChatColor.GRAY + description));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createRemoverTool() {
        ItemStack remover = new ItemStack(Material.STICK);
        ItemMeta removerMeta = remover.getItemMeta();
        removerMeta.setDisplayName(ChatColor.RED + "Ghost Block Remover");
        removerMeta.setLore(Arrays.asList(
            ChatColor.GRAY + "Right-click while looking at a ghost block to remove it",
            ChatColor.YELLOW + "This tool helps you clean up ghost blocks"
        ));
        remover.setItemMeta(removerMeta);
        return remover;
    }

    private String formatMaterialName(Material material) {
        String name = material.name().toLowerCase().replace('_', ' ');
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;
        
        for (char c : name.toCharArray()) {
            if (capitalizeNext) {
                result.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                result.append(c);
            }
            
            if (c == ' ') {
                capitalizeNext = true;
            }
        }
        
        return result.toString();
    }

    public boolean isGhostBlockGUI(String title) {
        return title.contains("Ghost Blocks -");
    }

    public boolean isGhostBlockRemover(ItemStack item) {
        if (item == null || item.getType() != Material.STICK) {
            return false;
        }
        
        ItemMeta meta = item.getItemMeta();
        if (meta == null || meta.getDisplayName() == null) {
            return false;
        }
        
        return meta.getDisplayName().equals(ChatColor.RED + "Ghost Block Remover");
    }

    public ItemStack createGhostBlockItem(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Ghost " + formatMaterialName(material));
        meta.setLore(Arrays.asList(
            ChatColor.GRAY + "This is a ghost block",
            ChatColor.YELLOW + "Place it to create a ghost block with no hitbox"
        ));
        item.setItemMeta(meta);
        return item;
    }

    public boolean isGhostBlockItem(ItemStack item) {
        if (item == null || item.getItemMeta() == null) {
            return false;
        }
        
        ItemMeta meta = item.getItemMeta();
        if (meta.getDisplayName() == null) {
            return false;
        }
        
        return meta.getDisplayName().contains(ChatColor.AQUA + "Ghost ");
    }

    public boolean isCategoryButton(ItemStack item) {
        if (item == null || item.getItemMeta() == null) {
            return false;
        }
        
        ItemMeta meta = item.getItemMeta();
        if (meta.getDisplayName() == null) {
            return false;
        }
        
        String name = meta.getDisplayName();
        return name.contains("Building Blocks") || name.contains("Nature Blocks") || 
               name.contains("Redstone Blocks") || name.contains("Decoration Blocks");
    }

    public boolean isNavigationButton(ItemStack item) {
        if (item == null || item.getItemMeta() == null) {
            return false;
        }
        
        ItemMeta meta = item.getItemMeta();
        if (meta.getDisplayName() == null) {
            return false;
        }
        
        String name = meta.getDisplayName();
        return name.contains("Previous Page") || name.contains("Next Page");
    }

    public String getCategoryFromButton(ItemStack item) {
        if (item == null || item.getItemMeta() == null) {
            return null;
        }
        
        ItemMeta meta = item.getItemMeta();
        if (meta.getDisplayName() == null) {
            return null;
        }
        
        String name = meta.getDisplayName();
        if (name.contains("Building")) return "Building";
        if (name.contains("Nature")) return "Nature";
        if (name.contains("Redstone")) return "Redstone";
        if (name.contains("Decoration")) return "Decoration";
        
        return null;
    }

    public void handleNavigation(Player player, ItemStack item) {
        if (item == null || item.getItemMeta() == null) {
            return;
        }
        
        ItemMeta meta = item.getItemMeta();
        if (meta.getDisplayName() == null) {
            return;
        }
        
        String name = meta.getDisplayName();
        String category = playerCategory.getOrDefault(player.getUniqueId(), "Building");
        int page = playerPage.getOrDefault(player.getUniqueId(), 0);
        
        if (name.contains("Previous Page")) {
            openCategoryGUI(player, category, page - 1);
        } else if (name.contains("Next Page")) {
            openCategoryGUI(player, category, page + 1);
        }
    }

    public Map<String, List<Material>> getCategories() {
        return categories;
    }
}
