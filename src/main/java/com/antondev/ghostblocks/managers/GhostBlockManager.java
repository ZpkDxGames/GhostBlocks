package com.antondev.ghostblocks.managers;

import com.antondev.ghostblocks.GhostBlocksPlugin;
import com.antondev.ghostblocks.data.GhostBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GhostBlockManager {
    private final GhostBlocksPlugin plugin;
    private final Map<UUID, GhostBlock> ghostBlocks;
    private final Map<Location, GhostBlock> locationMap;
    private final File dataFile;

    public GhostBlockManager(GhostBlocksPlugin plugin) {
        this.plugin = plugin;
        this.ghostBlocks = new ConcurrentHashMap<>();
        this.locationMap = new ConcurrentHashMap<>();
        this.dataFile = new File(plugin.getDataFolder(), "ghostblocks.yml");
        
        // Create data folder if it doesn't exist
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
    }

    public void createGhostBlock(Location location, Material material) {
        // Remove any existing ghost block at this location
        removeGhostBlockAt(location);

        // Create new ghost block
        GhostBlock ghostBlock = new GhostBlock(location, material);
        
        // Spawn the falling block entity
        FallingBlock fallingBlock = location.getWorld().spawnFallingBlock(location, material.createBlockData());
        fallingBlock.setGravity(false);
        fallingBlock.setDropItem(false);
        fallingBlock.setHurtEntities(false);
        fallingBlock.setVelocity(new Vector(0, 0, 0));
        fallingBlock.setPersistent(true);
        
        // Set the falling block in the ghost block
        ghostBlock.setFallingBlock(fallingBlock);
        
        // Store the ghost block
        ghostBlocks.put(ghostBlock.getId(), ghostBlock);
        locationMap.put(location, ghostBlock);
    }

    public boolean removeGhostBlockAt(Location location) {
        // First try exact location match
        GhostBlock ghostBlock = locationMap.remove(location);
        if (ghostBlock != null) {
            ghostBlocks.remove(ghostBlock.getId());
            if (ghostBlock.getFallingBlock() != null && !ghostBlock.getFallingBlock().isDead()) {
                ghostBlock.getFallingBlock().remove();
            }
            return true;
        }
        
        // Try to find ghost block within a small radius (for better accuracy)
        for (GhostBlock gb : new java.util.ArrayList<>(ghostBlocks.values())) {
            if (gb.getLocation().getWorld().equals(location.getWorld()) &&
                gb.getLocation().distance(location) <= 1.0) {
                locationMap.remove(gb.getLocation());
                ghostBlocks.remove(gb.getId());
                if (gb.getFallingBlock() != null && !gb.getFallingBlock().isDead()) {
                    gb.getFallingBlock().remove();
                }
                return true;
            }
        }
        
        return false;
    }

    public GhostBlock getGhostBlockAt(Location location) {
        return locationMap.get(location);
    }

    public boolean isGhostBlockAt(Location location) {
        return locationMap.containsKey(location);
    }

    public void saveGhostBlocks() {
        try {
            FileConfiguration config = new YamlConfiguration();
            
            List<Map<String, Object>> ghostBlocksList = new ArrayList<>();
            for (GhostBlock ghostBlock : ghostBlocks.values()) {
                Map<String, Object> blockData = new HashMap<>();
                blockData.put("id", ghostBlock.getId().toString());
                blockData.put("world", ghostBlock.getLocation().getWorld().getName());
                blockData.put("x", ghostBlock.getLocation().getX());
                blockData.put("y", ghostBlock.getLocation().getY());
                blockData.put("z", ghostBlock.getLocation().getZ());
                blockData.put("material", ghostBlock.getMaterial().name());
                ghostBlocksList.add(blockData);
            }
            
            config.set("ghostblocks", ghostBlocksList);
            config.save(dataFile);
            
            plugin.getLogger().info("Saved " + ghostBlocks.size() + " ghost blocks to file.");
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save ghost blocks: " + e.getMessage());
        }
    }

    public void loadGhostBlocks() {
        if (!dataFile.exists()) {
            return;
        }

        try {
            FileConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
            List<Map<?, ?>> ghostBlocksList = config.getMapList("ghostblocks");
            
            int loaded = 0;
            for (Map<?, ?> blockData : ghostBlocksList) {
                try {
                    UUID id = UUID.fromString((String) blockData.get("id"));
                    String worldName = (String) blockData.get("world");
                    World world = Bukkit.getWorld(worldName);
                    
                    if (world == null) {
                        plugin.getLogger().warning("Could not load ghost block: World '" + worldName + "' not found");
                        continue;
                    }
                    
                    double x = (Double) blockData.get("x");
                    double y = (Double) blockData.get("y");
                    double z = (Double) blockData.get("z");
                    Location location = new Location(world, x, y, z);
                    
                    Material material = Material.valueOf((String) blockData.get("material"));
                    
                    // Create the ghost block
                    GhostBlock ghostBlock = new GhostBlock(id, location, material);
                    
                    // Spawn the falling block entity
                    FallingBlock fallingBlock = world.spawnFallingBlock(location, material.createBlockData());
                    fallingBlock.setGravity(false);
                    fallingBlock.setDropItem(false);
                    fallingBlock.setHurtEntities(false);
                    fallingBlock.setVelocity(new Vector(0, 0, 0));
                    fallingBlock.setPersistent(true);
                    
                    ghostBlock.setFallingBlock(fallingBlock);
                    
                    // Store the ghost block
                    ghostBlocks.put(ghostBlock.getId(), ghostBlock);
                    locationMap.put(location, ghostBlock);
                    loaded++;
                    
                } catch (Exception e) {
                    plugin.getLogger().warning("Could not load ghost block: " + e.getMessage());
                }
            }
            
            plugin.getLogger().info("Loaded " + loaded + " ghost blocks from file.");
        } catch (Exception e) {
            plugin.getLogger().severe("Could not load ghost blocks: " + e.getMessage());
        }
    }

    public Collection<GhostBlock> getAllGhostBlocks() {
        return ghostBlocks.values();
    }

    public void reload() {
        plugin.getLogger().info("Reloading GhostBlocks plugin...");
        
        // Save current ghost blocks before clearing
        saveGhostBlocks();
        
        // Clear all existing ghost blocks
        clearAllGhostBlocks();
        
        // Reload plugin configuration
        plugin.reloadConfig();
        
        // Load ghost blocks from file
        loadGhostBlocks();
        
        plugin.getLogger().info("GhostBlocks plugin reloaded successfully!");
    }

    private void clearAllGhostBlocks() {
        // Remove all falling block entities
        for (GhostBlock ghostBlock : ghostBlocks.values()) {
            if (ghostBlock.getFallingBlock() != null && ghostBlock.getFallingBlock().isValid()) {
                ghostBlock.getFallingBlock().remove();
            }
        }
        
        // Clear all data structures
        ghostBlocks.clear();
        locationMap.clear();
        
        plugin.getLogger().info("Cleared all existing ghost blocks.");
    }

    public void cleanupInvalidGhostBlocks() {
        List<UUID> toRemove = new ArrayList<>();
        for (GhostBlock ghostBlock : ghostBlocks.values()) {
            if (!ghostBlock.isValid()) {
                toRemove.add(ghostBlock.getId());
                locationMap.remove(ghostBlock.getLocation());
            }
        }
        
        for (UUID id : toRemove) {
            ghostBlocks.remove(id);
        }
    }
}
