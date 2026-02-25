package com.voxeleditor.core;

public final class TestSuite {
    public static void main(String[] args) {
        VoxelGridTest.runAll();
        CommandHistoryTest.runAll();
        System.out.println("All tests passed.");
    }
}
