# Voxel Editor Project Plan (Avalonia + OpenGL)

## 1. Vision and Scope
Build a cross-platform voxel model editor with a desktop-first UX, powered by Avalonia for UI and OpenGL for rendering. The initial target is a stable MVP for creating, editing, and exporting voxel assets.

## 2. Product Goals
- Create and edit voxel models with intuitive tools.
- Keep rendering interactive with smooth viewport navigation.
- Support project persistence and common export formats.
- Establish a test-first (TDD) development workflow.

## 3. Technical Stack
- **Language:** C# (.NET)
- **UI:** Avalonia
- **Rendering:** OpenGL (through an Avalonia-compatible GL control strategy)
- **Testing:** xUnit (unit + integration), lightweight rendering contract tests where applicable

## 4. Architecture Outline
- **VoxelEditor.App**: Avalonia app bootstrap and composition root.
- **VoxelEditor.UI**: Views, view models, commands, and interaction layer.
- **VoxelEditor.Core**: Domain model (voxel grid, tools, scene state, undo/redo).
- **VoxelEditor.Rendering**: OpenGL abstraction, camera, shaders, mesh generation.
- **VoxelEditor.Persistence**: Project save/load, import/export adapters.
- **VoxelEditor.Tests**: Unit/integration tests.

## 5. Milestones

### Milestone 1: Foundation
- Create solution and projects.
- Define domain entities (`Voxel`, `VoxelGrid`, `Scene`, `ToolState`).
- Implement command pipeline and undo/redo contracts.
- Add CI pipeline running tests.

### Milestone 2: Rendering + Navigation
- Add OpenGL viewport hosting in Avalonia.
- Implement camera controls (orbit, pan, zoom).
- Render test scene with a simple voxel mesh.
- Add rendering contract tests for camera math and mesh generation.

### Milestone 3: Core Editing Tools
- Implement voxel placement/removal.
- Add brush radius and color/material assignment.
- Add selection and fill tools.
- Validate behavior via TDD scenarios.

### Milestone 4: Project Persistence
- Add project serialization format.
- Implement save/load workflows.
- Add import/export (start with one format, extend later).
- Cover persistence paths with integration tests.

### Milestone 5: Usability + Performance
- Add tool panels, palette management, and shortcuts.
- Optimize mesh rebuild strategy and GPU upload path.
- Add scene-level stress tests and profiling targets.

## 6. TDD Workflow (Mandatory)
For every feature:
1. Write failing tests first (red).
2. Implement minimal code to satisfy tests (green).
3. Refactor while keeping tests green.
4. Commit with a short note linking tests and behavior.

Definition of done for each feature:
- New/updated tests are present.
- All tests pass locally.
- Decision log updated if an architectural or product decision was made.

## 7. Initial Backlog (High Priority)
- Create voxel data structure with bounds-safe access.
- Add add/remove voxel command with undo/redo.
- Render chunked voxel mesh in viewport.
- Add camera interaction mapping.
- Add project save/load JSON prototype.

## 8. Quality Gates
- Unit tests for domain logic.
- Integration tests for persistence and command flows.
- Non-flaky deterministic tests required in CI.
- No feature merged with failing tests.
