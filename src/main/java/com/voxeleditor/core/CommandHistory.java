package com.voxeleditor.core;

import java.util.ArrayDeque;
import java.util.Deque;

public final class CommandHistory {
    private final Deque<VoxelCommand> undoStack = new ArrayDeque<>();
    private final Deque<VoxelCommand> redoStack = new ArrayDeque<>();

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public void execute(VoxelCommand command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public void undo() {
        if (!canUndo()) {
            throw new IllegalStateException("There is no command to undo.");
        }

        VoxelCommand command = undoStack.pop();
        command.undo();
        redoStack.push(command);
    }

    public void redo() {
        if (!canRedo()) {
            throw new IllegalStateException("There is no command to redo.");
        }

        VoxelCommand command = redoStack.pop();
        command.execute();
        undoStack.push(command);
    }
}
