package com.voxeleditor.core;

import java.util.Optional;

public final class VoxelGrid {
    private final int width;
    private final int height;
    private final int depth;
    private final Voxel[] cells;

    public VoxelGrid(int width, int height, int depth) {
        if (width <= 0 || height <= 0 || depth <= 0) {
            throw new IllegalArgumentException("Dimensions must be greater than zero.");
        }

        this.width = width;
        this.height = height;
        this.depth = depth;
        this.cells = new Voxel[width * height * depth];
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public int depth() {
        return depth;
    }

    public void setVoxel(int x, int y, int z, Voxel voxel) {
        cells[indexOf(x, y, z)] = voxel;
    }

    public Optional<Voxel> tryGetVoxel(int x, int y, int z) {
        return Optional.ofNullable(cells[indexOf(x, y, z)]);
    }

    public boolean removeVoxel(int x, int y, int z) {
        int index = indexOf(x, y, z);
        if (cells[index] == null) {
            return false;
        }
        cells[index] = null;
        return true;
    }

    private int indexOf(int x, int y, int z) {
        ensureInRange(x, width, "x");
        ensureInRange(y, height, "y");
        ensureInRange(z, depth, "z");

        return x + (y * width) + (z * width * height);
    }

    private static void ensureInRange(int coordinate, int limit, String name) {
        if (coordinate < 0 || coordinate >= limit) {
            throw new IndexOutOfBoundsException(name + " must be between 0 and " + (limit - 1));
        }
    }
}
