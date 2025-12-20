package com.badlogic.gdx.setup;

import com.badlogic.gdx.setup.DependencyBank.ProjectDependency;
import com.badlogic.gdx.setup.DependencyBank.ProjectType;
import java.util.*;

/**
 * Catalog of pre-defined project variants for libGDX.
 * Maps unique keys to specific combinations of platforms and extensions.
 * Updated for v1.11.x - Internationalized and complete.
 */
public class VariantsCatalog {

    public static class Variant {
        public String name;
        public List<ProjectType> platforms;
        public List<ProjectDependency> extensions;

        public Variant(String name, List<ProjectType> platforms, List<ProjectDependency> extensions) {
            this.name = name;
            this.platforms = platforms;
            this.extensions = extensions;
        }
    }

    private static final Map<String, Variant> catalog = new HashMap<>();

    static {
        // --- 1. Desktop Only ---
        catalog.put("desktop-only", new Variant(
            "Desktop Only (Basic)",
            Arrays.asList(ProjectType.CORE, ProjectType.DESKTOP),
            Arrays.asList(ProjectDependency.GDX)
        ));

        // --- 2. Mobile Basic ---
        catalog.put("mobile-basic", new Variant(
            "Mobile Starter (Android + iOS)",
            Arrays.asList(ProjectType.CORE, ProjectType.ANDROID, ProjectType.IOS),
            Arrays.asList(ProjectDependency.GDX, ProjectDependency.FREETYPE)
        ));

        // --- 3. Web Starter ---
        catalog.put("web-starter", new Variant(
            "Web Starter (HTML)",
            Arrays.asList(ProjectType.CORE, ProjectType.HTML),
            Arrays.asList(ProjectDependency.GDX)
        ));

        // --- 4. Physics 2D Basic ---
        catalog.put("physics-2d-basic", new Variant(
            "Physics 2D Pack (Box2D)",
            Arrays.asList(ProjectType.CORE, ProjectType.DESKTOP, ProjectType.ANDROID),
            Arrays.asList(ProjectDependency.GDX, ProjectDependency.BOX2D, ProjectDependency.BOX2DLIGHTS)
        ));

        // --- 5. Physics 3D Pro ---
        catalog.put("physics-3d-pro", new Variant(
            "Physics 3D Pro (Bullet)",
            Arrays.asList(ProjectType.CORE, ProjectType.DESKTOP, ProjectType.ANDROID),
            Arrays.asList(ProjectDependency.GDX, ProjectDependency.BULLET)
        ));

        // --- 6. UI Heavy Suite ---
        catalog.put("ui-heavy-suite", new Variant(
            "UI Heavy Suite (Tools + Controllers)",
            Arrays.asList(ProjectType.CORE, ProjectType.DESKTOP, ProjectType.ANDROID),
            Arrays.asList(ProjectDependency.GDX, ProjectDependency.FREETYPE, ProjectDependency.TOOLS, ProjectDependency.CONTROLLERS)
        ));

        // --- 7. ECS Core Ashley ---
        catalog.put("ecs-core-ashley", new Variant(
            "ECS Core (Ashley)",
            Arrays.asList(ProjectType.CORE, ProjectType.DESKTOP),
            Arrays.asList(ProjectDependency.GDX, ProjectDependency.ASHLEY)
        ));

        // --- 8. Smart Logic AI ---
        catalog.put("smart-logic-ai", new Variant(
            "Smart Logic (Ashley + AI)",
            Arrays.asList(ProjectType.CORE, ProjectType.DESKTOP),
            Arrays.asList(ProjectDependency.GDX, ProjectDependency.ASHLEY, ProjectDependency.AI)
        ));

        // --- 9. Full Platform Stack ---
        catalog.put("full-platform-stack", new Variant(
            "Full Platform Stack (All OS)",
            Arrays.asList(ProjectType.CORE, ProjectType.DESKTOP, ProjectType.ANDROID, ProjectType.IOS, ProjectType.HTML),
            Arrays.asList(ProjectDependency.GDX)
        ));

        // --- 10. Creative Sandbox ---
        catalog.put("creative-sandbox", new Variant(
            "Creative Sandbox (Mixed Essentials)",
            Arrays.asList(ProjectType.CORE, ProjectType.DESKTOP, ProjectType.ANDROID),
            Arrays.asList(ProjectDependency.GDX, ProjectDependency.FREETYPE, ProjectDependency.BOX2D, ProjectDependency.ASHLEY)
        ));

        // --- 11. All-in-One Ultimate ---
        catalog.put("all-all", new Variant(
            "Ultimate Full Stack (Everything)",
            Arrays.asList(ProjectType.CORE, ProjectType.DESKTOP, ProjectType.ANDROID, ProjectType.IOS, ProjectType.HTML),
            Arrays.asList(ProjectDependency.GDX, ProjectDependency.BULLET, ProjectDependency.FREETYPE, 
                          ProjectDependency.TOOLS, ProjectDependency.CONTROLLERS, ProjectDependency.BOX2D, 
                          ProjectDependency.BOX2DLIGHTS, ProjectDependency.AI, ProjectDependency.ASHLEY)
        ));
    }

    public static Variant getVariant(String key) {
        return catalog.get(key);
    }

    public static Set<String> getVariantNames() {
        return catalog.keySet();
    }
}