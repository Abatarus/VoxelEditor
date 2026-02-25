package com.voxeleditor.persistence;

import com.voxeleditor.core.Voxel;
import com.voxeleditor.core.VoxelGrid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VoxelProjectJsonPersistence {
    private static final Pattern INT_FIELD_PATTERN_TEMPLATE = Pattern.compile("\"([a-zA-Z]+)\"\\s*:\\s*(-?\\d+)");
    private static final Pattern VOXEL_OBJECT_PATTERN = Pattern.compile("\\{[^{}]*}");

    private VoxelProjectJsonPersistence() {
    }

    public static void save(VoxelGrid grid, Path path) throws IOException {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"width\": ").append(grid.width()).append(",\n");
        json.append("  \"height\": ").append(grid.height()).append(",\n");
        json.append("  \"depth\": ").append(grid.depth()).append(",\n");
        json.append("  \"voxels\": [\n");

        StringBuilder voxelEntries = new StringBuilder();
        grid.forEachFilled((x, y, z, voxel) -> {
            if (voxelEntries.length() > 0) {
                voxelEntries.append(",\n");
            }
            voxelEntries.append("    {")
                    .append("\"x\": ").append(x).append(", ")
                    .append("\"y\": ").append(y).append(", ")
                    .append("\"z\": ").append(z).append(", ")
                    .append("\"r\": ").append(voxel.r()).append(", ")
                    .append("\"g\": ").append(voxel.g()).append(", ")
                    .append("\"b\": ").append(voxel.b()).append(", ")
                    .append("\"a\": ").append(voxel.a()).append(", ")
                    .append("\"materialId\": ").append(voxel.materialId())
                    .append("}");
        });

        json.append(voxelEntries);
        json.append("\n  ]\n");
        json.append("}\n");

        Files.writeString(path, json.toString());
    }

    public static VoxelGrid load(Path path) throws IOException {
        String json = Files.readString(path);

        int width = parseRequiredInt(json, "width");
        int height = parseRequiredInt(json, "height");
        int depth = parseRequiredInt(json, "depth");

        String voxelsArrayContent = extractVoxelsArrayContent(json);
        VoxelGrid grid = new VoxelGrid(width, height, depth);

        Matcher voxelObjectMatcher = VOXEL_OBJECT_PATTERN.matcher(voxelsArrayContent);
        while (voxelObjectMatcher.find()) {
            String voxelJson = voxelObjectMatcher.group();
            int x = parseRequiredInt(voxelJson, "x");
            int y = parseRequiredInt(voxelJson, "y");
            int z = parseRequiredInt(voxelJson, "z");
            byte r = toByte(parseRequiredInt(voxelJson, "r"));
            byte g = toByte(parseRequiredInt(voxelJson, "g"));
            byte b = toByte(parseRequiredInt(voxelJson, "b"));
            byte a = toByte(parseRequiredInt(voxelJson, "a"));
            int materialId = parseRequiredInt(voxelJson, "materialId");

            grid.setVoxel(x, y, z, new Voxel(r, g, b, a, materialId));
        }

        return grid;
    }

    private static int parseRequiredInt(String json, String fieldName) {
        Matcher matcher = INT_FIELD_PATTERN_TEMPLATE.matcher(json);
        while (matcher.find()) {
            if (fieldName.equals(matcher.group(1))) {
                return Integer.parseInt(matcher.group(2));
            }
        }

        throw new IllegalArgumentException("Missing integer field: " + fieldName);
    }

    private static String extractVoxelsArrayContent(String json) {
        int voxelsFieldIndex = json.indexOf("\"voxels\"");
        if (voxelsFieldIndex < 0) {
            throw new IllegalArgumentException("Missing array field: voxels");
        }

        int arrayStart = json.indexOf('[', voxelsFieldIndex);
        if (arrayStart < 0) {
            throw new IllegalArgumentException("Invalid array field: voxels");
        }

        int depth = 0;
        for (int i = arrayStart; i < json.length(); i++) {
            char ch = json.charAt(i);
            if (ch == '[') {
                depth++;
            } else if (ch == ']') {
                depth--;
                if (depth == 0) {
                    return json.substring(arrayStart + 1, i);
                }
            }
        }

        throw new IllegalArgumentException("Unclosed array field: voxels");
    }

    private static byte toByte(int value) {
        if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
            throw new IllegalArgumentException("Voxel channel value is out of byte range: " + value);
        }
        return (byte) value;
    }
}
