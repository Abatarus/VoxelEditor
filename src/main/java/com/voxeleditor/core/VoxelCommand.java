package com.voxeleditor.core;

public interface VoxelCommand {
    void execute();

    void undo();
}
