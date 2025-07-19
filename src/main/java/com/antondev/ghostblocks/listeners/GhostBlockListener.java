package com.antondev.ghostblocks.listeners;

import com.antondev.ghostblocks.GhostBlocksPlugin;
import com.antondev.ghostblocks.data.GhostBlock;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GhostBlockListener implements Listener {
    private final GhostBlocksPlugin plugin;

    public GhostBlockListener(GhostBlocksPlugin plugin) {
        this.plugin = plugin;
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

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        String title = event.getView().getTitle();

        // Check if it's any of our GUIs
        if (!plugin.getGUIManager().isGhostBlockGUI(title)) {
            return;
        }

        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) {
            return;
        }

        // Handle Ghost Block Remover
        if (plugin.getGUIManager().isGhostBlockRemover(clickedItem)) {
            ItemStack remover = plugin.getGUIManager().createGhostBlockRemover();
            player.getInventory().addItem(remover);
            player.closeInventory();
            player.sendMessage(ChatColor.GREEN + "You received the Ghost Block Remover!");
            return;
        }

        // Handle Category buttons
        if (plugin.getGUIManager().isCategoryButton(clickedItem)) {
            String category = plugin.getGUIManager().getCategoryFromButton(clickedItem);
            if (category != null) {
                plugin.getGUIManager().openCategoryGUI(player, category);
            }
            return;
        }

        // Handle Navigation buttons
        if (plugin.getGUIManager().isNavigationButton(clickedItem)) {
            plugin.getGUIManager().handleNavigation(player, clickedItem);
            return;
        }

        // Handle Back button
        if (plugin.getGUIManager().isBackButton(clickedItem.getType())) {
            plugin.getGUIManager().openMainGUI(player);
            return;
        }

        // Handle Ghost Block items
        if (plugin.getGUIManager().isGhostBlockItem(clickedItem)) {
            Material material = clickedItem.getType();
            
            // Add to selected blocks for ghost placement
            plugin.getGUIManager().addSelectedBlock(player, material);
            
            // Give them the special ghost block item
            ItemStack ghostBlockItem = plugin.getGUIManager().createGhostBlockItem(material);
            player.getInventory().addItem(ghostBlockItem);
            
            // Keep GUI open and provide feedback
            player.sendMessage(ChatColor.GREEN + "Added " + material.name().toLowerCase().replace('_', ' ') + " ghost block to your inventory!");
            player.sendMessage(ChatColor.GREEN + "You received a Ghost " +
                    formatMaterialName(material) + "! Place it to create a ghost block.");
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = event.getItemInHand();
        
        // Check if it's a ghost block item
        if (plugin.getGUIManager().isGhostBlockItem(itemInHand)) {
            event.setCancelled(true);
            
            // Get the material from the item type
            Material ghostMaterial = itemInHand.getType();
            
            // Get the target location - this follows Minecraft's grid system
            Location targetLocation = event.getBlock().getLocation();
            
            // Check if there's already a ghost block at this location
            if (plugin.getGhostBlockManager().isGhostBlockAt(targetLocation)) {
                player.sendMessage(ChatColor.YELLOW + "There's already a ghost block at this location!");
                return;
            }
            
            // Create the ghost block with the same material as the item
            plugin.getGhostBlockManager().createGhostBlock(targetLocation, ghostMaterial);
            
            // Remove one item from stack
            if (itemInHand.getAmount() > 1) {
                itemInHand.setAmount(itemInHand.getAmount() - 1);
            } else {
                player.getInventory().remove(itemInHand);
            }
            
            player.sendMessage(ChatColor.GREEN + "Ghost block placed!");
            return;
        }
        
        // Note: Legacy support for selected materials has been removed to prevent 
        // regular blocks from being accidentally converted to ghost blocks.
        // Only items specifically created by the GUI should become ghost blocks.
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Check if it's a stick (any stick, including the special remover) and right-click
        if (item != null && item.getType() == Material.STICK && 
            (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)) {
            
            event.setCancelled(true);
            
            Location targetLocation = null;
            boolean foundGhostBlock = false;
            
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null) {
                // Right-clicked on a block - check that exact location first
                targetLocation = event.getClickedBlock().getLocation();
                
                if (plugin.getGhostBlockManager().isGhostBlockAt(targetLocation)) {
                    if (plugin.getGhostBlockManager().removeGhostBlockAt(targetLocation)) {
                        player.sendMessage(ChatColor.GREEN + "Ghost block removed!");
                        return;
                    }
                }
            }
            
            // Use ray tracing to find ghost blocks in line of sight
            Location eyeLocation = player.getEyeLocation();
            org.bukkit.util.Vector direction = eyeLocation.getDirection();
            
            // Check each block in line of sight up to 10 blocks away
            for (double distance = 0.5; distance <= 10; distance += 0.5) {
                Location checkLocation = eyeLocation.clone().add(direction.clone().multiply(distance));
                Location blockLocation = checkLocation.getBlock().getLocation();
                
                if (plugin.getGhostBlockManager().isGhostBlockAt(blockLocation)) {
                    if (plugin.getGhostBlockManager().removeGhostBlockAt(blockLocation)) {
                        player.sendMessage(ChatColor.GREEN + "Ghost block removed!");
                        foundGhostBlock = true;
                        break;
                    }
                }
            }
            
            if (!foundGhostBlock) {
                player.sendMessage(ChatColor.YELLOW + "No ghost block found in your line of sight!");
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getGUIManager().clearSelectedBlocks(event.getPlayer());
        plugin.getGUIManager().cleanupPlayerData(event.getPlayer());
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (plugin.getGUIManager().isGhostBlockGUI(event.getView().getTitle())) {
            // Optional: Clear selected blocks when closing the GUI
            // plugin.getGUIManager().clearSelectedBlocks((Player) event.getPlayer());
        }
    }
}
