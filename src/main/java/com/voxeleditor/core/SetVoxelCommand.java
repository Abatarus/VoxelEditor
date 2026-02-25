package com.voxeleditor.core;

public final class SetVoxelCommand implements VoxelCommand {
    private final VoxelGrid grid;
    private final int x;
    private final int y;
    private final int z;
    private final Voxel newVoxel;

    private boolean hadPrevious;
    private Voxel previousVoxel;

    public SetVoxelCommand(VoxelGrid grid, int x, int y, int z, Voxel newVoxel) {
        this.grid = grid;
        this.x = x;
        this.y = y;
        this.z = z;
        this.newVoxel = newVoxel;
    }

    @Override
    public void execute() {
        hadPrevious = grid.tryGetVoxel(x, y, z).isPresent();
        previousVoxel = grid.tryGetVoxel(x, y, z).orElse(null);
        grid.setVoxel(x, y, z, newVoxel);
    }

    @Override
    public void undo() {
        if (hadPrevious) {
            grid.setVoxel(x, y, z, previousVoxel);
            return;
        }

        grid.removeVoxel(x, y, z);
    }
}
