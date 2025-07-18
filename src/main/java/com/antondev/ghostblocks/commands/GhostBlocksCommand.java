package com.antondev.ghostblocks.commands;

import com.antondev.ghostblocks.GhostBlocksPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GhostBlocksCommand implements CommandExecutor {
    private final GhostBlocksPlugin plugin;

    public GhostBlocksCommand(GhostBlocksPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("ghostblocks.use")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
            return true;
        }

        if (args.length == 0) {
            // Open the GUI
            plugin.getGUIManager().openGUI(player);
            return true;
        }

        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "help":
                    sendHelp(player);
                    return true;
                case "reload":
                    if (!player.hasPermission("ghostblocks.admin")) {
                        player.sendMessage(ChatColor.RED + "You don't have permission to reload the plugin!");
                        return true;
                    }
                    plugin.getGhostBlockManager().saveGhostBlocks();
                    plugin.getGhostBlockManager().loadGhostBlocks();
                    player.sendMessage(ChatColor.GREEN + "GhostBlocks plugin reloaded!");
                    return true;
                case "cleanup":
                    if (!player.hasPermission("ghostblocks.admin")) {
                        player.sendMessage(ChatColor.RED + "You don't have permission to cleanup ghost blocks!");
                        return true;
                    }
                    plugin.getGhostBlockManager().cleanupInvalidGhostBlocks();
                    player.sendMessage(ChatColor.GREEN + "Cleaned up invalid ghost blocks!");
                    return true;
                case "count":
                    int count = plugin.getGhostBlockManager().getAllGhostBlocks().size();
                    player.sendMessage(ChatColor.YELLOW + "There are currently " + count + " ghost blocks in the world.");
                    return true;
                default:
                    player.sendMessage(ChatColor.RED + "Unknown subcommand. Use /ghostblocks help for more information.");
                    return true;
            }
        }

        return false;
    }

    private void sendHelp(Player player) {
        player.sendMessage(ChatColor.GOLD + "=== GhostBlocks Help ===");
        player.sendMessage(ChatColor.YELLOW + "/ghostblocks" + ChatColor.WHITE + " - Open the ghost blocks GUI");
        player.sendMessage(ChatColor.YELLOW + "/ghostblocks help" + ChatColor.WHITE + " - Show this help message");
        player.sendMessage(ChatColor.YELLOW + "/ghostblocks count" + ChatColor.WHITE + " - Show the number of ghost blocks");
        
        if (player.hasPermission("ghostblocks.admin")) {
            player.sendMessage(ChatColor.YELLOW + "/ghostblocks reload" + ChatColor.WHITE + " - Reload the plugin");
            player.sendMessage(ChatColor.YELLOW + "/ghostblocks cleanup" + ChatColor.WHITE + " - Clean up invalid ghost blocks");
        }
        
        player.sendMessage(ChatColor.GRAY + "Ghost blocks are visible but have no hitbox. Use the Ghost Block Remover to remove them.");
    }
}
