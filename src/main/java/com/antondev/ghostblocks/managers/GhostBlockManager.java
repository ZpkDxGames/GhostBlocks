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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class GhostBlockManager {
    private final GhostBlocksPlugin plugin;
    private final Map<UUID, GhostBlock> ghostBlocks;
    private final Map<Location, GhostBlock> locationMap;
    private final Map<String, Set<GhostBlock>> worldGhostBlocks; // Cache by world for performance
    private final File dataFile;
    private final AtomicBoolean isAutoSaving = new AtomicBoolean(false);
    private long lastCleanupTime = 0;
    private static final long CLEANUP_INTERVAL = 30000; // 30 seconds
    private static final int MAX_AGE_RESET_PER_TICK = 50; // Limit age resets per tick for TPS optimization

    public GhostBlockManager(GhostBlocksPlugin plugin) {
        this.plugin = plugin;
        this.ghostBlocks = new ConcurrentHashMap<>();
        this.locationMap = new ConcurrentHashMap<>();
        this.worldGhostBlocks = new ConcurrentHashMap<>();
        this.dataFile = new File(plugin.getDataFolder(), "ghostblocks.yml");
        
        // Create data folder if it doesn't exist
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
    }

    public void createGhostBlock(Location location, Material material) {
        // Ensure the location follows Minecraft's block grid system
        Location blockLocation = location.getBlock().getLocation();
        blockLocation.add(0.5, 0, 0.5); // Center the ghost block in the block space
        
        // Remove any existing ghost block at this location
        removeGhostBlockAt(blockLocation);

        // Create new ghost block
        GhostBlock ghostBlock = new GhostBlock(blockLocation, material);
        
        // Spawn the falling block entity at the exact block grid position
        // Use the non-deprecated method for Paper 1.21.4
        FallingBlock fallingBlock = blockLocation.getWorld().spawn(blockLocation, FallingBlock.class, fb -> {
            fb.setBlockData(material.createBlockData());
            fb.setGravity(false);
            fb.setDropItem(false);
            fb.setHurtEntities(false);
            fb.setVelocity(new Vector(0, 0, 0));
            fb.setPersistent(true);
            fb.setSilent(true); // Prevent sound effects
            
            // Set age to a small positive value to prevent decay
            try {
                fb.setTicksLived(1);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Could not set initial age for ghost block: " + e.getMessage());
            }
        });
        
        // Set the falling block in the ghost block
        ghostBlock.setFallingBlock(fallingBlock);
        
        // Store the ghost block using the block grid location
        ghostBlocks.put(ghostBlock.getId(), ghostBlock);
        locationMap.put(blockLocation.getBlock().getLocation(), ghostBlock);
        
        // Add to world cache for performance
        String worldName = blockLocation.getWorld().getName();
        worldGhostBlocks.computeIfAbsent(worldName, k -> ConcurrentHashMap.newKeySet()).add(ghostBlock);
        
        // Auto-save asynchronously to avoid TPS lag
        scheduleAsyncSave();
    }

    public boolean removeGhostBlockAt(Location location) {
        // Normalize to block grid coordinates
        Location blockLoc = location.getBlock().getLocation();
        
        // Remove from location map
        GhostBlock ghostBlock = locationMap.remove(blockLoc);
        if (ghostBlock != null) {
            // Remove from main collection
            ghostBlocks.remove(ghostBlock.getId());
            
            // Remove from world cache
            String worldName = ghostBlock.getLocation().getWorld().getName();
            Set<GhostBlock> worldBlocks = worldGhostBlocks.get(worldName);
            if (worldBlocks != null) {
                worldBlocks.remove(ghostBlock);
                if (worldBlocks.isEmpty()) {
                    worldGhostBlocks.remove(worldName);
                }
            }
            
            // Remove the entity
            if (ghostBlock.getFallingBlock() != null && !ghostBlock.getFallingBlock().isDead()) {
                ghostBlock.getFallingBlock().remove();
            }
            
            // Auto-save asynchronously
            scheduleAsyncSave();
            return true;
        }
        
        return false;
    }
    
    public boolean removeGhostBlockNear(Location location, double radius) {
        // Special method for stick removal with radius check
        for (GhostBlock gb : new ArrayList<>(ghostBlocks.values())) {
            if (gb.getLocation().getWorld().equals(location.getWorld()) &&
                gb.getLocation().distance(location) <= radius) {
                
                // Remove from all data structures
                locationMap.remove(gb.getLocation().getBlock().getLocation());
                ghostBlocks.remove(gb.getId());
                
                // Remove from world cache
                String worldName = gb.getLocation().getWorld().getName();
                Set<GhostBlock> worldBlocks = worldGhostBlocks.get(worldName);
                if (worldBlocks != null) {
                    worldBlocks.remove(gb);
                    if (worldBlocks.isEmpty()) {
                        worldGhostBlocks.remove(worldName);
                    }
                }
                
                // Remove the entity
                if (gb.getFallingBlock() != null && !gb.getFallingBlock().isDead()) {
                    gb.getFallingBlock().remove();
                }
                
                // Auto-save asynchronously
                scheduleAsyncSave();
                return true;
            }
        }
        
        return false;
    }

    public GhostBlock getGhostBlockAt(Location location) {
        // Normalize to block grid coordinates
        return locationMap.get(location.getBlock().getLocation());
    }

    public boolean isGhostBlockAt(Location location) {
        // Normalize to block grid coordinates
        return locationMap.containsKey(location.getBlock().getLocation());
    }

    public void saveGhostBlocks() {
        try {
            FileConfiguration config = new YamlConfiguration();
            
            List<Map<String, Object>> ghostBlocksList = new ArrayList<>();
            for (GhostBlock ghostBlock : ghostBlocks.values()) {
                // Only save valid ghost blocks
                if (ghostBlock.isValid()) {
                    Map<String, Object> blockData = new HashMap<>();
                    blockData.put("id", ghostBlock.getId().toString());
                    blockData.put("world", ghostBlock.getLocation().getWorld().getName());
                    
                    // Save block grid coordinates for consistency
                    Location blockLoc = ghostBlock.getLocation().getBlock().getLocation();
                    blockData.put("x", blockLoc.getX());
                    blockData.put("y", blockLoc.getY());
                    blockData.put("z", blockLoc.getZ());
                    blockData.put("material", ghostBlock.getMaterial().name());
                    ghostBlocksList.add(blockData);
                }
            }
            
            config.set("ghostblocks", ghostBlocksList);
            config.save(dataFile);
            
            plugin.getLogger().info("Saved " + ghostBlocksList.size() + " ghost blocks to file.");
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
                    
                    // Ensure the location follows Minecraft's block grid system (consistent with createGhostBlock)
                    Location blockLocation = location.getBlock().getLocation();
                    blockLocation.add(0.5, 0, 0.5); // Center the ghost block in the block space
                    
                    Material material = Material.valueOf((String) blockData.get("material"));
                    
                    // Create the ghost block
                    GhostBlock ghostBlock = new GhostBlock(id, blockLocation, material);
                    
                    // Spawn the falling block entity at the exact block grid position
                    // Use the non-deprecated method for Paper 1.21.4
                    FallingBlock fallingBlock = world.spawn(blockLocation, FallingBlock.class, fb -> {
                        fb.setBlockData(material.createBlockData());
                        fb.setGravity(false);
                        fb.setDropItem(false);
                        fb.setHurtEntities(false);
                        fb.setVelocity(new Vector(0, 0, 0));
                        fb.setPersistent(true);
                        fb.setSilent(true); // Prevent sound effects
                        
                        // Set age to a small positive value to prevent decay
                        try {
                            fb.setTicksLived(1);
                        } catch (IllegalArgumentException e) {
                            plugin.getLogger().warning("Could not set initial age for loaded ghost block: " + e.getMessage());
                        }
                    });
                    
                    ghostBlock.setFallingBlock(fallingBlock);
                    
                    // Store the ghost block using block grid coordinates (consistent with createGhostBlock)
                    ghostBlocks.put(ghostBlock.getId(), ghostBlock);
                    locationMap.put(blockLocation.getBlock().getLocation(), ghostBlock);
                    
                    // Add to world cache for performance
                    worldGhostBlocks.computeIfAbsent(worldName, k -> ConcurrentHashMap.newKeySet()).add(ghostBlock);
                    
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
        // Return only valid ghost blocks for accurate counting
        return ghostBlocks.values().stream()
                .filter(GhostBlock::isValid)
                .collect(Collectors.toList());
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
        worldGhostBlocks.clear();
        
        plugin.getLogger().info("Cleared all existing ghost blocks.");
    }

    public void cleanupInvalidGhostBlocks() {
        long currentTime = System.currentTimeMillis();
        
        // Only run cleanup every 30 seconds to avoid TPS impact
        if (currentTime - lastCleanupTime < CLEANUP_INTERVAL) {
            return;
        }
        
        lastCleanupTime = currentTime;
        
        List<UUID> toRemove = new ArrayList<>();
        for (GhostBlock ghostBlock : ghostBlocks.values()) {
            if (!ghostBlock.isValid()) {
                toRemove.add(ghostBlock.getId());
                locationMap.remove(ghostBlock.getLocation());
                
                // Remove from world cache
                String worldName = ghostBlock.getLocation().getWorld().getName();
                Set<GhostBlock> worldBlocks = worldGhostBlocks.get(worldName);
                if (worldBlocks != null) {
                    worldBlocks.remove(ghostBlock);
                    if (worldBlocks.isEmpty()) {
                        worldGhostBlocks.remove(worldName);
                    }
                }
            }
        }
        
        for (UUID id : toRemove) {
            ghostBlocks.remove(id);
        }
        
        if (!toRemove.isEmpty()) {
            plugin.getLogger().info("Cleaned up " + toRemove.size() + " invalid ghost blocks");
        }
    }
    
    /**
     * Get all ghost blocks in a specific world for performance-optimized operations
     */
    public Set<GhostBlock> getGhostBlocksInWorld(World world) {
        return worldGhostBlocks.getOrDefault(world.getName(), new HashSet<>());
    }
    
    /**
     * Get all ghost blocks in a specific world by name
     */
    public Set<GhostBlock> getGhostBlocksInWorld(String worldName) {
        return new HashSet<>(worldGhostBlocks.getOrDefault(worldName, new HashSet<>()));
    }
    
    /**
     * Schedule an asynchronous save to avoid TPS impact
     */
    private void scheduleAsyncSave() {
        if (!isAutoSaving.compareAndSet(false, true)) {
            return; // Already saving
        }
        
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                saveGhostBlocks();
            } finally {
                isAutoSaving.set(false);
            }
        });
    }
    
    /**
     * Optimized age reset method that limits operations per tick
     */
    public void resetGhostBlockAges() {
        Collection<GhostBlock> allBlocks = ghostBlocks.values();
        if (allBlocks.isEmpty()) {
            return;
        }
        
        int processed = 0;
        for (GhostBlock ghostBlock : allBlocks) {
            if (processed >= MAX_AGE_RESET_PER_TICK) {
                break; // Limit operations per tick to maintain TPS
            }
            
            if (ghostBlock.isValid()) {
                try {
                    ghostBlock.getFallingBlock().setTicksLived(1);
                    processed++;
                } catch (IllegalArgumentException e) {
                    // Log warning but continue processing
                    plugin.getLogger().warning("Could not reset age for ghost block at " + 
                        ghostBlock.getLocation() + ": " + e.getMessage());
                }
            }
        }
    }
}
