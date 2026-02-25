package com.voxeleditor.core;

public final class VoxelGridTest {
    public static void runAll() {
        constructorStoresDimensions();
        setThenGetReturnsVoxel();
        removeClearsCell();
        outOfBoundsThrows();
    }

    private static void constructorStoresDimensions() {
        VoxelGrid grid = new VoxelGrid(4, 5, 6);
        assertEquals(4, grid.width(), "width");
        assertEquals(5, grid.height(), "height");
        assertEquals(6, grid.depth(), "depth");
    }

    private static void setThenGetReturnsVoxel() {
        VoxelGrid grid = new VoxelGrid(3, 3, 3);
        Voxel voxel = new Voxel((byte) 127, (byte) 10, (byte) 40, (byte) 100, 1);

        grid.setVoxel(1, 1, 1, voxel);

        assertTrue(grid.tryGetVoxel(1, 1, 1).isPresent(), "voxel should be present");
        assertEquals(voxel, grid.tryGetVoxel(1, 1, 1).orElseThrow(), "voxel value");
    }

    private static void removeClearsCell() {
        VoxelGrid grid = new VoxelGrid(2, 2, 2);
        grid.setVoxel(1, 0, 1, new Voxel((byte) 1, (byte) 2, (byte) 3, (byte) 4, 2));

        assertTrue(grid.removeVoxel(1, 0, 1), "remove should return true");
        assertTrue(grid.tryGetVoxel(1, 0, 1).isEmpty(), "cell should be empty");
    }

    private static void outOfBoundsThrows() {
        VoxelGrid grid = new VoxelGrid(1, 1, 1);

        assertThrows(IndexOutOfBoundsException.class, () -> grid.setVoxel(-1, 0, 0, new Voxel((byte) 1, (byte) 1, (byte) 1, (byte) 1, 0)));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.tryGetVoxel(0, 1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.removeVoxel(0, 0, 2));
    }

    private static void assertEquals(Object expected, Object actual, String label) {
        if ((expected == null && actual != null) || (expected != null && !expected.equals(actual))) {
            throw new AssertionError(label + ": expected=" + expected + ", actual=" + actual);
        }
    }

    private static void assertTrue(boolean value, String label) {
        if (!value) {
            throw new AssertionError(label);
        }
    }

    private static <T extends Throwable> void assertThrows(Class<T> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable ex) {
            if (type.isInstance(ex)) {
                return;
            }
            throw new AssertionError("Expected " + type.getSimpleName() + " but got " + ex.getClass().getSimpleName(), ex);
        }

        throw new AssertionError("Expected exception: " + type.getSimpleName());
    }
}
