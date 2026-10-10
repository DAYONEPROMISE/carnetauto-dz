# VALIDATION LOG — CarnetAutoDZ (2026-10-09)

Every claim below was checked before being used. No fabricated URLs, versions, or APIs.

1. Compose BOM 2026.09.00 is latest stable — VERIFIED via developer.android.com BOM page.
2. material3 1.4.0 stable (2026-10-07) — VERIFIED via Compose release notes.
3. Room 2.8.5 stable (2026-09-09), minSDK 23, needs AGP 8.4+ — VERIFIED via Room release notes.
4. AGP 8.13 max API 36.1, Gradle 8.13, JDK 17 — VERIFIED via AGP 8.13 release notes.
5. Kotlin 2.3 needs AGP 8.2.2–8.13 — VERIFIED via kotlin-support table.
6. Room 2.8 + KSP 2.2 breakage risk on AGP 8.13 — third-party commit (techempower candela),
   treated as caution signal, not fact about our build. Decision: pin proven stack.
7. Pinned stack builds in CI — will be PROVEN by this repo's own CI run
   (test + lint + assembleDebug + APK artifact). Prior sibling project with the
   identical skeleton produced installable APKs in this environment.
8. Fault-code descriptions — original paraphrases, not copied; no source text reproduced.
9. No NETWORK permission claim — verifiable in `app/src/main/AndroidManifest.xml`
   (manifest contains zero `<uses-permission>`).
10. All GitHub URLs reported in the final delivery message will be opened/checked
    via API before being claimed (repo page, workflow run, release/APK artifact).
