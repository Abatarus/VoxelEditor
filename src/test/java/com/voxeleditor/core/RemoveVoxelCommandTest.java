package com.voxeleditor.core;

public final class RemoveVoxelCommandTest {
    public static void runAll() {
        removeExistingVoxelCanBeUndoneAndRedone();
        removeEmptyCellKeepsGridUnchangedAfterUndoRedo();
    }

    private static void removeExistingVoxelCanBeUndoneAndRedone() {
        CommandHistory history = new CommandHistory();
        VoxelGrid grid = new VoxelGrid(2, 2, 2);
        Voxel original = new Voxel((byte) 9, (byte) 8, (byte) 7, (byte) 6, 5);
        grid.setVoxel(1, 1, 1, original);

        history.execute(new RemoveVoxelCommand(grid, 1, 1, 1));
        assertTrue(grid.tryGetVoxel(1, 1, 1).isEmpty(), "voxel should be removed");

        history.undo();
        assertEquals(original, grid.tryGetVoxel(1, 1, 1).orElseThrow(), "undo should restore original voxel");

        history.redo();
        assertTrue(grid.tryGetVoxel(1, 1, 1).isEmpty(), "redo should remove voxel again");
    }

    private static void removeEmptyCellKeepsGridUnchangedAfterUndoRedo() {
        CommandHistory history = new CommandHistory();
        VoxelGrid grid = new VoxelGrid(2, 2, 2);

        history.execute(new RemoveVoxelCommand(grid, 0, 0, 0));
        assertTrue(grid.tryGetVoxel(0, 0, 0).isEmpty(), "empty cell should remain empty");

        history.undo();
        assertTrue(grid.tryGetVoxel(0, 0, 0).isEmpty(), "undo should keep empty cell empty");

        history.redo();
        assertTrue(grid.tryGetVoxel(0, 0, 0).isEmpty(), "redo should keep empty cell empty");
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
}
