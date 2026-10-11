# OpenPencil pass — carnetauto-dz

## Design (Shared/OpenPencil/carnetauto-dz/)
- `design.html` (expense cards + total + add action, DZ tokens) → `design.fig`
- `lint --preset accessibility`: 0 errors, 0 warnings

## Code review (no rewrite needed)
`ui/App.kt` nav items already carry `contentDescription`, `ui/Theme.kt` present.
No code change — forcing edits would only risk a green build.

## APKs
`ci.yml` adds `assembleRelease` (unsigned) + `app-release` artifact + a versioned
`release-v*-b*` GitHub Release for the release APK (debug flow unchanged).
