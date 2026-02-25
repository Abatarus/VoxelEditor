# Agent Working Instructions

These are execution rules for implementation work on this repository.

## 1. Language and Documentation
- Keep all project artifacts in English.
- Use clear, implementation-oriented phrasing.

## 2. TDD-First Rule
For every new behavior or bug fix:
1. Write tests first.
2. Confirm tests fail for the expected reason.
3. Implement functionality.
4. Re-run tests and confirm all pass.
5. Refactor if needed without breaking tests.

Never skip the failing-test step unless the task is purely documentation.

## 3. Scope and Planning
Before coding:
- Define the smallest vertical slice.
- List acceptance criteria.
- Identify impacted modules.

During coding:
- Keep commits focused and coherent.
- Avoid mixing refactoring with unrelated feature work.

## 4. Decision Logging
When a meaningful decision is made (architecture, API, format, dependency, performance trade-off):
- Add an entry to `docs/DecisionLog.md`.
- Include context, options, selected choice, and consequences.
- Record status (`Proposed`, `Accepted`, `Deprecated`, `Superseded`).

## 5. Testing Expectations
- Domain logic: unit tests.
- Inter-module behavior: integration tests.
- Rendering math and mesh generation: deterministic contract tests.
- Bug fixes: include a regression test.

## 6. Pull Request Hygiene
Each PR should contain:
- What changed.
- Why it changed.
- Which tests were added/updated first.
- Evidence of passing test run.
- Decision log updates (if applicable).
