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

   This fork introduces headless project generation and CI/CD automation capabilities, making libGDX setup usable in pipelines such as GitHub Actions.

🔹 HeadlessSetup
   - New class HeadlessSetup allows generating libGDX projects without UI.
   - Integrates with VariantsCatalog for pre‑configured architectures.
   - Parameters supported: --variant, --dir, --name, --package, --mainClass, --language.
   - Cleans output directory before generation to avoid conflicts in CI runners.
   - Prints [SUCCESS] Project generated at: <dir> upon completion.

🔹 VariantsCatalog
   - Central catalog of pre‑defined variants.
   - Keys: only-desktop-basic, only-android-basic, mobile-basic, desktop-box2d-freetype, desktop-ashley-ai, all-all.

🔹 GitHub Actions Workflow
   - Workflow file: .github/workflows/libgdx-factory.yml.
   - Manual trigger with inputs (projectname, packagename, generate_all).
   - Generates all variants or just only-desktop-basic.
   - Publishes report in Job Summary and uploads artifacts.

📦 Example Usage
   ```bash
   java -cp publish/gdx-setup.jar:bin com.badlogic.gdx.setup.HeadlessSetup \
     --variant only-desktop-basic --dir MyDesktopGame --name MyDesktopGame
   ```

   ---

🚀 New Features & Infrastructure (v1.11.1)

   This minor update consolidates the transition to a modern CI/CD environment, internationalized code, and a refined variants catalog.

🔹 Repository Migration
   - Default branch moved from master to main.
   - Repository name updated to libgdx-setup.

🔹 VariantsCatalog (Refactored)
   - Expanded to 11 master variants.
   - New naming convention: desktop-only, mobile-basic, physics-2d-basic, smart-logic-ai, all-all.

📦 Example Usage
   ```bash
   java -cp publish/gdx-setup.jar:bin com.badlogic.gdx.setup.HeadlessSetup \
     --variant desktop-only --dir MyDesktopGame --name MyDesktopGame
   ```

   ---
For a detailed history of changes and version milestones, please see the [CHANGELOG.md](./CHANGELOG.md).
