# Test Execution Commands

> **PowerShell note:** Always wrap `-D` properties in double quotes in PowerShell terminal.
> Example: `mvn test "-Dsurefire.suiteXmlFiles=..."` (not needed in cmd.exe)

---

## Users Module

| Command                                                               | Execution                            | Scenarios                        |
| --------------------------------------------------------------------- | ------------------------------------ | -------------------------------- |
| `mvn test -Dtest=UserModuleRunner`                                  | All sequential                       | TC1–TC25 + Cleanup              |
| `mvn test "-Dsurefire.suiteXmlFiles=testng-suites/users-suite.xml"` | Create parallel + Actions sequential | TC1–TC25 + Cleanup              |
| `mvn test -Dtest=UserCreateRunner`                                  | Parallel (3 threads)                 | TC1, TC2, TC11, TC12, TC21, TC22 |
| `mvn test -Dtest=UserActionsRunner`                                 | Sequential                           | TC3–TC25 + Cleanup              |

### Users Suite XML — What happens

1. **Phase 1** — `UserCreateRunner` runs TC1, TC2, TC11, TC12 in **parallel** (3 threads)
2. **Phase 2** — `UserActionsRunner` runs TC3–TC20 sequentially, then **TC_CLEANUP removes all created users**

---

## Login Module

| Command                               | Execution | Scenarios           |
| ------------------------------------- | --------- | ------------------- |
| `mvn test -Dtest=LoginModuleRunner` | Parallel  | All login scenarios |

---

## All Modules (Login + Users + AWS Labs + GCP Labs)

| Command                                                                          | Duration        | Scenarios                                      |
| -------------------------------------------------------------------------------- | --------------- | ---------------------------------------------- |
| `mvn test "-Dsurefire.suiteXmlFiles=testng-suites/all-except-roles-suite.xml"` | ~2h 20m–2h 45m | All login + all users + all AWS + all GCP labs |

### Execution order

1. **Phase 1** — Login (parallel 3 threads)
2. **Phase 2** — Users (sequential)
3. **Phase 3** — AWS TC1–TC12 (parallel 2 threads)
4. **Phase 4** — GCP TC1–TC12 (parallel 2 threads)
5. **Phase 5** — AWS TC13–TC16 (sequential)
6. **Phase 6** — GCP TC13–TC16 (sequential)
7. **Phase 7** — AWS Cleanup
8. **Phase 8** — GCP Cleanup

---

## Login + Users Combined

| Command                                                                     | Execution                         | Scenarios             |
| --------------------------------------------------------------------------- | --------------------------------- | --------------------- |
| `mvn test -Dtest=LoginUserModuleRunner`                                   | Sequential (both)                 | All login + all users |
| `mvn test "-Dsurefire.suiteXmlFiles=testng-suites/login-users-suite.xml"` | Login parallel + Users sequential | All login + all users |

### login-users-suite.xml — What happens

1. **Phase 1** — Login tests run in **parallel** (3 threads)
2. **Phase 2** — User Management tests run **sequentially** (1 thread)

---

## Labs Module

| Command                                                                       | Duration    | Use When                                                        |
| ----------------------------------------------------------------------------- | ----------- | --------------------------------------------------------------- |
| `mvn test "-Dsurefire.suiteXmlFiles=testng-suites/fast-smoke-suite.xml"`    | ~25–30 min | **During development** — quick validation after each fix |
| `mvn test "-Dsurefire.suiteXmlFiles=testng-suites/labs-suite.xml"`          | ~50–60 min | AWS only full run                                               |
| `mvn test "-Dsurefire.suiteXmlFiles=testng-suites/gcp-labs-suite.xml"`      | ~50–60 min | GCP only full run                                               |
| `mvn test "-Dsurefire.suiteXmlFiles=testng-suites/combined-labs-suite.xml"` | ~1.5–2 hr  | **Final validation** — full AWS+GCP run                  |

### fast-smoke-suite.xml — What it runs

4 TCs in parallel (2 threads): AWS TC1, AWS TC9, GCP TC1, GCP TC9 + both cleanups.
Use this after every fix — fast feedback without waiting 2 hours.

### combined-labs-suite.xml — Execution order

1. **Phase 1** — AWS TC1–TC12 (provision) — **parallel 2 threads**
2. **Phase 2** — GCP TC1–TC12 (provision) — **parallel 2 threads**
3. **Phase 3** — AWS TC13–TC16 (lazy lab) — sequential
4. **Phase 4** — GCP TC13–TC16 (lazy lab) — sequential
5. **Phase 5** — AWS Cleanup — sequential
6. **Phase 6** — GCP Cleanup — sequential

### Single TC during development

```powershell
# Run one specific TC (fastest — ~5-10 min)
mvn test "-Dtest=LabsProvisionRunner" "-Dcucumber.filter.tags=@AWS_TC1"
mvn test "-Dtest=GcpLabsRunner" "-Dcucumber.filter.tags=@GCP_TC9"
```

---

## Suite XML Files

| File                                      | Runs                                                 |
| ----------------------------------------- | ---------------------------------------------------- |
| `testng-suites/users-suite.xml`         | Users module (create parallel → actions sequential) |
| `testng-suites/login-users-suite.xml`   | Login (parallel) + Users (sequential)                |
| `testng-suites/login-suite.xml`         | Login module only                                    |
| `testng-suites/labs-suite.xml`          | AWS labs only (TC1–TC16 + cleanup)                  |
| `testng-suites/gcp-labs-suite.xml`      | GCP labs only (TC1–TC16 + cleanup)                  |
| `testng-suites/combined-labs-suite.xml` | AWS + GCP labs (TC1–TC16 each + both cleanups)      |
| `testng-suites/smoke-suite.xml`         | Smoke tests                                          |
| `testng-suites/regression-suite.xml`    | Regression tests                                     |
| `testng-suites/full-suite.xml`          | Full suite                                           |
| `testng-suites/parallel-all-suite.xml`  | All tests in parallel                                |

---

## Run by Tag

```powershell
# Single test case
mvn test "-Dtest=UserModuleRunner" "-Dcucumber.filter.tags=@TC5"

# Multiple test cases
mvn test "-Dtest=UserModuleRunner" "-Dcucumber.filter.tags=@TC5 or @TC6"

# All regression
mvn test "-Dtest=UserModuleRunner" "-Dcucumber.filter.tags=@regression"

# Specific group
mvn test "-Dtest=UserModuleRunner" "-Dcucumber.filter.tags=@usercreate"
mvn test "-Dtest=UserModuleRunner" "-Dcucumber.filter.tags=@useractions"
```

---

## Feature Files — Users Module

| File                                           | Tag              | Scenarios                        |
| ---------------------------------------------- | ---------------- | -------------------------------- |
| `FeatureFiles/users/user_create.feature`     | `@usercreate`  | TC1, TC2, TC11, TC12, TC21, TC22 |
| `FeatureFiles/users/user_management.feature` | `@useractions` | TC3–TC25 + TC_CLEANUP           |

---

## Quick Reference — Recommended Commands

```powershell
# Run all users (parallel create + sequential actions) — RECOMMENDED
mvn test "-Dsurefire.suiteXmlFiles=testng-suites/users-suite.xml"

# Run all users fully sequential — simple fallback
mvn test -Dtest=UserModuleRunner

# Run login + users together
mvn test "-Dsurefire.suiteXmlFiles=testng-suites/login-users-suite.xml"
```
