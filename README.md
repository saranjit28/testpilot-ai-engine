# TestPilot AI Engine

TestPilot AI Engine is an internal AI-assisted automation enablement platform designed to accelerate test automation development by converting manual test cases into Gherkin scenarios and mapping them to existing automation step definitions.

The system analyzes the existing automation framework, understands reusable steps, and highlights missing steps that need to be implemented.

---

## 🎯 Objectives

- Reduce manual effort in writing automation scripts
- Reuse existing Selenium/Cucumber step definitions
- Identify missing automation coverage
- Standardize automation across projects
- Enable faster onboarding of automation engineers

---

## 🧠 Core Capabilities

- Fetch automation source code from Azure DevOps (TFS)
- Index existing Step Definitions (`*Steps.java`)
- Store and manage manual test cases
- Convert manual test cases into:
    - Gherkin Feature files
    - Reusable or new Step Definitions
- Provide AI-generated suggestions aligned with project conventions

---

## 🏗️ High-Level Architecture
[ Portal UI ]
|
v
[ TestPilot AI Engine ]
|
+--> Azure DevOps REST APIs (Repo Analysis)
|
+--> Test Case Repository
|
+--> Step Definition Index
|
+--> AI Prompt + RAG Engine


---

## 🔐 Security & Access

- Authentication via Azure DevOps Personal Access Tokens (PAT)
- No credentials stored in source code
- Project-level access control
- Admin and User roles supported

---

## 📦 Repository Structure

testpilot-ai-engine
├── src/main/java
│ ├── azure # Azure DevOps REST clients
│ ├── repoanalyzer # Repository scanning & indexing
│ ├── gherkin # Gherkin & step generators
│ ├── ai # Prompt & RAG logic
│ └── runner # Entry points
├── src/main/resources
├── pom.xml
├── README.md
└── tech-stack.md

1. Clone repository from Azure DevOps
2. Configure PAT in environment variable
3. Build using Maven
4. Run repository analyzer
5. Generate Gherkin scenarios

┌──────────────────────────┐
│ TestPilot AI Portal (UI) │  ← React / Angular
└──────────┬───────────────┘
│ REST API
┌──────────▼───────────────┐
│ TestPilot AI Backend     │  ← Spring Boot
│                          │
│ 1. TestCase Parser       │
│ 2. Gherkin Generator     │
│ 3. Step Matcher (AI)     │
│ 4. Missing Step Finder  │
│ 5. Java Preview Builder │
│ 6. Export Engine        │
└──────────┬───────────────┘
│
┌──────────▼───────────────┐
│ Existing Automation Repo │  ← Azure DevOps
│ (Steps you already read) │
└──────────────────────────┘
