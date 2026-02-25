package com.voxeleditor.core;

public final class TestSuite {
    public static void main(String[] args) throws Exception {
        VoxelGridTest.runAll();
        CommandHistoryTest.runAll();
        RemoveVoxelCommandTest.runAll();
        VoxelProjectJsonPersistenceTest.runAll();
        System.out.println("All tests passed.");
    }
}
