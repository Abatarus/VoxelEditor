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
### DEC-003: Lightweight JSON Persistence Prototype Without External JSON Dependency
- **Date:** 2026-02-25
- **Status:** Accepted
- **Context:** The immediate backlog requires a project save/load JSON prototype for voxel grids, while the current bootstrap keeps dependencies minimal.
- **Options Considered:**
  - Add a JSON library dependency (e.g., Jackson/Gson)
  - Implement a constrained, format-specific serializer/parser for the prototype
- **Decision:** Implement a constrained JSON serializer/parser dedicated to voxel project persistence for the prototype phase.
- **Consequences:**
  - Delivers save/load capability quickly with no new dependency setup.
  - Parser is intentionally narrow and should be replaced or hardened before production-grade import/export.
