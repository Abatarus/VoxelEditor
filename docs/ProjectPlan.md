
# Voxel Editor Project Plan (Java + OpenGL)

## 1. Vision and Scope
Build a cross-platform voxel model editor with a desktop-first UX, powered by Java for portability and OpenGL for rendering. The initial target is a stable MVP for creating, editing, and exporting voxel assets on Linux and Windows.


## 2. Product Goals
- Create and edit voxel models with intuitive tools.
- Keep rendering interactive with smooth viewport navigation.
- Support project persistence and common export formats.
- Establish a test-first (TDD) development workflow.

## 3. Technical Stack

- **Language:** Java 21+
- **UI:** JavaFX
- **Rendering:** OpenGL via LWJGL
- **Build:** Gradle
- **Testing:** JUnit 5 (unit + integration), deterministic rendering contract tests where applicable

## 4. Architecture Outline
- **voxeleditor-app**: app bootstrap and composition root.
- **voxeleditor-ui**: JavaFX views, controllers, interaction layer.
- **voxeleditor-core**: domain model (voxel grid, tools, scene state, undo/redo).
- **voxeleditor-rendering**: OpenGL abstraction, camera, shaders, mesh generation.
- **voxeleditor-persistence**: project save/load, import/export adapters.
- **voxeleditor-tests**: unit/integration tests.
## 5. Milestones

### Milestone 1: Foundation

- Create Gradle project structure.
- Define domain entities (`Voxel`, `VoxelGrid`, `Scene`, `ToolState`).
- Implement command pipeline and undo/redo contracts.
- Add CI pipeline running tests on Linux + Windows.

### Milestone 2: Rendering + Navigation
- Add OpenGL viewport hosting in JavaFX.

- Implement camera controls (orbit, pan, zoom).
- Render test scene with a simple voxel mesh.
- Add rendering contract tests for camera math and mesh generation.

### Milestone 3: Core Editing Tools
- Implement voxel placement/removal.
- Add brush radius and material assignment.

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
