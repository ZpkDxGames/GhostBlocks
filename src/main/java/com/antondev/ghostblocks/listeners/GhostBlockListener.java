package com.antondev.ghostblocks.listeners;

import com.antondev.ghostblocks.GhostBlocksPlugin;
import com.antondev.ghostblocks.data.GhostBlock;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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

        // Check if it's our GUI
        if (!plugin.getGUIManager().isGhostBlockGUI(title)) {
            return;
        }

        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        // Check if it's the remover tool
        if (plugin.getGUIManager().isGhostBlockRemover(clickedItem)) {
            player.getInventory().addItem(clickedItem.clone());
            player.sendMessage(ChatColor.GREEN + "You received the Ghost Block Remover!");
            player.closeInventory();
            return;
        }

        // Check if it's a category button
        if (plugin.getGUIManager().isCategoryButton(clickedItem)) {
            String category = plugin.getGUIManager().getCategoryFromButton(clickedItem);
            if (category != null) {
                plugin.getGUIManager().openCategoryGUI(player, category, 0);
            }
            return;
        }

        // Check if it's a navigation button
        if (plugin.getGUIManager().isNavigationButton(clickedItem)) {
            plugin.getGUIManager().handleNavigation(player, clickedItem);
            return;
        }

        // Check if it's a valid block material from any category
        boolean isValidBlock = false;
        for (List<Material> categoryBlocks : plugin.getGUIManager().getCategories().values()) {
            if (categoryBlocks.contains(clickedItem.getType())) {
                isValidBlock = true;
                break;
            }
        }

        if (isValidBlock) {
            ItemStack ghostBlockItem = plugin.getGUIManager().createGhostBlockItem(clickedItem.getType());
            player.getInventory().addItem(ghostBlockItem);
            player.sendMessage(ChatColor.GREEN + "You received a Ghost " + 
                formatMaterialName(clickedItem.getType()) + "!");
            player.closeInventory();
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemInHand();

        // Check if it's a ghost block item
        if (!plugin.getGUIManager().isGhostBlockItem(item)) {
            return;
        }

        // Cancel the normal block placement
        event.setCancelled(true);

        // Get the block location
        Location location = event.getBlockPlaced().getLocation();
        
        // Add 0.5 to center the falling block in the block space
        location.add(0.5, 0, 0.5);

        // Create the ghost block
        Material material = item.getType();
        plugin.getGhostBlockManager().createGhostBlock(location, material);

        // Remove one item from the player's hand
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(null);
        }

        player.sendMessage(ChatColor.GREEN + "Ghost block placed!");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        // Check if it's the ghost block remover
        if (!plugin.getGUIManager().isGhostBlockRemover(item)) {
            return;
        }

        // Check if it's a right-click
        if (event.getAction().toString().contains("RIGHT_CLICK")) {
            // Try to find and remove ghost blocks in the area the player is looking at
            boolean removed = false;
            
            // Get all ghost blocks and check which one is closest to the player's line of sight
            for (com.antondev.ghostblocks.data.GhostBlock ghostBlock : plugin.getGhostBlockManager().getAllGhostBlocks()) {
                Location ghostLoc = ghostBlock.getLocation();
                
                // Check if ghost block is within reasonable distance (10 blocks)
                if (ghostLoc.getWorld().equals(player.getWorld()) && 
                    ghostLoc.distance(player.getLocation()) <= 10) {
                    
                    // Check if the ghost block is roughly in the player's line of sight
                    Location playerEye = player.getEyeLocation();
                    org.bukkit.util.Vector direction = playerEye.getDirection();
                    org.bukkit.util.Vector toGhost = ghostLoc.toVector().subtract(playerEye.toVector());
                    
                    // Check if the ghost block is in the general direction the player is looking
                    if (direction.dot(toGhost.normalize()) > 0.8) { // 0.8 is roughly 36 degrees
                        if (plugin.getGhostBlockManager().removeGhostBlockAt(ghostLoc)) {
                            player.sendMessage(ChatColor.GREEN + "Ghost block removed!");
                            removed = true;
                            break;
                        }
                    }
                }
            }

            if (!removed) {
                player.sendMessage(ChatColor.RED + "No ghost block found in that direction!");
            }

            event.setCancelled(true);
        }
    }
}
