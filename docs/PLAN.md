# PLAN — CarnetAutoDZ

## Scope (v1.0)

Offline-first Android app, Kotlin + Compose Material 3 + Room, FR UI with AR
fault-code content, DZD expenses, zero permissions, no account.

Screens: Suivi (reminders) · Entretien (history) · Codes (48 P0 offline) ·
Dépenses (DZD) · Véhicules (multi-vehicle + odometer).

## Pipeline

1. RESEARCH/VALIDATE/SELECT — done (docs/).
2. DEVELOP — clean-room source in `app/src` (no copied app code; only the
   proven Gradle/CI skeleton pattern reused).
3. TEST — JVM unit tests: Reminders (8), Expenses (5), FaultCodeCatalog (8).
4. FIX — iterate on CI failures until green.
5. PUBLISH SOURCE — push `main` to DAYONEPROMISE/carnautodz (public, MIT).
6. CLOUD BUILD — GitHub Actions: test + lint + assembleDebug, APK artifact +
   `debug-latest` + versioned `v1.0.N` releases.
7. DELIVER — verified APK copied to Shared + verified URLs.

## Limitations (honest)

- Debug APK (debug signature), not Play-signed; install via sideload / "unknown sources".
- No live OBD-II Bluetooth reading — reference only (adapter SDKs + permissions
  out of scope for v1; Play policy + BLE stack risk).
- Reminders are km-based on manual odometer entry; no background notifications in v1.
- Room schema v1, no migration yet (fresh install; exportSchema=false).
- FR-first UI; AR only for fault-code content in v1 (full AR UI is follow-up).

## Costs

- Build infra: $0 (GitHub Actions free tier, no backend, no keys, no services).
- Ongoing: $0 (offline app; only cost is optional Play Console $25 one-time if
  publishing to Play later).

## First-10-customers plan (Algeria)

1. 3 taxi drivers (Alger) via driver WhatsApp groups — logbook replaces paper carnet.
2. 2 auto-électriciens — fault-code FR/AR reference as daily tool.
3. 2 pièces détachées shops (Bab Ezzouar, Oran) — preload their customers' cars.
4. 2 driving schools (auto-écoles) — fleet maintenance tracking.
5. 1 mechana-friend mechanic — feedback loop on intervals/codes.
Channel: direct APK share (WhatsApp/Telegram) + QR at shops; ask each for 1
referral. Success metric: 10 active logbooks with ≥3 service entries in 30 days.
