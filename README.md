## libgdx-setup
This repository is a copy of [this one](https://github.com/libgdx/libgdx/tree/master/extensions/gdx-setup), however, with some peculiarities, such as the use of **Java 8** as default and the [libgdx-common](https://github.com/kendaozinho/libgdx-common) library.

Libgdx gradle setup
===================

Modular setup powered by gradle, allowing any combination of sub projects and official extensions to get you up and running in a few clicks.  Although this tool will handle setup for you, LEARN GRADLE!

![Setup Ui](http://i.imgur.com/M1e5TLU.png)

Example of use:

```java
DependencyBank bank = new DependencyBank();

ProjectBuilder builder = new ProjectBuilder();
List<ProjectType> modules = new ArrayList<ProjectType>();
modules.add(ProjectType.CORE);
modules.add(ProjectType.DESKTOP);
modules.add(ProjectType.ANDROID);
modules.add(ProjectType.IOS);
// Gwt has no friends
//modules.add(ProjectType.GWT);

List<Dependency> dependencies = new ArrayList<Dependency>();
dependencies.add(bank.getDependency(ProjectDependency.GDX));
dependencies.add(bank.getDependency(ProjectDependency.BULLET));
dependencies.add(bank.getDependency(ProjectDependency.FREETYPE));

List<String> incompatList = builder.buildProject(modules, dependencies);
//incompatList is a list of strings if there are incompatibilities found.
// The setup ui checks for these and pops up a dialog.
```

The builder will generate the settings.gradle, build.gradle file, as well as alter all the platform specific files that reference dependencies/assets.

Files Altered:
* settings.gradle
* build.gradle
* GdxDefinition.gwt.xml
* GdxDefinitionSuperDev.gwt.xml
* robovm.xml
* desktop/build.gradle (for eclipse task)

Modular setup classes
=====================

* BuildScriptHelper - Helper class for writing the build.gradle script to file.
* Dependency - Holds all the information for a dependency for all platforms
* ProjectBuilder - The project builder, manages the writers and temporary files
* DependencyBank - The bank for all supported sub modules, and dependencies.  Project repositories and plugin versions are defined here.

Assets
======

android/assets is the assets directory, however if there is no android project selected, it will configure the project to use core/assets

Run project
===========

```
$ gradle run
```

Generate .jar file (inside [this folder](./build/libs/))
========================================================

```
$ gradle build
```

Release
=======

After each release, [this file](./publish/gdx-setup.jar) is updated.

To open it, just execute this command:

```
$ java -jar ./publish/gdx-setup.jar
```

---


🚀 New Features (Headless Automation & CI/CD) v1.11.0
==================================================

This fork introduces headless project generation and CI/CD automation capabilities, making libGDX setup usable in pipelines such as GitHub Actions.

🔹 HeadlessSetup
- New class HeadlessSetup allows generating libGDX projects without UI.
- Integrates with VariantsCatalog for pre‑configured architectures.
- Parameters supported:
  - --variant → select technical variant.
  - --dir → output directory.
  - --name → project name.
  - --package → base package.
  - --mainClass → main class.
  - --language → language (Java, Kotlin, etc.).
- Cleans output directory before generation to avoid conflicts in CI runners.
- Prints [SUCCESS] Project generated at: <dir> upon completion.

🔹 VariantsCatalog
- Central catalog of pre‑defined variants.
- Each variant maps a unique key to platforms + extensions.
- Available keys:
  - only-desktop-basic → Desktop only (basic).
  - only-android-basic → Android only (basic).
  - mobile-basic → Mobile (Android + iOS).
  - desktop-box2d-freetype → Desktop advanced (Box2D + FreeType).
  - desktop-ashley-ai → Desktop ECS (Ashley + AI).
  - all-all → Stress test (all platforms + extensions).
- Utility methods:
  - getVariant(key) → retrieve variant by key.
  - getVariantNames() → list all available keys.

🔹 GitHub Actions Workflow
- Workflow file: .github/workflows/libgdx-factory.yml.
- Manual trigger (workflow_dispatch) with inputs:
  - project_name → application name.
  - package_name → Java package.
  - generate_all → boolean to generate all catalog variants.
- Steps:
  1. Checkout repository.
  2. Setup JDK 8 (Temurin).
  3. Compile setup engine.
  4. Generate projects:
     - All variants if generate_all = true.
     - Only only-desktop-basic otherwise.
  5. Publish generation report in Job Summary.
  6. Upload artifacts (dist/) with all generated projects.

🔹 Benefits
- Full automation of libGDX project generation.
- Modular catalog for easy variant management.
- Seamless integration with CI/CD pipelines.
- No graphical interface required.

---

📦 Example Usage (Headless)

Generate a basic Desktop project:

```bash
java -cp publish/gdx-setup.jar:bin com.badlogic.gdx.setup.HeadlessSetup \
  --variant only-desktop-basic --dir MyDesktopGame --name MyDesktopGame
```

Generate a full project with all platforms and extensions:

```bash
java -cp publish/gdx-setup.jar:bin com.badlogic.gdx.setup.HeadlessSetup \
  --variant all-all --dir MyFullGame --name MyFullGame
```
