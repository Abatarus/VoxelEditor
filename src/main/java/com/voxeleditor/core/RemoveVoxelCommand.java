package com.voxeleditor.core;

public final class RemoveVoxelCommand implements VoxelCommand {
    private final VoxelGrid grid;
    private final int x;
    private final int y;
    private final int z;

    private boolean hadPrevious;
    private Voxel previousVoxel;

    public RemoveVoxelCommand(VoxelGrid grid, int x, int y, int z) {
        this.grid = grid;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void execute() {
        previousVoxel = grid.tryGetVoxel(x, y, z).orElse(null);
        hadPrevious = previousVoxel != null;
        grid.removeVoxel(x, y, z);
    }

    @Override
    public void undo() {
        if (hadPrevious) {
            grid.setVoxel(x, y, z, previousVoxel);
        } else {
            grid.removeVoxel(x, y, z);
        }
    }
}
