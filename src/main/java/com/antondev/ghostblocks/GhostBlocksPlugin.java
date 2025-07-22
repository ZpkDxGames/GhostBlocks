package com.antondev.ghostblocks;

import com.antondev.ghostblocks.commands.GhostBlocksCommand;
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
        // Run every 5 seconds (100 ticks) to reset age of ghost blocks
        // FallingBlocks naturally despawn at ~600 ticks (30 seconds), so we reset well before that
        // Reduced frequency from 1 second to 5 seconds for better TPS performance
        ageResetTask = getServer().getScheduler().runTaskTimer(this, () -> {
            // Use the optimized age reset method that limits operations per tick
            ghostBlockManager.resetGhostBlockAges();
            
            // Also run cleanup periodically to remove invalid blocks
            ghostBlockManager.cleanupInvalidGhostBlocks();
        }, 100L, 100L); // Run every 5 seconds with TPS-optimized operations
    }

    public GhostBlockManager getGhostBlockManager() {
        return ghostBlockManager;
    }

    public GUIManager getGUIManager() {
        return guiManager;
    }
}
