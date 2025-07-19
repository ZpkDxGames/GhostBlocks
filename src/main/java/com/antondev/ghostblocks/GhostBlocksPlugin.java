package com.antondev.ghostblocks;

import com.antondev.ghostblocks.commands.GhostBlocksCommand;
import com.antondev.ghostblocks.data.GhostBlock;
import com.antondev.ghostblocks.listeners.GhostBlockListener;
import com.antondev.ghostblocks.managers.GhostBlockManager;
import com.antondev.ghostblocks.managers.GUIManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class GhostBlocksPlugin extends JavaPlugin {

    private GhostBlockManager ghostBlockManager;
    private GUIManager guiManager;
    private BukkitTask ageResetTask;

    @Override
    public void onEnable() {
        // Initialize managers
        this.ghostBlockManager = new GhostBlockManager(this);
        this.guiManager = new GUIManager(this);

        // Register listeners
        getServer().getPluginManager().registerEvents(new GhostBlockListener(this), this);

        // Register commands
        getCommand("ghostblocks").setExecutor(new GhostBlocksCommand(this));

        // Load ghost blocks from file
        ghostBlockManager.loadGhostBlocks();

        // Start age reset task to prevent ghost blocks from decaying
        startAgeResetTask();

        // Debug: Print category info
        guiManager.printCategoryInfo();

        getLogger().info("GhostBlocks plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        // Cancel age reset task
        if (ageResetTask != null) {
            ageResetTask.cancel();
        }
        
        // Save ghost blocks to file
        if (ghostBlockManager != null) {
            ghostBlockManager.saveGhostBlocks();
        }

        getLogger().info("GhostBlocks plugin has been disabled!");
    }
    
    private void startAgeResetTask() {
        // Run every 10 ticks (0.5 seconds) to reset age of all ghost blocks
        // FallingBlocks naturally despawn at ~600 ticks (30 seconds), so we reset well before that
        ageResetTask = getServer().getScheduler().runTaskTimer(this, () -> {
            for (GhostBlock ghostBlock : ghostBlockManager.getAllGhostBlocks()) {
                if (ghostBlock.isValid()) {
                    try {
                        // Reset age to a very small positive value (Paper 1.21.4+ requires positive values)
                        // This keeps the ghost block "young" and prevents natural despawning
                        ghostBlock.getFallingBlock().setTicksLived(1);
                    } catch (IllegalArgumentException e) {
                        // Fallback: if even small values fail, we'll skip age setting for this entity
                        // Ghost blocks will rely on persistence and other properties
                        getLogger().warning("Could not reset age for ghost block at " + ghostBlock.getLocation() + ": " + e.getMessage());
                    }
                }
            }
        }, 10L, 10L); // Run every 0.5 seconds instead of 1 second for better reliability
    }

    public GhostBlockManager getGhostBlockManager() {
        return ghostBlockManager;
    }

    public GUIManager getGUIManager() {
        return guiManager;
    }
}
