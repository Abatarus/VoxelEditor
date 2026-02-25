# Decision Log

Track important technical and product decisions in chronological order.

## Template
### DEC-XXX: Title
- **Date:** YYYY-MM-DD
- **Status:** Proposed | Accepted | Deprecated | Superseded
- **Context:**
- **Options Considered:**
  - Option A
  - Option B
- **Decision:**
- **Consequences:**

---

### DEC-001: Technology Stack Baseline
- **Date:** 2026-02-25
- **Status:** Accepted
- **Context:** A desktop voxel editor requires a cross-platform UI framework and explicit GPU rendering control.
- **Options Considered:**
  - Avalonia + OpenGL
  - WPF + DirectX
  - Web stack + WebGL
- **Decision:** Use Avalonia for cross-platform desktop UI and OpenGL for rendering.
- **Consequences:**
  - Good portability across desktop platforms.
  - Rendering pipeline control remains explicit.
  - Requires careful integration between UI threading and GL context lifecycle.

### DEC-002: Development Workflow
- **Date:** 2026-02-25
- **Status:** Accepted
- **Context:** The project needs reliability while implementing editing operations and persistence.
- **Options Considered:**
  - Code-first then tests
  - Test-driven development (TDD)
- **Decision:** Adopt mandatory TDD for all functional work.
- **Consequences:**
  - More predictable behavior and lower regression risk.
  - Slightly higher short-term implementation overhead.
  - Better long-term maintainability.

### DEC-003: First Implementation Slice in Milestone 1
- **Date:** 2026-02-25
- **Status:** Accepted
- **Context:** The project must begin implementation in small validated steps aligned with the plan.
- **Options Considered:**
  - Start with UI/rendering host integration
  - Start with core voxel domain model and tests
- **Decision:** Start with `VoxelEditor.Core` domain primitives (`Voxel`, `VoxelGrid`) and tests as the first vertical slice.
- **Consequences:**
  - Provides a stable, testable base for command pipeline and rendering mesh generation.
  - Keeps first implementation independent from platform/UI constraints.

### DEC-004: Undo/Redo Contract Shape
- **Date:** 2026-02-25
- **Status:** Accepted
- **Context:** Milestone 1 requires a command pipeline and undo/redo contract before UI integration.
- **Options Considered:**
  - Generic command interface with execute/undo methods
  - Event-sourcing-only model without direct undo
- **Decision:** Use an `IVoxelCommand` contract with `Execute()` and `Undo()` plus a `CommandHistory` stack manager.
- **Consequences:**
  - Clear and testable behavior for command sequencing.
  - Easy extension for future editing commands.
  - Requires command implementations to capture undo state correctly.

### DEC-005: Platform Migration from .NET/Avalonia to Java
- **Date:** 2026-02-25
- **Status:** Accepted
- **Context:** The project must reliably target Linux and Windows with a simpler build/runtime setup in the current environment.
- **Options Considered:**
  - Keep .NET + Avalonia + OpenGL
  - Migrate to Java + JavaFX + LWJGL
- **Decision:** Migrate the codebase to Java with Gradle, JavaFX for desktop UI, and LWJGL for OpenGL rendering.
- **Consequences:**
  - Cross-platform support remains strong on Linux and Windows.
  - Existing .NET scaffolding is removed and replaced with Java project structure.
  - DEC-001 is superseded for implementation stack.
