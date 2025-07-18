# Setup Guide for GhostBlocks Plugin

This guide will help you set up and use the GhostBlocks plugin on your Minecraft server.

## Installation

1. **Download the JAR file** from the `target/` directory after building, or download a pre-built release
2. **Copy the JAR file** to your server's `plugins/` directory
3. **Restart your server** to enable the plugin

## First Use

1. **Join your server** and run `/ghostblocks` to open the GUI
2. **Click on any block** in the GUI to get it as a ghost block item
3. **Place the ghost block** like a normal block - it will automatically become a ghost block
4. **Get the remover tool** by clicking the stick in the GUI
5. **Right-click ghost blocks** with the remover tool to remove them

## Admin Commands

- `/ghostblocks reload` - Reload the plugin and save/load ghost blocks
- `/ghostblocks cleanup` - Remove any invalid ghost blocks
- `/ghostblocks count` - Show the number of ghost blocks in the world

## Configuration

The plugin automatically saves ghost blocks to `plugins/GhostBlocks/ghostblocks.yml`. This file should not be manually edited.

## Troubleshooting

If you encounter issues:
1. Check the server console for error messages
2. Make sure you're using Paper 1.20.4 (not Bukkit/Spigot)
3. Ensure the plugin has proper permissions to read/write files
4. Try the cleanup command to remove invalid ghost blocks

## Permissions

- `ghostblocks.use` - Allows using the plugin (default: true)
- `ghostblocks.admin` - Allows admin commands (default: op)

## Building from Source

If you want to build the plugin yourself:
1. Clone the repository
2. Make sure you have Java 17+ and Maven 3.6+
3. Run `mvn clean package`
4. The JAR will be in the `target/` directory

## Support

If you need help or find bugs, check the README.md file for more detailed information.
