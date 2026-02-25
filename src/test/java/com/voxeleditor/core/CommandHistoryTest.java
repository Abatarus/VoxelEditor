package com.voxeleditor.core;

public final class CommandHistoryTest {
    public static void runAll() {
        executeUndoRedoFlowWorks();
        executeAfterUndoClearsRedoStack();
        undoRedoWithoutEntriesThrows();
    }

    private static void executeUndoRedoFlowWorks() {
        CommandHistory history = new CommandHistory();
        VoxelGrid grid = new VoxelGrid(2, 2, 2);

        history.execute(new SetVoxelCommand(grid, 1, 1, 1, new Voxel((byte) 11, (byte) 22, (byte) 33, (byte) 44, 7)));
        assertTrue(grid.tryGetVoxel(1, 1, 1).isPresent(), "set should apply voxel");

        history.undo();
        assertTrue(grid.tryGetVoxel(1, 1, 1).isEmpty(), "undo should remove voxel");

        history.redo();
        assertTrue(grid.tryGetVoxel(1, 1, 1).isPresent(), "redo should restore voxel");
    }

    private static void executeAfterUndoClearsRedoStack() {
        CommandHistory history = new CommandHistory();
        VoxelGrid grid = new VoxelGrid(2, 2, 2);

        history.execute(new SetVoxelCommand(grid, 0, 0, 0, new Voxel((byte) 1, (byte) 1, (byte) 1, (byte) 1, 1)));
        history.undo();
        history.execute(new SetVoxelCommand(grid, 1, 1, 1, new Voxel((byte) 2, (byte) 2, (byte) 2, (byte) 2, 2)));

        assertTrue(!history.canRedo(), "redo stack must be cleared");
    }

    private static void undoRedoWithoutEntriesThrows() {
        CommandHistory history = new CommandHistory();

        assertThrows(IllegalStateException.class, history::undo);
        assertThrows(IllegalStateException.class, history::redo);
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
