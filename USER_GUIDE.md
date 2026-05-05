# Orgo UI Test Automation Framework — User Guide

> **Who is this guide for?**
> Everyone — from someone who has never heard of test automation to an experienced developer.
> No coding knowledge is required to run tests or add test data.

---

## Table of Contents

1. [What Is This Framework?](#1-what-is-this-framework)
2. [How It Works — Big Picture](#2-how-it-works--big-picture)
3. [Folder Structure](#3-folder-structure)
4. [One-Time Setup](#4-one-time-setup)
5. [Configuration — Credentials & Environment](#5-configuration--credentials--environment)
6. [Test Data — The Excel File](#6-test-data--the-excel-file)
7. [Running Tests](#7-running-tests)
8. [Understanding Test Results](#8-understanding-test-results)
9. [Writing New Test Cases](#9-writing-new-test-cases)
10. [Self-Healing AI Feature](#10-self-healing-ai-feature)
11. [Parallel Execution](#11-parallel-execution)
12. [Troubleshooting](#12-troubleshooting)
13. [Quick Reference — Cheat Sheet](#13-quick-reference--cheat-sheet)

---

## 1. What Is This Framework?

Think of this framework as a **robot that operates a web browser** exactly the way a human tester would — it opens the Orgo web application, clicks buttons, fills forms, checks results, and reports whether everything worked correctly.

### What problems does it solve?

| Without Automation | With This Framework |
|--------------------|---------------------|
| A human must click through every screen after each change | The robot does it automatically in minutes |
| Easy to miss a bug when manually testing 50+ screens | Every scenario is checked every time |
| Testing the same thing repeatedly is boring and error-prone | Runs are consistent and repeatable |
| No record of what was tested | Full reports with screenshots on failure |

### What does it test?

- **Login Module** — valid/invalid credentials, different user roles
- **User Management** — create, edit, delete, activate/deactivate users, change passwords, roles, add to teams
- **Lab Management** — request labs (single & batch), different plan types, policies

---

## 2. How It Works — Big Picture

```
┌─────────────────────────────────────────────────────────────────┐
│                        YOU / CI PIPELINE                        │
│                   runs a Maven command                          │
└──────────────────────────┬──────────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────────┐
│                    CUCUMBER (Test Orchestrator)                  │
│   Reads .feature files written in plain English                 │
│   Matches each line to a Java method (step definition)          │
└──────────────────────────┬──────────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────────┐
│                   PLAYWRIGHT (Browser Robot)                    │
│   Opens Chrome/Firefox, navigates pages, clicks, types          │
└──────────────────────────┬──────────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────────┐
│              SELF-HEALING AI (Safety Net)                       │
│   If a button moves or an ID changes, Gemini AI finds it again  │
└──────────────────────────┬──────────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────────┐
│                      REPORTS & LOGS                             │
│   HTML report, screenshots on failure, healing report           │
└─────────────────────────────────────────────────────────────────┘
```

### The three-layer test description

| Layer | File type | Written in | Example |
|-------|-----------|------------|---------|
| **What to test** (scenario) | `.feature` | Plain English | `And click on create a user button` |
| **How to do it** (step definition) | `.java` | Java code | Playwright clicks `#createUserBtn` |
| **Where the buttons are** (POM) | `.java` | Java code | `page.locator("#createUserBtn")` |

---

## 3. Folder Structure

```
Automation_Orgo_UI_Test/
│
├── src/
│   ├── main/java/
│   │   ├── AI_Framework/          ← Gemini AI integration classes
│   │   ├── Generic_Utility/       ← Core utilities (browser setup, Excel reader, waits, self-healing)
│   │   └── POM/                   ← Page Object Model — one file per screen
│   │       ├── LoginPage.java
│   │       ├── HomePage.java
│   │       ├── CreateUserPage.java
│   │       ├── UserPage.java
│   │       ├── LabsPage.java
│   │       └── ...
│   │
│   ├── main/resources/
│   │   └── TestData_Improved.xlsx ← ALL test data lives here
│   │
│   ├── test/java/
│   │   ├── FeatureFiles/          ← Plain-English test scenarios
│   │   │   ├── login/
│   │   │   │   └── login.feature
│   │   │   ├── users/
│   │   │   │   └── user_management.feature
│   │   │   └── labs/
│   │   │       ├── single_lab_request.feature
│   │   │       └── batch_provision.feature
│   │   │
│   │   ├── stepDefinitions/       ← Java code behind each feature line
│   │   │   ├── Hook.java          ← Opens/closes browser before/after each scenario
│   │   │   ├── Login.java
│   │   │   ├── User.java
│   │   │   └── Lab.java
│   │   │
│   │   └── runner/                ← Controls which tests run and in what mode
│   │       ├── UserModuleRunner.java
│   │       ├── LabsModuleRunner.java
│   │       ├── LoginModuleRunner.java
│   │       ├── SmokeTestRunner.java
│   │       └── RegressionTestRunner.java
│   │
│   └── test/resources/
│       ├── testng-suites/         ← TestNG XML config files
│       └── testdata/              ← Auto-generated JSON (do NOT edit manually)
│           ├── lab_data.json
│           ├── user_data.json
│           └── credentials.json
│
├── test-output/                   ← Generated after every run (not committed to git)
│   └── 2025-06-10_14-30-00/       ← Timestamped run folder
│       ├── reports/               ← HTML report
│       ├── screenshots/           ← PNG screenshots of failed tests
│       └── logs/                  ← Full execution logs
│
├── .env                           ← Your secret credentials (never commit this)
├── pom.xml                        ← Maven build configuration
└── USER_GUIDE.md                  ← This file
```

> **Rule of thumb:**
> - To change **what is tested** → edit a `.feature` file
> - To change **test data** → edit `TestData_Improved.xlsx`
> - To change **credentials / URL** → edit the `.env` file
> - Everything else is framework code — leave it alone unless you know what you are doing

---

## 4. One-Time Setup

### Prerequisites

Install these tools once on your machine:

| Tool | Version | Why needed | Download |
|------|---------|------------|----------|
| Java JDK | 11 or higher | Runs the framework | https://adoptium.net |
| Apache Maven | 3.6+ | Builds and runs tests | https://maven.apache.org |
| Git | Any | Version control | https://git-scm.com |
| Chrome or Firefox | Latest | The browser the robot uses | Already installed |

### Verify your installation

Open a terminal (Command Prompt or PowerShell on Windows) and run:

```
java -version
mvn -version
```

Both commands should print a version number. If you see an error, Java or Maven is not installed correctly.

### Get the project

```bash
git clone <repository-url>
cd Automation_Orgo_UI_Test
```

### Install dependencies

```bash
mvn clean install -DskipTests
```

This downloads all required libraries (takes a few minutes the first time).

### Install browser drivers

Playwright downloads its own browser binaries automatically on first run. No manual driver download is needed.

---

## 5. Configuration — Credentials & Environment

### The `.env` file

Create a file named `.env` in the project root (same folder as `pom.xml`). This file holds all secrets and is **never committed to git**.

```
# Application URL
BASE_URL=https://your-tenant.orgo.com

# Browser (chrome or firefox)
BROWSER_TYPE=chrome

# Gemini AI API Key (for self-healing)
GEMINI_API_KEY=AIzaSy...

# Tenant Admin credentials
TENANT_ADMIN_USERNAME=admin@example.com
TENANT_ADMIN_PASSWORD=YourPassword123

# MSP Admin credentials
MSP_ADMIN_USERNAME=mspadmin@example.com
MSP_ADMIN_PASSWORD=YourPassword123

# System Admin credentials
SYS_ADMIN_USERNAME=sysadmin
SYS_ADMIN_PASSWORD=YourPassword123

# Regular User credentials
USER_USERNAME=user@example.com
USER_PASSWORD=YourPassword123
```

### Multiple environments

If you run tests against different environments (trail, mini-prod, main-prod), create environment-specific files:

| File | Used when |
|------|-----------|
| `.env` | Default / local testing |
| `.env.trail` | Trail environment |
| `.env.miniprod` | Mini production |
| `.env.mainprod` | Main production |

To run against a specific environment:

```bash
mvn test -DENV=trail -P users
```

### Credential resolution order

The framework looks for credentials in this order and uses the first match:

```
1. System environment variable  (set on the OS or in CI pipeline)
       ↓  not found?
2. .env file                    (your local file)
       ↓  not found?
3. Excel fallback               (TestData_Improved.xlsx Credentials sheet)
```

This means CI pipelines can inject credentials as environment variables without any `.env` file.

---

## 6. Test Data — The Excel File

**Location:** `src/main/resources/TestData_Improved.xlsx`

This is the single source of truth for all test data. The framework reads it at the start of every test run and converts it to JSON automatically — **you never need to touch the JSON files**.

### Sheet: `Credentials`

| Row | What it stores |
|-----|----------------|
| 3 | Browser type (chrome/firefox) |
| 12 | Invalid password (for negative tests) |
| 13 | Invalid username (for negative tests) |

> Actual login credentials should be in `.env`, not Excel, for security.

### Sheet: `User`

This sheet drives all 20 user management test cases.

| Row | Test Case | Key columns |
|-----|-----------|-------------|
| 2 (TC01) | Create User | Col D=FirstName, E=LastName, F=Email, G=EmployeeId, H=LoginId, I=Password, J=ConfirmPassword |
| 4 (TC03) | Edit User | Col D-G=Updated values, H=Target LoginId, I=New LoginId |
| 5 (TC04) | Delete User | Col H=LoginId of user to delete |
| 7 (TC06) | Change Password | Col H=LoginId |
| 9 (TC08) | Add to Team | Col H=LoginId, Col C=TeamName |
| 10 (TC09) | View Details | Col H=LoginId |
| 11 (TC10) | Change Login ID | Col H=Old LoginId, Col I=New LoginId base |
| 14 (TC13) | Change Role | Col C=New Role name |
| 15 (TC14) | Change Email | Col F=New Email base |
| 18 (TC17) | Search by Email | Col H=LoginId |
| 20 (TC19) | Add as Team Admin | Col H=LoginId |
| 28 | Shared: UserRole | Col B=Default role name |

> **Column N (index 13)** is used by the framework at runtime to persist dynamically created login IDs so that later test cases can find the user. Do not delete or overwrite this column.

### Sheet: `Lab`

Each row is one lab test case.

| Column | Name | Example value |
|--------|------|---------------|
| A | TC_ID | TC01 |
| B | PlanType | Normal |
| C | LabRequestType | Single |
| D | LabType | Virtual |
| E | TeamID | team123 |
| F | PlanID | plan456 |
| H | PolicyName | DefaultPolicy |
| I | BatchName | Batch_Test |
| J | BatchDescription | Test batch |
| K | UserEmail | user@example.com |
| L | UserName | John Doe |

---

## 7. Running Tests

All test runs use Maven from the project root directory.

### Run by module (most common)

```bash
# Run all user management tests
mvn test -P users

# Run all login tests
mvn test -P login

# Run all lab tests
mvn test -P labs

# Run smoke tests only (quick sanity check)
mvn test -P smoke

# Run full regression suite
mvn test -P full
```

### Run specific test cases by tag

```bash
# Run only TC1 and TC2
mvn test -P users -Dcucumber.filter.tags="@TC1 or @TC2"

# Run only create-user scenarios
mvn test -P users -Dcucumber.filter.tags="@createuser"

# Run regression tests excluding negative cases
mvn test -P regression -Dcucumber.filter.tags="@regression and not @negative"
```

### Run against a specific environment

```bash
mvn test -P users -DENV=trail
```

### Available Maven profiles

| Profile | Tests included | Runs in parallel? |
|---------|---------------|-------------------|
| `smoke` | Scenarios tagged `@smoke` | Yes |
| `login` | All login scenarios | Yes |
| `users` | All user management scenarios | **No** (must be sequential) |
| `labs` | All lab scenarios | Yes |
| `regression` | Scenarios tagged `@regression` | Yes |
| `full` | Everything | Mixed |

> **Why are user tests sequential?**
> Some user test cases depend on data created by earlier ones (e.g., TC5 deactivates the user TC1 created). Running them in parallel would cause conflicts.

---

## 8. Understanding Test Results

### Where to find results

After every run, a timestamped folder is created:

```
test-output/
└── 2025-06-10_14-30-00/
    ├── reports/
    │   └── cucumber-report.html    ← Open this in a browser
    ├── screenshots/
    │   └── Failed_Scenario_Name.png
    └── logs/
        └── test-execution.log
```

A `_latest` shortcut (Windows junction) always points to the most recent run, so you can bookmark `test-output/_latest/reports/cucumber-report.html`.

### Reading the HTML report

Open `cucumber-report.html` in any browser. You will see:

```
✅ Green = Passed
❌ Red   = Failed
⚠️ Yellow = Skipped
```

Click on any failed scenario to expand it and see:
- Which step failed
- The error message
- A screenshot (if available)

### Screenshot naming

Failed test screenshots are saved as:

```
screenshots/Scenario_Name_ThreadName.png
```

### Self-healing report

If the AI had to repair a broken element locator, a `healing_report.json` is saved in the reports folder. It shows:
- Which element was broken
- What the original locator was
- What the AI found as a replacement
- Whether it succeeded

---

## 9. Writing New Test Cases

This section walks through adding a brand-new test case from scratch.

### Step 1 — Add test data to Excel (if needed)

Open `src/main/resources/TestData_Improved.xlsx`:
- Go to the relevant sheet (`User` or `Lab`)
- Add a new row with the test case ID and required data
- Save and close Excel

### Step 2 — Add the scenario to a feature file

Open the relevant `.feature` file in `src/test/java/FeatureFiles/`.

Add a new scenario block. Use plain English — each line must start with `Given`, `When`, `And`, or `Then`:

```gherkin
@TC21 @regression @newFeature
Scenario: My new test case description
  Given open the browser and enter the Url
  And login as tenant admin
  When navigate to organization and click on users tab
  And do something new
  Then validate the result
  And click on logout
  Then validate login page is displayed
```

**Tag rules:**
- `@TC21` — unique TC number
- `@regression` — included in regression runs
- `@smoke` — included in quick smoke runs (only add for critical paths)
- Custom tag like `@newFeature` — for running just this group

### Step 3 — Add the step definitions (Java code)

Open the relevant step definition file in `src/test/java/stepDefinitions/`.

For each **new** step line you added in the feature file, add a corresponding Java method:

```java
@And("do something new")
public void do_something_new() throws Throwable {
    Hook.base().page.waitForLoadState();
    WaitUtils.pause(WaitUtils.MEDIUM);
    // Use shDriver to interact with the page
    Hook.base().shDriver.click(Pages.getSomePage().getSomeButton(), "some button");
}
```

> **If you reuse an existing step line** (like `And click on logout`) — do NOT add it again. It already has a definition.

### Step 4 — Add POM locators (if interacting with a new element)

If your test clicks a button or fills a field that no existing POM method covers, open the relevant POM file in `src/main/java/POM/` and add a method:

```java
public Locator getMyNewButton() {
    return page.locator("#myNewButtonId");
}
```

Then expose it in `src/main/java/Util/Pages.java` if it's on a new page (follow the existing lazy getter pattern).

### Step 5 — Run just your new test

```bash
mvn test -P users -Dcucumber.filter.tags="@TC21"
```

---

## 10. Self-Healing AI Feature

### What is it?

Web applications change over time — a developer might rename a button's ID, move a field, or restructure a page. Normally this would break automated tests immediately.

The **self-healing feature** acts as a safety net. When the framework cannot find an element, instead of immediately failing it tries to fix itself.

### How healing works — step by step

```
Step 1: Try the original locator (e.g., #submitBtn)
           ↓ fails?

Step 2: Try a library of alternative locators
        (e.g., button:has-text('Submit'), input[type='submit'])
           ↓ all fail?

Step 3: Ask Google Gemini AI — send the full page HTML and
        the description of what we're looking for.
        Gemini returns a new locator.
           ↓ success?

Step 4: Save the new locator to healing_cache.json
        Next run: use the healed locator directly (skip Steps 1-3)
```

### What you need for AI healing

A Gemini API key in your `.env` file:
```
GEMINI_API_KEY=AIzaSy...
```

If no API key is provided, healing still works (Steps 1 and 2) but without AI (Step 3).

### Reading the healing report

After a run, check `test-output/_latest/reports/healing_report.json`:

```json
{
  "stepDescription": "create user button",
  "originalLocator": "#createUserBtn",
  "healedLocator": "button:has-text('Create User')",
  "healingMethod": "fallback",
  "finalStatus": "SUCCESS"
}
```

| Field | Meaning |
|-------|---------|
| `originalLocator` | What the framework tried first |
| `healedLocator` | What it found instead |
| `healingMethod` | `fallback` (library) or `AI` (Gemini) |
| `finalStatus` | `SUCCESS` = test continued; `FAILED` = test stopped |

> **Action required**: If healing is `AI` and `SUCCESS`, update the POM locator to the healed value so future runs don't need AI healing for the same element.

---

## 11. Parallel Execution

Tests that are independent can run in multiple browser windows at the same time, reducing total execution time.

### Which tests run in parallel?

| Module | Parallel? | Reason |
|--------|-----------|--------|
| Login | Yes | Each scenario is independent |
| Labs | Yes | Each scenario uses its own lab |
| Users | **No** | TC1 creates a user that TC5-TC20 depend on |
| Regression | Yes (except users) | Mixes login + lab scenarios |

### Thread safety

The framework uses Java `ThreadLocal` to give each parallel thread its own browser instance. There is no shared browser state between threads.

Dynamic data (like a newly created login ID) is stored in:
1. `static volatile` fields — visible across threads
2. Excel column N — persisted to disk for cross-scenario use

---

## 12. Troubleshooting

### Test fails with "Element not found" or timeout

**Cause:** The page element's ID/selector changed, or the page loaded too slowly.

**Fix:**
1. Check `test-output/_latest/screenshots/` — look at the screenshot of the failure
2. Check `test-output/_latest/reports/healing_report.json` — was healing attempted?
3. If the element changed permanently, update the locator in the POM file
4. If it was a timing issue, increase `WaitUtils.MEDIUM` or add an extra pause

### Login fails — "credentials not found"

**Cause:** The `.env` file is missing or a key is misspelled.

**Fix:**
1. Verify `.env` exists in the project root (same folder as `pom.xml`)
2. Check that all required keys are present (see [Section 5](#5-configuration--credentials--environment))
3. Run `mvn test -Dlog.level=DEBUG -P login` to see detailed credential loading logs

### Browser does not open / "playwright not found"

**Cause:** Playwright browser binaries not downloaded.

**Fix:**
Run this once:
```bash
mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install chromium"
```

### Excel file not found error

**Cause:** Excel path mismatch or file renamed.

**Fix:**
Confirm the file exists at `src/main/resources/TestData_Improved.xlsx`. If renamed, update `ConstantFilePath.java` line with the Excel path.

### TC1 passes but TC5 fails with "user not found"

**Cause:** TC1 wrote a login ID to Excel column N, but TC5 cannot read it (Excel not saved, or wrong row).

**Fix:**
1. Open `TestData_Improved.xlsx` — check that column N (column 14) of the User sheet has a value in the TC1 row (row 2)
2. If empty, re-run TC1 first: `mvn test -P users -Dcucumber.filter.tags="@TC1"`

### "Home page detected — relaunching browser" in logs

This is **normal behaviour**, not an error. Some tenant configurations redirect to a home page with a login popup instead of a direct sign-in form. The framework automatically closes and reopens the browser to get a clean sign-in page.

### Tests pass locally but fail in CI

**Common causes:**
1. `.env` file not present in CI — inject credentials as environment variables in the CI pipeline
2. CI machine has no display — add `setHeadless(true)` in the browser launch or run with a virtual display (Xvfb on Linux)
3. Different browser version in CI — pin the Playwright version in `pom.xml`

---

## 13. Quick Reference — Cheat Sheet

### Run commands

```bash
# Smoke tests (fastest)
mvn test -P smoke

# All login tests
mvn test -P login

# All user tests (sequential)
mvn test -P users

# All lab tests
mvn test -P labs

# Full regression
mvn test -P full

# Specific tag
mvn test -P users -Dcucumber.filter.tags="@TC1"

# Different environment
mvn test -P users -DENV=trail
```

### Important file locations

| What | Where |
|------|-------|
| Test data | `src/main/resources/TestData_Improved.xlsx` |
| Credentials | `.env` (project root) |
| Test scenarios | `src/test/java/FeatureFiles/` |
| Test logic | `src/test/java/stepDefinitions/` |
| Page element locators | `src/main/java/POM/` |
| Latest HTML report | `test-output/_latest/reports/` |
| Latest screenshots | `test-output/_latest/screenshots/` |
| Latest logs | `test-output/_latest/logs/` |

### Feature file tags

| Tag | Meaning |
|-----|---------|
| `@smoke` | Critical path — run before every deployment |
| `@regression` | Full regression — run before every release |
| `@users` | User module only |
| `@labs` | Lab module only |
| `@login` | Login module only |
| `@positive` | Tests expected to succeed |
| `@negative` | Tests expected to fail (validation errors) |
| `@TC1` … `@TC20` | Individual test case number |

### Step definition prefixes

| Cucumber keyword | Used for |
|-----------------|----------|
| `Given` | Setup / precondition (e.g., open browser, login) |
| `When` | Action (e.g., click button, fill form) |
| `And` | Continuation of previous action or check |
| `Then` | Validation / assertion (e.g., verify message shown) |

---

## Need Help?

1. **Check the logs first** — `test-output/_latest/logs/test-execution.log` contains every action the framework took
2. **Check the screenshot** — `test-output/_latest/screenshots/` shows exactly what the browser saw when it failed
3. **Check the healing report** — `test-output/_latest/reports/healing_report.json` shows whether AI tried to fix a broken locator
4. **Re-run the single failing TC** — isolate the problem before investigating

---

*Framework built with: Java 11 · Playwright · Cucumber · TestNG · Apache POI · Google Gemini AI*
