package com.antondev.ghostblocks;

import com.antondev.ghostblocks.commands.GhostBlocksCommand;
import com.antondev.ghostblocks.listeners.GhostBlockListener;
import com.antondev.ghostblocks.managers.GhostBlockManager;
import com.antondev.ghostblocks.managers.GUIManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class GhostBlocksPlugin extends JavaPlugin {

    private GhostBlockManager ghostBlockManager;
    private GUIManager guiManager;

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

        // Debug: Print category info
        guiManager.printCategoryInfo();

        getLogger().info("GhostBlocks plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        // Save ghost blocks to file
        if (ghostBlockManager != null) {
            ghostBlockManager.saveGhostBlocks();
        }

        getLogger().info("GhostBlocks plugin has been disabled!");
    }

    public GhostBlockManager getGhostBlockManager() {
        return ghostBlockManager;
    }

    public GUIManager getGUIManager() {
        return guiManager;
    }
}
