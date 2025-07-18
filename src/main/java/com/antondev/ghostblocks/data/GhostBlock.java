package com.antondev.ghostblocks.data;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;

import java.util.UUID;

public class GhostBlock {
    private final UUID id;
    private final Location location;
    private final Material material;
    private FallingBlock fallingBlock;

    public GhostBlock(Location location, Material material) {
        this.id = UUID.randomUUID();
        this.location = location.clone();
        this.material = material;
    }

    public GhostBlock(UUID id, Location location, Material material) {
        this.id = id;
        this.location = location.clone();
        this.material = material;
    }

    public UUID getId() {
        return id;
    }

    public Location getLocation() {
        return location.clone();
    }

    public Material getMaterial() {
        return material;
    }

    public FallingBlock getFallingBlock() {
        return fallingBlock;
    }

    public void setFallingBlock(FallingBlock fallingBlock) {
        this.fallingBlock = fallingBlock;
    }

    public boolean isValid() {
        return fallingBlock != null && !fallingBlock.isDead();
    }
}
