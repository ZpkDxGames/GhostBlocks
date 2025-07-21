# GhostBlocks Plugin - Version 3.5.0 Changelog

## New Features Added

### 1. Performance Enhancements 🚀
- **Optimized TPS Performance**: Introduced `MAX_AGE_RESET_PER_TICK` limit (50 operations per tick) to prevent server lag
- **World-based Caching**: Added `worldGhostBlocks` map for faster ghost block lookups per world
- **Asynchronous Saving**: Ghost blocks are now saved asynchronously to prevent blocking the main thread
- **Intelligent Cleanup**: Added automatic cleanup every 30 seconds to remove invalid ghost blocks
- **Improved Age Reset**: New `resetGhostBlockAges()` method with batching for better performance

### 2. GUI Management System 🎮
- **Main Menu GUI**: New main menu with options for creation, management, and tools
- **Management GUI**: Browse, teleport to, and remove existing ghost blocks in the current world
  - **Pagination**: Navigate through large numbers of ghost blocks
  - **Location Display**: Shows coordinates and world name for each ghost block
  - **Quick Actions**: Left-click to teleport, right-click to remove
  - **Refresh**: Update the list in real-time
- **Enhanced Navigation**: Back buttons, page navigation, and user-friendly interface

### 3. New Commands 📝
- `/ghostblocks manage` - Open the ghost block management GUI
- `/ghostblocks remover` - Get a ghost block remover tool directly
- Enhanced help system with all new commands

### 4. Technical Improvements 🔧
- **Non-deprecated Methods**: Updated to use `World.spawn()` instead of deprecated `spawnFallingBlock()`
- **Better Error Handling**: Improved exception handling and logging
- **Memory Optimization**: Better data structure management and cleanup
- **Thread Safety**: Used `ConcurrentHashMap` for thread-safe operations

## Enhanced Features

### Improved Ghost Block Manager
- Added world-specific ghost block retrieval
- Better location normalization and grid alignment
- Enhanced persistence with async saving
- Improved cleanup and validation methods

### Enhanced GUI System
- New main menu for better user experience
- Management GUI for existing ghost blocks
- Better inventory management and navigation
- Improved player data tracking and cleanup

### Better Performance Monitoring
- Limited operations per tick to maintain TPS
- Intelligent cleanup intervals
- Async operations for heavy tasks
- Better memory management

## Technical Details

### Performance Optimizations
- **Age Reset Batching**: Maximum 50 ghost blocks processed per tick
- **Cleanup Interval**: Every 30 seconds automatic cleanup
- **World Caching**: O(1) lookup for ghost blocks by world
- **Async Saving**: Non-blocking save operations

### Memory Management
- **Concurrent Collections**: Thread-safe data structures
- **Automatic Cleanup**: Removes invalid ghost blocks
- **Player Data Cleanup**: Cleans up GUI state when players disconnect

### New Methods Added
- `getGhostBlocksInWorld(World)` - Get ghost blocks for specific world
- `getGhostBlocksInWorld(String)` - Get ghost blocks by world name
- `resetGhostBlockAges()` - Optimized age reset with batching
- `scheduleAsyncSave()` - Asynchronous saving
- `openMainMenuGUI(Player)` - Main menu interface
- `openManagementGUI(Player)` - Management interface
- `getPlayerCurrentPage(UUID)` - Player GUI state tracking

## Compatibility
- **Minecraft**: 1.20.4+
- **Java**: 17+
- **Server**: Paper/Spigot

## Installation
1. Stop your server
2. Replace the old GhostBlocks plugin with `ghostblocks-3.5.0.jar`
3. Start your server
4. Existing ghost blocks will be automatically migrated

## Future Plans (Noted for Future Development)
- New block types and categories will be added in future updates
- The codebase is now prepared for easy expansion of block selections

---

**Version**: 3.5.0  
**Build Date**: 2025-07-21  
**Compatibility**: Paper 1.20.4+, Java 17+  
**Performance**: Optimized for high-capacity servers
