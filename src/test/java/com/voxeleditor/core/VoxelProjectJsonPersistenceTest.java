package com.voxeleditor.core;

import com.voxeleditor.persistence.VoxelProjectJsonPersistence;

import java.nio.file.Files;
import java.nio.file.Path;

public final class VoxelProjectJsonPersistenceTest {
    public static void runAll() throws Exception {
        saveThenLoadRoundTripRestoresGrid();
        loadRejectsInvalidJson();
        loadRejectsMissingVoxelsArray();
        loadRejectsMalformedVoxelEntry();
    }

    private static void saveThenLoadRoundTripRestoresGrid() throws Exception {
        VoxelGrid grid = new VoxelGrid(3, 2, 2);
        grid.setVoxel(1, 0, 1, new Voxel((byte) 10, (byte) 20, (byte) 30, (byte) 40, 4));
        grid.setVoxel(2, 1, 0, new Voxel((byte) 50, (byte) 60, (byte) 70, (byte) 80, 9));

        Path tempFile = Files.createTempFile("voxel-project", ".json");

        VoxelProjectJsonPersistence.save(grid, tempFile);
        VoxelGrid loaded = VoxelProjectJsonPersistence.load(tempFile);

        assertEquals(3, loaded.width(), "width");
        assertEquals(2, loaded.height(), "height");
        assertEquals(2, loaded.depth(), "depth");

        assertEquals(grid.tryGetVoxel(1, 0, 1).orElseThrow(), loaded.tryGetVoxel(1, 0, 1).orElseThrow(), "voxel(1,0,1)");
        assertEquals(grid.tryGetVoxel(2, 1, 0).orElseThrow(), loaded.tryGetVoxel(2, 1, 0).orElseThrow(), "voxel(2,1,0)");
        assertTrue(loaded.tryGetVoxel(0, 0, 0).isEmpty(), "empty cell should remain empty");
    }

    private static void loadRejectsInvalidJson() throws Exception {
        Path tempFile = Files.createTempFile("voxel-project-invalid", ".json");
        Files.writeString(tempFile, "{ invalid json }");

        assertThrows(IllegalArgumentException.class, () -> VoxelProjectJsonPersistence.load(tempFile));
    }

    private static void loadRejectsMissingVoxelsArray() throws Exception {
        Path tempFile = Files.createTempFile("voxel-project-missing-voxels", ".json");
        Files.writeString(tempFile, """
                {
                  "width": 1,
                  "height": 1,
                  "depth": 1
                }
                """);

        assertThrows(IllegalArgumentException.class, () -> VoxelProjectJsonPersistence.load(tempFile));
    }

    private static void loadRejectsMalformedVoxelEntry() throws Exception {
        Path tempFile = Files.createTempFile("voxel-project-malformed-voxel", ".json");
        Files.writeString(tempFile, """
                {
                  "width": 1,
                  "height": 1,
                  "depth": 1,
                  "voxels": [
                    { "x": 0, "y": 0, "z": 0, "r": 1, "g": 2, "b": 3, "a": 4 }
                  ]
                }
                """);

        assertThrows(IllegalArgumentException.class, () -> VoxelProjectJsonPersistence.load(tempFile));
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

    private static <T extends Throwable> void assertThrows(Class<T> type, ThrowingRunnable action) {
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

    @FunctionalInterface
    private interface ThrowingRunnable {
        void run() throws Exception;
    }
}
