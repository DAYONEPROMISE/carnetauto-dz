# CarnetAutoDZ

Offline vehicle maintenance logbook for Algerian drivers. 100% offline, no account, no permissions.

- **Suivi** — km reminders: vidange (10 000 km), courroie (60 000 km), pneus (40 000 km)
- **Entretien** — service history per vehicle (km + note)
- **Codes** — offline OBD-II fault-code reference (P0 codes, French + Arabic)
- **Dépenses** — expense tracker in Algerian dinars (DZD), totals by month/category
- **Véhicules** — multi-vehicle logbook with odometer tracking

## Stack

Kotlin, Jetpack Compose (Material 3), Room, Navigation-Compose. Pinned versions
(AGP 8.6.1, Kotlin 1.9.25, Room 2.6.1, Compose BOM 2024.09.02, Gradle 8.9) chosen
for proven CI build reliability — see `docs/`.

- `minSdk 26`, `targetSdk 34`, `compileSdk 34`
- Zero runtime permissions (no `INTERNET`): the app cannot access the network by construction

## Build

```sh
./gradlew :app:testDebugUnitTest :app:lintDebug :app:assembleDebug
```

APK: `app/build/outputs/apk/debug/app-debug.apk`

## Docs

- `docs/RESEARCH.md` — stack research (verified versions)
- `docs/VALIDATION.md` — claim validation log
- `docs/PLAN.md` — build plan + first-10-customers plan

## License

MIT — see `LICENSE`.
