# RESEARCH — CarnetAutoDZ (verified 2026-10-09)

## Stack versions (all verified against developer.android.com / mvnrepository)

| Component | Selected | Source |
|---|---|---|
| Compose BOM (stable) | 2026.09.00 | developer.android.com/…/compose/bom |
| material3 | 1.4.0 | developer.android.com/jetpack/androidx/releases/compose |
| Room (stable) | 2.8.5 (released 2026-09-09) | developer.android.com/jetpack/androidx/releases/room |
| AGP | 8.13.x supports Kotlin 2.2–2.3, max API 36.1 | developer.android.com/build/releases |
| Gradle | 8.13 pairs with AGP 8.13 | same |

## SELECT decision: pin proven versions, not latest

Evidence against chasing latest (verified):
- Room 2.8 requires minSDK 23+, AGP 8.4+, Kotlin 2.0+ with KSP2; third-party report of
  KSP-processor breakage when mixing Room 2.8 + KSP 2.2 on AGP 8.13 (candela cfe512b).
- Hilt 2.59 requires AGP 9; Compose 1.12+ requires compileSdk 37 + AGP 9.

Selected (proven by prior cloud build producing installable APKs in this same
environment): **AGP 8.6.1, Kotlin 1.9.25, KSP 1.9.25-1.0.20, Gradle 8.9,
compileSdk/targetSdk 34, minSdk 26, Compose BOM 2024.09.02, Room 2.6.1,
navigation-compose 2.7.7, coroutines 1.8.1, JUnit 4.13.2.**

Rationale: deterministic green CI on `ubuntu-latest` + Temurin 17 matters more
than novelty for a fully-offline CRUD app that uses no new platform APIs.

## Domain facts (Algeria)

- Currency: Algerian dinar (DZD); centimes effectively unused in cash prices →
  store amounts as whole-dinar `Long`.
- Service intervals used: vidange 10 000 km (standard for semi-synthetic in DZ
  conditions), courroie de distribution 60 000 km (conservative; manufacturer
  range 60–120k), pneus 40 000 km (rotation/replacement heuristic).
  These are defaults, not prescriptions — recorded per service in km.
- OBD-II P0xxx codes are SAE-standardised identifiers (facts). FR/AR
  descriptions in this repo are original clean-room paraphrases.
- RTL: Arabic strings render RTL automatically; `supportsRtl=true`, no
  INTERNET permission (offline by construction).
