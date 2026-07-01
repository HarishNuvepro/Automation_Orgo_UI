# Smart Browser Flow — User Guide

> **What is it?**
> A tool that watches you use the Orgo web application, then automatically
> writes a Cucumber test file for you. No coding required to create the test.
> You only write Java code for brand-new actions that no existing test covers.

---

## Table of Contents

1. [How It Works — Simple Explanation](#1-how-it-works--simple-explanation)
2. [Folder Structure](#2-folder-structure)
3. [Step-by-Step Usage](#3-step-by-step-usage)
4. [Reading the Report](#4-reading-the-report)
5. [After Generation — What to Do Next](#5-after-generation--what-to-do-next)
6. [Re-Converting a Recording](#6-re-converting-a-recording)
7. [Running a Generated Test](#7-running-a-generated-test)
8. [How to Remove SmartFlow Completely](#8-how-to-remove-smartflow-completely)
9. [Troubleshooting](#9-troubleshooting)
10. [Quick Reference](#10-quick-reference)

---

## 1. How It Works — Simple Explanation

Think of it as a **screen recorder for tests**, but instead of a video it records your clicks and keystrokes, then asks Gemini AI to write the test steps.

```
You click around in Orgo          Recorder captures JSON         Gemini converts to test
        │                                  │                              │
        ▼                                  ▼                              ▼
 [Open Roles page]            { action:"click",              Scenario: Create a role
 [Click Create Role]            id:"createRoleBtn",            Given open the browser
 [Type "Support Agent"]         text:"Create Role" }           And login as tenant admin
 [Click Save]                 { action:"fill",                 When navigate to Roles tab
 [See success message]          label:"Role Name",             And enter role name as "..."
                                value:"Support Agent" }        When click on Save button
                                                               Then validate "Role created"
```

The output is a real `.feature` file saved directly into your project. The existing tests are completely untouched.

---

## 2. Folder Structure

```
SmartFlow/ (ALL new files — existing framework unchanged)
│
├── src/main/java/SmartFlow/
│   ├── SmartFlowEngine.java          ← Main entry point (run this)
│   ├── model/BrowserAction.java      ← Data model for one captured action
│   ├── recorder/ActionRecorder.java  ← Injects JS, collects browser events
│   ├── converter/
│   │   ├── PromptBuilder.java        ← Builds the Gemini AI prompt
│   │   └── GeminiFeatureConverter.java ← Calls Gemini API
│   ├── generator/
│   │   ├── FeatureFileWriter.java    ← Writes the .feature file
│   │   └── StepMatcher.java         ← Checks which steps already exist
│   └── report/SmartFlowReport.java  ← Console + JSON report
│
├── src/main/resources/SmartFlow/
│   └── recorder_script.js            ← JavaScript injected into browser
│
├── src/test/java/stepDefinitions/
│   └── SmartFlowStep.java            ← Generic steps for generated files
│
├── src/test/java/FeatureFiles/
│   └── generated/                    ← Generated .feature files land here
│       └── roles_20250610_143022.feature  (example)
│
└── smart-flow/
    ├── recordings/   ← Raw JSON saved after each recording session
    └── reports/      ← JSON reports from each generation run
```

---

## 3. Step-by-Step Usage

### Prerequisites

- `.env` file with `GEMINI_API_KEY` set (same key used for self-healing)
- Maven installed and `mvn --version` works in terminal

---

### Step 1 — Open a terminal in the project root

```
cd C:\Users\HarishKumar\Downloads\Automation_Orgo\Automation_Orgo_UI_Test\Automation_Orgo_UI_Test
```

---

### Step 2 — Run the record command

```bash
mvn compile exec:java -Dexec.mainClass="SmartFlow.SmartFlowEngine" -Dexec.args="record roles_creation"
```

Replace `roles_creation` with any name that describes your session (no spaces).

You will see:

```
╔══════════════════════════════════════════════════════╗
║         SMART BROWSER FLOW — RECORDER               ║
╠══════════════════════════════════════════════════════╣
║  Session/Module : roles_creation                    ║
╚══════════════════════════════════════════════════════╝

  🟢  Browser is open and recording has started.
  👉  Perform your actions in the browser now.
  ⏹   When finished, come back here and press ENTER.

  > Press ENTER to stop recording:
```

---

### Step 3 — Perform your actions in the browser

A Chrome browser window opens automatically and navigates to the Orgo URL.

Do what a tester would do manually:
- Click the Organization menu
- Click Roles
- Click Create Role
- Fill in the Role Name field
- Select permissions
- Click Save
- Verify the success message

Every click and every field fill is being silently recorded.

---

### Step 4 — Press ENTER in the terminal

Come back to the terminal and press ENTER.

```
  ✅  Recording stopped — 14 actions captured.
  💾  Raw actions saved → smart-flow/recordings/roles_creation.json

  Enter module name (e.g. roles, teams, approvals): roles
```

Type the module name (e.g. `roles`) and press ENTER.

---

### Step 5 — Wait for Gemini

```
  ⏳  Sending to Gemini AI — this may take a few seconds...

  ──── GENERATED FEATURE FILE ───────────────────────────────
  @roles @TC1 @regression
  Feature: Roles Management

  Scenario: Create a new role with permissions
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to "Roles" tab
    And click on "Create Role" button
    And enter role name as "Support Agent"
    And select permission "View Users"
    When click on save button
    Then validate "Role created successfully" message is displayed
    And click on logout
    Then validate login page is displayed
  ────────────────────────────────────────────────────────────
```

---

### Step 6 — Read the report

```
╔══════════════════════════════════════════════════════════════╗
║  SMART BROWSER FLOW — GENERATION REPORT                     ║
╠══════════════════════════════════════════════════════════════╣
║  Module       : roles                                       ║
║  Feature File : roles_20250610_143022.feature               ║
║  Total Steps  : 9                                           ║
║  ✅ Reused    : 5  (no new Java code needed)                ║
║  ❌ New       : 4  (need Java step definitions)             ║
║  Reuse Rate   : 56%                                         ║
╠══════════════════════════════════════════════════════════════╣
║  ✅ REUSED STEPS (Java code already exists):                ║
║     • Given open the browser and enter the Url              ║
║     • And login as tenant admin                             ║
║     • When click on save button                             ║
║     • And click on logout                                   ║
║     • Then validate login page is displayed                 ║
╠══════════════════════════════════════════════════════════════╣
║  ❌ NEW STEPS (write Java code for these):                  ║
║     • When navigate to "Roles" tab                          ║
║     • And click on "Create Role" button                     ║
║     • And enter role name as "Support Agent"                ║
║     • Then validate "Role created..." message is displayed  ║
╠══════════════════════════════════════════════════════════════╣
║  NEXT STEPS:                                                ║
║  1. Create stepDefinitions/roles.java                       ║
║  2. Create POM/RolesPage.java                               ║
║  3. Add lazy getter to Util/Pages.java                      ║
║  4. Run: mvn test -Dcucumber.filter.tags="@roles"           ║
╚══════════════════════════════════════════════════════════════╝
```

---

## 4. Reading the Report

| Section | What it means |
|---------|---------------|
| **✅ Reused** | These steps already have Java code. Zero work needed. |
| **❌ New** | These steps need new Java code. The report tells you exactly which ones. |
| **Reuse Rate** | How much of the test already existed. Gets higher with each new module. |

> **Note:** Some of the ❌ New steps might actually be covered by the generic steps
> in `SmartFlowStep.java` (like `click on {string} button`, `navigate to {string} tab`,
> `validate {string} message is displayed`). Try running the test first before writing
> new Java code — it may already work.

---

## 5. After Generation — What to Do Next

### Option A — Test might already run (try first)

```bash
mvn test -Dcucumber.filter.tags="@roles"
```

If it passes — you are done. No new code needed.

### Option B — Some steps need Java code

For each ❌ New step in the report:

**1. Create the step definition file**

`src/test/java/stepDefinitions/Roles.java`

```java
package stepDefinitions;

import Generic_Utility.WaitUtils;
import Util.Pages;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class Roles {

    @When("navigate to {string} tab")
    public void navigate_to_tab(String tabName) throws Throwable {
        // already handled by SmartFlowStep.java — no code needed here
    }

    @And("enter role name as {string}")
    public void enter_role_name(String roleName) throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.fill(Pages.getRolesPage().getRoleNameField(), roleName, "role name");
    }
}
```

**2. Create the POM file**

`src/main/java/POM/RolesPage.java`

```java
package POM;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class RolesPage {
    private Page page;

    public RolesPage(Page page) { this.page = page; }

    public Locator getRoleNameField() { return page.locator("#roleName"); }
    public Locator getCreateRoleBtn() { return page.locator("#createRoleBtn"); }
    public Locator getSaveBtn()       { return page.locator("#saveRole"); }
}
```

**3. Add a getter to Pages.java**

```java
// In src/main/java/Util/Pages.java — add field:
private RolesPage rolesPage;

// Add getter:
public static RolesPage getRolesPage() {
    Pages p = TL.get();
    if (p.rolesPage == null) p.rolesPage = new RolesPage(p.page);
    return p.rolesPage;
}
```

**4. Run the test**

```bash
mvn test -Dcucumber.filter.tags="@roles"
```

---

## 6. Re-Converting a Recording

Raw JSON recordings are saved in `smart-flow/recordings/`. You can re-convert
any recording at any time — useful if you want to tweak the module name or
regenerate after Gemini produces better output.

```bash
mvn compile exec:java \
  -Dexec.mainClass="SmartFlow.SmartFlowEngine" \
  -Dexec.args="convert smart-flow/recordings/roles_creation.json roles"
```

This will:
1. Read the saved JSON
2. Send to Gemini again
3. Write a new `.feature` file (timestamped, so old one is not overwritten)
4. Show the step match report again

---

## 7. Running a Generated Test

Generated feature files are in `src/test/java/FeatureFiles/generated/` and use
the same tags as hand-written tests.

```bash
# Run by module tag
mvn test -Dcucumber.filter.tags="@roles"

# Run as part of full regression
mvn test -P regression

# Run a specific TC
mvn test -Dcucumber.filter.tags="@TC1 and @roles"
```

---

## 8. How to Remove SmartFlow Completely

Delete only these — **nothing else changes**:

```
src/main/java/SmartFlow/                     ← all SmartFlow Java code
src/main/resources/SmartFlow/                ← recorder_script.js
src/test/java/stepDefinitions/SmartFlowStep.java
src/test/java/FeatureFiles/generated/        ← generated feature files
smart-flow/                                  ← recordings + reports
SMART_FLOW_GUIDE.md                          ← this file
```

Every existing test, step definition, POM, runner, and configuration file
is completely unmodified. Deleting the above restores the framework to
its exact original state.

---

## 9. Troubleshooting

### "Gemini API key must not be empty"

Your `.env` file is missing `GEMINI_API_KEY`. Add it:
```
GEMINI_API_KEY=AIzaSy...
```

### "recorder_script.js not found on classpath"

Run `mvn compile` before the exec command:
```bash
mvn compile exec:java -Dexec.mainClass="SmartFlow.SmartFlowEngine" ...
```

### Generated feature file has CSS selectors in step text

Gemini occasionally ignores the prompt rules. Re-run the convert command —
Gemini is non-deterministic and produces cleaner output on a second attempt:
```bash
mvn compile exec:java -Dexec.mainClass="SmartFlow.SmartFlowEngine" \
  -Dexec.args="convert smart-flow/recordings/<file>.json <module>"
```

### Recording captured too many/too few actions

**Too many:** The recorder captures every click including navigation menu items.
This is intentional. Gemini merges related clicks into logical steps.

**Too few:** Make sure to change field values (not just click them) — the
recorder uses the `change` event, which fires when focus leaves the field.

### Steps in generated feature file fail to match existing definitions

The `StepMatcher` uses pattern matching. If Gemini adds slightly different
wording, open the generated `.feature` file and manually edit the step text
to match the exact phrase already in a step definition file.

---

## 10. Quick Reference

### Commands

```bash
# Record a new session
mvn compile exec:java -Dexec.mainClass="SmartFlow.SmartFlowEngine" -Dexec.args="record <name>"

# Re-convert an existing recording
mvn compile exec:java -Dexec.mainClass="SmartFlow.SmartFlowEngine" -Dexec.args="convert smart-flow/recordings/<file>.json <module>"

# Run a generated test
mvn test -Dcucumber.filter.tags="@<module>"
```

### Output locations

| Output | Location |
|--------|----------|
| Raw action JSON | `smart-flow/recordings/<session>.json` |
| Generated feature file | `src/test/java/FeatureFiles/generated/<module>_<timestamp>.feature` |
| Generation report (JSON) | `smart-flow/reports/<module>_<timestamp>_report.json` |

### Files added by SmartFlow (safe to delete together)

| File/Folder | Purpose |
|-------------|---------|
| `src/main/java/SmartFlow/` | All SmartFlow Java source |
| `src/main/resources/SmartFlow/recorder_script.js` | JS injected into browser |
| `src/test/java/stepDefinitions/SmartFlowStep.java` | Generic Cucumber steps |
| `src/test/java/FeatureFiles/generated/` | Auto-generated feature files |
| `smart-flow/` | Recordings + reports |
| `SMART_FLOW_GUIDE.md` | This guide |

---

*Smart Browser Flow is powered by Google Gemini 2.0 Flash + Playwright*
